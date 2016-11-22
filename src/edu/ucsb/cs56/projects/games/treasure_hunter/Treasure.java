package edu.ucsb.cs56.projects.games.treasure_hunter;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
   A brand-new Treasure class by Lisa Liao and Patrick Vidican
   @author Lisa Liao
   @author Patrick Vidican
   @version for CS56, F16, UCSB, 11/19/2016
*/


public class Treasure {
    // private instance variables
    private int xTile = 0;
    private int yTile = 0;
    private String name;
    private boolean found = false;
    private BufferedImage image = null;

    //Treasure constructor
    public Treasure(String name) {
      this.name = name;
      this.resetXY(); // otherwise the first treasure is always at 0,0
      try{
          image = ImageIO.read(getClass().getResource(GameGui.resourcesDir
                               + "treasure/treasure0.png"));
          // function calls above might not be necessary if we just have one image
	       } catch (Exception e) {}
    }

    @Override
    public boolean equals(Object o){
      // Start: boilerplate code for .equals
	    if (this == o) return true; // comparing to itself
	    if (o == null) return false; // comparing to nothing
	    if (getClass() != o.getClass()) return false; // comparing apples to oranges
	    Treasure r = (Treasure) o; // .equals now knows that o is-a Treasure
      // End boilerplate

      return this.xTile == r.xTile && this.yTile == r.yTile;
    }

    @Override
    public int hashCode(){ // straight outt conrad's tutorials
      int xTileBit = this.xTile & 0x0000FFFF;
      int yTileBit = this.yTile & 0x0000FFFF;
      return (xTileBit << 16) | yTileBit;
    }

    public BufferedImage getImage() { return image; }
    public int getX() { return xTile; }
    public int getY() { return yTile; }
    public String getName() { return name; }
    public boolean getFound() { return found; }
    public void setFoundTrue() { found = true; }
    public void resetXY() {
	     this.xTile = (int)(Math.random()*12);
	     this.yTile = (int)(Math.random()*9);
    }
}
