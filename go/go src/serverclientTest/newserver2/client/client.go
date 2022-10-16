package main

import (
	"fmt"
	"serverclientTest"
	"time"
)

func main() {
	_, err := serverclientTest.GlobalBroadcast("HULLO", serverclientTest.Port)
	if err != nil {
		panic(err)
	}
	listener, err := serverclientTest.SubscribeUDP("", serverclientTest.RespondPort)
	if err != nil {
		panic(err)
	}
	listener.Timeout = 5 * time.Second
	ch, err := listener.ReadTimed()
	if err != nil {
		panic(err)
	}
	for i := range ch {
		fmt.Printf("%s sent: %s", i.Addr.String(), i.Data)
	}
	listener.Close()
}
