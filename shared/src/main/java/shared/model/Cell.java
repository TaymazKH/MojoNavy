package shared.model;

import java.util.Objects;

public class Cell {
    private int x,y;
    private boolean isBombed,hasShip;

    public Cell(){}
    public Cell(int x, int y, boolean hasShip) {
        this.x = x;
        this.y = y;
        this.hasShip = hasShip;
        this.isBombed = false;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean isBombed() {
        return isBombed;
    }
    public boolean isHasShip() {
        return hasShip;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setBombed(boolean bombed) {
        isBombed = bombed;
    }
    public void setHasShip(boolean hasShip) {
        this.hasShip = hasShip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x &&
                y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
