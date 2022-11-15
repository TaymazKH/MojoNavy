package shared.response;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.model.GameState;

@JsonTypeName("gameStateResponse")
public class GameStateResponse implements Response {
    private GameState gameState;
    private String opponentsName;
    private int side;

    public GameStateResponse(){}
    public GameStateResponse(GameState gameState, String opponentsName, int side) {
        this.gameState = gameState;
        this.opponentsName = opponentsName;
        this.side = side;
    }

    public GameState getGameState() {
        return gameState;
    }
    public String getOpponentsName() {
        return opponentsName;
    }
    public int getSide() {
        return side;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    public void setOpponentsName(String opponentsName) {
        this.opponentsName = opponentsName;
    }
    public void setSide(int side) {
        this.side = side;
    }

    @Override
    public void run(ResponseHandler responseHandler) {
        responseHandler.handleGameStateResponse(this);
    }
}
