package edu.ucsb.cs56.projects.games.treasure_hunter;

import java.net.URL;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Scanner;

/**
   A component that draws the map for the treasure hunter game by Alex Wood
   Edited by Danielle Dodd and George Lieu
   
   @author Alex Wood (for CS56, W12, UCSB, 2/16/2012)
   @author Danielle Dodd and George Lieu
   @version for CS56, W16, UCSB, 2/18/2016 
   
*/


public class GameComponent extends JComponent
{  
    Player player;
    Player treasure;
    Player treasure1;
    Player treasure2;
    private ArrayList<BufferedImage> tiles;
    private ArrayList<Character> tiletypes;
    public String message = "";
    private String t ="";
    private String t1 ="";
    private String t2 ="";
    private int tilesWidth;
    private int tilesHeight;
	private int foundTreasureNum = 0;  

    /*
      paintComponent: It draws all of the tiles on the map. Also loads the player sprite. 
      When player find the treasure, the message variable value changes and the "TREASURE # FOUND" message box is drawn onto the screen. 
      
    */
    public void paintComponent(Graphics g)   
    {

	for(int i = 0; i < tilesHeight; i++) {
	   for(int j = 0; j< tilesWidth; j++) {
		g.drawImage(tiles.get(tilesWidth*i + j), j*50, i*50, null);
	    }
	}

	if(!t.equals(""))
	   g.drawImage(treasure.getCurrentImage(), treasure.getXPos(), treasure.getYPos(),null);
	if(!t1.equals(""))
	    g.drawImage(treasure1.getCurrentImage(), treasure1.getXPos(), treasure1.getYPos(), null);
	if(!t2.equals(""))    
	    g.drawImage(treasure2.getCurrentImage(), treasure2.getXPos(), treasure2.getYPos(), null);
	g.drawImage(player.getCurrentImage(), player.getXPos(), player.getYPos(),null);
	Graphics2D g2 = (Graphics2D) g;
	if(!message.equals("")) {
	    g2.setColor(new Color(1f,0f,0f,.5f));
	    g2.fill(new Rectangle(100,0,250,100));
	    g2.setFont(new Font(null,Font.BOLD, 20));
	    g2.setColor(Color.BLACK);
	    g2.drawString(message, 110, 50);
	}
	
    }

    /* Draws the player sprite onto a new tile */

    public void updatePlayer() {
	paintImmediately(player.getXPos()-10, player.getYPos()-10,100,100); 
    }

    /* 
       loadMap first reads in the png files of the appropriate tile. 
       It scans the text file map.txt and loads the appropriate png image into the instance variable tiles. 
       Tiles is later used  by paintComponent to actually make the tiles appear. 
     */
        
    public void loadMap(String name){
	tiletypes = new ArrayList<Character>();
	try {
	    
            String dir = "/resources/";
	    String imagefile1 = "bush.png";
	    String imagefile2 = "grass.png"; 
	    URL url = (getClass().getResource(dir+name));	    

	    if(GameGui.debug){  	
		System.out.println("dir + name = " + (dir + name));
		System.out.println("url = " + url);  }
		
	    Scanner scanner = new Scanner(getClass().getResourceAsStream(dir+ name));
	    
	    BufferedImage grassTile = ImageIO.read(getClass().getResource("/resources/grass.png"));
	    BufferedImage bushTile = ImageIO.read(getClass().getResource("/resources/bush.png"));
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
	    }
	    
	} catch (IOException e) {
	    e.printStackTrace();}
	
    }
    
    /* 
       Ensures that the player does not go outside the bounds of the map (0-11 by 0-9). 
       If the player is standing on the same tile as a treasure, then the message variable will change which makes the "TREASURE # FOUND" message box appear 
    */
    public void checkMove(int xTile, int yTile) {
	//limits where the player can move (ie. can move out of the box)
	if(xTile < 0 || xTile > 11 || yTile < 0 || yTile > 8)
	    player.setMovable(false);
	//allows player to move after finding treasure
	else if(!message.equals(""))
	    player.setMovable(true);
	//allows player to move into bushes
	else if(tiletypes.get(yTile*tilesWidth + xTile) == 'B')
	    player.setMovable(true);
	else if(player.getXPos() != player.getXTile() * 50 || player.getYPos() != player.getYTile() * 50)
	    player.setMovable(false);
	else
	    player.setMovable(true);
	//if player finds treasure the string "Treasure Found is displayed"
	//if player finds three treasure than the string "YOU WIN! Would you want to play again?"
	
	if(xTile == treasure.getXTile() && yTile == treasure.getYTile()) {
	    setMessage(1);
	    t = "found";
		foundTreasureNum++;
		if(GameGui.debug){  	
		System.out.println("foundTreasureNum++");
		}
	}
	else if(xTile == treasure1.getXTile() && yTile == treasure1.getYTile()) {
	    setMessage(2);
	    t1 = "found";
	    foundTreasureNum++;
		if(GameGui.debug){  	
		System.out.println("foundTreasureNum++");
		}
	}
	else if(xTile == treasure2.getXTile() && yTile == treasure2.getYTile()) {
	    setMessage(3);
	    t2= "found";
		foundTreasureNum++;
		if(GameGui.debug){  	
		System.out.println("foundTreasureNum++");
		}
	}
	
	if(foundTreasureNum >= 3){
		setMessageFinal(true);
	}
    }
    /* changes the message instance variable
     */
    public void setMessage(int treasure_number) {
	message = "TREASURE " + treasure_number + " FOUND!";
	new Thread(new MessageThread(this)).start();
    }
    /* changes the message with final
     */	
	 public void setMessageFinal(boolean answer){
		 if (answer){
			 message = "YOU WIN!";
			 new Thread(new MessageThread(this)).start();
			 //TODO: fix the problem of the time a message box appear
			 //TODO: find a way to pause the game
			 message = "PLAY AGAIN?";
			 new Thread(new MessageThread(this)).start();
		 }
	 }
	
    /* loadPlayer is being used by the go() method in GameGui.java. 
       It initializes the 3 treasures and the player sprite. 
     */
    public void loadPlayer(Player player, String name) {
	if( name.equals("player"))
	    this.player = player;
    if( name.equals("treasure"))
        this.treasure = player;
	if( name.equals("treasure1"))
        this.treasure1 = player;
	if( name.equals("treasure2"))
		this.treasure2 = player;
    }
    
}
