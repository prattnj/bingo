package model

type UserBean struct {
	Username string
	Password string
	Email    string
}

type BoardBean struct {
	Id        string
	Username  string
	Items     []Item
	CreatedAt int64
	Public    bool
}

type Item struct {
	Description string
	Checked     bool
	CheckedAt   int64
}
