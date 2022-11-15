package server.db;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import server.util.Config;
import shared.model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DataBaseAgent {
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private final File IDMapFile,usernameMapFile, scoresMapFile,usersDirectory;

    public DataBaseAgent() {
        Config config = Config.getConfig("databaseDirectories");
        usersDirectory = new File(config.getProperty(String.class,"usersDirectory"));
        IDMapFile = new File(config.getProperty(String.class, "idMapFile"));
        usernameMapFile = new File(config.getProperty(String.class,"usernameMapFile"));
        scoresMapFile = new File(config.getProperty(String.class,"scoresMapFile"));
    }

    public synchronized HashMap<Long,String> getIDMap(){
        try{
            if(IDMapFile.createNewFile()){
                return new HashMap<>();
            }
            else{
                return objectMapper.readValue(IDMapFile, new TypeReference<HashMap<Long,String>>(){});
            }
        } catch(IOException e){
            return new HashMap<>();
        }
    }
    public synchronized HashMap<String,Long> getUsernameMap(){
        try{
            if(usernameMapFile.createNewFile()){
                return new HashMap<>();
            }
            else{
                return objectMapper.readValue(usernameMapFile, new TypeReference<HashMap<String,Long>>(){});
            }
        } catch(IOException e){
            return new HashMap<>();
        }
    }
    public synchronized void updateIDUsernameMapFiles(User user){
        HashMap<Long,String> IDMap = getIDMap();
        HashMap<String,Long> usernameMap = getUsernameMap();
        IDMap.put(user.getId(),user.getUsername());
        usernameMap.put(user.getUsername(),user.getId());
        try {
            objectMapper.writeValue(new FileWriter(IDMapFile),IDMap);
            objectMapper.writeValue(new FileWriter(usernameMapFile),usernameMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized LinkedHashMap<Long,Integer> getScoresMap(){
        try{
            if(scoresMapFile.createNewFile()){
                return new LinkedHashMap<>();
            }
            else{
                return objectMapper.readValue(scoresMapFile, new TypeReference<LinkedHashMap<Long,Integer>>(){});
            }
        } catch(IOException e){
            return new LinkedHashMap<>();
        }
    }
    public synchronized void updateScoresMapFile(User user){
        LinkedHashMap<Long,Integer> scoresMap = getScoresMap();
        LinkedHashMap<Long,Integer> sortedScoresMap = new LinkedHashMap<>();
        scoresMap.put(user.getId(),user.getWinCount()-user.getLoseCount());
        long id; int score;
        LinkedList<Integer> values = new LinkedList<>(scoresMap.values());
        values.sort(Collections.reverseOrder());
        Iterator<Integer> valueIt = values.iterator();
        while(valueIt.hasNext()){
            score = valueIt.next();
            Iterator<Long> keyIt = new LinkedList<>(scoresMap.keySet()).iterator();
            while(keyIt.hasNext()){
                id = keyIt.next();
                int val2 = scoresMap.get(id);
                if(score==val2){
                    scoresMap.remove(id);
                    sortedScoresMap.put(id,score);
                    break;
                }
            }
        }
        try {
            objectMapper.writeValue(new FileWriter(scoresMapFile),sortedScoresMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized User getUser(String username){
        if(!getUsernameMap().containsKey(username)){
            return null;
        }
        return getUser(getUsernameMap().get(username));
    }
    public synchronized User getUser(long id){
        try{
            return objectMapper.readValue(new File(usersDirectory,id+".json"),User.class);
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    public synchronized LinkedList<String> getUsernames(){
        return new LinkedList<>(getIDMap().values());
    }
    public synchronized String getUsername(long id){
        return getIDMap().get(id);
    }
    public synchronized void saveUser(User user){
        try{
            objectMapper.writeValue(new FileWriter(usersDirectory.getPath()+"/"+user.getId()+".json"),user);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public synchronized long generateNewUserID(){
        try {
            File jsonFile = new File(usersDirectory,"latestUserID.json");
            if(!jsonFile.createNewFile()){
                long newID = objectMapper.readValue(jsonFile,long.class)+1;
                objectMapper.writeValue(new FileWriter(jsonFile),newID);
                return newID;
            }
            else{
                objectMapper.writeValue(new FileWriter(jsonFile),1);
                return 1;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
