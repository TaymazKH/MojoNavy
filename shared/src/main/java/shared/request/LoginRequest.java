package shared.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.response.Response;

@JsonTypeName("loginRequest")
public class LoginRequest extends Request {
    private String username,password;

    public LoginRequest(){}
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleLoginRequest(username,password);
    }
}
