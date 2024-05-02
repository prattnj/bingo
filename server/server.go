package server

import (
	"bingo/da"
	"fmt"
	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
)

func Run(port string) {
	e := echo.New()

	err := da.CreateTables()
	if err != nil {
		fmt.Println(err.Error())
	}

	e.Use(middleware.CORSWithConfig(middleware.CORSConfig{
		AllowOrigins: []string{"*"},
	}))

	auth := e.Group("/auth")
	auth.POST("/register", HandleRegister)
	auth.POST("/login", HandleLogin)

	api := e.Group("/api")
	api.Use()

	if err := e.Start(port); err != nil {
		e.Logger.Fatal(err.Error())
	}
}
