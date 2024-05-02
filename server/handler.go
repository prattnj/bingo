package server

import "github.com/labstack/echo/v4"

func HandleRegister(c echo.Context) error {
	return nil
}

func HandleLogin(c echo.Context) error {
	return nil
}

func Authenticate(next echo.HandlerFunc) echo.HandlerFunc {
	return func(c echo.Context) error {
		// do stuff
		return next(c)
	}
}
