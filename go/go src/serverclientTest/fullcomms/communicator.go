package main

import (
	"bufio"
	"flag"
	"fmt"
	"log"
	"os"
	"os/signal"
	"strconv"
	"strings"
	"syscall"
	"time"

	"github.com/schollz/peerdiscovery"
)

var (
	isClient      = flag.Bool("c", false, "is program a client or server")
	discoveryPort = flag.String("port", "7734", "lan discovery port")
	websocketPort = flag.String("wsPort", "", "(optional) set a port for the websocket to use")
)

const version = 1
const identifier = "COMMUNICATOR"

func serverSettings(id []byte) peerdiscovery.Settings {
	return peerdiscovery.Settings{
		Port:      *discoveryPort,
		Limit:     -1,
		Delay:     1 * time.Second,
		TimeLimit: -1,
		StopChan:  stopDiscovery,
		Payload:   id,
	}
}

//todo command corrospondence
func clientSettings(discover func(d peerdiscovery.Discovered)) peerdiscovery.Settings {
	return peerdiscovery.Settings{
		Port:             *discoveryPort,
		Limit:            -1,
		Delay:            500 * time.Millisecond,
		TimeLimit:        5 * time.Second,
		DisableBroadcast: true,
		AllowSelf:        true, //
		StopChan:         stopDiscovery,
		Notify:           discover,
	}
}

var stopDiscovery chan struct{}

func main() {
	flag.Parse()
	log.SetFlags(0)

	interruptChan := make(chan os.Signal, 1)
	signal.Notify(interruptChan, os.Interrupt, syscall.SIGINT, syscall.SIGTERM)

	Commands["server"] = func([]string) string {
		log.Println("starting server")
		go Server(*websocketPort)
		<-interruptChan
		CleanupServer()
		return "DONE"
	}

	prompt := "> "
	r, m := Reader(&prompt)
	var connectionOptions []string

	Commands["connect"] = func(args []string) string {
		if len(args) == 0 {
			return "index or address needed"
		}

		var addr string
		idx, err := strconv.Atoi(args[0])
		if err != nil {
			addr = args[0]
		} else {
			if len(connectionOptions) == 0 {
				return "local connection not populated"
			}
			if idx < 0 && len(connectionOptions) <= idx {
				return "index given is out of range"
			}
			addr = connectionOptions[idx]
		}

		c, err := Connect(addr)
		if err != nil {
			return fmt.Sprintf("could not connect: %s", err)
		}

		c.Start(func(message []byte) {
			log.Printf("recv: %s", message)
		})
		//c.Write("HULLO")

		defer func() {
			c.Stop()
			prompt = "> "
		}()
		prompt = "< "

		r <- true
		for {
			select {
			case <-interruptChan:
				return "DONE"
			case <-c.done:
				return "DONE"
			case s := <-m:
				if s == "exit" {
					return "DONE"
				}
				if len(s) == 0 {
					continue
				}
				c.Write(s)
				r <- true
			}
		}
	}
	Commands["scan"] = func([]string) string {
		discoveries = make(map[string]peerdiscovery.Discovered)
		stopDiscovery = make(chan struct{})

		_, err := peerdiscovery.Discover(clientSettings(Discovered))
		if err != nil {
			log.Fatalln(err)
		}

		connectionOptions = make([]string, len(discoveries))
		i := 0
		for aPort, d := range discoveries {
			fmt.Printf("%d) %s\n", i, d)
			port := strings.SplitN(aPort, " ", 2)[0]
			connectionOptions[i] = fmt.Sprintf("%s:%s", d.Address, port)
			i++
		}
		return "DONE"
	}

	if *isClient {
		for {
			r <- true
			select {
			case <-interruptChan:
				os.Exit(0)
			case s := <-m:
				fmt.Println(CommandProcessor(s))
			}
		}
	} else {
		Commands["server"]([]string{})
	}
}

func Reader(deliminator ...*string) (readyForMessage chan bool, messenger chan string) {
	messenger = make(chan string)
	readyForMessage = make(chan bool)

	scanner := bufio.NewScanner(os.Stdin)

	var delim *string
	if len(deliminator) > 0 {
		delim = deliminator[0]
	} else {
		d := "> "
		delim = &d
	}

	go func() {
		for {
			<-readyForMessage
			fmt.Printf(*delim)
			scanner.Scan()
			line := scanner.Text()
			if err := scanner.Err(); len(line) == 0 && err != nil {
				log.Fatal(err)
			}
			messenger <- line
		}
	}()
	return
}
