package client.fx_controller;

import java.net.URL;
import java.util.ResourceBundle;

import client.command.ExitCommand;
import client.command.InitializeConnectionCommand;
import client.pageData.PageData;
import client.util.PageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ConnectionPageController extends PageController {
    @FXML
    void exit(ActionEvent event) {
        runCommand(new ExitCommand());
    }

    @FXML
    void retry(ActionEvent event) {
        runCommand(new InitializeConnectionCommand());
    }

    @Override
    public void update(PageData pageData) {
        // nothing to show
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPageType(PageType.connection);
        passSelf();
    }
}
