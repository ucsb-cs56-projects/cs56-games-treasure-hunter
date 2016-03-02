package edu.ucsb.cs56.projects.games.treasure_hunter;
import java.lang.Runnable;

/*    Allows the "treasure found" message to disappear.    
      @author George Lieu (3/1/16, W16, CS56)
      @author Danielle Dodd (3/1/16, W16, CS56)
      

*/

public class MessageThread implements Runnable{
    
    GameComponent gc;
    
    public MessageThread(GameComponent gc) {
	this.gc = gc;
    }
    
    public void run(){
	
	try{
	    Thread.sleep(5000);
	    gc.message = "";
	}
	catch(InterruptedException ie){
	    System.out.println("Thread was interrupted!");
	}
    }
}
