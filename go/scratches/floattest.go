package main

import (
	"encoding/binary"
	"fmt"
    "math"
)

var magicConst float32
func init(){
    magicConst = float32(math.Pow(2,-14))
}
func Float32frombytes(bytes []byte, magic bool) float32 {
	bits := binary.LittleEndian.Uint32(bytes)
	float := math.Float32frombits(bits)
	if magic {
        float *= magicConst
	}
	return float
}

func Float32bytes(float float32, zero, magic bool) []byte {
	if magic {
		float /= magicConst
	}
	bits := math.Float32bits(float)
	bytes := make([]byte, 4)
	binary.LittleEndian.PutUint32(bytes, bits)
	if zero {
		bytes[0] = 0
		bytes[1] = 0
	}
	return bytes
}

func convert(magic, zero bool) {
	bytes := Float32bytes(math.Pi, zero, magic)
	float := Float32frombytes(bytes, magic)
	fmt.Printf("magic: %v\tzero: %v\t| %d   \t- %f\n", magic, zero, bytes, float)
}

func main() {
    fmt.Println(magicConst)
	convert(false, false)
	convert(true, false)
	convert(false, true)
	convert(true, true)
}
