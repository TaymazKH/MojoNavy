package builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import shared.model.Board;
import shared.model.Cell;
import shared.model.Ship;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Builder {
    public static void main(String[] args) throws IOException {
        Cell[][] cells = new Cell[10][10];
        for(int i=0;i<10;i++) for(int j=0;j<10;j++) cells[i][j] = new Cell(i,j,false);
        ArrayList<Ship> ships = createShips();
        for(Ship ship: ships){
            int x = ship.getX(), y = ship.getY();
            for(int i=0;i<ship.getLength();i++){
                cells[x][y].setHasShip(true);
                if(ship.isHorizontal()) x++;
                else y++;
            }
        }
        Board board = new Board(cells,ships);
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File("./src/main/resources/boards/9.json");
        if(file.exists()) throw new IllegalStateException("file exists");
        objectMapper.writeValue(new FileWriter(file),board);
    }

    private static ArrayList<Ship> createShips(){
        ArrayList<Ship> ships = new ArrayList<>();

        ships.add(new Ship(1,1,1,true));
        ships.add(new Ship(6,1,1,true));
        ships.add(new Ship(2,5,1,true));
        ships.add(new Ship(4,8,1,true));

        ships.add(new Ship(4,6,2,true));
        ships.add(new Ship(0,5,2,false));
        ships.add(new Ship(7,3,2,false));

        ships.add(new Ship(3,1,3,false));
        ships.add(new Ship(0,9,3,true));

        ships.add(new Ship(8,6,4,false));

        return ships;
    }
}
