package client.fx_loader;

import client.util.Config;
import client.util.PageType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SceneLoader {
    private final HashMap<PageType,String> scenesFilesLocations;

    public SceneLoader(){
        Config config = Config.getConfig("scenesLocations");
        scenesFilesLocations = new HashMap<>();
        scenesFilesLocations.put(PageType.login, config.getProperty(String.class,"login"));
        scenesFilesLocations.put(PageType.mainMenu, config.getProperty(String.class,"mainMenu"));
        scenesFilesLocations.put(PageType.profile, config.getProperty(String.class, "profile"));
        scenesFilesLocations.put(PageType.gamePage, config.getProperty(String.class,"gamePage"));
        scenesFilesLocations.put(PageType.gamesList, config.getProperty(String.class, "gamesList"));
        scenesFilesLocations.put(PageType.stream, config.getProperty(String.class, "stream"));
        scenesFilesLocations.put(PageType.scoreBoard, config.getProperty(String.class,"scoreBoard"));
        scenesFilesLocations.put(PageType.connection, config.getProperty(String.class,"noConnection"));
    }

    public Scene loadScene(PageType pageType){
        try{
            return new Scene(FXMLLoader.load(new File(scenesFilesLocations.get(pageType)).toURI().toURL()));
        } catch(IOException e){
            return null;
        }
    }
}
