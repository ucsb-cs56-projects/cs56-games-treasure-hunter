package edu.ucsb.cs56.projects.games.treasure_hunter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/*
  A component that draws the map for the treasure hunter game by Alex Wood (CS56, W12, UCSB, 2/16/2012)
  Edited by Danielle Dodd and George Lieu
  Edited by Lisa Liao and Patrick Vidican
  Edited by Yusuf Alnawakhtha and Sang Min Oh
*/

/**
   A component that draws the map for the Treasure Hunter game.
   
   @author Alex Wood
   @author Danielle Dodd and George Lieu
   @author Lisa Liao and Patrick Vidican
   @author Yusuf Alnawakhtha and Sang Min Oh
   @version for UCSB CS56, F17, 11/07/2017
   
*/

public class GameComponent extends JComponent
{
    Player player;
    ArrayList<Treasure> theTreasures;
    
    private char[][] tiletypes;
    private int[][] treasureMap;
    
    private String message = "";

    private int tilesWidth;
    private int tilesHeight;
    
    private BufferedImage grassTile;
    private BufferedImage bushTile;
    private BufferedImage stoneTile;
    

    /**
     * The initial time that the game starts at and the time limit of the game.
     */
    long startTime;
    
    /**
     * The time limit for the game
     */
    private static int timeLimit = 50;
    
    /**
     * The amount of time the game has been paused for
     */
    private static int pausedTime = 0;
    
    /**
     * Boolean that checks if the game is paused
     */
    private boolean pause = false;
    
    /**
     * The time string that is printed onto the screen
     */
    String time;
    
    /**
     * States if the game has been won
     */
    boolean winningCondition = false;
    
    /**
       Constructs a <tt>GameComponent</tt> object. 
    */
    public GameComponent() {
	
	message = "";

	
	startTime = System.currentTimeMillis();
    }
    
    /**
       Sets the message of this <tt>GameComponent</tt> object.
       
       @param newMessage The new message that this <tt>GameComponent</tt> object should have
    */
    public void setMessage(String newMessage) {
	message = newMessage;
    }
    
    /**
       Draws all components onto the screen. All of the tiles and the player sprite are drawn onto the screen. When a player finds a treasure, draws the <i>"TREASURE # FOUND"</i> message onto the screen. Keeps track of the timer and draws the <i>"YOU LOSE"</i> message onto the screen if the player runs out of time.
       
       @param g <tt>Graphics</tt> object
    */
    public void paintComponent(Graphics g) {
	for(int i = 0; i < tilesHeight; i++) {
	    for(int j = 0; j < tilesWidth; j++) {
		switch(tiletypes[i][j]) {
		case 'G':
		    g.drawImage(grassTile, j*50, i*50, null);
		    break;
		case 'B':
		    g.drawImage(bushTile, j*50, i*50, null);
		    break;
		case 'S':
		    g.drawImage(stoneTile, j*50, i*50, null);
		    break;
		}
	    }
	}
	
        if ((((System.currentTimeMillis()-startTime - pausedTime)/1000>=timeLimit) && !winningCondition) && !pause) {
	    if(!message.equals("YOU LOSE!")) {
		message = "YOU LOSE!";
		new Thread(new MessageThread(this)).start();
	    }
        }
	
	////////////////////ACTUAL TREASURES////////////////////////////////
        for (int i = 0; i < theTreasures.size(); ++i) {
            if(theTreasures.get(i).getFound()) {
                if (GameGui.debug) System.out.println("Drawing Treasure object " + i + "\n" + "x = " + theTreasures.get(i).getX() + " y = " + theTreasures.get(i).getY());
                g.drawImage(theTreasures.get(i).getImage(), theTreasures.get(i).getX()*50, theTreasures.get(i).getY()*50, null);
            }
        }
	////////////////////////////////////////////////////////////////////
	
	// draw the actual player
        g.drawImage(player.getCurrentImage(), player.getXPos(), player.getYPos(), null);
	
    	Graphics2D g2 = (Graphics2D) g;
	if(!message.equals("")) {
	    g2.setColor(new Color(1f,0f,0f,.5f));
	    g2.fill(new Rectangle(100,0,250,100));
	    g2.setFont(new Font(null,Font.BOLD, 20));
	    g2.setColor(Color.BLACK);
	    g2.drawString(message, 110, 50);
	}
	
	if((System.currentTimeMillis()-startTime - pausedTime)/1000 <= timeLimit) {
	    if(!pause && !winningCondition) {
		time = "Time: " + (timeLimit - ((System.currentTimeMillis() - startTime - pausedTime) / 1000));
	    }
	}
	else if(!pause) {
	    time = "Time: " + 0;
	}
	g2.setFont(new Font(null,Font.BOLD, 20));
	g2.setColor(Color.YELLOW);
	g.drawString(time, 180, 20);
    }
    
    /** 
	Updates the player sprite on the map.
    */
    public void updatePlayer() {
	paintImmediately(player.getXPos()-10, player.getYPos()-10,100,100);
    }
    
    /**
       Loads the map from the layout specified in a text document. Each type of tile is loaded from the resources directory as a <tt>BufferedImage</tt>. According to the tile specified in the map layout, the Character that specifies the tile is stored in <tt>ArrayList&lt;Character&gt; tiletypes</tt> and the respective <tt>BufferedImage</tt> map tile is stored in <tt>ArrayList&lt;BufferedImage&gt; tiles</tt>. The <tt>ArrayList&lt;BufferedImage&gt; tiles</tt> is used later by <tt>paintComponent()</tt> in order to draw the tiles on the screen.
       
       @param name The name of the text tile that stores the layout of the map
    */
    
    public void loadMap(String name){
	try {
	    
	    String dir = "/resources/";
	    URL url = (getClass().getResource(dir+name));
	    
	    if(GameGui.debug) {
		System.out.println("dir + name = " + (dir + name));
		System.out.println("url = " + url);
	    }
	    
	    grassTile = ImageIO.read(getClass().getResource("/resources/grass.png"));
	    bushTile = ImageIO.read(getClass().getResource("/resources/bush.png"));
	    stoneTile = ImageIO.read(getClass().getResource("/resources/stone.PNG"));
	    
	    Scanner scanner = new Scanner(getClass().getResourceAsStream(dir + name));
	    tilesWidth = scanner.nextInt();
	    tilesHeight = scanner.nextInt();
	    tiletypes = new char[tilesHeight][tilesWidth];
	    
	    // Place the char that represents the tile in a 2x2 array that is representative of the map
	    for(int i=0; i<tilesHeight; i++) {
	    	for(int j=0; j<tilesWidth; j++) {
		    if(scanner.hasNext()) {
			// Since all input tiles are a string of length 1, we can just use the charAt() function
			String temp = scanner.next();
			tiletypes[i][j] = temp.charAt(0);	    
		    } else {
			// If there is no more input to read, just fill with grass tiles
			tiletypes[i][j] = 'G';
		    }
	    	}
	    }
	    
	    // Close the Scanner
	    scanner.close();
	    
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    /**
       Checks the move that the player made. Makes sure that the player stays within the bounds of the map ([0,11] width x [0,9] height). Provides collision detection in order to ensure that the player does not move through rocks (the player, however, is allowed to move through bushes). Checks the timer and prevents the player from moving once the time is up. Checks whether the player is standing on a tile with a hidden treasure. If a treasure is found, the message <i>"TREASURE # FOUND"</i> is displayed. 
       
       @param xTile The x-coordinate of the player's location on the map
       @param yTile The y-coordinate of the player's location on the map
    */
    public void checkMove(int xTile, int yTile) {
	//limits where the player can move (ie. can move out of the box)
	if(xTile < 0 || xTile > 11 || yTile < 0 || yTile > 8) {
	    player.setMovable(false);
	}	
	
	//prevents the player from moving while time is up
	else if((((System.currentTimeMillis()-startTime - pausedTime)/1000)>=timeLimit) && !winningCondition) {
	    player.setMovable(false);
	}

	//prevent player from move into stones
	else if(tiletypes[yTile][xTile] == 'S') {
	    player.setMovable(false);
	}

	else {
	    player.setMovable(true);
	}
	
	try {
	    Thread.sleep(1);
	} catch(Exception ex) {}
	
	
	// loop through the Treasures and check if they are found
	for (int i = 0; i < theTreasures.size(); ++i) {
	    if(xTile == theTreasures.get(i).getX() && yTile == theTreasures.get(i).getY() && theTreasures.get(i).getFound() == false) {
		theTreasures.get(i).setFoundTrue();
		setMessage(theTreasures.get(i).getNumFound());
		
		if (theTreasures.get(0).getNumFound() == theTreasures.size()) {
		    winningCondition = true;
		    setMessageFinal();
		}

		if(GameGui.debug)
		    System.out.println("foundTreasureNum++");

		validate();
		repaint();
	    }
	}
    }
    
    /**
       Sets the message that will be displayed once a treasure is found.
       
       @param treasureNumber The number of the treasure that is found
    */
    public void setMessage(int treasureNumber) {
	message = "TREASURE " + treasureNumber + " FOUND!";
	new Thread(new MessageThread(this)).start();
    }
    
    /** 
	Sets the message that will be displayed if the player wins the game (i.e. finds all treasures on the map).
	
    */
    public void setMessageFinal(){
	    message = "YOU WIN!";
	    new Thread(new MessageThread(this)).start();
    }
    
    /** 
	Load and initialize the player sprite. It is used by the <tt>go()</tt> method in the <tt>GameGui</tt> class. 
	
	@param player The <tt>Player</tt> object 
    */
    public void loadPlayer(Player player) {
	this.player = player;
    }
    
    /**
       Randomly place <tt>Treasure</tt> objects around the map. This function also ensures that the <tt>Treasure</tt> objects do not share a location with another <tt>Treasure</tt> object, is not placed underneath a stone, and does not start at the coordinate (0, 0) (which ensures that the player does not find immediately find a treasure after starting the game).
       
       @param howMany The number of <tt>Treasure</tt> objects to be placed in the map
    */
    public void placeTheTreasures(int howMany) {
    	treasureMap = new int[tilesHeight][tilesWidth];
    	theTreasures = new ArrayList<Treasure>();
    	
    	// Make sure that at least one treasure is created (or else it would be a very boring game)
        if(howMany < 1)
	    System.out.println("Make at least one treasure!");
	
        for(int i=0; i<howMany; i++) {
	    // Create a new Treasure
	    Treasure tempTreasure = new Treasure("Treasure " + i);
	    
	    // Reset the position of the Treasure to make sure that:
	    // 	1) No two Treasures share the same location
	    // 	2) A Treasure isn't located underneath stones
	    while (treasureMap[tempTreasure.getY()][tempTreasure.getX()] == 1 || tiletypes[tempTreasure.getY()][tempTreasure.getX()] == 'S' || (tempTreasure.getX() == 0 && tempTreasure.getY() == 0)) {
		tempTreasure.resetXY();
	    }
	    
	    // At this point, there is no collision, so we can add the Treasure to the theTreasures ArrayList.
	    theTreasures.add( tempTreasure );
	    treasureMap[tempTreasure.getY()][tempTreasure.getX()] = 1;
        }
    }

    /**
       Updates the amount of time that the game was paused for.

       @param time The time that the game was paused for
     */
    public void pauseTimer(long time) {
    	pausedTime += time;
    }

    /**
       Sets an indicator that determines whether the game is currently paused or not.

       @param state The indicator for whether a game is paused or not
     */
    public void setPause(boolean state) {
	pause = state;
    }
}
