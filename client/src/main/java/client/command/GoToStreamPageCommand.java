package client.command;

import shared.model.SummarizedGameState;

public class GoToStreamPageCommand implements Command {
    private final SummarizedGameState gameState;

    public GoToStreamPageCommand(SummarizedGameState gameState) {
        this.gameState = gameState;
    }

    public SummarizedGameState getGameState() {
        return gameState;
    }

    @Override
    public void run(CommandHandler commandHandler) {
        commandHandler.handleGoToStreamPageCommand(this);
    }
}
