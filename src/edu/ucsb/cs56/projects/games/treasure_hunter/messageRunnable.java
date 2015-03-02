package edu.ucsb.cs56.projects.games.treasure_hunter;

/**
 * This is the message thread GameComponent uses. Whenever the GameComponent.message string is not empty,
 * the message will appear on screen. Created for CS56 Lab07, W15.
 *
 * @author Shayan Sadgh
 * @version for CS56, W15, UCSB, 3/1/2015
 */
public class messageRunnable implements Runnable{

    private int value = 0;

    public void startThread(int value) {
        Thread messageThread = new Thread(this);
        this.value = value;
        messageThread.start();
    }
    public void run() {
        foundTreasure(value);
        try{
            Thread.sleep(3500);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        GameComponent.message = "";
    }
    public void foundTreasure(int treasure_number) {
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
