package shared.model;

import java.util.Objects;

public class Ship {
    private int x,y,length,hp;
    private boolean isHorizontal,isAlive;

    public Ship(){}
    public Ship(int x, int y, int length, boolean isHorizontal) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.isHorizontal = isHorizontal;
        this.hp = length;
        this.isAlive = true;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getLength() {
        return length;
    }
    public int getHp() {
        return hp;
    }
    public boolean isHorizontal() {
        return isHorizontal;
    }
    public boolean isAlive() {
        return isAlive;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return x == ship.x &&
                y == ship.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Ship{" +
                "x=" + x +
                ", y=" + y +
                ", length=" + length +
                ", isHorizontal=" + isHorizontal +
                '}';
    }
}
