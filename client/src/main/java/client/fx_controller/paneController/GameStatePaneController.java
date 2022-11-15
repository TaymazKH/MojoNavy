package client.fx_controller.paneController;

import client.command.GoToStreamPageCommand;
import client.fx_controller.GamesListPageController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import shared.model.SummarizedGameState;

import java.net.URL;
import java.util.ResourceBundle;

public class GameStatePaneController implements Initializable {
    private GamesListPageController controller;
    private SummarizedGameState gameState;
    @FXML
    private Label nameLabel;
    @FXML
    private Label shipCountLabel;
    @FXML
    private Label attackCountLabel;
    @FXML
    private Label turnCountLabel;

    public void paint(GamesListPageController controller, SummarizedGameState gameState){
        this.controller = controller;
        this.gameState = gameState;
        nameLabel.setText(gameState.getNames()[0]+" vs. "+gameState.getNames()[1]);
        shipCountLabel.setText(gameState.getAliveShips()[0]+"/"+gameState.getAliveShips()[1]);
        attackCountLabel.setText(gameState.getBombsHit()[0]+"/"+gameState.getBombsHit()[1]);
        turnCountLabel.setText(String.valueOf(gameState.getTotalTurnsPlayed()));
    }

    ////////////////////////////////////////////////////
    @FXML
    void watch(MouseEvent event) {
        controller.runCommand(new GoToStreamPageCommand(gameState));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GamesListPageController.setGameStatePaneController(this);
    }
}
