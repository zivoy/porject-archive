//https://gamedev.stackexchange.com/a/115992

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
	buffer := make([]byte, maxBufferSize)

	pc, err := net.ListenPacket("udp", ":8829")
	if err != nil {
		panic(err)
	}
	defer pc.Close()

	n, addr, err := pc.ReadFrom(buffer)
	if err != nil {
		panic(err)
	}

	text := buffer[:n]
	fmt.Printf("%s sent this: %s\n", addr, text)

	if strings.Contains(string(text), "SERVERTEST") {
		deadline := time.Now().Add(timeout)
		err = pc.SetWriteDeadline(deadline)
		if err != nil {
			panic(err)
		}

		n, err = pc.WriteTo(text, addr)
		if err != 	nil {
			panic(err)
		}
		fmt.Printf("packet-written: bytes=%d to=%s\n", n, addr.String())
	}
}
