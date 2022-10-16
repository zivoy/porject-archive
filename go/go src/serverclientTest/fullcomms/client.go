package main

import (
	"fmt"
	"github.com/gorilla/websocket"
	"github.com/schollz/peerdiscovery"
	"log"
	"net/url"
	"strconv"
	"strings"
	"time"
)

// port  discovery
var discoveries map[string]peerdiscovery.Discovered

func validDiscovery(d peerdiscovery.Discovered) (bool, string) {
	parts := strings.Split(string(d.Payload), " ")

	serviceId := strings.Split(parts[0], "-")
	if serviceId[0] != identifier {
		return false, ""
	}

	ver := parts[1]
	port := parts[2]

	verI, _ := strconv.Atoi(ver[1:])
	verOk := verI == version

	id := fmt.Sprintf("%s %s", port, serviceId[1])

	_, portUsed := discoveries[id]

	return verOk && !portUsed, id
}

func Discovered(d peerdiscovery.Discovered) {
	if ok, id := validDiscovery(d); ok {
		discoveries[id] = d
	}
}

type Connection struct {
	conn     *websocket.Conn
	done     chan struct{}
	messager chan string
}

//var conn *Connection

func (c *Connection) CloseConnection() {
	if c.conn == nil {
		return
	}
	err := c.conn.WriteMessage(websocket.CloseMessage, websocket.FormatCloseMessage(websocket.CloseNormalClosure, ""))
	if err != nil {
		log.Println("write close:", err)
		return
	}
	select {
	case <-c.done:
	case <-time.After(time.Second):
	}
}

//func Client() {
//	discoveries = make(map[string]peerdiscovery.Discovered)
//	stopDiscovery = make(chan struct{})
//
//	_, err := peerdiscovery.Discover(clientSettings(Discovered))
//	if err != nil {
//		log.Fatalln(err)
//	}
//
//	options := make([]string, len(discoveries))
//	i := 0
//	for aPort, d := range discoveries {
//		fmt.Printf("%d) %s\n", i, d)
//		port := strings.SplitN(aPort, " ", 2)[0]
//		options[i] = fmt.Sprintf("%s:%s", d.Address, port)
//		i++
//	}
//
//	if len(options) == 0 {
//		os.Exit(0)
//	}
//
//	var selected int
//	for {
//		fmt.Printf("> ")
//		_, err = fmt.Scan(&selected)
//		if err != nil {
//			fmt.Println("not a valid number")
//			continue
//		}
//		if selected < 0 || len(options) <= selected {
//			fmt.Printf("%d is not in range\n", selected)
//			continue
//		}
//		break
//	}
//
//	conn, err = Connect(options[selected])
//	if err != nil {
//		log.Fatal("dial:", err)
//	}
//
//	conn.Start(func(message []byte) {
//		log.Printf("recv: %s", message)
//	})
//
//	conn.Write("HULLO")
//	<-conn.done
//
//	os.Exit(0)
//}

func Connect(addr string) (*Connection, error) {
	u := url.URL{Scheme: "ws", Host: addr, Path: "/ws"}
	fmt.Println("connecting to", u.String())

	c, _, err := websocket.DefaultDialer.Dial(u.String(), nil)
	return &Connection{conn: c}, err
}

func (c *Connection) startListener(onMessage func(message []byte)) {
	defer close(c.done)
	for {
		_, message, err := c.conn.ReadMessage()
		if err != nil {
			log.Println("read:", err)
			return
		}
		onMessage(message)
	}
}

func (c *Connection) Write(message string) {
	if c.done != nil && c.messager != nil {
		c.messager <- message
	} else {
		log.Println("start first")
	}
}

func (c *Connection) startWriter() {
	c.messager = make(chan string)

	for {
		select {
		case <-c.done:
			return
		case m := <-c.messager:
			err := c.conn.WriteMessage(websocket.TextMessage, []byte(m))
			if err != nil {
				log.Println("write:", err)
				return
			}
		}
	}
}

func (c *Connection) Start(onMessage func([]byte)) {
	c.done = make(chan struct{})
	go c.startListener(onMessage)
	go c.startWriter()
}

func (c *Connection) Stop() {
	c.CloseConnection()
}
