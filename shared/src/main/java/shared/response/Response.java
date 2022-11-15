package shared.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "subclassType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GameStateResponse.class, name = "gameStateResponse"),
        @JsonSubTypes.Type(value = LoginResponse.class, name = "loginResponse"),
        @JsonSubTypes.Type(value = ProfileResponse.class, name = "profileResponse"),
        @JsonSubTypes.Type(value = ScoreBoardResponse.class, name = "scoreBoardResponse"),
        @JsonSubTypes.Type(value = ActiveGamesResponse.class, name = "activeGamesResponse"),
        @JsonSubTypes.Type(value = StreamResponse.class, name = "streamResponse"),
        @JsonSubTypes.Type(value = StoppedWatchingResponse.class, name = "stoppedWatchingResponse")
})
public interface Response {
    void run(ResponseHandler responseHandler);
}
