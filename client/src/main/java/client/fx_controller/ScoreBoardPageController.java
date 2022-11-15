package client.fx_controller;

import client.fx_loader.PaneLoader;
import client.command.BackToMainMenuCommand;
import client.fx_controller.paneController.ScorePaneController;
import client.pageData.PageData;
import client.pageData.ScoreBoardPageData;
import client.util.PageType;
import client.util.PaneType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ScoreBoardPageController extends PageController {
    private static ScorePaneController scorePaneController;
    @FXML
    private VBox box;

    public static void setScorePaneController(ScorePaneController scorePaneController) {
        ScoreBoardPageController.scorePaneController = scorePaneController;
    }

    //////////////////////////////////////
    @FXML
    void back(ActionEvent event) {
        runCommand(new BackToMainMenuCommand());
    }

    @Override
    public void update(PageData pageData) {
        ScoreBoardPageData data = (ScoreBoardPageData) pageData;
        box.getChildren().clear();
        for(Map.Entry<String,Integer> m: data.getScores().entrySet()){
            Pane pane = PaneLoader.getPaneLoader().loadPane(PaneType.score);
            scorePaneController.paint(m.getKey(), m.getValue(), data.getOnlineState().get(m.getKey()));
            box.getChildren().add(pane);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPageType(PageType.scoreBoard);
        passSelf();
    }
}
