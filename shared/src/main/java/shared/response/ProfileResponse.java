package shared.response;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.model.User;

@JsonTypeName("profileResponse")
public class ProfileResponse implements Response {
    private User user;

    public ProfileResponse(){}
    public ProfileResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void run(ResponseHandler responseHandler) {
        responseHandler.handleProfileResponse(this);
    }
}
