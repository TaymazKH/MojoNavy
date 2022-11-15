package client.util;

import client.agent.MainController;
import shared.request.GetActiveGamesRequest;
import shared.request.GetGameStateRequest;
import shared.request.GetScoreBoardRequest;
import shared.request.GetStreamGameStateRequest;

public class Loop extends Thread {
    private final int delay = 500;
    private final MainController mainController;
    private volatile boolean keepRunning = true, active = false;
    private volatile int taskCode;

    public Loop(MainController mainController){
        this.mainController = mainController;
    }

    @Override
    public void run() {
        while(keepRunning){
            try {
                if(!active){
                    synchronized(this){
                        wait();
                    }
                    continue;
                }
                Thread.sleep(delay);
                switch(taskCode){
                    case 1-> sendGetGameStateRequest();
                    case 2-> sendGetScoreBoardRequest();
                    case 3-> sendGetActiveGamesRequest();
                    case 4-> sendGetStreamGameStateRequest();
                }
            } catch(InterruptedException ignored){}
        }
    }

    public boolean isActive() {
        return active;
    }
    public void setTaskCodeAndStart(int taskCode) {
        this.taskCode = taskCode;
        active = true;
        synchronized(this){
            notifyAll();
        }
    }
    public void stopLater(){
        keepRunning = false;
        synchronized(this){
            notifyAll();
        }
    }
    public void deactivate(){
        active = false;
    }

    private void sendGetGameStateRequest(){
        mainController.loopSendRequest(new GetGameStateRequest());
    }
    private void sendGetScoreBoardRequest(){
        mainController.loopSendRequest(new GetScoreBoardRequest());
    }
    private void sendGetActiveGamesRequest(){
        mainController.loopSendRequest(new GetActiveGamesRequest());
    }
    private void sendGetStreamGameStateRequest(){
        mainController.loopSendRequest(new GetStreamGameStateRequest());
    }
}
