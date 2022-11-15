package server.logic;

import server.db.DataBaseAgent;
import server.network.SocketResponseSender;
import shared.model.GameState;
import shared.model.SummarizedGameState;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.LinkedList;

public class DataHolder {
    private volatile DataBaseAgent dataBaseAgent = new DataBaseAgent();
    private volatile Integer waiting=-1;
    private volatile SecureRandom random = new SecureRandom();
    private volatile HashMap<Integer,GameState> activeGames = new HashMap<>();
    private volatile LinkedList<SummarizedGameState> gameSummaries = new LinkedList<>();
    private volatile HashMap<SocketResponseSender,Integer> authTokens = new HashMap<>();
    private volatile HashMap<Integer,Long> onlinePlayers = new HashMap<>();

    public synchronized DataBaseAgent getDataBaseAgent() {
        return dataBaseAgent;
    }
    public synchronized Integer getWaiting() {
        return waiting;
    }
    public synchronized SecureRandom getRandom() {
        return random;
    }
    public synchronized HashMap<Integer, GameState> getActiveGames() {
        return activeGames;
    }
    public synchronized LinkedList<SummarizedGameState> getGameSummaries() {
        return gameSummaries;
    }
    public synchronized HashMap<SocketResponseSender, Integer> getAuthTokens() {
        return authTokens;
    }
    public synchronized HashMap<Integer, Long> getOnlinePlayers() {
        return onlinePlayers;
    }

    public synchronized void setWaiting(Integer waiting) {
        this.waiting = waiting;
    }
}
