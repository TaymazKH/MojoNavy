package client.fx_controller;

import client.fx_loader.PaneLoader;
import client.command.BackToMainMenuCommand;
import client.fx_controller.paneController.GameStatePaneController;
import client.pageData.GamesListPageData;
import client.pageData.PageData;
import client.util.PageType;
import client.util.PaneType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import shared.model.SummarizedGameState;

import java.net.URL;
import java.util.ResourceBundle;

public class GamesListPageController extends PageController {
    private static GameStatePaneController gameStatePaneController;
    @FXML
    private VBox box;

    public static void setGameStatePaneController(GameStatePaneController gameStatePaneController) {
        GamesListPageController.gameStatePaneController = gameStatePaneController;
    }

    /////////////////////////////////////////////////////////
    @FXML
    void back(ActionEvent event) {
        runCommand(new BackToMainMenuCommand());
    }

    @Override
    public void update(PageData pageData) {
        GamesListPageData data = (GamesListPageData) pageData;
        box.getChildren().clear();
        for(SummarizedGameState gameState: data.getSummarizedGameStates()){
            Pane pane = PaneLoader.getPaneLoader().loadPane(PaneType.gameState);
            gameStatePaneController.paint(this,gameState);
            box.getChildren().add(pane);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPageType(PageType.gamesList);
        passSelf();
    }
}
