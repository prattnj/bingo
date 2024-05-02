package logging

import "fmt"

func LogError(e error) {
	fmt.Println(e.Error())
}
