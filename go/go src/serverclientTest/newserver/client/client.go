package main

import (
	"fmt"
	"serverclientTest"
)

func main() {
	serverclientTest.GlobalBroadcast("HULLO", serverclientTest.Port)
	listener, err := serverclientTest.SubscribeUDP("", serverclientTest.Port)
	if err != nil {
		panic(err)
	}
	defer listener.Close()
	ch, err := listener.ReadTimed()
	if err != nil {
		panic(err)
	}
	for i := range ch {
		fmt.Printf("%s sent: %s", i.Addr.String(), i.Data)
	}
}
