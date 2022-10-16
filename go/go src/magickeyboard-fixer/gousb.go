package main

import (
	"fmt"
	"github.com/google/gousb"
	//"github.com/google/gousb/usbid"
)

func runUsb() error {
	gousb
	fmt.Println("hello")
	return nil
}

/*
https://www.reddit.com/r/computers/comments/58o4pe/whats_the_equivalent_of_lsusb_v_on_windows/
https://dev.to/davidsbond/golang-reverse-engineering-an-akai-mpd26-using-gousb-3b49
https://github.com/google/gousb
https://github.com/google/gousb/issues/69
 */
