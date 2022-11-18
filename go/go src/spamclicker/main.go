package main

import (
	"time"

	"github.com/hajimehoshi/ebiten/v2"

	//"github.com/hajimehoshi/ebiten/v2/inpututil"
	"image/color"

	"github.com/go-vgo/robotgo"
	"github.com/kindlyfire/go-keylogger"
	log "github.com/sirupsen/logrus"
)

const (
	width  = 100
	height = width

    cps = 100
)

var (
	image  = ebiten.NewImage(width, height)
	state1 bool
)

func init() {
	image.Fill(color.White)
}

type keys struct {
	x16  int
	y16  int
	side bool
}

func (m *keys) Layout(_, _ int) (int, int) {
	return width, height
}

func (m *keys) Update() error {
	//    if inpututil.IsKeyJustPressed(ebiten.KeyJ){
	//        m.state = !m.state
	//        log.Info(m.MousePos())
	//    }

	sw, _ := ebiten.ScreenSizeInFullscreen()
	cx, cy := ebiten.CursorPosition()

	m.y16 = 0

	if cx >= 0 && cx <= width && cy >= 0 && cy <= height {
		m.side = !m.side
		if m.side {
			m.x16 = 0
		} else {
			m.x16 = (sw - width) * 16
		}
	}
	ebiten.SetWindowPosition(m.x16/16, m.y16/16)
	return nil
}

func (m *keys) Draw(screen *ebiten.Image) {
	if state1 {
		image.Fill(color.RGBA{R: 255, A: 255})
	} else {
		image.Fill(color.White)
	}
	screen.DrawImage(image, nil)
}

func (m *keys) MousePos() (x, y int) {
	x, y = ebiten.CursorPosition()
	return m.x16/16 + x, m.y16/16 + y + 23
}

func listener() {
	kl := keylogger.NewKeylogger()
	for {
		key := kl.GetKey()
		if key.Keycode == 0 || key.Empty {
			continue
		}
		if key.Rune == 'h' || key.Rune == 'H' {
			state1 = !state1
		}
		//        log.Info(key.Keycode)
	}
}

func clicker() {
    t := time.NewTicker(time.Second/cps)
	for {
        <- t.C
        if !state1 {
			continue
		}
		robotgo.Click("left")
	}
}

func main() {
	ebiten.SetScreenTransparent(true)
	ebiten.SetWindowDecorated(false)
	ebiten.SetWindowFloating(true)
	ebiten.SetWindowSize(width, height)
	//	    log.SetOutput(os.Stdout)
	go listener()
	go clicker()
	if err := ebiten.RunGame(&keys{}); err != nil {
		log.Fatal(err)
	}
}
