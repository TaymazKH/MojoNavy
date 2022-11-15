package shared.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.response.Response;

@JsonTypeName("getStreamGameStateRequest")
public class GetStreamGameStateRequest extends Request {
    public GetStreamGameStateRequest(){}

    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleGetStreamGameStateRequest(this);
    }
}
