package shared.request;

import shared.response.Response;

public interface RequestHandler {
    Response handleClickRequest(ClickRequest clickRequest);
    Response handleGetGameStateRequest(GetGameStateRequest getGameStateRequest);
    Response handleGetScoreBoardRequest();
    Response handleNewGameRequest(NewGameRequest newGameRequest);
    Response handleNewSetupRequest(NewSetupRequest newSetupRequest);
    Response handleLoginRequest(String username, String password);
    Response handleSignupRequest(String username, String password);
    Response handleProfileRequest(ProfileRequest profileRequest);
    Response handleSetReadyRequest(SetReadyRequest setReadyRequest);
    Response handleGetActiveGamesRequest();
    Response handleWatchGameRequest(WatchGameRequest watchGameRequest);
    Response handleGetStreamGameStateRequest(GetStreamGameStateRequest getStreamGameStateRequest);
    Response handleStopWatchingRequest(StopWatchingRequest stopWatchingRequest);
}
