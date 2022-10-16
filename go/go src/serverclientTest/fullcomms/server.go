package main

import (
	"fmt"
	"github.com/gorilla/websocket"
	"github.com/schollz/peerdiscovery"
	"log"
	"math/rand"
	"net"
	"net/http"
	"strconv"
	"time"
)

const (
	minPort = 7000
	maxPort = 8000
)

func portIsFree(port string) bool {
	ln, err := net.Listen("tcp", ":"+port)
	if err != nil {
		return false
	}

	err = ln.Close()
	return err == nil
}

func CleanupServer() {
	if stopDiscovery != nil {
		close(stopDiscovery)
	}
}

var commId int

func Server(port string) {
	//get port
	if port == "" {
		rand.Seed(time.Now().UnixNano())
		for {
			port = strconv.Itoa(rand.Intn(maxPort-minPort) + minPort)
			if portIsFree(port) {
				break
			}
		}
	} else if _, err := strconv.Atoi(port); err == nil && !portIsFree(port) {
		log.Fatalf("'%s' is not a valid free port", port)
	}

	commId = rand.Intn(9999)

	// broadcast existence
	go func() {
		stopDiscovery = make(chan struct{})

		_, err := peerdiscovery.Discover(
			serverSettings([]byte(fmt.Sprintf("%s-%d v%d %s", identifier, commId, version, port))))
		if err != nil {
			log.Fatal(err)
		}
	}()

	http.HandleFunc("/ws", process)
	log.Fatal(http.ListenAndServe(":"+port, nil))
}

var upgrader = websocket.Upgrader{
	ReadBufferSize:  1024,
	WriteBufferSize: 1024,
}

func process(w http.ResponseWriter, r *http.Request) {
	c, err := upgrader.Upgrade(w, r, nil)
	if err != nil {
		log.Print("upgrade:", err)
		return
	}
	defer c.Close()
	for {
		mt, message, err := c.ReadMessage()
		if err != nil {
			log.Println("read:", err)
			break
		}
		log.Printf("recv: %s", message)
		err = c.WriteMessage(mt, []byte(CommandProcessor(string(message))))
		if err != nil {
			log.Println("write:", err)
			break
		}
	}
}

//func echo(w http.ResponseWriter, r *http.Request) {
//	c, err := upgrader.Upgrade(w, r, nil)
//	if err != nil {
//		log.Print("upgrade:", err)
//		return
//	}
//	defer c.Close()
//	for {
//		mt, message, err := c.ReadMessage()
//		if err != nil {
//			log.Println("read:", err)
//			break
//		}
//		log.Printf("recv: %s", message)
//		err = c.WriteMessage(mt, message)
//		if err != nil {
//			log.Println("write:", err)
//			break
//		}
//	}
//}
