package server.network;

import server.logic.ClientHandler;
import server.logic.DataHolder;
import server.util.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketStarter extends Thread {
    @Override
    public void run() {
        try {
            ServerSocket serverSocket
                    = new ServerSocket(Config.getConfig("connection").getProperty(Integer.class,"port"));
            DataHolder dataHolder = new DataHolder();
            while(true){
                Socket socket = serverSocket.accept();
                new ClientHandler(new SocketResponseSender(socket),dataHolder).start();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
