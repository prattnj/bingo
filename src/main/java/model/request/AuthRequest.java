package model.request;

public final class AuthRequest extends BaseRequest {

    private final String username;
    private final String password;
    private final String email;

    public AuthRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String email() {
        return email;
    }
}
