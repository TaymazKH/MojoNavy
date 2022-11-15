package shared.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.response.Response;

@JsonTypeName("newGameRequest")
public class NewGameRequest extends Request {
    public NewGameRequest(){}

    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleNewGameRequest(this);
    }
}
