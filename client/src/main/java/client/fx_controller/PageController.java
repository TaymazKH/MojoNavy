package client.fx_controller;

import client.agent.GraphicalAgent;
import client.command.Command;
import client.pageData.PageData;
import client.util.PageType;
import javafx.fxml.Initializable;
import shared.request.Request;

public abstract class PageController implements Initializable {
    private static GraphicalAgent graphicalAgent;
    private PageType pageType;

    public static GraphicalAgent getGraphicalAgent() {
        return graphicalAgent;
    }
    public PageType getPageType() {
        return pageType;
    }

    public static void setGraphicalAgent(GraphicalAgent graphicalAgent) {
        PageController.graphicalAgent = graphicalAgent;
    }
    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    public void passSelf(){
        graphicalAgent.setCurrentPageController(this);
    }
    public void sendRequest(Request request){
        graphicalAgent.sendRequest(request);
    }
    public void runCommand(Command command){
        graphicalAgent.runCommand(command);
    }
    public abstract void update(PageData pageData);
}
