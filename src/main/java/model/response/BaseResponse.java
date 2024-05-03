package model.response;

public class BaseResponse {

    private final String message;

    public BaseResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
