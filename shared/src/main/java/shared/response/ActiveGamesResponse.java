package shared.response;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.model.SummarizedGameState;

import java.util.ArrayList;

@JsonTypeName("activeGamesResponse")
public class ActiveGamesResponse implements Response {
    private ArrayList<SummarizedGameState> gameStates;

    public ActiveGamesResponse(){}
    public ActiveGamesResponse(ArrayList<SummarizedGameState> gameStates) {
        this.gameStates = gameStates;
    }

    public ArrayList<SummarizedGameState> getGameStates() {
        return gameStates;
    }

    public void setGameStates(ArrayList<SummarizedGameState> gameStates) {
        this.gameStates = gameStates;
    }

    @Override
    public void run(ResponseHandler responseHandler) {
        responseHandler.handleActiveGamesResponse(this);
    }
}
