package edu.ucsb.cs56.projects.games.treasure_hunter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
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
    private Player player, player2;
    private GameComponent component, component2;

    private int numTreasures = 5;
    
    /**
     A boolean that is true when the game loop is running
     */
    boolean running = true;
    
    /**
     Stores the code for the key that is pressed
     */
    private int keypress = 0;
    
    public static boolean debug = false;
    public static final String resourcesDir = "/resources/";
    private int state;
    
    private boolean inAnimation, inAnimation2;
    private int frameCount, frameCount2;
    
    /**
     The main method of the <tt>GameGui</tt>. It creates a <tt>GameGui</tt> and calls the <tt>go()</tt> method.
     
     @param args A list of arguments that can be passed in by the input
     */
    public static void main(String[] args) {
        if(debug) System.out.println("Starting main");
        // Create the GUI
        GameGui gui = new GameGui();
        if(debug) System.out.println("In main calling gui.go()");
        gui.createMainMenu();
	gui.createEndMenu();
    }

    private void createEndMenu() {
	JFrame n_frame = new JFrame();
	state = 4;
	n_frame.setSize(608,480);
	n_frame.setTitle("Treasure Hunter");
	n_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	while(state == 4){
	    try{
		Thread.sleep(1);
	    } catch(Exception e) {};
	}
	try {
	    Thread.sleep(100);
	    frame.dispose();
	} catch(Exception e) {}
    }
    
    /**
     Creates the GUI frame and places all of the Main Menu components in it.
     */
    private void createMainMenu() {
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
        menuPanel.add(new JButton(new MultiplayerAction("MULTIPLAYER")));
	menuPanel.add(new JButton(new OptionsAction("OPTIONS")));
        menuPanel.setVisible(true);
        
        // Add the panel to the frame
        frame.add(menuPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(frame.getContentPane());
        frame.setVisible(true);
        
        // Wait until the button is pressed (the state will change)
        while(state == 0) {
            try {
                Thread.sleep(1);
            } catch (Exception e) {}
        }
        try{
            Thread.sleep(100);
            frame.dispose();
        } catch (Exception e) {}
        if(state == 1 || state == 2) go();

	
        if(state == 3) options_go();

	
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
    
    private class MultiplayerAction extends AbstractAction {
        public MultiplayerAction(String text) {
            super(text);
        }
        
        public void actionPerformed(ActionEvent e) {
            state = 2;
        }
    }

    private class OptionsAction extends AbstractAction {

	public OptionsAction(String text) {
	    super(text);
    }


	
	public void actionPerformed(ActionEvent e) {
	    state = 3;
	}
    }
      

	
    

    public void options_go() {

	JFrame new_frame = new JFrame();
	new_frame.setSize(608,480);
	new_frame.setTitle("Treasure Hunter");
	new_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	JPanel optionsPanel = new JPanel();
	JLabel optionsLabel = new JLabel("TREASURE");
	optionsLabel.setForeground(Color.BLACK);
	optionsPanel.add(optionsLabel);
	JTextField tField = new JTextField("Set number of treasures",30);
	optionsPanel.add(tField);

	tField.addActionListener(new ActionListener()
	    {
		public void actionPerformed(ActionEvent e)
		{
		    String input = tField.getText();
		    numTreasures = Integer.parseInt(input);
		}
	    });
		    
	//JButton tButton = new JButton("TREASURE");
	//optionsPanel.add(tButton);
	new_frame.add(optionsPanel, BorderLayout.CENTER);

	//tButton.addActionListener(new ActionListener()
	//  {
	//	public void actionPerformed(ActionEvent e)
	//	{
	//	    state = 5;
	//	}
	//  });
	optionsPanel.setVisible(true);
	new_frame.add(optionsPanel, BorderLayout.CENTER);
	new_frame.setLocationRelativeTo(frame.getContentPane());
	new_frame.setVisible(true);

	

	

    }


    public void treasure_go(){

	JFrame tFrame = new JFrame();
	JPanel tPanel = new JPanel();
	JTextField tField = new JTextField(30);
	tPanel.add(tField);
	tFrame.add(tPanel);
	
    }
    
    /**
     Creates the GUI frame and places all components onto it.
     */
    public void go() {
        frame = new JFrame();
        
        // Set the name and frame size
        if(state == 1) frame.setSize(608, 480);
        else if(state == 2) frame.setSize(1216, 480);
        frame.setTitle("Treasure Hunter");
        
        // Allows for game window to be closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Randomly places 3 treasures on game map
        component = new GameComponent(state);
        player = new Player(0, 0, 16, 8, "player");
        component.loadPlayer(player);
        if(state == 2) {
            player2 = new Player(0, 0, 16, 8, "player");
            component.loadPlayer2(player2);
        }
        
        // Load the map and randomly set the treasures on the map
        component.loadMap("map.txt");
        component.placeTheTreasures(5); // change the amount of treasures here
        
        // Add a listener that listens for directional key presses and tells the character to move accordingly.
        MoveAction move = new MoveAction();
        MoveAction move2 = new MoveAction();
        PauseAction pause = new PauseAction();
        if(state == 1) {
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
                public void keyReleased(KeyEvent e) {
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
        } else if(state ==2) {
            frame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(!move.moveLeft && !move.moveRight && !move.moveUp && !move.moveDown) {
                        keypress = e.getKeyCode();
                        switch (keypress) {
                            case KeyEvent.VK_A:
                                move.moveLeft = true;
                                break;
                            case KeyEvent.VK_D:
                                move.moveRight = true;
                                break;
                            case KeyEvent.VK_W:
                                move.moveUp = true;
                                break;
                            case KeyEvent.VK_S:
                                move.moveDown = true;
                                break;
                            case KeyEvent.VK_LEFT:
                                move2.moveLeft = true;
                                break;
                            case KeyEvent.VK_RIGHT:
                                move2.moveRight = true;
                                break;
                            case KeyEvent.VK_UP:
                                move2.moveUp = true;
                                break;
                            case KeyEvent.VK_DOWN:
                                move2.moveDown = true;
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
                public void keyReleased(KeyEvent e) {
                    keypress = e.getKeyCode();
                    switch (keypress) {
                        case KeyEvent.VK_A:
                            move.moveLeft = false;
                            break;
                        case KeyEvent.VK_D:
                            move.moveRight = false;
                            break;
                        case KeyEvent.VK_W:
                            move.moveUp = false;
                            break;
                        case KeyEvent.VK_S:
                            move.moveDown = false;
                            break;
                        case KeyEvent.VK_LEFT:
                            move2.moveLeft = false;
                            break;
                        case KeyEvent.VK_RIGHT:
                            move2.moveRight = false;
                            break;
                        case KeyEvent.VK_UP:
                            move2.moveUp = false;
                            break;
                        case KeyEvent.VK_DOWN:
                            move2.moveDown = false;
                            break;
                    }
                }
            });
        }
            
        // adds game components and makes the window visible
        frame.add(component);
        frame.setVisible(true);
        component.validate();
        component.repaint();
        inAnimation = false;
        inAnimation2 = false;
        frameCount = 0;
        frameCount2 = 0;
        if(state == 1) {
            while(running) {
                try {
                    Thread.sleep(5);
                } catch(Exception ex) {}
                if(inAnimation) {
                    animate(player, move, frameCount);
                    frameCount++;
                    if(frameCount == 50) inAnimation = false;
                } else {
                    move.x = 0;
                    move.y = 0;
                    component.validate();
                    component.repaint();
                    move.performMovement();
                    if(move.moving()) {
                        player.setSprite(move.startingSprite);
                        component.checkMove(player, player.getXTile() + move.x, player.getYTile() + move.y);
                        if(player.isMovable()) {
                            frameCount = 0;
                            inAnimation = true;
                            player.setTiles(player.getXTile() + move.x, player.getYTile() + move.y);
                        }
                    }
                }
            }
        } else if(state ==2) {
            while(running) {
                try {
                    Thread.sleep(5);
                } catch(Exception ex) {}
                
                if(inAnimation) {
                    animate(player, move, frameCount);
                    frameCount++;
                    if(frameCount == 50) inAnimation = false;
                } else {
                    move.x = 0;
                    move.y = 0;
                    component.validate();
                    component.repaint();
                    move.performMovement();
                    if(move.moving()) {
                        player.setSprite(move.startingSprite);
                        component.checkMove(player, player.getXTile() + move.x, player.getYTile() + move.y);
                        if(player.isMovable()) {
                            frameCount = 0;
                            inAnimation = true;
                            player.setTiles(player.getXTile() + move.x, player.getYTile() + move.y);
                        }
                    }
                }
                
                if(inAnimation2) {
                    animate(player2, move2, frameCount2);
                    frameCount2++;
                    if(frameCount2 == 50) inAnimation2 = false;
                } else {
                    move2.x = 0;
                    move2.y = 0;
                    component.validate();
                    component.repaint();
                    move2.performMovement();
                    if(move2.moving()) {
                        player2.setSprite(move2.startingSprite);
                        component.checkMove(player2, player2.getXTile() + move2.x, player2.getYTile() + move2.y);
                        if(player2.isMovable()) {
                            frameCount2 = 0;
                            inAnimation2 = true;
                            player2.setTiles(player2.getXTile() + move2.x, player2.getYTile() + move2.y);
                        }
                    }
                }
            }
        }
    }
    
    private void animate(Player player, MoveAction move, int frameCount) {
        player.moveTo(player.getXPos() + move.x, player.getYPos() + move.y);
        if(move.moving())
            player.setSprite(move.startingSprite + frameCount / 10);
        if(player.getCurrentSprite() >= move.startingSprite + 4 && move.moving())
            player.setSprite(move.startingSprite);
        component.updatePlayer(player);
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
        
        public boolean moving() {
            return !(x == 0 && y == 0);
        }
        
        /**
         Changes the player's sprite to reflect the direction that the player is moving in. Depending on the deltas in coordinates, the player sprite can be changed to standing still while facing north, south, west, or east. The number of the sprite is changed; the actual sprite picture is set in the <tt>Player</tt> object's <tt>setSprite()</tt> method.
         */
        public void performMovement() {
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
