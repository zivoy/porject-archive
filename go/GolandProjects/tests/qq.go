package main

import (
	"image"
	"image/png"
	"log"
	"os"

	"github.com/boombuler/barcode"
	"github.com/boombuler/barcode/qr"
)

func main() {
	base64 := "IAV19ysYSl0HUuG5QiCDvdHkowqdGXb0HbaUAWUzHw=="
	log.Println("Original data:", base64)
	code, err := qr.Encode(base64, qr.L, qr.Unicode)
	if err != nil {
		log.Fatal(err)
	}
	log.Println("Encoded data: ", code.Content())

	if base64 != code.Content() {
		log.Fatal("data differs")
	}

	code, err = barcode.Scale(code, 300, 300)
	if err != nil {
		log.Fatal(err)
	}

	writePng("test.png", code)
	log.Println(`Now open test.png and scan QR code, it will be:
"IAV19ysYSl0HUuG5QiCDvdHkowqdGXb0HbqUAWUzHw=="
 instead of
"IAV19ysYSl0HUuG5QiCDvdHkowqdGXb0HbaUAWUzHw=="
 ('a' is changed to 'q' in 'aUAWUzHw==' part)
`)
}

func writePng(filename string, img image.Image) {
	file, err := os.Create(filename)
	if err != nil {
		log.Fatal(err)
	}
	err = png.Encode(file, img)
	if err != nil {
		log.Fatal(err)
	}
	file.Close()
}
