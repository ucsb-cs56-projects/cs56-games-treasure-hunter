package edu.ucsb.cs56.projects.games.treasure_hunter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

public class GameGui{

    Player player;
    //ArrayList<Treasure> theTreasures = new ArrayList<Treasure>();
    GameComponent component;

	/** a boolean that is true when the game loop is running
	 */
	boolean running = true;

	/**
	 * Stores the movement's key input
	 */
	int direction = 0;

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

		
		//component.loadTreasure();
		component.loadMap("map.txt");
		component.placeTheTreasures(5); // change the amount of treasures here
		MoveAction move = new MoveAction();


		// adds game components and makes the window visible

		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			    if(!move.moveLeft && !move.moveRight && !move.moveUp && !move.moveDown) {
                    direction = e.getKeyCode();
                    switch (direction) {
                        case KeyEvent.VK_LEFT:
                            move.moveLeft = true;
                            break;
                        case KeyEvent.VK_RIGHT:
                            move.moveRight = true;
                            break;
                        case KeyEvent.VK_UP:
                            move.moveUp = true;
                            break;
                        case KeyEvent.VK_DOWN:
                            move.moveDown = true;
                            break;
                    }
                }
			}
			@Override
            public void keyReleased(KeyEvent e){
				direction = e.getKeyCode();
				switch (direction) {
					case KeyEvent.VK_LEFT:
						move.moveLeft = false;
						break;
					case KeyEvent.VK_RIGHT:
						move.moveRight = false;
						break;
					case KeyEvent.VK_UP:
						move.moveUp = false;
						break;
					case KeyEvent.VK_DOWN:
						move.moveDown = false;
						break;
				}
            }
		});
		frame.add(component);
		frame.setVisible(true);
		component.validate();
		component.repaint();
		while(running)
			{
				move.PerformMovement();
				player.setSprite(move.startingSprite);
				component.checkMove(player.getXTile() + move.x, player.getYTile() + move.y);
				if(player.isMovable()) {
					for(int i = 0; i < 50; i++) {
						player.moveTo(player.getXPos() + move.x,player.getYPos()+move.y);
						if(move.x !=0 || move.y!=0)
							player.setSprite(move.startingSprite+i/10);
						if(player.getCurrentSprite() >= move.startingSprite + 4 && (move.x != 0 || move.y != 0))
							player.setSprite(move.startingSprite);
						component.updatePlayer();
						try {
							Thread.sleep(5);
						} catch(Exception ex) {}
					}
					player.setTiles(player.getXTile() + move.x, player.getYTile()+move.y);
				}
				move.x = 0;
				move.y = 0;
				component.validate();
				component.repaint();
			}
    	}


	/**
       A private inner class that handles player movement when one of the directional keys are pressed. <tt>MoveAction</tt> must be a private inner class because it needs access to the <tt>GameComponent</tt> and <tt>Player</tt> objects in the external <tt>GameGui</tt> class.
     */
    private class MoveAction {
	int startingSprite = 0;
	int x = 0;
	int y = 0;

	boolean moveLeft;
	boolean moveRight;
	boolean moveUp;
	boolean moveDown;
	
	/**
	 * TODO: fix javadoc comment
	   Changes the player's sprite to reflect the direction that the player is moving in. Depending on the deltas in coordinates, the player sprite can be changed to standing still while facing north, south, west, or east. The number of the sprite is changed; the actual sprite picture is set in the <tt>Player</tt> object's <tt>setSprite()</tt> method.
	   
	   @param x The amount of steps the player moves in the x-direction
	   @param y The amount of steps the player moves in the y-direction
	*/
	public void PerformMovement() {
	    if(x == 0 && y ==0) {
            if (moveLeft) {
                x = -1;
                startingSprite = 4;
            }
            if (moveRight) {
                x = 1;
                startingSprite = 8;
            }
            if (moveUp) {
                y = -1;
                startingSprite = 12;
            }
            if (moveDown) {
                y = 1;
                startingSprite = 0;
            }
        }
	}

    }




}
