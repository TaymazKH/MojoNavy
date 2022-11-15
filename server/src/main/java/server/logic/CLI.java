package server.logic;

import server.network.SocketStarter;
import server.util.Config;

import java.util.Scanner;

public class CLI extends Thread {
    private final SocketStarter socketStarter;

    public CLI(SocketStarter socketStarter) {
        this.socketStarter = socketStarter;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while(true){
            String command = scanner.nextLine();
            if(Config.getConfig("commands").getProperty(String.class,"stopNow").equals(command)) System.exit(0);
        }
    }
}
