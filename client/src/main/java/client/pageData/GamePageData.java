package client.pageData;

import client.util.PageType;
import shared.model.GameState;

public class GamePageData implements PageData {
    private final GameState gameState;
    private String opponentsName;
    private final int side;

    public GamePageData(GameState gameState, String opponentsName, int side) {
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

    @Override
    public PageType getPageType() {
        return PageType.gamePage;
    }
}
