package edu.ucsb.cs56.projects.games.treasure_hunter;

import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/*
  Created by Alex Wood (UCSB CS56, W12, 02/16/2012)
  Edited by Yusuf Alnawakhtha and Sang Min Oh
 */

/**
   Creates the GUI for the Treasure Hunter game. It contains a <tt>GameComponent</tt> and listens for user input. When the user gives an input, an action is performed and reflected on the screen.
 
   @author Alex Wood 
   @author Danielle Dodd and George Lieu
   @author Lisa Liao and Patrick Vidican
   @author Yusuf Alnawakhtha and Sang Min Oh
   @version for UCSB CS56, F17, 11/07/2017
*/

public class GameGui {

    Player player;
    ArrayList<Treasure> theTreasures = new ArrayList<Treasure>();
    GameComponent component;

    public static boolean debug = false;
    public static final String resourcesDir = "/resources/";

    /**
       The main method of the <tt>GameGui</tt>. It creates a <tt>GameGui</tt> and calls the <tt>go()</tt> method.

       @param args A list of arguments that can be passed in by the input
     */
    public static void main(String[] args) {
	if(debug) 
	    System.out.println("Starting main");
	GameGui gui = new GameGui();
	
	if(debug) 
	    System.out.println("In main calling gui.go()");
	gui.go();
    }
    
    /**
       Creates the GUI frame and places all components onto it.
     */
    public void go() {
	JFrame frame = new JFrame();

	// Set the name and frame size
	frame.setSize(608,480);
	frame.setTitle("Treasure Hunter");
       
	// Allows for game window to be closed
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// Randomly places 3 treasures on game map
	component = new GameComponent();
	player = new Player(0,0,16,8,"player");
	component.loadPlayer(player,"player");

	this.placeTheTreasures(5); // change the amount of treasures here
	component.loadTreasure( theTreasures );
	component.loadMap("map.txt");
	addBindings();

	// adds game components and makes the window visible
	frame.add(component);
	frame.setVisible(true);
	component.validate();
	component.repaint();
    }

    /**
       A private inner class that handles player movement when one of the directional keys are pressed. <tt>MoveAction</tt> must be a private inner class because it needs access to the <tt>GameComponent</tt> and <tt>Player</tt> objects in the external <tt>GameGui</tt> class.
     */
    private class MoveAction extends AbstractAction {
	int startingSprite = 0;
	int x = 0;
	int y = 0;
	
	/**
	   Changes the player's sprite to reflect the direction that the player is moving in. Depending on the deltas in coordinates, the player sprite can be changed to standing still while facing north, south, west, or east. The number of the sprite is changed; the actual sprite picture is set in the <tt>Player</tt> object's <tt>setSprite()</tt> method.
	   
	   @param x The amount of steps the player moves in the x-direction
	   @param y The amount of steps the player moves in the y-direction
	*/
	public MoveAction(int x, int y) {
	    if(x == -1)
		startingSprite = 4;
	    if(x == 1)
		startingSprite = 8;
	    if(y == -1)
		startingSprite = 12;
	    if(y == 1)
		startingSprite = 0;
	    this.x = x;
	    this.y = y;
	}

	/**
	   Updates the screen to reflect the player's movement. The sprite is checked to ensure that the move is valid. If the player is able to move, animation is provided to make the player's movement more realistic. 

	   @param e <tt>ActionEvent</tt> object
	 */
	public void actionPerformed(ActionEvent e) {
	    player.setSprite(startingSprite);
	    component.checkMove(player.getXTile() + x, player.getYTile() + y);
	    if(player.isMovable()) {
		//player.setMovable(false);
		for(int i = 0; i < 50; i++) {
		    player.moveTo(player.getXPos() + x,player.getYPos()+y);
		    if(x !=0 || y!=0)
			player.setSprite(startingSprite+i/10);
		    if(player.getCurrentSprite() >= startingSprite + 4 && (x != 0 || y != 0))
			player.setSprite(startingSprite);
		    component.updatePlayer();
		    try {
			Thread.sleep(5);
		    } catch(Exception ex) {}
		}
		player.setTiles(player.getXTile() + x, player.getYTile()+y);
	    }
	    
	    component.validate();
	    component.repaint();
	}

    }

    /**
       Binds keys to the respective actions. 
       <ul>
       <li><i>UP</i> - moves the player one tile upwards</li>
       <li><i>DOWN</i> - moves the player one tile downwards</li>
       <li><i>LEFT</i> - moves the player one tile left</li>
       <li><i>RIGHT</i> - moves the player one tile right</li>
       </ul>
    */
    public void addBindings() {
        // https://docs.oracle.com/javase/7/docs/api/javax/swing/KeyStroke.html
	component.registerKeyboardAction(new MoveAction(0,1), KeyStroke.getKeyStroke("DOWN"), JComponent.WHEN_FOCUSED);
        component.registerKeyboardAction(new MoveAction(0,-1), KeyStroke.getKeyStroke("UP"), JComponent.WHEN_FOCUSED);
        component.registerKeyboardAction(new MoveAction(-1,0), KeyStroke.getKeyStroke("LEFT"), JComponent.WHEN_FOCUSED);
        component.registerKeyboardAction(new MoveAction(1,0), KeyStroke.getKeyStroke("RIGHT"), JComponent.WHEN_FOCUSED);
        component.registerKeyboardAction(new PauseAction(), KeyStroke.getKeyStroke("P"), JComponent.WHEN_FOCUSED);
    }

    /**
       Places the treasures on the map. Since the coordinates of the treasure are checked to make sure that they do not have the same coordinates as a rock (which would make the treasure unreachable since the player cannot walk through rocks). While an overlap of coordinates occurs, the treasure is given new coordinates.

       @param howMany The number of treasures that are to be placed onto the map
     */
    public void placeTheTreasures(int howMany) {
        if(howMany == 0)
	    System.out.println("make at least one treasure!");

        //Treasure first = new Treasure("treasure0");
        //theTreasures.add( first );
        //        System.out.println("first treasure: " + first.getX() + ", " + first.getY() );

        for(int i=0; i<howMany; i++) {
	    Treasure tempTreasure = new Treasure("treasure" + i);
	    while (theTreasures.contains( tempTreasure )) {
		tempTreasure.resetXY();
	    }
	    theTreasures.add( tempTreasure );
	    /*System.out.println("treasure object " + i + ": " +
                              theTreasures.get(i).getX() + ", " +
                              theTreasures.get(i).getY());*/
	    // use resetXY so we don't keep creating new objects on the heap
        }

        // prevents placement of treasures underneath stones //
        // maybe put this in a function cause this code smells //
        for(int i=0; i<theTreasures.size(); ++i) {
	    while((theTreasures.get(i).getX() == 0 && theTreasures.get(i).getY() == 6)
		  || (theTreasures.get(i).getX() == 1 && theTreasures.get(i).getY() == 6)
		  || (theTreasures.get(i).getX() == 3 && theTreasures.get(i).getY() == 8)
      		  || (theTreasures.get(i).getX() == 4 && theTreasures.get(i).getY() == 8)
		  || (theTreasures.get(i).getX() == 5 && theTreasures.get(i).getY() == 8)
		  || (theTreasures.get(i).getX() == 8 && theTreasures.get(i).getY() == 1)
      		  || (theTreasures.get(i).getX() == 8 && theTreasures.get(i).getY() == 6)
		  || (theTreasures.get(i).getX() == 9 && theTreasures.get(i).getY() == 6)
		  || (theTreasures.get(i).getX() == 10 && theTreasures.get(i).getY() == 2)
      		  || (theTreasures.get(i).getX() == 11 && theTreasures.get(i).getY() == 3)
		  || (theTreasures.get(i).getX() == 11 && theTreasures.get(i).getY() == 5)
		  || (theTreasures.get(i).getX() == 11 && theTreasures.get(i).getY() == 6)) {
		theTreasures.get(i).resetXY();
	    }
        }
    }
}
