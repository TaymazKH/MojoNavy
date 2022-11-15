package shared.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.response.Response;

@JsonTypeName("getActiveGamesRequest")
public class GetActiveGamesRequest extends Request {
    public GetActiveGamesRequest(){}

    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleGetActiveGamesRequest();
    }
}
