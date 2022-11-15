package shared.model;

import java.util.Arrays;

public class GameState {
    private long[] ids;
    private int[] times;
    private Board[] boards;
    private boolean[] ready;
    private int[] setupCountLeft;
    private int thisPlayersTurn, totalTurnsPlayed, state; // -1: setup / 0: active game / 1 or 2: winner
    private int version;

    public GameState(){}
    public GameState(long[] ids, Board[] boards) {
        this.ids = ids;
        this.boards = boards;
        ready = new boolean[]{false,false};
        thisPlayersTurn=1;
        totalTurnsPlayed=0;
        state=-1;
        version=1;
    }

    public long[] getIds() {
        return ids;
    }
    public int[] getTimes() {
        return times;
    }
    public Board[] getBoards() {
        return boards;
    }
    public boolean[] getReady() {
        return ready;
    }
    public int[] getSetupCountLeft() {
        return setupCountLeft;
    }
    public int getThisPlayersTurn() {
        return thisPlayersTurn;
    }
    public int getTotalTurnsPlayed() {
        return totalTurnsPlayed;
    }
    public int getState() {
        return state;
    }
    public int getVersion() {
        return version;
    }

    public void setIds(long[] ids) {
        this.ids = ids;
    }
    public void setTimes(int[] times) {
        this.times = times;
    }
    public void setBoards(Board[] boards) {
        this.boards = boards;
    }
    public void setReady(boolean[] ready) {
        this.ready = ready;
    }
    public void setSetupCountLeft(int[] setupCountLeft) {
        this.setupCountLeft = setupCountLeft;
    }
    public void setThisPlayersTurn(int thisPlayersTurn) {
        this.thisPlayersTurn = thisPlayersTurn;
    }
    public void setTotalTurnsPlayed(int totalTurnsPlayed) {
        this.totalTurnsPlayed = totalTurnsPlayed;
    }
    public void setState(int state) {
        this.state = state;
    }
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameState gameState = (GameState) o;
        return Arrays.equals(ids, gameState.ids);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(ids);
    }
}
