package main

import (
	"fmt"
	"net"
	"time"
)

func main() {
	// Connect to the server
	conn, err := net.Dial("tcp", "localhost:8080")
	if err != nil {
		fmt.Println("Error connecting:", err.Error())
		return
	}
	defer conn.Close()

	// Send data to the server
	_, err = conn.Write([]byte("Hello, server"))
	if err != nil {
		fmt.Println("Error writing:", err.Error())
		return
	}

	time.Sleep(2 * time.Minute)
	// Read response from the server
	buf := make([]byte, 1024)
	n, err := conn.Read(buf)
	if err != nil {
		fmt.Println("Error reading:", err.Error())
		return
	}
	fmt.Println("Received response from server:", string(buf[:n]))
}
