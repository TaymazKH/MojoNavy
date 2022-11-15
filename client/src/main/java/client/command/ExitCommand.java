package client.command;

public class ExitCommand implements Command {
    @Override
    public void run(CommandHandler commandHandler) {
        commandHandler.handleExitCommand();
    }
}
