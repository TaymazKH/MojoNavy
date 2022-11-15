package shared.response;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.LinkedHashMap;

@JsonTypeName("scoreBoardResponse")
public class ScoreBoardResponse implements Response {
    private LinkedHashMap<String,Integer> scores;
    private LinkedHashMap<String,Boolean> onlineState;

    public ScoreBoardResponse(){}
    public ScoreBoardResponse(LinkedHashMap<String, Integer> scores, LinkedHashMap<String, Boolean> onlineState) {
        this.scores = scores;
        this.onlineState = onlineState;
    }

    public LinkedHashMap<String, Integer> getScores() {
        return scores;
    }
    public LinkedHashMap<String, Boolean> getOnlineState() {
        return onlineState;
    }

    public void setScores(LinkedHashMap<String, Integer> scores) {
        this.scores = scores;
    }
    public void setOnlineState(LinkedHashMap<String, Boolean> onlineState) {
        this.onlineState = onlineState;
    }

    @Override
    public void run(ResponseHandler responseHandler) {
        responseHandler.handleScoreBoardResponse(this);
    }
}
