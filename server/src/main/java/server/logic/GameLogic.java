package server.logic;

import server.db.BoardLoader;
import server.util.Config;
import shared.model.Board;
import shared.model.Cell;
import shared.model.GameState;
import shared.model.Ship;

import java.time.LocalTime;

public class GameLogic {
    private static final BoardLoader boardLoader = new BoardLoader();
    private static final int
            setupTime = Config.getConfig("importantNumbers").getProperty(Integer.class,"setupTime"),
            bonusSetupTime = Config.getConfig("importantNumbers").getProperty(Integer.class,"bonusSetupTime"),
            turnTime = Config.getConfig("importantNumbers").getProperty(Integer.class,"turnTime"),
            dayPassCheckTime = Config.getConfig("importantNumbers").getProperty(Integer.class,"dayPassCheckTime"),
            newSetupCount = Config.getConfig("importantNumbers").getProperty(Integer.class,"newSetupCount");

    public static void click(GameState gameState, int x, int y, int side){
        if(gameState.getThisPlayersTurn()!=side) return;
        int index = side%2;
        Board board = gameState.getBoards()[index];
        Cell attackedCell = board.getCells()[x][y];
        if(!attackedCell.isBombed()){
            attackedCell.setBombed(true);
            if(attackedCell.isHasShip()){
                findShip:{
                    for(Ship ship: board.getShips()){
                        int shipX = ship.getX(), shipY = ship.getY();
                        for(int i=0;i<ship.getLength();i++){
                            if(shipX==x && shipY==y){
                                ship.setHp(ship.getHp()-1);
                                if(ship.getHp()==0){
                                    int maxX=1,maxY=1;
                                    if(ship.isHorizontal()) maxX = ship.getLength();
                                    else maxY = ship.getLength();
                                    for(int destroyX=ship.getX()-1;destroyX<=ship.getX()+maxX;destroyX++){
                                        for(int destroyY=ship.getY()-1;destroyY<=ship.getY()+maxY;destroyY++){
                                            try{
                                                board.getCells()[destroyX][destroyY].setBombed(true);
                                            } catch(IndexOutOfBoundsException ignored){}
                                        }
                                    }
                                    ship.setAlive(false);
                                    boolean ended = true;
                                    for(Ship boardShip: board.getShips()){
                                        if(boardShip.isAlive()){
                                            ended = false;
                                            break;
                                        }
                                    }
                                    if(ended){
                                        gameState.setState(side);
                                    }
                                }
                                break findShip;
                            }
                            else{
                                if(ship.isHorizontal()) shipX++;
                                else shipY++;
                            }
                        }
                    }
                }
            }
            else{
                gameState.setThisPlayersTurn((side%2)+1);
            }
            gameState.setTimes(new int[]{LocalTime.now().plusSeconds(turnTime).toSecondOfDay()});
            gameState.setTotalTurnsPlayed(gameState.getTotalTurnsPlayed()+1);
            gameState.setVersion(gameState.getVersion()+1);
        }
    }

    public static void checkTimerEnd(GameState gameState){
        if(gameState.getState()==-1){
            if(!gameState.getReady()[0]){
                int diff1 = LocalTime.now().toSecondOfDay()-gameState.getTimes()[0];
                if(diff1<dayPassCheckTime && diff1>=0){
                    gameState.setReady(new boolean[]{true,gameState.getReady()[1]});
                    gameState.setVersion(gameState.getVersion()+1);
                }
            }
            if(!gameState.getReady()[1]){
                int diff2 = LocalTime.now().toSecondOfDay()-gameState.getTimes()[1];
                if(diff2<dayPassCheckTime && diff2>=0){
                    gameState.setReady(new boolean[]{gameState.getReady()[0],true});
                    gameState.setVersion(gameState.getVersion()+1);
                }
            }
            if(gameState.getReady()[0] && gameState.getReady()[1]){
                gameState.setState(0);
                gameState.setTimes(new int[]{LocalTime.now().plusSeconds(turnTime).toSecondOfDay()});
                gameState.setVersion(gameState.getVersion()+1);
            }
        }
        else{
            int diff = LocalTime.now().toSecondOfDay()-gameState.getTimes()[0];
            if(diff<dayPassCheckTime && diff>=0){
                gameState.setTimes(new int[]{LocalTime.now().plusSeconds(turnTime).toSecondOfDay()});
                gameState.setThisPlayersTurn((gameState.getThisPlayersTurn()%2)+1);
                gameState.setVersion(gameState.getVersion()+1);
            }
        }
    }

    public static void newSetup(GameState gameState, int side){
        if(side==1){
            gameState.setBoards(new Board[]{boardLoader.loadRandom(), gameState.getBoards()[1]});
            LocalTime time = LocalTime.ofSecondOfDay(gameState.getTimes()[0]).plusSeconds(bonusSetupTime);
            gameState.setTimes(new int[]{time.toSecondOfDay(),gameState.getTimes()[1]});
            gameState.setSetupCountLeft(new int[]{gameState.getSetupCountLeft()[0]-1,gameState.getSetupCountLeft()[1]});
        }
        else{
            gameState.setBoards(new Board[]{gameState.getBoards()[0], boardLoader.loadRandom()});
            LocalTime time = LocalTime.ofSecondOfDay(gameState.getTimes()[1]).plusSeconds(bonusSetupTime);
            gameState.setTimes(new int[]{gameState.getTimes()[0],time.toSecondOfDay()});
            gameState.setSetupCountLeft(new int[]{gameState.getSetupCountLeft()[0],gameState.getSetupCountLeft()[1]-1});
        }
    }

    public static GameState newGameState(long[] ids){
        GameState gameState = new GameState(ids,new Board[]{boardLoader.loadRandom(),boardLoader.loadRandom()});
        int time = LocalTime.now().plusSeconds(setupTime).toSecondOfDay();
        gameState.setTimes(new int[]{time,time});
        gameState.setSetupCountLeft(new int[]{newSetupCount,newSetupCount});
        return gameState;
    }
}
