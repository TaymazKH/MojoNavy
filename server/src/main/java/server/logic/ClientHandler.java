package server.logic;

import server.network.SocketResponseSender;
import shared.model.*;
import shared.request.*;
import shared.response.*;

import java.util.*;

public class ClientHandler extends Thread implements RequestHandler {
    private volatile DataHolder dataHolder;
    private final SocketResponseSender responseSender;

    public ClientHandler(SocketResponseSender responseSender, DataHolder dataHolder){
        this.responseSender = responseSender;
        this.dataHolder = dataHolder;
    }

    @Override
    public void run() {
        try{
            while(true){
                responseSender.sendResponse(responseSender.getRequest().run(this));
            }
        } catch(Exception e){
            closeSocket(responseSender);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Response handleNewGameRequest(NewGameRequest newGameRequest) {
        synchronized(dataHolder.getWaiting()){
            int waiting = dataHolder.getWaiting();
            if(waiting==-1) dataHolder.setWaiting(newGameRequest.getAuthToken());
            else if(waiting!=newGameRequest.getAuthToken()){
                long[] ids = new long[]{getID(waiting),getID(newGameRequest.getAuthToken())};
                GameState gameState = GameLogic.newGameState(ids);
                synchronized(dataHolder.getActiveGames()){
                    dataHolder.getActiveGames().put(waiting,gameState);
                    dataHolder.getActiveGames().put(newGameRequest.getAuthToken(),gameState);
                }
                synchronized(dataHolder.getGameSummaries()){
                    dataHolder.getGameSummaries().add(summarizeGameState(gameState));
                }
                String name = dataHolder.getDataBaseAgent().getUsername(waiting);
                dataHolder.setWaiting(-1);
                return new GameStateResponse(secureGameState(gameState,2),name,2);
            }
        }
        return new GameStateResponse(null,null,-1);
    }
    @Override
    public Response handleNewSetupRequest(NewSetupRequest newSetupRequest) {
        GameState gameState = getGameState(newSetupRequest.getAuthToken());
        long id = getID(newSetupRequest.getAuthToken());
        synchronized(gameState){
            if(gameState.getIds()[0]==id){
                if(gameState.getSetupCountLeft()[0]>0)
                    GameLogic.newSetup(gameState,1);
                return new GameStateResponse(secureGameState(gameState,1),dataHolder.getDataBaseAgent().getUsername(gameState.getIds()[1]),1);
            }
            else{
                if(gameState.getSetupCountLeft()[1]>0)
                    GameLogic.newSetup(gameState,2);
                return new GameStateResponse(secureGameState(gameState,2),dataHolder.getDataBaseAgent().getUsername(gameState.getIds()[0]),2);
            }
        }
    }
    @Override
    public Response handleSetReadyRequest(SetReadyRequest setReadyRequest) {
        GameState gameState = getGameState(setReadyRequest.getAuthToken());
        long id = getID(setReadyRequest.getAuthToken());
        synchronized(gameState){
            if(gameState.getIds()[0]==id){
                gameState.setReady(new boolean[]{true,gameState.getReady()[1]});
                return new GameStateResponse(secureGameState(gameState,1),dataHolder.getDataBaseAgent().getUsername(gameState.getIds()[1]),1);
            }
            else{
                gameState.setReady(new boolean[]{gameState.getReady()[0],true});
                return new GameStateResponse(secureGameState(gameState,2),dataHolder.getDataBaseAgent().getUsername(gameState.getIds()[0]),2);
            }
        }
    }
    @Override
    public Response handleClickRequest(ClickRequest clickRequest) {
        GameState gameState = getGameState(clickRequest.getAuthToken());
        long id = getID(clickRequest.getAuthToken());
        if(gameState!=null && gameState.getState()==0){
            synchronized(gameState){
                int side;
                if(gameState.getIds()[0]==id) side=1;
                else side=2;
                GameLogic.click(gameState, clickRequest.getX(), clickRequest.getY(), side);
                SummarizedGameState summarizedGameState = new SummarizedGameState();
                summarizedGameState.setIds(gameState.getIds());
                synchronized(dataHolder.getGameSummaries()){
                    int index = dataHolder.getGameSummaries().indexOf(summarizedGameState);
                    dataHolder.getGameSummaries().add(index,summarizeGameState(gameState));
                    dataHolder.getGameSummaries().remove(index+1);
                }
                if(gameState.getState()>0){
                    recordWin(gameState);
                    removeGameState(clickRequest.getAuthToken());
                }
                return new GameStateResponse(secureGameState(gameState,side),dataHolder.getDataBaseAgent().getUsername(gameState.getIds()[side%2]),side);
            }
        }
        else return new GameStateResponse(null,null,-1);
    }
    @Override
    public Response handleGetGameStateRequest(GetGameStateRequest getGameStateRequest) {
        GameState gameState = getGameState(getGameStateRequest.getAuthToken());
        if(gameState!=null){
            synchronized(gameState){
                long id = getID(getGameStateRequest.getAuthToken());
                int side;
                if(gameState.getIds()[0]==id) side=1;
                else if(gameState.getIds()[1]==id) side=2;
                else side=-1;
                GameLogic.checkTimerEnd(gameState);
                if(gameState.getState()>0) removeGameState(getGameStateRequest.getAuthToken());
                return new GameStateResponse(secureGameState(gameState,side),dataHolder.getDataBaseAgent().getUsername(gameState.getIds()[side%2]),side);
            }
        }
        else return new GameStateResponse(null,null,-1);
    }
    @Override
    public Response handleLoginRequest(String username, String password) {
        User user = dataHolder.getDataBaseAgent().getUser(username);
        if(user==null) return new LoginResponse(-1,1);
        else if(!user.getPassword().equals(password)) return new LoginResponse(-1,2);
        else{
            synchronized(dataHolder.getOnlinePlayers()){
                if(dataHolder.getOnlinePlayers().containsValue(user.getId()))
                    return new LoginResponse(-1,3);
            }
        }
        return successfulLogin(username);
    }
    @Override
    public Response handleSignupRequest(String username, String password) {
        if(dataHolder.getDataBaseAgent().getUsernames().contains(username)) return new LoginResponse(-1,4);
        else{
            User user = new User(username, password, dataHolder.getDataBaseAgent().generateNewUserID());
            dataHolder.getDataBaseAgent().saveUser(user);
            dataHolder.getDataBaseAgent().updateIDUsernameMapFiles(user);
            dataHolder.getDataBaseAgent().updateScoresMapFile(user);
            return successfulLogin(username);
        }
    }
    @Override
    public Response handleProfileRequest(ProfileRequest profileRequest) {
        User user = dataHolder.getDataBaseAgent().getUser(getID(profileRequest.getAuthToken()));
        user.setPassword("");
        return new ProfileResponse(user);
    }
    @Override
    public Response handleGetScoreBoardRequest() {
        LinkedHashMap<String,Integer> output1 = new LinkedHashMap<>();
        LinkedHashMap<String,Boolean> output2 = new LinkedHashMap<>();
        HashMap<Long,String> idMap = dataHolder.getDataBaseAgent().getIDMap();
        LinkedHashMap<Long,Integer> scoresMap = dataHolder.getDataBaseAgent().getScoresMap();
        ArrayList<Long> onlineUsers;
        synchronized(dataHolder.getOnlinePlayers()){
            onlineUsers = new ArrayList<>(dataHolder.getOnlinePlayers().values());
        }
        String username;
        for(Map.Entry<Long,Integer> m: scoresMap.entrySet()){
            username = idMap.get(m.getKey());
            output1.put(username,m.getValue());
            output2.put(username,onlineUsers.contains(m.getKey()));
        }
        return new ScoreBoardResponse(output1,output2);
    }
    @Override
    public Response handleGetActiveGamesRequest() {
        ArrayList<SummarizedGameState> summarizedGameStates;
        synchronized(dataHolder.getGameSummaries()){
            summarizedGameStates = new ArrayList<>(dataHolder.getGameSummaries());
        }
        return new ActiveGamesResponse(summarizedGameStates);
    }
    @Override
    public Response handleWatchGameRequest(WatchGameRequest watchGameRequest) {
        GameState gameState = null;
        synchronized(dataHolder.getActiveGames()){
            for(Map.Entry<Integer,GameState> m: dataHolder.getActiveGames().entrySet()){
                if(Arrays.equals(m.getValue().getIds(),watchGameRequest.getSummarizedGameState().getIds())){
                    gameState = m.getValue();
                    break;
                }
            }
        }
        if(gameState!=null){
            synchronized(dataHolder.getActiveGames()){
                dataHolder.getActiveGames().put(watchGameRequest.getAuthToken(), gameState);
            }
            return new StreamResponse(secureGameState(gameState,-1));
        }
        else return new StreamResponse(null);
    }
    @Override
    public Response handleGetStreamGameStateRequest(GetStreamGameStateRequest getStreamGameStateRequest) {
        GameState gameState = getGameState(getStreamGameStateRequest.getAuthToken());
        if(gameState!=null){
            synchronized(gameState){
                if(gameState.getState()>0) removeGameState(getStreamGameStateRequest.getAuthToken());
                return new StreamResponse(secureGameState(gameState,-1));
            }
        }
        else return new StreamResponse(null);
    }
    @Override
    public Response handleStopWatchingRequest(StopWatchingRequest stopWatchingRequest) {
        synchronized(dataHolder.getActiveGames()){
            dataHolder.getActiveGames().remove(stopWatchingRequest.getAuthToken());
        }
        return new StoppedWatchingResponse();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private Response successfulLogin(String username){
        int authToken;
        synchronized(dataHolder.getRandom()){
            authToken = dataHolder.getRandom().nextInt(Integer.MAX_VALUE);
        }
        synchronized(dataHolder.getAuthTokens()){
            dataHolder.getAuthTokens().put(responseSender,authToken);
        }
        synchronized(dataHolder.getOnlinePlayers()){
            dataHolder.getOnlinePlayers().put(authToken,dataHolder.getDataBaseAgent().getUsernameMap().get(username));
        }
        return new LoginResponse(authToken,0);
    }
    private GameState getGameState(int authToken){
        GameState gameState;
        synchronized(dataHolder.getActiveGames()){
            gameState = dataHolder.getActiveGames().get(authToken);
        }
        return gameState;
    }
    private GameState secureGameState(GameState gameState, int side){
        Board board1,board2;
        Cell[][] oldCells;
        Cell[][] newCells = new Cell[10][10];
        if(side==1){
            oldCells = gameState.getBoards()[1].getCells();
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(oldCells[i][j].isHasShip() && !oldCells[i][j].isBombed())
                        newCells[i][j] = new Cell(i,j,false);
                    else{
                        newCells[i][j] = new Cell(i,j,oldCells[i][j].isHasShip());
                        newCells[i][j].setBombed(oldCells[i][j].isBombed());
                    }
                }
            }
            board1 = gameState.getBoards()[0];
            board2 = new Board(newCells,new ArrayList<>());
        }
        else if(side==2){
            oldCells = gameState.getBoards()[0].getCells();
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(oldCells[i][j].isHasShip() && !oldCells[i][j].isBombed())
                        newCells[i][j] = new Cell(i,j,false);
                    else{
                        newCells[i][j] = new Cell(i,j,oldCells[i][j].isHasShip());
                        newCells[i][j].setBombed(oldCells[i][j].isBombed());
                    }
                }
            }
            board1 = new Board(newCells,new ArrayList<>());
            board2 = gameState.getBoards()[1];
        }
        else{
            oldCells = gameState.getBoards()[0].getCells();
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(oldCells[i][j].isHasShip() && !oldCells[i][j].isBombed())
                        newCells[i][j] = new Cell(i,j,false);
                    else{
                        newCells[i][j] = new Cell(i,j,oldCells[i][j].isHasShip());
                        newCells[i][j].setBombed(oldCells[i][j].isBombed());
                    }
                }
            }
            board1 = new Board(newCells,new ArrayList<>());
            oldCells = gameState.getBoards()[1].getCells();
            newCells = new Cell[10][10];
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(oldCells[i][j].isHasShip() && !oldCells[i][j].isBombed())
                        newCells[i][j] = new Cell(i,j,false);
                    else{
                        newCells[i][j] = new Cell(i,j,oldCells[i][j].isHasShip());
                        newCells[i][j].setBombed(oldCells[i][j].isBombed());
                    }
                }
            }
            board2 = new Board(newCells,new ArrayList<>());
        }
        GameState newGameState = new GameState();
        newGameState.setIds(gameState.getIds());
        newGameState.setTimes(gameState.getTimes());
        newGameState.setBoards(new Board[]{board1,board2});
        newGameState.setReady(gameState.getReady());
        newGameState.setSetupCountLeft(gameState.getSetupCountLeft());
        newGameState.setThisPlayersTurn(gameState.getThisPlayersTurn());
        newGameState.setTotalTurnsPlayed(gameState.getTotalTurnsPlayed());
        newGameState.setState(gameState.getState());
        newGameState.setVersion(gameState.getVersion());
        return newGameState;
    }
    private SummarizedGameState summarizeGameState(GameState gameState){
        String[] names = new String[]{
                dataHolder.getDataBaseAgent().getUsername(gameState.getIds()[0]),
                dataHolder.getDataBaseAgent().getUsername(gameState.getIds()[1])};
        int[] aliveShips = new int[]{0,0}, bombsHit = new int[]{0,0};
        for(int i=0;i<2;i++){
            for(Ship ship: gameState.getBoards()[i].getShips()){
                if(ship.isAlive()) aliveShips[i]++;
                bombsHit[(i+1)%2] += ship.getLength()-ship.getHp();
            }
        }
        return new SummarizedGameState(gameState.getIds(), names, aliveShips, bombsHit, gameState.getTotalTurnsPlayed());
    }
    private void recordWin(GameState gameState){
        synchronized(dataHolder.getDataBaseAgent()){
            User winner = dataHolder.getDataBaseAgent().getUser(gameState.getIds()[gameState.getState()-1]);
            winner.setWinCount(winner.getWinCount()+1);
            dataHolder.getDataBaseAgent().saveUser(winner);
            dataHolder.getDataBaseAgent().updateScoresMapFile(winner);
            User loser = dataHolder.getDataBaseAgent().getUser(gameState.getIds()[gameState.getState()%2]);
            loser.setLoseCount(loser.getLoseCount()+1);
            dataHolder.getDataBaseAgent().saveUser(loser);
            dataHolder.getDataBaseAgent().updateScoresMapFile(loser);
        }
    }
    private void removeGameState(int authToken){
        GameState gameState;
        synchronized(dataHolder.getActiveGames()){
            gameState = dataHolder.getActiveGames().remove(authToken);
        }
        if(gameState!=null){
            synchronized(dataHolder.getGameSummaries()){
                dataHolder.getGameSummaries().remove(summarizeGameState(gameState));
            }
        }
    }
    private long getID(int authToken){
        long id;
        synchronized(dataHolder.getOnlinePlayers()){
            id = dataHolder.getOnlinePlayers().get(authToken);
        }
        return id;
    }
    private void closeSocket(SocketResponseSender responseSender){
        responseSender.close();
        int authToken=-1;
        synchronized(dataHolder.getAuthTokens()){
            if(dataHolder.getAuthTokens().containsKey(responseSender))
                authToken = dataHolder.getAuthTokens().remove(responseSender);
        }
        if(authToken!=-1){
            long id;
            GameState gameState;
            synchronized(dataHolder.getWaiting()){
                if(dataHolder.getWaiting()==authToken)
                    dataHolder.setWaiting(-1);
            }
            synchronized(dataHolder.getOnlinePlayers()){
                id = dataHolder.getOnlinePlayers().remove(authToken);
            }
            synchronized(dataHolder.getActiveGames()){
                gameState = dataHolder.getActiveGames().remove(authToken);
            }
            if(gameState!=null){
                synchronized(gameState){
                    if(gameState.getIds()[0]==id) gameState.setState(2);
                    else if(gameState.getIds()[1]==id) gameState.setState(1);
                    if(gameState.getState()>0){
                        recordWin(gameState);
                        synchronized(dataHolder.getGameSummaries()){
                            dataHolder.getGameSummaries().remove(summarizeGameState(gameState));
                        }
                    }
                }
            }
        }
    }
}
