package client.command;

public class GoToScoreBoardPageCommand implements Command {
    @Override
    public void run(CommandHandler commandHandler) {
        commandHandler.handleGoToScoreBoardPageCommand();
    }
}
