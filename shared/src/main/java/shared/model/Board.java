package shared.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Board {
    private Cell[][] cells;
    private ArrayList<Ship> ships;

    public Board(){}
    public Board(Cell[][] cells, ArrayList<Ship> ships) {
        this.cells = cells;
        this.ships = ships;
    }

    public Cell[][] getCells() {
        return cells;
    }
    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }
    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Arrays.equals(cells, board.cells) &&
                ships.equals(board.ships);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(ships);
        result = 31 * result + Arrays.hashCode(cells);
        return result;
    }
}
