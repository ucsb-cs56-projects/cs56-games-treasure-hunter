package edu.ucsb.cs56.projects.games.treasure_hunter;

import java.lang.Runnable;

/*
  Created by Danielle Dodd and George Lieu (CS56, W16, 3/1/2016)
  Edited by Yusuf Alnawakhtha and Sang Min Oh
 */

/**
   The <tt>MessageThread</tt> class takes care of displaying the game's messages.

   @author George Lieu
   @author Danielle Dodd
   @author Yusuf Alnawakhtha and Sang Min Oh
   @version for UCSB CS56, F17, 11/07/2017
*/

public class MessageThread implements Runnable{
    
    GameComponent gc;

    /**
       Constructs a <tt>MessageThread</tt> object that contains a <tt>GameComponent</tt> object.

       @param gc <tt>GameComponent</tt> object
     */
    public MessageThread(GameComponent gc) {
	this.gc = gc;
    }

    /**
       Runs the thread. The message is displayed for 5 seconds before it is erased.
     */
    public void run() {
	try{
	    Thread.sleep(5000);
	    gc.setMessage("");
	}
	catch(InterruptedException ie) {
	    System.out.println("Thread was interrupted!");
	}
    }
}
