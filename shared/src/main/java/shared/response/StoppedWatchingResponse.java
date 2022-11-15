package shared.response;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("stoppedWatchingResponse")
public class StoppedWatchingResponse implements Response {
    @Override
    public void run(ResponseHandler responseHandler) {
        responseHandler.handleStoppedWatchingResponse();
    }
}
