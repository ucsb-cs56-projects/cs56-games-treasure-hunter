package edu.ucsb.cs56.projects.games.treasure_hunter;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

/**
   A component that draws the Player  by Alex Wood
   
   @author Alex Wood
   @version for CS56, W12, UCSB, 2/16/2012
   
*/


public class Player {  
    private boolean movable = true;
    private int xTile;
    private int yTile;
    private int xPos;
    private int yPos;
    ArrayList<BufferedImage> sprites;
    private int currentSprite = 0;

    public Player(int xTile, int yTile, int numSprites, int currentSprite, String name) {
	try {
	    sprites = new ArrayList<BufferedImage>();
	    for(int i = 0; i < numSprites; i++)
		sprites.add(ImageIO.read(getClass().getResource(name + "/" + name + i + ".png")));
	    this.xTile = xTile;
	    this.yTile = yTile;
	    this.currentSprite = currentSprite;
	    this.moveTo(xTile*50,yTile*50);
	} catch (Exception e) {}
    }

    public void setSprite(int sprite) {
	currentSprite = sprite;
    }
    public int getXPos() {return xPos;}
    public int getXTile() {return xTile;}
    public int getYPos() {return yPos;}
    public int getYTile() {return yTile;}
    public void setTiles(int xTile,int yTile) {
	this.xTile = xTile;
	this.yTile = yTile;
    }
    public void moveTo(int xPos, int yPos) {
	this.xPos = xPos;
	this.yPos = yPos;
    }

    public boolean isMovable() {return movable;}
    public void setMovable(boolean movable) {this.movable = movable;}

    public int getCurrentSprite() { return currentSprite; }
    public BufferedImage getCurrentImage() { return sprites.get(currentSprite); }
}
