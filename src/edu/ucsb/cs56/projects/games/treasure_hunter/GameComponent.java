package edu.ucsb.cs56.projects.games.treasure_hunter;

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
    private ArrayList<BufferedImage> tiles;
    private ArrayList<Character> tiletypes;
	private String message = "";
	private BufferedImage messageBox;
    private int tilesWidth;
    private int tilesHeight;
   public void paintComponent(Graphics g)
   { 
       for(int i = 0; i < tilesHeight; i++) {
	       for(int j = 0; j< tilesWidth; j++) {
		   g.drawImage(tiles.get(tilesWidth*i + j), j*50, i*50, null);
	       }
	    }
	   g.drawImage(treasure.getCurrentImage(), treasure.getXPos(), treasure.getYPos(),null);
	   g.drawImage(player.getCurrentImage(), player.getXPos(), player.getYPos(),null);
       Graphics2D g2 = (Graphics2D) g;
		if(!message.equals("")) {
			g2.setColor(Color.WHITE);
			g2.fill(new Rectangle(100,0,200,100));
			g2.setFont(new Font(null,Font.BOLD, 20));
			g2.setColor(Color.BLACK);
			g2.drawString(message, 110, 50);
		}

   }

    public void updatePlayer() {
	paintImmediately(player.getXPos()-10, player.getYPos()-10,60,60); 
    }

    public void loadMap(String name){
	tiletypes = new ArrayList<Character>();
	try {
	    Scanner scanner = new Scanner(getClass().getResourceAsStream("/" + name));
	    BufferedImage grassTile = ImageIO.read(getClass().getResource("grass.png"));
	    BufferedImage bushTile = ImageIO.read(getClass().getResource("bush.png"));
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
	} catch (IOException e) {}
	
    }
    public void checkMove(int xTile, int yTile) {
		if(xTile < 0 || xTile > 7 || yTile < 0 || yTile > 7)
			player.setMovable(false);
		else if(!message.equals(""))
			player.setMovable(false);
		else if(tiletypes.get(yTile*tilesWidth + xTile) == 'B')
			player.setMovable(false);
		else if(player.getXPos() != player.getXTile() * 50 || player.getYPos() != player.getYTile() * 50)
			player.setMovable(false);
		else
			player.setMovable(true);
		if(xTile == treasure.getXTile() && yTile == treasure.getYTile()) {
			winGame();
		}
    }

	public void winGame() {
		message = "TREASURE FOUND!";
	}

    public void loadPlayer(Player player, String name) {
	if( name.equals("player"))
	    this.player = player;
	if( name.equals("treasure"))
	    treasure = player;
    }
    
}
