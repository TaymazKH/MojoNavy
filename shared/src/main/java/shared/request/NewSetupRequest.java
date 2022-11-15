package shared.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.response.Response;

@JsonTypeName("newSetupRequest")
public class NewSetupRequest extends Request {
    public NewSetupRequest(){}

    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleNewSetupRequest(this);
    }
}
