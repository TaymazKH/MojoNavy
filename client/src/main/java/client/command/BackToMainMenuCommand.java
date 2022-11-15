package client.command;

public class BackToMainMenuCommand implements Command {
    @Override
    public void run(CommandHandler commandHandler) {
        commandHandler.handleBackToMainMenuCommand();
    }
}
