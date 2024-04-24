package main

import (
	"fmt"
	"net"
	"strconv"
)

func handleConnection(conn net.Conn, nth int) {
	defer conn.Close()

	// Read data from the client
	buf := make([]byte, 1024)
	n, err := conn.Read(buf)
	if err != nil {
		fmt.Println("Error reading:", err.Error())
		return
	}
	fmt.Println("Received data from client:", string(buf[:n]))

	// Send data back to the client
	_, err = conn.Write([]byte("Hey " + strconv.Itoa(nth) + ", Message received by server"))
	if err != nil {
		fmt.Println("Error writing:", err.Error())
		return
	}
}

func main() {

	var nth = 0
	// Listen for incoming connections
	listener, err := net.Listen("tcp", ":8080")
	if err != nil {
		fmt.Println("Error listening:", err.Error())
		return
	}
	defer listener.Close()

	fmt.Println("Server listening on :8080")

	for {
		// Accept new connections
		conn, err := listener.Accept()
		if err != nil {
			fmt.Println("Error accepting connection:", err.Error())
			return
		}
		nth = nth + 1
		// Handle each connection in a new goroutine
		go handleConnection(conn, nth)
	}
}
