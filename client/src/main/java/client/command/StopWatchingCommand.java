package client.command;

public class StopWatchingCommand implements Command {
    @Override
    public void run(CommandHandler commandHandler) {
        commandHandler.handleStopWatchingCommand();
    }
}
