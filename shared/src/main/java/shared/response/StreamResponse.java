package shared.response;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.model.GameState;

@JsonTypeName("streamResponse")
public class StreamResponse implements Response {
    private GameState gameState;

    public StreamResponse(){}
    public StreamResponse(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void run(ResponseHandler responseHandler) {
        responseHandler.handleStreamResponse(this);
    }
}
