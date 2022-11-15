package shared.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.response.Response;

@JsonTypeName("getGameStateRequest")
public class GetGameStateRequest extends Request {
    public GetGameStateRequest(){}

    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleGetGameStateRequest(this);
    }
}
