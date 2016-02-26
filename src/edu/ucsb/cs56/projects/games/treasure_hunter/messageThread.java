package edu.ucsb.cs56.projects.games.treasure_hunter;

import java.lang.Runnable;

public class messageThread implements Runnable{
    
    GameComponent gc;
    
    public messageThread(GameComponent gc) {
	this.gc = gc;
    }
    
    public void setMessage(int treasure_num){
	gc.message = "TREASURE " + treasure_num + " FOUND!";
	run();
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
