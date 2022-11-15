package client.fx_loader;

import client.util.Config;
import client.util.PaneType;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PaneLoader {
    private static PaneLoader paneLoader;
    private final HashMap<PaneType,String> paneFileLocations;

    private PaneLoader(){
        Config config = Config.getConfig("panesLocations");
        paneFileLocations = new HashMap<>();
        paneFileLocations.put(PaneType.score, config.getProperty(String.class, "scorePane"));
        paneFileLocations.put(PaneType.gameState, config.getProperty(String.class, "gameStatePane"));
    }

    public static PaneLoader getPaneLoader() {
        if(paneLoader==null) paneLoader = new PaneLoader();
        return paneLoader;
    }

    public Pane loadPane(PaneType paneType){
        try{
            return FXMLLoader.load(new File(paneFileLocations.get(paneType)).toURI().toURL());
        } catch(IOException e){
            return null;
        }
    }
}
