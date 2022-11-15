package client;

import client.agent.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Client extends Application {
    @Override
    public void start(Stage stage){
        new MainController(stage);
    }

    public static void main(String[] args){
        launch(args);
    }
}

/*
to-do list
=============
where are the "notify"s ????
GamePageController line 133
*/
