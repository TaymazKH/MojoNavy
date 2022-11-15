package shared.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.response.Response;

@JsonTypeName("stopWatchingRequest")
public class StopWatchingRequest extends Request {
    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleStopWatchingRequest(this);
    }
}
