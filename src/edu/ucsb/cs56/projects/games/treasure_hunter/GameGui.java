package edu.ucsb.cs56.projects.games.treasure_hunter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

	private JFrame frame;

    Player player;
    GameComponent component;

	/** a boolean that is true when the game loop is running
	 */
	boolean running = true;

	/**
	 * Stores the code for the key that is pressed
	 */
	int keypress = 0;

    public static boolean debug = false;
    public static final String resourcesDir = "/resources/";
    
	private int state;

    /**
       The main method of the <tt>GameGui</tt>. It creates a <tt>GameGui</tt> and calls the <tt>go()</tt> method.

       @param args A list of arguments that can be passed in by the input
     */
    public static void main(String[] args) {
	if(debug) 
	    System.out.println("Starting main");
	
	// Create the GUI
	GameGui gui = new GameGui();
	
	if(debug) 
	    System.out.println("In main calling gui.go()");
	
	gui.createMainMenu();
    }
    
    /**
    	Creates the GUI frame and places all of the Main Menu components in it.
    */
    public void createMainMenu() {
    	frame = new JFrame();
    	state = 0;
    	
    	// Set the name and frame size
    	frame.setSize(608, 480);
    	frame.setTitle("Treasure Hunter");
    	
    	// Allows for the game window to be closed
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	// Create the panel and the buttons on the Main Menu
    	JPanel menuPanel = new JPanel();
    	
    	JLabel menuLabel = new JLabel("Main Menu");
    	menuLabel.setForeground(Color.BLACK);
		menuPanel.add(menuLabel);
		menuPanel.add(new JButton(new StartAction("START")));
		menuPanel.setVisible(true);
		
		frame.add(menuPanel);
		frame.setVisible(true);
		
		while(state == 0) {
			try{
			Thread.sleep(1);
			} catch (Exception e) {}
		} 
			frame.getContentPane().removeAll();
			frame = new JFrame();
			frame.getContentPane().setBackground(Color.BLACK);
			try{
			Thread.sleep(2000);
			} catch (Exception e) {}
			go();
    }
    
    /**
    	A private inner class that listens to the "START" button and starts the game when it is pressed.
    */
    private class StartAction extends AbstractAction {
    	/**
    		Constructs a <tt>StartAction</tt> object.
   			
   			@param text The next that appears on the <tt>JButton</tt> and describes the action taken when it is pressed
    	*/
    	public StartAction(String text) {
    		super(text);
    	}
    
    /**
    	Removes everything from the <tt>JFrame</tt> and loads the game.
    	
    	@param e An <tt>ActionEvent</tt> object
    */
    @Override
    public void actionPerformed(ActionEvent e) {
   		state = 1;
    }
    }
    
    /**
       Creates the GUI frame and places all components onto it.
     */
    public void go() {
		frame = new JFrame();

		// Set the name and frame size
		frame.setSize(608,480);
		frame.setTitle("Treasure Hunter");
       
		// Allows for game window to be closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Randomly places 3 treasures on game map
		component = new GameComponent();
		player = new Player(0,0,16,8,"player");
		component.loadPlayer(player,"player");

		// Load the map and randomly set the treasures on the map
		component.loadMap("map.txt");
		component.placeTheTreasures(5); // change the amount of treasures here
		
		// Add a listener that listens for directional key presses and tells the character to move accordingly.
		MoveAction move = new MoveAction();
		PauseAction pause = new PauseAction();
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			    if(!move.moveLeft && !move.moveRight && !move.moveUp && !move.moveDown) {
                    keypress = e.getKeyCode();
                    switch (keypress) {
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
                        case KeyEvent.VK_P:
                        	component.setPause(true);
                        	long pausedTime = pause.drawPauseMenu(frame);
                        	component.pauseTimer(pausedTime);
                        	component.setPause(false);
                        	break;
                    }
                }
			}
			@Override
            public void keyReleased(KeyEvent e){
				keypress = e.getKeyCode();
				switch (keypress) {
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
		
		// adds game components and makes the window visible
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
       A private inner class that handles player movement when one of the directional keys are pressed. 
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
	   Changes the player's sprite to reflect the direction that the player is moving in. Depending on the deltas in coordinates, the player sprite can be changed to standing still while facing north, south, west, or east. The number of the sprite is changed; the actual sprite picture is set in the <tt>Player</tt> object's <tt>setSprite()</tt> method.
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
