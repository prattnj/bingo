package model.response;

public final class AuthResponse extends BaseResponse {

    private final String username;
    private final String token;

    public AuthResponse(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String username() {
        return username;
    }

    public String token() {
        return token;
    }
}
