package shared.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.model.SummarizedGameState;
import shared.response.Response;

@JsonTypeName("watchGameRequest")
public class WatchGameRequest extends Request {
    private SummarizedGameState summarizedGameState;

    public WatchGameRequest(){}
    public WatchGameRequest(SummarizedGameState summarizedGameState) {
        this.summarizedGameState = summarizedGameState;
    }

    public SummarizedGameState getSummarizedGameState() {
        return summarizedGameState;
    }

    public void setSummarizedGameState(SummarizedGameState summarizedGameState) {
        this.summarizedGameState = summarizedGameState;
    }

    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleWatchGameRequest(this);
    }
}
