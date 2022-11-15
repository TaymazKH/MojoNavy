package shared.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.response.Response;

@JsonTypeName("setReadyRequest")
public class SetReadyRequest extends Request {
    public SetReadyRequest(){}

    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleSetReadyRequest(this);
    }
}
