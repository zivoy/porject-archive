//https://ops.tips/blog/udp-client-and-server-in-go
//https://github.com/cirocosta/go-sample-udp/blob/master/main.go

package main

import (
	"fmt"
	"net"
	"strings"
	"time"
)

const maxBufferSize = 32
const timeout = 1 * time.Second

func main() {
	BROADCAST_IPv4 := net.IPv4(255, 255, 255, 255)
	port := 8829
	list, err := net.ListenUDP("udp", &net.UDPAddr{Port: port})
	if err != nil{
		panic(err)
	}
	defer list.Close()

	_, err = list.WriteToUDP([]byte("SERVERTEST - scanning"), &net.UDPAddr{IP: BROADCAST_IPv4, Port: port})
	if err != nil {
		panic(err)
	}

	buffer := make([]byte, maxBufferSize)

	deadline := time.Now().Add(timeout)
	err = list.SetReadDeadline(deadline)
	if err != nil {
		panic(err)
	}

	for {
		n, addr, err := list.ReadFrom(buffer)
		if addr == nil{
			break
		}

		ifaces, err := net.Interfaces()

		if err != nil {
			panic(err)
		}
		me := false
		for _, i := range ifaces {
			addrs, err := i.Addrs()
			if err != nil {
				panic(err)
			}
			for _, addr := range addrs {
				var ip net.IP
				switch v := addr.(type) {
				case *net.IPNet:
					ip = v.IP
				case *net.IPAddr:
					ip = v.IP
				}
				// process IP address
				if strings.Contains(addr.String(),ip.String()) {
					me = true
				}
			}
		}
		if me {
			continue
		}

		if err != nil {
			fmt.Println(err)
		} else {
			fmt.Printf("%s sent this: %s\n", addr, buffer[:n])
		}
	}
}
