package server.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import server.util.Config;
import shared.model.Board;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class BoardLoader {
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private final Random random = new Random();
    private final File boardsDirectory;

    public BoardLoader() {
        Config config = Config.getConfig("boardsDirectories");
        boardsDirectory = new File(config.getProperty(String.class,"presetBoards"));
    }

    public synchronized Board loadRandom(){
        try{
            File[] files = boardsDirectory.listFiles();
            int index = random.nextInt(files.length);
            return objectMapper.readValue(files[index],Board.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
