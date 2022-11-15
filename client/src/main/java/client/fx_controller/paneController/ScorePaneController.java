package client.fx_controller.paneController;

import client.fx_controller.ScoreBoardPageController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ScorePaneController implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Circle onlineStatusCircle;

    public void paint(String username, int score, boolean online){
        nameLabel.setText(username);
        scoreLabel.setText(String.valueOf(score));
        if(online) onlineStatusCircle.setFill(Color.GREEN);
        else onlineStatusCircle.setFill(Color.GRAY);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScoreBoardPageController.setScorePaneController(this);
    }
}
