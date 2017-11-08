package edu.ucsb.cs56.projects.games.treasure_hunter;

import javax.swing.*;
import java.awt.*;

/*
  Created by Yusuf Alnawakhtha and Sang Min Oh (CS56, F17, 11/07/2017)
 */

/**
   The <tt>PausePanel</tt> holds all of the components that make up the pause menu for the game.

   @author Yusuf Alnawakhtha and Sang Min Oh
   @version for UCSB CS56, F17, 11/07/2017
*/

class PausePanel extends JPanel {

    /**
       Constructs a <tt>PausePanel</tt> object. The superclass' constructor is called.
     */
    public PausePanel() {
	super();
    }

    /**
       Fills the entire screen with the background color. 

       @param g <tt>Graphics</tt> object
     */
    @Override
    public void paintComponent(Graphics g) {
	g.setColor(getBackground());
	g.fillRect(0, 0, getWidth(), getHeight());
    }
}
