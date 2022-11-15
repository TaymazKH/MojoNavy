package builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import shared.model.Board;
import shared.model.Ship;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Reader {
    public static void main(String[] args) throws IOException {
        File file = new File("./src/main/resources/boards/2.json");
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        Board board = objectMapper.readValue(file, Board.class);
        for (Ship ship : board.getShips()) {
            System.out.println(ship);
        }
    }
}
