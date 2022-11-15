package client.fx_controller;

import client.command.ExitCommand;
import client.command.GoToGamesListPageCommand;
import client.command.GoToScoreBoardPageCommand;
import client.command.StartGameCommand;
import client.pageData.PageData;
import client.util.PageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import shared.request.ProfileRequest;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuPageController extends PageController {
    @FXML
    void exit(ActionEvent event) {
        runCommand(new ExitCommand());
    }

    @FXML
    void showMyProfile(ActionEvent event) {
        sendRequest(new ProfileRequest());
    }

    @FXML
    void showScoreBoard(ActionEvent event) {
        runCommand(new GoToScoreBoardPageCommand());
    }

    @FXML
    void startNewGame(ActionEvent event) {
        runCommand(new StartGameCommand());
    }

    @FXML
    void watchAnotherGame(ActionEvent event) {
        runCommand(new GoToGamesListPageCommand());
    }

    @Override
    public void update(PageData pageData) {
        // nothing to show
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPageType(PageType.mainMenu);
        passSelf();
    }
}
