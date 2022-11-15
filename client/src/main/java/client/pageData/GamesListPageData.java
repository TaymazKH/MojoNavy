package client.pageData;

import client.util.PageType;
import shared.model.SummarizedGameState;

import java.util.ArrayList;

public class GamesListPageData implements PageData {
    private final ArrayList<SummarizedGameState> summarizedGameStates;

    public GamesListPageData(ArrayList<SummarizedGameState> summarizedGameStates) {
        this.summarizedGameStates = summarizedGameStates;
    }

    public ArrayList<SummarizedGameState> getSummarizedGameStates() {
        return summarizedGameStates;
    }

    @Override
    public PageType getPageType() {
        return PageType.gamesList;
    }
}
