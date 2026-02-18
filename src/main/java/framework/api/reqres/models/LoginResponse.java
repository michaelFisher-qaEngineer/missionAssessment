package framework.api.reqres.models;

import io.restassured.response.Response;

public class LoginResponse {

    private final String token;
    private final int statusCode;
    private final String errorMessage;

    private LoginResponse(String token, int statusCode, String errorMessage) {
        this.token = token;
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public static LoginResponse from(Response r) {
        return new LoginResponse(
                r.path("token"),
                r.getStatusCode(),
                r.path("error")
        );
    }

    public String getToken() { return token; }
    public int getStatusCode() { return statusCode; }
    public String getErrorMessage() { return errorMessage; }
}
