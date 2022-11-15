package client.fx_controller;

import client.command.ExitCommand;
import client.pageData.LoginPageData;
import client.pageData.PageData;
import client.util.PageType;
import client.util.Texts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import shared.request.LoginRequest;
import shared.request.SignupRequest;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController extends PageController {
    @FXML
    private TextField usernameText;
    @FXML
    private TextField passwordText;

    ////////////////////////////////////////////////////////
    @FXML
    void exit(ActionEvent event) {
        runCommand(new ExitCommand());
    }

    @FXML
    void login(ActionEvent event) {
        if(usernameText.getText()!=null && !usernameText.getText().isBlank()
                && passwordText.getText()!=null && !passwordText.getText().isBlank())
            sendRequest(new LoginRequest(usernameText.getText(),passwordText.getText()));
    }

    @FXML
    void signup(ActionEvent event) {
        if(usernameText.getText()!=null && !usernameText.getText().isBlank()
                && passwordText.getText()!=null && !passwordText.getText().isBlank())
            sendRequest(new SignupRequest(usernameText.getText(),passwordText.getText()));
    }

    @Override
    public void update(PageData pageData) {
        LoginPageData data = (LoginPageData) pageData;
        if(data.getMessage()!=0){
            String message="";
            switch(data.getMessage()){
                case 1-> message = Texts.getNoAccount();
                case 2-> message = Texts.getWrongPassword();
                case 3-> message = Texts.getAlreadyLoggedIn();
                case 4-> message = Texts.getUsernameTaken();
            }
            Alert alert = new Alert(Alert.AlertType.WARNING,message);
            alert.setHeaderText(Texts.getWrongInputHeader());
            alert.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPageType(PageType.login);
        passSelf();
    }
}
