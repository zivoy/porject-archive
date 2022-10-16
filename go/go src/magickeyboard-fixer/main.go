package main

import (
	"log"
)

func main() {
	log.SetFlags(0)
	log.SetPrefix("error: ")

	run := runUsb
	//run := runHook

	if err := run(); err != nil {
		log.Fatal(err)
	}
}