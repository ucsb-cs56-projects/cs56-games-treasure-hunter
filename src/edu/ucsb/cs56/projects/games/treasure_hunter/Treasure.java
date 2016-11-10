package edu.ucsb.cs56.projects.games.treasure_hunter;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
   A component that draws the Treasure by Patrick Vidican

   @author Alex Wood (UCSB, CS56 W12, 2/16/2012)
   @author Danielle Dodd and George Lieu
   @version for CS56, W16, UCSB, 2/18/2016

*/


public class Treasure {
    // private instance variables
    private int xTile;
    private int yTile;
    private ArrayList<BufferedImage> sprites;
    private int currentSprite = 0;

    //Treasure constructor
    public Treasure(String name) {
	     try {
         sprites.add(ImageIO.read(getClass().getResource(GameGui.resourcesDir
                                                          + name
                                                          + "/" + name
                                                          + "0" + ".png")));
	       this.xTile = (int)(Math.random()*12);
	       this.yTile = (int)(Math.random()*9);

	    } catch (Exception e) {}
    }

    public int getXTile() {return xTile;}
    public int getYTile() {return yTile;}

    public void setTiles(int xTile,int yTile) {
	    this.xTile = xTile;
	    this.yTile = yTile;
    }
    public BufferedImage getCurrentImage() { return sprites.get(0); }
}
