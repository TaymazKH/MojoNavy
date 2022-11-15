package client.pageData;

import client.util.PageType;

import java.util.LinkedHashMap;

public class ScoreBoardPageData implements PageData {
    private final LinkedHashMap<String,Integer> scores;
    private final LinkedHashMap<String,Boolean> onlineState;

    public ScoreBoardPageData(LinkedHashMap<String, Integer> scores, LinkedHashMap<String, Boolean> onlineState) {
        this.scores = scores;
        this.onlineState = onlineState;
    }

    public LinkedHashMap<String, Integer> getScores() {
        return scores;
    }
    public LinkedHashMap<String, Boolean> getOnlineState() {
        return onlineState;
    }

    @Override
    public PageType getPageType() {
        return PageType.scoreBoard;
    }
}
