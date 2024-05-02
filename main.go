package main

import (
	"bingo/server"
	"fmt"
	"os"
)

func main() {
	portError := "Please provide a port number (format-> :8080)."

	// Get the port from the command line
	if len(os.Args) < 2 {
		fmt.Println(portError)
		return
	}
	port := os.Args[1]
	if port == "" || port[0] != ':' {
		fmt.Println(portError)
		return
	}

	server.Run(port)
}
