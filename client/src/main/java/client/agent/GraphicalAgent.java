package client.agent;

import client.command.Command;
import client.fx_controller.PageController;
import client.fx_loader.SceneLoader;
import client.pageData.PageData;
import client.util.Config;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import shared.request.Request;

public class GraphicalAgent {
    private final MainController mainController;
    private final SceneLoader sceneLoader;
    private PageController currentPageController;
    private final Stage stage;
    private PageData pageData;

    public GraphicalAgent(MainController mainController, Stage stage) {
        this.mainController = mainController;
        this.sceneLoader = new SceneLoader();
        this.stage = stage;
        PageController.setGraphicalAgent(this);
    }

    public void initialize(){
        stage.setTitle("MojoNavy");
        stage.getIcons().add(new Image(Config.getConfig("imageLocations").getProperty(String.class,"icon")));
        stage.setResizable(false);
        stage.show();
    }

    public void sendRequest(Request request){
        mainController.sendRequest(request);
    }
    public void runCommand(Command command){
        mainController.runCommand(command);
    }
    public void updateGraphics(PageData data){
        pageData = data;
        Platform.runLater(()->{
            if(pageData!=null){
                if(currentPageController==null || pageData.getPageType()!=currentPageController.getPageType()){
                    stage.setScene(sceneLoader.loadScene(pageData.getPageType()));
                }
                currentPageController.update(pageData);
            }
            else stage.close();
        });
    }

    public void setCurrentPageController(PageController currentPageController) {
        this.currentPageController = currentPageController;
    }
}
