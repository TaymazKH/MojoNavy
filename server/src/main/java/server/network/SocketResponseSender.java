package server.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import shared.request.Request;
import shared.response.Response;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketResponseSender {
    private final Socket socket;
    private final PrintStream printStream;
    public final Scanner scanner;
    private final ObjectMapper objectMapper;

    public SocketResponseSender(Socket socket) throws IOException {
        this.socket = socket;
        printStream = new PrintStream(socket.getOutputStream());
        scanner = new Scanner(socket.getInputStream());
        objectMapper = new ObjectMapper();
    }

    public boolean hasNewRequest(){
        return scanner.hasNext();
    }
    public Request getRequest(){
        try {
            return objectMapper.readValue(scanner.nextLine(),Request.class);
        } catch (IOException e) {
            return null;
        }
    }
    public void sendResponse(Response response){
        try {
            printStream.println(objectMapper.writeValueAsString(response));
        } catch (JsonProcessingException ignored){}
    }
    public void close(){
        try{
            scanner.close();
            printStream.close();
            socket.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
