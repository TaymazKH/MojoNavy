package client.command;

public interface CommandHandler {
    void handleBackToMainMenuCommand();
    void handleExitCommand();
    void handleInitializeConnectionCommand();
    void handleStartGameCommand();
    void handleGoToScoreBoardPageCommand();
    void handleGoToGamesListPageCommand();
    void handleGoToStreamPageCommand(GoToStreamPageCommand goToStreamPageCommand);
    void handleStopWatchingCommand();
}
