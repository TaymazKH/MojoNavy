package shared.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.response.Response;

@JsonTypeName("profileRequest")
public class ProfileRequest extends Request {
    public ProfileRequest(){}

    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleProfileRequest(this);
    }
}
