package shared.response;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("loginResponse")
public class LoginResponse implements Response {
    private int authToken,message;

    public LoginResponse(){}
    public LoginResponse(int authToken, int message) {
        this.authToken = authToken;
        this.message = message;
    }

    public int getAuthToken() {
        return authToken;
    }
    public int getMessage() {
        return message;
    }

    public void setAuthToken(int authToken) {
        this.authToken = authToken;
    }
    public void setMessage(int message) {
        this.message = message;
    }

    @Override
    public void run(ResponseHandler responseHandler) {
        responseHandler.handleLoginResponse(this);
    }
}
