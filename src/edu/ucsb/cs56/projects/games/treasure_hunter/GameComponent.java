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
    ArrayList<Treasure> theTreasures = new ArrayList<Treasure>();

    private ArrayList<BufferedImage> tiles;
    private ArrayList<Character> tiletypes;
    
    private String message = "";
    private String t ="";
    private String t1 ="";
    private String t2 ="";
    
    private int tilesWidth;
    private int tilesHeight;
    
    private int foundTreasureNum = 0;

    /*
     * The initial time that the game starts at and the time limit of the game.
     */
    long startTime = System.currentTimeMillis();
    private int timeLimit = 200;
    
    /**
       Constructs a <tt>GameComponent</tt> object. 
     */
    public GameComponent() {
	tiles = new ArrayList<BufferedImage>();
	tiletypes = new ArrayList<Character>();
	
	message = "";
	t = "";
	t1 = "";
	t2 = "";
	
	timeLimit = 200;
	
	foundTreasureNum = 0;
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
	// probably draws the tiles
	for(int i = 0; i < tilesHeight; i++) {
	    for(int j = 0; j< tilesWidth; j++) {
		g.drawImage(tiles.get(tilesWidth*i + j), j*50, i*50, null);
	    }
	}
	
        if ((System.currentTimeMillis()-startTime)/1000>=timeLimit){
            message = "YOU LOSE!";
            new Thread(new MessageThread(this)).start();
        }
	


	////////////////////ACTUAL TREASURES////////////////////////////////
        for (int i = 0; i < theTreasures.size(); ++i){
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

	String time = "Time: " + ((System.currentTimeMillis()-startTime)/1000);
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
	tiletypes = new ArrayList<Character>();
	try {

	    String dir = "/resources/";
	    String imagefile1 = "bush.png";
	    String imagefile2 = "grass.png";
	    String imagefile3 = "stone.PNG";
	    URL url = (getClass().getResource(dir+name));

	    if(GameGui.debug) {
		System.out.println("dir + name = " + (dir + name));
		System.out.println("url = " + url);
	    }

	    Scanner scanner = new Scanner(getClass().getResourceAsStream(dir+ name));
	    BufferedImage grassTile = ImageIO.read(getClass().getResource("/resources/grass.png"));
	    BufferedImage bushTile = ImageIO.read(getClass().getResource("/resources/bush.png"));
	    BufferedImage stoneTile = ImageIO.read(getClass().getResource("/resources/stone.PNG"));
	    tilesWidth = scanner.nextInt();
	    tilesHeight = scanner.nextInt();
	    
	    String temp;
	    tiles = new ArrayList<BufferedImage>();
	    while (scanner.hasNext()) {
		temp = scanner.nextLine();
		if(temp.equals(("G"))) {
		    tiles.add(grassTile);
		    tiletypes.add('G');
		}
		if(temp.equals(("B"))) {
		    tiles.add(bushTile);
		    tiletypes.add('B');
		}
		if(temp.equals(("S"))) {
		    tiles.add(stoneTile);
		    tiletypes.add('S');
		}
	    }
	    
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
	if(xTile < 0 || xTile > 11 || yTile < 0 || yTile > 8)
	    player.setMovable(false);
	
	//prevents the player from moving while time is up
	else if(((System.currentTimeMillis()-startTime)/1000)>=timeLimit)
	    player.setMovable(false);
	
	//allows player to move after finding treasure
	/* probably useless code, but we'll leave it here for now in case it is needed
	   else if(!message.equals(""))
	   player.setMovable(true);*/
	
	//allows player to move into bushes
	else if(tiletypes.get(yTile*tilesWidth + xTile) == 'B')
	    player.setMovable(true);
	//prevent player from move into stones
	else if(tiletypes.get(yTile*tilesWidth + xTile) == 'S')
	    player.setMovable(false);
	else if(player.getXPos() != player.getXTile() * 50 || player.getYPos() != player.getYTile() * 50)
	    player.setMovable(false);
	else
	    player.setMovable(true);

	// loop through the Treasures and check if they are found
	for (int i = 0; i < theTreasures.size(); ++i) {
	    if(xTile == theTreasures.get(i).getX() && yTile == theTreasures.get(i).getY() && theTreasures.get(i).getFound() == false) {
                setMessage(i);
                theTreasures.get(i).setFoundTrue();
                foundTreasureNum++;
                if( foundTreasureNum == theTreasures.size() )
		    setMessageFinal(true);
                if(GameGui.debug)
		    System.out.println("foundTreasureNum++");
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

	@param gameWon A value that indicates whether the game is won or not
    */
    public void setMessageFinal(boolean gameWon){
	if(gameWon) {
	    message = "YOU WIN!";
	    new Thread(new MessageThread(this)).start();
	    //TODO: fix the problem of the time a message box appear
	    //TODO: find a way to pause the game
	}
    }

    /** 
       Load and initialize the player sprite. It is used by the <tt>go()</tt> method in the <tt>GameGui</tt> class. 

       @param player The player object 
       @param name The name of the player
    */
    public void loadPlayer(Player player, String name) {
	this.player = player;
    }


    /**
       Initializes the treasures and sets them on the map.

       @param treasures The list of treasures that are to be placed on the map
     */
    public void loadTreasure(ArrayList<Treasure> treasures) {
	this.theTreasures = treasures;

	for ( int i = 0; i < treasures.size(); ++i) {
	    this.theTreasures.set(i, treasures.get(i));
	}
    }
}
