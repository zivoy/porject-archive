// +build windows

package main

import (
	"fmt"
	"github.com/moutend/go-hook/pkg/win32"
	"os"
	"os/signal"
	"time"
	"unsafe"

	"github.com/moutend/go-hook/pkg/keyboard"
	"github.com/moutend/go-hook/pkg/types"
)

func customHook(c chan<- types.KeyboardEvent) types.HOOKPROC {
	return func(code int32, wParam, lParam uintptr) uintptr {
		if lParam != 0 {
			c <- types.KeyboardEvent{
				Message:  types.Message(wParam),
				KBDLLHOOKSTRUCT: *(*types.KBDLLHOOKSTRUCT)(unsafe.Pointer(lParam)),
			}
		}

		return win32.CallNextHookEx(0, code, wParam, lParam)
	}
}

func runHook() error {
	// Buffer size is depends on your need. The 100 is placeholder value.
	keyboardChan := make(chan types.KeyboardEvent, 100)



	if err := keyboard.Install(customHook, keyboardChan); err != nil {
		return err
	}

	defer keyboard.Uninstall()

	signalChan := make(chan os.Signal, 1)
	signal.Notify(signalChan, os.Interrupt)

	fmt.Println("start capturing keyboard input")

	for {
		select {
		case <-time.After(5 * time.Minute):
			fmt.Println("Received timeout signal")
			return nil
		case <-signalChan:
			fmt.Println("Received shutdown signal")
			return nil
		case k := <-keyboardChan:
			//fmt.Printf("Received %v %v\n", k.Message, k.VKCode)
			fmt.Println(k)
			continue
		}
	}
}
