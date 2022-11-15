package client.command;

public class StartGameCommand implements Command {
    @Override
    public void run(CommandHandler commandHandler) {
        commandHandler.handleStartGameCommand();
    }
}
