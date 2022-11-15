package client.agent;

import client.command.Command;
import client.command.CommandHandler;
import client.command.ExitCommand;
import client.command.GoToStreamPageCommand;
import client.network.SocketRequestSender;
import client.pageData.*;
import client.util.Loop;
import javafx.stage.Stage;
import shared.model.GameState;
import shared.request.*;
import shared.response.*;

import java.io.IOException;

public class MainController implements ResponseHandler, CommandHandler {
    private final GraphicalAgent graphicalAgent;
    private SocketRequestSender socketRequestSender;
    private final Loop loop;
    private int authToken=-1;
    private final Object syncObj1 = new Object(), syncObj2 = new Object();
    private volatile boolean userHasRequest = false, loopHasRequest = false;
    private PageData pageData;

    public MainController(Stage stage){
        stage.setOnCloseRequest(event -> runCommand(new ExitCommand()));
        graphicalAgent = new GraphicalAgent(this,stage);
        loop = new Loop(this);
        loop.start();
        handleInitializeConnectionCommand();
        graphicalAgent.initialize();
        graphicalAgent.updateGraphics(pageData);
    }

    public void sendRequest(Request request){
        synchronized(getSyncObj1()){
            userHasRequest = true;
        }
        synchronized(getSyncObj2()){
            if(loopHasRequest){
                try {
                    getSyncObj2().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        request.setAuthToken(authToken);
        try {
            Response response;
            response = socketRequestSender.sendRequest(request);
            response.run(this);
        } catch (Exception e) {
            socketRequestSender = null;
            pageData = new ConnectionPageData();
        }
        synchronized(graphicalAgent){
            graphicalAgent.updateGraphics(pageData);
        }
        synchronized(getSyncObj1()){
            userHasRequest = false;
            getSyncObj1().notifyAll();
        }
    }
    public void loopSendRequest(Request request){
        synchronized(getSyncObj1()){
            if(userHasRequest){
                try {
                    getSyncObj1().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        synchronized(getSyncObj2()){
            loopHasRequest = true;
        }
        request.setAuthToken(authToken);
        try {
            Response response;
            response = socketRequestSender.sendRequest(request);
            if(loop.isActive()) response.run(this);
        } catch (Exception e) {
            loop.deactivate();
            socketRequestSender = null;
            pageData = new ConnectionPageData();
        }
        synchronized(graphicalAgent){
            graphicalAgent.updateGraphics(pageData);
        }
        synchronized(getSyncObj2()){
            loopHasRequest = false;
            getSyncObj2().notifyAll();
        }
    }
    public void runCommand(Command command){
        command.run(this);
        synchronized(graphicalAgent){
            graphicalAgent.updateGraphics(pageData);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    public Object getSyncObj1() {
        return syncObj1;
    }
    public Object getSyncObj2() {
        return syncObj2;
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void handleGameStateResponse(GameStateResponse gameStateResponse) {
        GameState gameState = gameStateResponse.getGameState();
        if(gameState!=null && gameState.getState()>0) loop.deactivate();
        pageData = new GamePageData(gameState, gameStateResponse.getOpponentsName(), gameStateResponse.getSide());
    }
    @Override
    public void handleLoginResponse(LoginResponse loginResponse) {
        if(loginResponse.getAuthToken()!=-1){
            authToken = loginResponse.getAuthToken();
            pageData = new MainMenuPageData();
        }
        else pageData = new LoginPageData(loginResponse.getMessage());
    }
    @Override
    public void handleProfileResponse(ProfileResponse profileResponse) {
        pageData = new ProfilePageData(profileResponse.getUser());
    }
    @Override
    public void handleScoreBoardResponse(ScoreBoardResponse scoreBoardResponse) {
        pageData = new ScoreBoardPageData(scoreBoardResponse.getScores(), scoreBoardResponse.getOnlineState());
    }
    @Override
    public void handleActiveGamesResponse(ActiveGamesResponse activeGamesResponse) {
        pageData = new GamesListPageData(activeGamesResponse.getGameStates());
    }
    @Override
    public void handleStreamResponse(StreamResponse streamResponse) {
        GameState gameState = streamResponse.getGameState();
        if(gameState==null || gameState.getState()>0) loop.deactivate();
        pageData = new StreamPageData(gameState);
    }
    @Override
    public void handleStoppedWatchingResponse() {
        pageData = new MainMenuPageData();
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void handleBackToMainMenuCommand() {
        loop.deactivate();
        pageData = new MainMenuPageData();
    }
    @Override
    public void handleExitCommand() {
        loop.stopLater();
        if(socketRequestSender!=null) socketRequestSender.close();
        pageData = null;
    }
    @Override
    public void handleInitializeConnectionCommand() {
        try{
            socketRequestSender = new SocketRequestSender();
            pageData = new LoginPageData(0);
        } catch(IOException e){
            socketRequestSender = null;
            pageData = new ConnectionPageData();
        }
    }
    @Override
    public void handleStartGameCommand() {
        sendRequest(new NewGameRequest());
        loop.setTaskCodeAndStart(1);
    }
    @Override
    public void handleGoToScoreBoardPageCommand() {
        sendRequest(new GetScoreBoardRequest());
        loop.setTaskCodeAndStart(2);
    }
    @Override
    public void handleGoToGamesListPageCommand() {
        sendRequest(new GetActiveGamesRequest());
        loop.setTaskCodeAndStart(3);
    }
    @Override
    public void handleGoToStreamPageCommand(GoToStreamPageCommand goToStreamPageCommand) {
        loop.deactivate();
        sendRequest(new WatchGameRequest(goToStreamPageCommand.getGameState()));
        loop.setTaskCodeAndStart(4);
    }
    @Override
    public void handleStopWatchingCommand() {
        loop.deactivate();
        sendRequest(new StopWatchingRequest());
    }
}
