package da

import (
	"bingo/logging"
	"database/sql"
	"errors"
	_ "github.com/go-sql-driver/mysql"
	"os"
)

func getSchema() []string {
	return []string{`
		CREATE TABLE IF NOT EXISTS user (
			username VARCHAR(255) NOT NULL,
			password VARCHAR(255) NOT NULL,
			email VARCHAR(255) NOT NULL,
			PRIMARY KEY (username)
		)
		`, `
		CREATE TABLE IF NOT EXISTS board (
			id VARCHAR(255) NOT NULL,
			username VARCHAR(255) NOT NULL,
			items TEXT NOT NULL,
			created_at BIGINT NOT NULL,
			public BOOL NOT NULL,
			PRIMARY KEY (id),
			FOREIGN KEY (username) REFERENCES user(username)
		)
	`}
}

func CreateTables() error {
	db := Connect()
	if db == nil {
		return errors.New("unable to connect to database")
	}
	for _, scheme := range getSchema() {
		_, err := db.Exec(scheme)
		if err != nil {
			CloseConnection(db)
			return err
		}
	}
	CloseConnection(db)
	return nil
}

func Connect() *sql.DB {
	database, err := sql.Open("mysql", "pratt:"+os.Getenv("MYSQL_PASSWORD")+"@tcp(localhost:3306)/bingo")
	if err != nil {
		logging.LogError(err)
		return nil
	}
	err = database.Ping()
	if err != nil {
		logging.LogError(err)
		return nil
	}
	return database
}

func CloseConnection(db *sql.DB) {
	err := db.Close()
	if err != nil {
		logging.LogError(err)
	}
}
