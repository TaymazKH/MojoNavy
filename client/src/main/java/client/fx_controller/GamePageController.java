package client.fx_controller;

import client.command.BackToMainMenuCommand;
import client.pageData.GamePageData;
import client.pageData.PageData;
import client.util.Config;
import client.util.PageType;
import client.util.Texts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import shared.model.Cell;
import shared.model.GameState;
import shared.model.Ship;
import shared.request.ClickRequest;
import shared.request.NewSetupRequest;
import shared.request.SetReadyRequest;

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GamePageController extends PageController {
    private Image verticalShipImage,horizontalShipImage,explosionImage;
    private int cellW1,cellH1,cellW2,cellH2;
    @FXML
    private Canvas canvas1;
    @FXML
    private Canvas canvas2;
    @FXML
    private Label timerLabel1;
    @FXML
    private Label timerLabel2;
    @FXML
    private Label messageLabel;
    @FXML
    private Label opponentsNameLabel;
    @FXML
    private Button setReadyButton;
    @FXML
    private Button newSetupButton;
    @FXML
    private Button finishButton;

    //////////////////////////////////////////////////////////////////////
    @FXML
    void click(MouseEvent event) {
        sendRequest(new ClickRequest((int)(event.getX()/cellW2),(int)(event.getY()/cellH2)));
    }

    @FXML
    void newSetup(ActionEvent event) {
        sendRequest(new NewSetupRequest());
    }

    @FXML
    void setReady(ActionEvent event) {
        sendRequest(new SetReadyRequest());
    }

    @FXML
    void finish(ActionEvent event) {
        runCommand(new BackToMainMenuCommand());
    }

    @Override
    public void update(PageData pageData) {
        GamePageData data = (GamePageData) pageData;
        GameState gameState = data.getGameState();
        int side = data.getSide();
        if(gameState!=null){
            opponentsNameLabel.setText(Texts.getOpponentTitle()+data.getOpponentsName());
            if(gameState.getState()==-1){
                messageLabel.setText(Texts.getBoardSetup());
                LocalTime now = LocalTime.now();
                if(gameState.getReady()[side-1]){
                    timerLabel1.setText(Texts.getReady());
                    setReadyButton.setVisible(false);
                    newSetupButton.setVisible(false);
                }
                else{
                    int diff = gameState.getTimes()[side-1]-now.toSecondOfDay();
                    if(diff<0) diff+=86400;
                    timerLabel1.setText("0 : "+diff);
                    setReadyButton.setVisible(true);
                    newSetupButton.setVisible(gameState.getSetupCountLeft()[side-1]>0);
                }
                if(gameState.getReady()[side%2]) timerLabel2.setText(Texts.getReady());
                else{
                    int diff = gameState.getTimes()[side%2]-now.toSecondOfDay();
                    if(diff<0) diff+=86400;
                    timerLabel2.setText("0 : "+diff);
                }
                paint(gameState,side);
            }
            else{
                setReadyButton.setVisible(false);
                newSetupButton.setVisible(false);
                timerLabel2.setText("");
                if(gameState.getState()==0){
                    int diff = gameState.getTimes()[0]-LocalTime.now().toSecondOfDay();
                    if(diff<0) diff+=86400;
                    timerLabel1.setText("0 : "+diff);
                    if(gameState.getThisPlayersTurn()==side) messageLabel.setText(Texts.getYourTurn());
                    else messageLabel.setText(Texts.getOpponentsTurn());
                    paint(gameState,side);
                }
                else{
                    timerLabel1.setText("");
                    finishButton.setVisible(true);
                    paint(gameState,side);
                    if(gameState.getState()==side) messageLabel.setText(Texts.getYouWon());
                    else messageLabel.setText(Texts.getYouLost());
                }
            }
        }
    }
    private void paint(GameState gameState, int side){
        GraphicsContext g1 = canvas1.getGraphicsContext2D();
        GraphicsContext g2 = canvas2.getGraphicsContext2D();
        g1.setFill(Color.BLACK);
        g2.setFill(Color.BLACK);
        g1.clearRect(0,0,canvas1.getWidth(),canvas1.getHeight());
        g2.clearRect(0,0,canvas1.getWidth(),canvas1.getHeight());
        ArrayList<Ship> myShips = gameState.getBoards()[side-1].getShips();
        for(Ship ship: myShips){
            int x = ship.getX(),y = ship.getY();
            if(ship.isHorizontal())
                g1.drawImage(horizontalShipImage, x*cellW1, y*cellH1, ship.getLength()*cellW1, cellH1);
            else g1.drawImage(verticalShipImage, x*cellW1, y*cellH1, cellW1, ship.getLength()*cellH1);
        }
        Cell[][] myCells = gameState.getBoards()[side-1].getCells();
        for(Cell[] cellRow: myCells){
            for(Cell cell: cellRow){
                if(cell.isBombed()){
                    if(cell.isHasShip())
                        g1.drawImage(explosionImage,cell.getX()*cellW1, cell.getY()*cellH1, cellW1, cellH1);
                    else g1.fillOval(cell.getX()*cellW1+cellW1/5, cell.getY()*cellH1+cellH1/5, cellW1*3/5, cellH1*3/5);
                }
            }
        }
        Cell[][] enemyCells = gameState.getBoards()[side%2].getCells();
        for(Cell[] cellRow: enemyCells){
            for(Cell cell: cellRow){
                if(cell.isBombed()){
                    if(cell.isHasShip())
                        g2.drawImage(explosionImage,cell.getX()*cellW2, cell.getY()*cellH2, cellW2, cellH2);
                    else g2.fillOval(cell.getX()*cellW2+cellW2/5, cell.getY()*cellH2+cellH2/5, cellW2*3/5, cellH2*3/5);
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        explosionImage = new Image(Config.getConfig("imageLocations").getProperty(String.class,"explosion"));
        verticalShipImage = new Image(Config.getConfig("imageLocations").getProperty(String.class,"ship"));
        ImageView imageView = new ImageView(verticalShipImage);
        imageView.setRotate(90);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        horizontalShipImage = imageView.snapshot(parameters,null);
        cellW1 = (int)(canvas1.getWidth()/10);
        cellH1 = (int)(canvas1.getHeight()/10);
        cellW2 = (int)(canvas2.getWidth()/10);
        cellH2 = (int)(canvas2.getHeight()/10);
        setPageType(PageType.gamePage);
        passSelf();
    }
}
