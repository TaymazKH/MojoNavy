package client.pageData;

import client.util.PageType;
import shared.model.GameState;

public class StreamPageData implements PageData {
    private final GameState gameState;

    public StreamPageData(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    @Override
    public PageType getPageType() {
        return PageType.stream;
    }
}
