package client.command;

public class GoToGamesListPageCommand implements Command {
    @Override
    public void run(CommandHandler commandHandler) {
        commandHandler.handleGoToGamesListPageCommand();
    }
}
