package model.bean;

public class UserBean {

    private final String username;
    private final String password;
    private final String email;
    private String token;

    public UserBean(String username, String password, String email, String token) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
