package client.fx_controller;

import client.command.StopWatchingCommand;
import client.pageData.PageData;
import client.pageData.StreamPageData;
import client.util.Config;
import client.util.PageType;
import client.util.Texts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import shared.model.Cell;
import shared.model.GameState;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class StreamPageController extends PageController {
    private Image explosionImage;
    private int cellW1,cellH1,cellW2,cellH2;
    @FXML
    private Label timerLabel1;
    @FXML
    private Label timerLabel2;
    @FXML
    private Label messageLabel;
    @FXML
    private Canvas canvas1;
    @FXML
    private Canvas canvas2;

    //////////////////////////////////////////////////
    @FXML
    void quit(ActionEvent event) {
        runCommand(new StopWatchingCommand());
    }

    @Override
    public void update(PageData pageData) {
        StreamPageData data = (StreamPageData) pageData;
        GameState gameState = data.getGameState();
        if(gameState==null) messageLabel.setText(Texts.getJoinedWhenEnded());
        else{
            if(gameState.getState()==-1){
                messageLabel.setText(Texts.getBoardSetup());
                LocalTime now = LocalTime.now();
                if(gameState.getReady()[0]) timerLabel1.setText(Texts.getReady());
                else{
                    int diff = gameState.getTimes()[0]-now.toSecondOfDay();
                    if(diff<0) diff+=86400;
                    timerLabel1.setText("0 : "+diff);
                }
                if(gameState.getReady()[1]) timerLabel2.setText(Texts.getReady());
                else{
                    int diff = gameState.getTimes()[1]-now.toSecondOfDay();
                    if(diff<0) diff+=86400;
                    timerLabel2.setText("0 : "+diff);
                }
                paint(gameState);
            }
            else{
                timerLabel2.setText("");
                if(gameState.getState()==0){
                    int diff = gameState.getTimes()[0]-LocalTime.now().toSecondOfDay();
                    if(diff<0) diff+=86400;
                    timerLabel1.setText("0 : "+diff);
                    if(gameState.getThisPlayersTurn()==1) messageLabel.setText(Texts.getFirstsTurn());
                    else messageLabel.setText(Texts.getSecondsTurn()    );
                    paint(gameState);
                }
                else{
                    timerLabel1.setText("");
                    paint(gameState);
                    if(gameState.getState()==1) messageLabel.setText(Texts.getFirstWon());
                    else messageLabel.setText(Texts.getSecondWon());
                }
            }
        }
    }
    private void paint(GameState gameState){
        GraphicsContext g1 = canvas1.getGraphicsContext2D();
        GraphicsContext g2 = canvas2.getGraphicsContext2D();
        g1.setFill(Color.BLACK);
        g2.setFill(Color.BLACK);
        g1.clearRect(0,0,canvas1.getWidth(),canvas1.getHeight());
        g2.clearRect(0,0,canvas1.getWidth(),canvas1.getHeight());
        Cell[][] Cells1 = gameState.getBoards()[0].getCells();
        for(Cell[] cellRow: Cells1){
            for(Cell cell: cellRow){
                if(cell.isBombed()){
                    if(cell.isHasShip())
                        g1.drawImage(explosionImage,cell.getX()*cellW1, cell.getY()*cellH1, cellW1, cellH1);
                    else g1.fillOval(cell.getX()*cellW1+cellW1/5, cell.getY()*cellH1+cellH1/5, cellW1*3/5, cellH1*3/5);
                }
            }
        }
        Cell[][] Cells2 = gameState.getBoards()[1].getCells();
        for(Cell[] cellRow: Cells2){
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
        cellW1 = (int)(canvas1.getWidth()/10);
        cellH1 = (int)(canvas1.getHeight()/10);
        cellW2 = (int)(canvas2.getWidth()/10);
        cellH2 = (int)(canvas2.getHeight()/10);
        setPageType(PageType.gamePage);
        passSelf();
    }
}
