package edu.ucsb.cs56.projects.games.treasure_hunter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;


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
    private GameComponent component;
    private int numTreasures = 5;
    private int timeSet = 0;
    public static boolean debug = false;
    public static final String resourcesDir = "/resources/";
    private int state;
    private boolean[] keyDown = new boolean[200];
    private Timer timer;
    
    /**
     A boolean that is true when the game loop is running
     */
    boolean running = true;
    
    /**
     Stores the code for the key that is pressed
     */
    private int keypress = 0;
    
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
        TimerTask task = new TimerTask() {
            public void run() {
                if(state == 0) return;
                timer.cancel();
                try{
                    Thread.sleep(100);
                    frame.dispose();
                } catch (Exception e) {}
                
                if(state == 3) options_go();
                if(state == 1) go();
                else if(state == 2) goMulti();
            }
        };
        
        timer = new Timer("Timer");
        timer.scheduleAtFixedRate(task, 0, 1);
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
        JPanel treasurePanel = new JPanel();
        JLabel treasureLabel = new JLabel("TREASURE");
        treasureLabel.setForeground(Color.BLACK);
        treasurePanel.add(treasureLabel);
        JTextField tField = new JTextField("Set number of treasures", 30);
        treasurePanel.add(tField);
        
        JButton treasure_enter = new JButton("ENTER");
        treasurePanel.add(treasure_enter);
        treasure_enter.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
		String input = tField.getText();
                numTreasures = Integer.parseInt(input);
                new_frame.dispose();
                createMainMenu();
		
            }
        });
	treasurePanel.setVisible(true);
	new_frame.add(treasurePanel, BorderLayout.CENTER);
       

	JPanel timePanel = new JPanel();
	JLabel timeLabel = new JLabel("TIME");
	timeLabel.setForeground(Color.BLACK);
	timePanel.add(timeLabel);
	JTextField timeField = new JTextField("Set time limit", 30);
	timePanel.add(timeField);
	JButton time_enter = new JButton("ENTER");
	timePanel.add(time_enter);
	time_enter.addActionListener(new ActionListener()
	    {
		public void actionPerformed(ActionEvent e)
		{
		    String timeInput = timeField.getText();
		    timeSet = Integer.parseInt(timeInput);
		    new_frame.dispose();
		    createMainMenu();
		}
	    });

	
        timePanel.setVisible(true);
        treasurePanel.add(timePanel);
        new_frame.setLocationRelativeTo(frame.getContentPane());
        new_frame.setVisible(true);
        
    }
    
    /**
     Changes the player's sprite to reflect the direction that the player is moving in. Depending on the deltas in coordinates, the player sprite can be changed to standing still while facing north, south, west, or east. The number of the sprite is changed; the actual sprite picture is set in the <tt>Player</tt> object's <tt>setSprite()</tt> method.
     */
    private void performMovement() {
        if (keyDown[KeyEvent.VK_A]) {
            player.setX(-1);
            player.setStartingSprite(4);
        } else if (keyDown[KeyEvent.VK_D]) {
            player.setX(1);
            player.setStartingSprite(8);
        } else player.setX(0);
        
        if (keyDown[KeyEvent.VK_W]) {
            player.setY(-1);
            player.setStartingSprite(12);
        } else if (keyDown[KeyEvent.VK_S]) {
            player.setY(1);
            player.setStartingSprite(0);
        } else player.setY(0);
        
        if(state == 1) return;
        
        if (keyDown[KeyEvent.VK_LEFT]) {
            player2.setX(-1);
            player2.setStartingSprite(4);
        } else if (keyDown[KeyEvent.VK_RIGHT]) {
            player2.setX(1);
            player2.setStartingSprite(8);
        } else player2.setX(0);
        
        if (keyDown[KeyEvent.VK_UP]) {
            player2.setY(-1);
            player2.setStartingSprite(12);
        } else if (keyDown[KeyEvent.VK_DOWN]) {
            player2.setY(1);
            player2.setStartingSprite(0);
        } else player2.setY(0);
    }
    
    private void updatePlayer(Player player) {
        if(player.isInMotion()) {
            player.animate();
            component.updatePlayer(player);
        } else {
            component.validate();
            component.repaint();
            performMovement();
            if(player.moving()) {
                player.setSprite(player.getStartingSprite());
                component.checkMove(player);
                if(player.isMovable()) {
                    player.setFrameCount(0);
                    player.setInMotion(true);
                    player.setTiles(player.getXTile() + player.getX(), player.getYTile() + player.getY());
                }
            }
        }
    }
    
    /**
     Creates the GUI frame and places all components onto it.
     */
    public void go() {
        frame = new JFrame();
        
        // Set the name and frame size
        frame.setSize(608, 480);
        frame.setTitle("Treasure Hunter");
        
        // Allows for game window to be closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Randomly places 3 treasures on game map
        component = new GameComponent(state);
        player = new Player(0, 0, 16, 8, "player");
        component.loadPlayer(player);
        
        // Load the map and randomly set the treasures on the map
        component.loadMap("map.txt");
        component.placeTheTreasures(numTreasures);// change the amount of treasures here

	if(timeSet!=0)
	    component.setTimeLimit(timeSet);
	
        
        // Add a listener that listens for directional key presses and tells the character to move accordingly.
        PauseAction pause = new PauseAction();
        
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keypress = e.getKeyCode();
                keyDown[keypress] = true;
                
                if(keypress == KeyEvent.VK_P) {
                    component.setPause(true);
                    long pausedTime = pause.drawPauseMenu(frame);
                    component.pauseTimer(pausedTime);
                    component.setPause(false);
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                keypress = e.getKeyCode();
                keyDown[keypress] = false;
            }
        });
        
        // adds game components and makes the window visible
        frame.add(component);
        frame.setVisible(true);
        component.validate();
        component.repaint();
        TimerTask task = new TimerTask() {
            public void run() {
                updatePlayer(player);
            }
        };
        
        timer = new Timer("Timer");
        timer.scheduleAtFixedRate(task, 0, 10);
    }
    
    public void goMulti() {
        frame = new JFrame();
        
        // Set the name and frame size
        frame.setSize(1216, 480);
        frame.setTitle("Treasure Hunter");
        
        // Allows for game window to be closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Randomly places 3 treasures on game map
        component = new GameComponent(state);
        player = new Player(0, 0, 16, 8, "player");
        component.loadPlayer(player);
        player2 = new Player(0, 0, 16, 8, "player");
        component.loadPlayer2(player2);
        
        // Load the map and randomly set the treasures on the map
        component.loadMap("map.txt");
        component.placeTheTreasures(numTreasures); // change the amount of treasures here
        
        // Add a listener that listens for directional key presses and tells the character to move accordingly.
        PauseAction pause = new PauseAction();
        
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keypress = e.getKeyCode();
                keyDown[keypress] = true;
                
                if(keypress == KeyEvent.VK_P) {
                    component.setPause(true);
                    long pausedTime = pause.drawPauseMenu(frame);
                    component.pauseTimer(pausedTime);
                    component.setPause(false);
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                keypress = e.getKeyCode();
                keyDown[keypress] = false;
            }
        });
        
        // adds game components and makes the window visible
        frame.add(component);
        frame.setVisible(true);
        component.validate();
        component.repaint();
        TimerTask task = new TimerTask() {
            public void run() {
                updatePlayer(player);
                updatePlayer(player2);
            }
        };
        
        timer = new Timer("Timer");
        timer.scheduleAtFixedRate(task, 0, 10);
    }
}
