package edu.ucsb.cs56.projects.games.treasure_hunter;

import java.net.URL;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Scanner;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
/**
   A component that draws a map by Alex Wood
   
   @author Alex Wood
   @version for CS56, W12, UCSB, 2/16/2012
   
*/


public class GameComponent extends JComponent
{  

    /** The paintComponent method is always required if you want
     * any graphics to appear in your JComponent.    
     * 
     * There is a paintComponent
     * method that is created for you in the JComponent class, but it
     * doesn't do what we want, so we have to "override" that method with
     * our own method.  
     */
    Player player;
    Player treasure;
    Player treasure1;
    Player treasure2;
    private ArrayList<BufferedImage> tiles;
    private ArrayList<Character> tiletypes;
	private String message = "";
	private BufferedImage messageBox;
    private int tilesWidth;
    private int tilesHeight;
    
//    public GameComponent(){
//	this.loadMap("map.txt");
		
//	}   

    public void paintComponent(Graphics g)   
  {  		
	for(int i = 0; i < tilesHeight; i++) {
	       for(int j = 0; j< tilesWidth; j++) {
		   g.drawImage(tiles.get(tilesWidth*i + j), j*50, i*50, null);
	       }
	    }
	   g.drawImage(treasure.getCurrentImage(), treasure.getXPos(), treasure.getYPos(),null);
	 //  g.drawImage(treasure2.getCurrentImage(), treasure2.getXPos(), treasure2.getYPos(), null);
	   g.drawImage(player.getCurrentImage(), player.getXPos(), player.getYPos(),null);
       Graphics2D g2 = (Graphics2D) g;
		if(!message.equals("")) {
		    g2.setColor(new Color(1f,0f,0f,.5f));
			g2.fill(new Rectangle(100,0,250,100));
			g2.setFont(new Font(null,Font.BOLD, 20));
			g2.setColor(Color.BLACK);
			g2.drawString(message, 110, 50);
			//Thread.sleep(2000);
			// message.equals("");
			
		}
		

   }

    public void updatePlayer() {
	paintImmediately(player.getXPos()-10, player.getYPos()-10,100,100); 
    }

    

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
		if(xTile == treasure.getXTile() && yTile == treasure.getYTile()) {
			winGame(1);
		}
		else if(xTile == treasure1.getXTile() && yTile == treasure1.getYTile()) {
			winGame(2);
			
		}
		else if(xTile == treasure2.getXTile() && yTile == treasure2.getYTile()) {
			winGame(3);	
		}
    }

	public void winGame(int treasure_number) {
	    message = "TREASURE " + treasure_number + " FOUND!";

	}
	
    public void loadPlayer(Player player, String name) {
	if( name.equals("player"))
	    this.player = player;
	if( name.equals("treasure"))
	    treasure = player;
	if( name.equals("treasure1"))
	    treasure1 = player;
	if( name.equals("treasure2"))
	    treasure2 = player;
    }
    
}
