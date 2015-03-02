package edu.ucsb.cs56.projects.games.treasure_hunter;

/**
 * Created by Shayan on 3/1/2015.
 */
public class messageRunnable implements Runnable{

    private int value = 0;

    public void startThread(int value) {
        Thread messageThread = new Thread(this);
        this.value = value;
        messageThread.start();
    }
    public void run() {
        winGame(value);
        try{
            Thread.sleep(3500);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        GameComponent.message = "";
    }
    public void winGame(int treasure_number) {
        switch (treasure_number) {
            case 1:
                GameComponent.message = "TREASURE 1 FOUND!";
                break;
            case 2:
                GameComponent.message = "TREASURE 2 FOUND!";
                break;
            case 3:
                GameComponent.message = "TREASURE 3 FOUND!";
                break;
        }

    }
}
