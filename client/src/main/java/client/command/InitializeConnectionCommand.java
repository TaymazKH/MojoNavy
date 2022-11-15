package client.command;

public class InitializeConnectionCommand implements Command {
    @Override
    public void run(CommandHandler commandHandler) {
        commandHandler.handleInitializeConnectionCommand();
    }
}
