package edu.ucsb.cs56.projects.games.treasure_hunter;

import java.awt.Font;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
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
    private BufferedImage keys;
    private int charSprite = 0;
    private int charSprite2 = 0;
    
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
        menuPanel.add(new JButton(new ControlAction("CONTROLS")));
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
                if(state == 4) control_go();
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
    
    private class ControlAction extends AbstractAction {
        
        public ControlAction(String text) {
            super(text);
        }
        
        public void actionPerformed(ActionEvent e) {
            state = 4;
        }
    }
    
    public void options_go() {
        JFrame new_frame = new JFrame();
        new_frame.setSize(720,480);
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
        
        JPanel spritePanel = new JPanel();
        JLabel spriteLabel = new JLabel("Pick a character: Player 1");
        spriteLabel.setForeground(Color.BLACK);
        JButton player1c = new JButton("CAVEMAN");
        player1c.addActionListener(new ActionListener()
                                   {
            public void actionPerformed(ActionEvent e)
            {
                charSprite = 0;
                new_frame.dispose();
                createMainMenu();
            }
        });
        JButton player1p = new JButton("POKEMON TRAINER");
        player1p.addActionListener(new ActionListener()
                                   {
            public void actionPerformed(ActionEvent e)
            {
                charSprite = 1;
                new_frame.dispose();
                createMainMenu();
            }
        });
        
        JButton player1o = new JButton("ORC PIRATE");
        player1o.addActionListener(new ActionListener()
                                   {
            public void actionPerformed(ActionEvent e)
            {
                charSprite = 2;
                new_frame.dispose();
                createMainMenu();
            }
        });
        JButton player1d = new JButton("DETECTIVE");
        player1d.addActionListener(new ActionListener()
                                   {
            public void actionPerformed(ActionEvent e)
            {
                charSprite = 3;
                new_frame.dispose();
                createMainMenu();
            }
        });
        
        spritePanel.add(spriteLabel);
        spritePanel.add(player1c);
        spritePanel.add(player1p);
        spritePanel.add(player1o);
        spritePanel.add(player1d);
        
        JPanel sprite2Panel = new JPanel();
        JLabel sprite2Label = new JLabel("Pick a character: Player 2");
        sprite2Label.setForeground(Color.BLACK);
        JButton player2c = new JButton("CAVEMAN");
        player2c.addActionListener(new ActionListener()
                                   {
            public void actionPerformed(ActionEvent e)
            {
                charSprite2 = 0;
                new_frame.dispose();
                createMainMenu();
            }
        });
        JButton player2p = new JButton("POKEMON TRAINER");
        player2p.addActionListener(new ActionListener()
                                   {
            public void actionPerformed(ActionEvent e)
            {
                charSprite2 = 1;
                new_frame.dispose();
                createMainMenu();
            }
        });
        
        JButton player2o = new JButton("ORC PIRATE");
        player2o.addActionListener(new ActionListener()
                                   {
            public void actionPerformed(ActionEvent e)
            {
                charSprite2 = 2;
                new_frame.dispose();
                createMainMenu();
            }
        });
        
        JButton player2d = new JButton("DETECTIVE");
        player2d.addActionListener(new ActionListener()
                                   {
            public void actionPerformed(ActionEvent e)
            {
                charSprite2 = 3;
                new_frame.dispose();
                createMainMenu();
            }
        });
        
        sprite2Panel.add(sprite2Label);
        sprite2Panel.add(player2c);
        sprite2Panel.add(player2p);
        sprite2Panel.add(player2o);
        sprite2Panel.add(player2d);
        treasurePanel.add(spritePanel);
        treasurePanel.add(sprite2Panel);
        
        new_frame.setLocationRelativeTo(frame.getContentPane());
        new_frame.setVisible(true);
        
    }
    
    public void control_go() {
        
        JFrame cframe = new JFrame();
        cframe.setSize(800,480);
        cframe.setTitle("Controls");
        cframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel cPanel = new JPanel();
        JPanel controlPanel = new JPanel();
        JLabel controlLabel = new JLabel("Use the WASD keys to move your player.");
        JLabel control2Label = new JLabel("If you are playing multiplayer, move the player on the right using the directional keys");
        JLabel control3Label = new JLabel("and move the player on the left with the WASD keys.");
        JLabel control4Label = new JLabel("After the game ends, press enter to return to the main menu.");
        controlLabel.setFont(new Font("Verdana",1,16));
        control2Label.setFont(new Font("Verdana",1,16));
        control3Label.setFont(new Font("Verdana",1,16));
        control4Label.setFont(new Font("Verdana",1,16));
        JButton cButton = new JButton("MAIN MENU");
        controlPanel.setLayout(new GridLayout(4,1));
        controlPanel.add(controlLabel);
        controlPanel.add(control2Label);
        controlPanel.add(control3Label);
        controlPanel.add(control4Label);
        cPanel.add(controlPanel);
        cPanel.add(cButton);
        cButton.addActionListener(new ActionListener()
                                  {
            public void actionPerformed(ActionEvent e)
            {
                cframe.dispose();
                createMainMenu();
            }
        });
        
        cPanel.setVisible(true);
        cframe.add(cPanel, BorderLayout.CENTER);
        cframe.setLocationByPlatform(true);
        cframe.setLocationRelativeTo(frame.getContentPane());
        cframe.setVisible(true);
    }
    
    /**
     Changes the player's sprite to reflect the direction that the player is moving in. Depending on the deltas in coordinates, the player sprite can be changed to standing still while facing north, south, west, or east. The number of the sprite is changed; the actual sprite picture is set in the <tt>Player</tt> object's <tt>setSprite()</tt> method.
     */
    private void performMovement(Player player) {
        if(this.player.equals(player)) {
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
        }
        
        else {
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
    }
    
    private void updatePlayer(Player player) {
        if(player.isInMotion()) {
            player.animate();
            component.updatePlayer(player);
        } else {
            component.validate();
            component.repaint();
            performMovement(player);
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
        player = new Player(0, 0, 16, 8, charSprite, "player");
        component.loadPlayer(player);
        
        // Load the map and randomly set the treasures on the map
        component.loadMap();
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
                
                if(keypress == KeyEvent.VK_ENTER) {
                    if(component.gameOver()) {
                        frame.dispose();
                        new Treasure("treasure").resetNumFound();
                        new GameGui().createMainMenu();
                    }
                }
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
        player = new Player(0, 0, 16, 8, charSprite, "player");
        component.loadPlayer(player);
        player2 = new Player(0, 0, 16, 8, charSprite2, "player");
        component.loadPlayer2(player2);
        
        // Load the map and randomly set the treasures on the map
        component.loadMap();
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
                
                if(keypress == KeyEvent.VK_ENTER) {
                    if(component.gameOver()) {
                        frame.dispose();
                        new Treasure("treasure").resetNumFound();
                        new GameGui().createMainMenu();
                    }
                }
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
