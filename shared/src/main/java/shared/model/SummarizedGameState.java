package shared.model;

import java.util.Arrays;

public class SummarizedGameState {
    private long[] ids;
    private String[] names;
    private int[] aliveShips,bombsHit;
    private int totalTurnsPlayed;

    public SummarizedGameState(){}
    public SummarizedGameState(long[] ids, String[] names, int[] aliveShips, int[] bombsHit, int totalTurnsPlayed) {
        this.ids = ids;
        this.names = names;
        this.aliveShips = aliveShips;
        this.bombsHit = bombsHit;
        this.totalTurnsPlayed = totalTurnsPlayed;
    }

    public long[] getIds() {
        return ids;
    }
    public String[] getNames() {
        return names;
    }
    public int[] getAliveShips() {
        return aliveShips;
    }
    public int[] getBombsHit() {
        return bombsHit;
    }
    public int getTotalTurnsPlayed() {
        return totalTurnsPlayed;
    }

    public void setIds(long[] ids) {
        this.ids = ids;
    }
    public void setNames(String[] names) {
        this.names = names;
    }
    public void setAliveShips(int[] aliveShips) {
        this.aliveShips = aliveShips;
    }
    public void setBombsHit(int[] bombsHit) {
        this.bombsHit = bombsHit;
    }
    public void setTotalTurnsPlayed(int totalTurnsPlayed) {
        this.totalTurnsPlayed = totalTurnsPlayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SummarizedGameState that = (SummarizedGameState) o;
        return Arrays.equals(ids, that.ids);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(ids);
    }
}
