package shared.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import shared.response.Response;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "subclassType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClickRequest.class, name = "clickRequest"),
        @JsonSubTypes.Type(value = GetGameStateRequest.class, name = "getGameStateRequest"),
        @JsonSubTypes.Type(value = GetScoreBoardRequest.class, name = "getScoreBoardRequest"),
        @JsonSubTypes.Type(value = LoginRequest.class, name = "loginRequest"),
        @JsonSubTypes.Type(value = NewGameRequest.class, name = "newGameRequest"),
        @JsonSubTypes.Type(value = NewSetupRequest.class, name = "newSetupRequest"),
        @JsonSubTypes.Type(value = ProfileRequest.class, name = "profileRequest"),
        @JsonSubTypes.Type(value = SignupRequest.class, name = "signupRequest"),
        @JsonSubTypes.Type(value = SetReadyRequest.class, name = "setReadyRequest"),
        @JsonSubTypes.Type(value = GetActiveGamesRequest.class, name = "getActiveGamesRequest"),
        @JsonSubTypes.Type(value = WatchGameRequest.class, name = "watchGameRequest"),
        @JsonSubTypes.Type(value = GetStreamGameStateRequest.class, name = "getStreamGameStateRequest"),
        @JsonSubTypes.Type(value = StopWatchingRequest.class, name = "stopWatchingRequest")
})
public abstract class Request {
    private int authToken;

    public Request(){}
    public Request(int authToken) {
        this.authToken = authToken;
    }

    public int getAuthToken() {
        return authToken;
    }
    public void setAuthToken(int authToken) {
        this.authToken = authToken;
    }

    public abstract Response run(RequestHandler requestHandler);
}
