package main

import (
	"fmt"
	"serverclientTest"
)

func main() {
	list, err := serverclientTest.SubscribeUDP("", serverclientTest.Port)
	if err != nil {
		panic(err)
	}
	defer list.Close()

	resps := list.Read()
	if err != nil {
		panic(err)
	}

	fmt.Printf("%s sent: %s\n", resps.Addr.String(), resps.Data)
	resp, err := serverclientTest.GetDialer(resps.Addr.IP.String(), serverclientTest.RespondPort)
	if err != nil {
		panic(err)
	}
	defer resp.Close()

	_, err = fmt.Fprintf(resp, "%s world", resps.Data)
	if err != nil {
		panic(err)
	}
}
