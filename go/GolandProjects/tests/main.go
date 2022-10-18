package main

import (
	"fmt"
	"image"
	"image/png"
	"io"
	"net/http"

	// "net/http"

	// "net/url"
	"os"

	"github.com/fatih/color"

	"github.com/boombuler/barcode"
	"github.com/boombuler/barcode/qr"
)

func main() {
	// You can register another format here
	image.RegisterFormat("png", "png", png.Decode, png.DecodeConfig)
	// image.RegisterFormat("jpg", "jpg", jpg.Decode, jpg.DecodeConfig)

	data := "99999999" //url.QueryEscape("https://copy-passed.web.app/VerifyID.html#99999999")
	size := 880        //220
	// sizeS := fmt.Sprintf("%vx%v", size, size)

	// name, err := OpenURL(
	// 	"https://api.qrserver.com/v1/create-qr-code/?size=" + sizeS + "&data=" + data)
	// "http://i.imgur.com/m1UIjW1.jpg")
	qrCode, err := qr.Encode(data, qr.M, qr.Numeric)
	file, _ := barcode.Scale(qrCode, size, size)

	if err != nil {
		fmt.Println("19")
		fmt.Println("Error: Cannot download file")
		os.Exit(1)
	}

	// file, err := os.Open(name)
	if err != nil {
		fmt.Println("31")
		fmt.Println("Error: File could not be opened")
		os.Exit(1)
	}

	pixels, err := getPixels(file)

	if err != nil {
		fmt.Println("29")
		fmt.Println("Error: Image could not be decoded")
		os.Exit(1)
	}

	whiteBackground := color.New(color.FgBlack).Add(color.BgHiWhite)
	defer color.Unset()
	pix := blek(pixels)
	step := int(size / 22)
	// fmt.Println(pix[20
	//▀2580 ▄2584 █2588
	for y := 0; y < (len(pix) - step); y += step * 2 {
		for x := 0; x < len(pix[y]); x += step {
			if pix[y][x] && pix[y+step][x] { // full
				whiteBackground.Print("\u2588")
				// fmt.Print("\u2588")
			} else if !pix[y][x] && !pix[y+step][x] { // empty
				whiteBackground.Print("\u2003")
				// fmt.Print("\u2003")
			} else if pix[y][x] && !pix[y+step][x] { // top
				whiteBackground.Print("\u2580")
				// fmt.Print("\u2580")
			} else if !pix[y][x] && pix[y+step][x] { // bottom
				whiteBackground.Print("\u2584")
				// fmt.Print("\u2584")
			}
		}
		whiteBackground.Println(" ")
	}

	color.Unset()
	white := color.New(color.FgHiWhite)
	for x := 0; x < len(pix[0])+1; x += step {
		white.Print("\u2580")
	}
	// file.Close()
	// os.Remove(name)
}

func blek(in [][]Pixel) [][]bool {
	var out [][]bool
	for y := 0; y < len(in); y++ {
		var row []bool
		for x := 0; x < len(in[y]); x++ {
			var val bool
			if (in[y][x] == Pixel{0, 0, 0, 255}) { // Pixel{255, 255, 255, 255}) {
				val = true
			} else {
				val = false
			}
			row = append(row, val)
		}
		out = append(out, row)
	}
	return out
}

// Get the bi-dimensional pixel array
func getPixels(file barcode.Barcode) ([][]Pixel, error) {
	// img, _, err := image.Decode(file)
	img := file

	// if err != nil {
	// 	fmt.Println("42")
	// 	return nil, err
	// }

	bounds := img.Bounds()
	width, height := bounds.Max.X, bounds.Max.Y

	var pixels [][]Pixel
	for y := 0; y < height; y++ {
		var row []Pixel
		for x := 0; x < width; x++ {
			row = append(row, rgbaToPixel(img.At(x, y).RGBA()))
		}
		pixels = append(pixels, row)
	}

	return pixels, nil
}

// img.At(x, y).RGBA() returns four uint32 values; we want a Pixel
func rgbaToPixel(r uint32, g uint32, b uint32, a uint32) Pixel {
	return Pixel{int(r / 257), int(g / 257), int(b / 257), int(a / 257)}
}

// Pixel struct example
type Pixel struct {
	R int
	G int
	B int
	A int
}

//OpenURL gets image from url
func OpenURL(url string) (string, error) {
	// don't worry about errors
	response, e := http.Get(url)
	if e != nil {
		fmt.Println("79")
		return "nil", e
	}
	defer response.Body.Close()

	//open a file for writing
	file, err := os.Create("./tmp.png")
	if err != nil {
		fmt.Println("87")
		return "nil", err
	}
	defer file.Close()

	// Use io.Copy to just dump the response body to the file. This supports huge files
	_, err = io.Copy(file, response.Body)
	if err != nil {
		fmt.Println("95")
		return "nil", err
	}
	return "./tmp.png", nil
}
