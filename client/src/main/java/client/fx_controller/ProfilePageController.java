package client.fx_controller;

import client.command.BackToMainMenuCommand;
import client.pageData.PageData;
import client.pageData.ProfilePageData;
import client.util.PageType;
import client.util.Texts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import shared.model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfilePageController extends PageController {
    @FXML
    private Label usernameLabel;
    @FXML
    private Label totalPointsLabel;
    @FXML
    private Label winCountLabel;
    @FXML
    private Label loseCountLabel;

    //////////////////////////////////////////////////////
    @FXML
    void back(ActionEvent event) {
        runCommand(new BackToMainMenuCommand());
    }

    @Override
    public void update(PageData pageData) {
        ProfilePageData data = (ProfilePageData) pageData;
        String name = Texts.getEmptyPlayerName();
        int totalPoints=0,winCount=0,loseCount=0;
        if(data.getUser()==null){
            Alert alert = new Alert(Alert.AlertType.WARNING, Texts.getCouldNotLoadProfile());
            alert.setHeaderText(Texts.getLoadingProblemHeader());
            alert.show();
        }
        else{
            User user = data.getUser();
            name = user.getUsername();
            winCount = user.getWinCount();
            loseCount = user.getLoseCount();
            totalPoints = winCount-loseCount;
        }
        usernameLabel.setText(Texts.getPlayerTitle()+name);
        totalPointsLabel.setText(totalPoints+" "+Texts.getTotalPointsTitle());
        winCountLabel.setText(winCount+" "+Texts.getWinsTitle());
        loseCountLabel.setText(loseCount+" "+Texts.getDefeatsTitle());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPageType(PageType.profile);
        passSelf();
    }
}
