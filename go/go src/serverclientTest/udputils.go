package serverclientTest

import (
	"errors"
	"fmt"
	"net"
	"strings"
	"time"
)

const (
	dialer = iota
	listener
)

const timeout = 1 * time.Second
const buffer = 1024

type UDPConn struct {
	udpType    int
	tAddr      *net.UDPAddr
	Conn       *net.UDPConn
	Timeout    time.Duration
	BufferSize int
}

func getUDPAddress(address net.IP, port int) *net.UDPAddr {
	return &net.UDPAddr{IP: address, Port: port}
}

func getIP(address string) net.IP {
	return net.ParseIP(address)
}

func getUDPFromString(address string, port int) *net.UDPAddr {
	return getUDPAddress(getIP(address), port)
}

func GetDialer(address string, port int) (*UDPConn, error) {
	addr := getUDPFromString(address, port)
	conn, err := net.DialUDP("udp", nil, addr)
	return &UDPConn{Conn: conn, udpType: dialer, Timeout: timeout, BufferSize: buffer}, err
}

func GetGlobalDialer(port int) (*UDPConn, error) {
	return GetDialer("255.255.255.255", port)
}

func GlobalBroadcast(message string, port int) (int, error) {
	conn, err := GetGlobalDialer(port)
	if err != nil {
		return -1, err
	}
	defer conn.Close()
	return fmt.Fprintf(conn, message)
}

func SubscribeUDP(address string, port int) (*UDPConn, error) {
	var addr *net.UDPAddr
	if address == "" {
		addr = getUDPAddress(nil, port)
	} else {
		addr = getUDPFromString(address, port)
	}
	conn, err := net.ListenUDP("udp", addr)
	return &UDPConn{Conn: conn, udpType: dialer, Timeout: timeout, BufferSize: buffer}, err
}

func (conn *UDPConn) Write(b []byte) (int, error) {
	switch conn.udpType {
	case dialer:
		return conn.Conn.Write(b)
	case listener:
		if conn.tAddr == nil {
			return -1, errors.New("address needed")
		}
		defer func() {
			conn.tAddr = nil
		}()
		return conn.Conn.WriteToUDP(b, conn.tAddr)
	}
	return -1, errors.New("invalid type")
}

func (conn *UDPConn) SetTarget(addr *net.UDPAddr) *UDPConn {
	conn.tAddr = addr
	return conn
}

func (conn *UDPConn) Close() {
	conn.Conn.Close()
}

type ReadResponse struct {
	Data []byte
	Addr *net.UDPAddr
	Err  error
}

func (conn *UDPConn) setDeadline() error {
	deadline := time.Now().Add(conn.Timeout)
	return conn.Conn.SetReadDeadline(deadline)
}

func (conn *UDPConn) clearDeadline() error {
	return conn.Conn.SetReadDeadline(time.Time{})
}

func (conn *UDPConn) MakeBuffer() []byte {
	return make([]byte, conn.BufferSize)
}

func (conn *UDPConn) read(buffer []byte) (int, *net.UDPAddr, error) {
	return conn.Conn.ReadFromUDP(buffer)
}

func (conn *UDPConn) Read() ReadResponse {
	b := conn.MakeBuffer()
	n, addr, err := conn.read(b)
	return ReadResponse{Data: b[:n],Addr: addr,Err: err}
}

func (conn *UDPConn) ReadTimed() (<-chan ReadResponse, error) {
	if err := conn.setDeadline(); err != nil {
		conn.clearDeadline()
		return nil, err
	}

	ch := make(chan ReadResponse)
	go func() {
		defer func() {
			conn.clearDeadline()
			close(ch)
		}()
		for {
			buffer := conn.MakeBuffer()
			n, addr, err := conn.read(buffer)
			if err != nil {
				if strings.Contains(err.Error(), "i/o timeout") {
					return
				}
				ch <- ReadResponse{Err: err}
				return
				//return n, addr, err
			}
			ch <- ReadResponse{Data: buffer[:n], Addr: addr, Err: err}
		}
	}()
	return ch, nil
}
