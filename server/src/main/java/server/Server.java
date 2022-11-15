package server;

import server.logic.CLI;
import server.network.SocketStarter;

public class Server {
    public static void main(String[] args) {
        SocketStarter socketStarter = new SocketStarter();
        socketStarter.start();
        new CLI(socketStarter).start();
    }
}
