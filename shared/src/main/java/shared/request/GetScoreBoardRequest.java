package shared.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.response.Response;

@JsonTypeName("getScoreBoardRequest")
public class GetScoreBoardRequest extends Request {
    public GetScoreBoardRequest(){}

    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleGetScoreBoardRequest();
    }
}
