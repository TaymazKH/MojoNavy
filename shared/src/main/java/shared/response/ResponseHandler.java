package shared.response;

public interface ResponseHandler {
    void handleGameStateResponse(GameStateResponse gameStateResponse);
    void handleScoreBoardResponse(ScoreBoardResponse scoreBoardResponse);
    void handleLoginResponse(LoginResponse loginResponse);
    void handleProfileResponse(ProfileResponse profileResponse);
    void handleActiveGamesResponse(ActiveGamesResponse activeGamesResponse);
    void handleStreamResponse(StreamResponse streamResponse);
    void handleStoppedWatchingResponse();
}
