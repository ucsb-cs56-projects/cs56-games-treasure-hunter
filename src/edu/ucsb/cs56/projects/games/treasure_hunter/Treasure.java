package edu.ucsb.cs56.projects.games.treasure_hunter;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/*
  Created by Lisa Liao and Patrick Vidican (CS56, F16, 11/19/2016)
  Edited by Yusuf Alnawakhtha and Sang Min Oh
 */

/**
   A class that contains all of the <tt>Treasure</tt> object's information such as its location, its sprite image, and whether it has been found.

   @author Lisa Liao
   @author Patrick Vidican
   @author Yusuf Alnawakhtha and Sang Min Oh
   @version for UCSB CS56, F17, 11/07/2017
*/

public class Treasure {
    // private instance variables
    private int xTile = 0;
    private int yTile = 0;
    private String name;
    private boolean found = false;
    private BufferedImage image = null;
    
    /**
       Constructs a <tt>Treasure</tt> object. The <tt>Treasure</tt> object's initial position is randomly generated so its initial location wouldn't always be at the origin. The <tt>Treasure</tt> is also given an image.

       @param name The name of the <tt>Treasure</tt> object
     */
    public Treasure(String name) {
	this.name = name;
	this.resetXY(); // otherwise the first treasure is always at 0,0
	try{
	    image = ImageIO.read(getClass().getResource(GameGui.resourcesDir + "treasure/treasure0.png"));
	    // function calls above might not be necessary if we just have one image
	} catch (Exception e) {}
    }

    /**
       Compares the location of this <tt>Treasure</tt> with the location of the <tt>(Treasure) Object</tt>. 

       @param o The object that is being compared
       @return Whether the two objects have the same location
     */
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

    /**
       Generates the hashcode of this <tt>Treasure</tt>.

       @return The hashcode of this <tt>Treasure</tt> object
     */
    @Override
    public int hashCode(){ // straight out conrad's tutorials
	int xTileBit = this.xTile & 0x0000FFFF;
	int yTileBit = this.yTile & 0x0000FFFF;
	return (xTileBit << 16) | yTileBit;
    }

    /**
       Returns the <tt>Treasure</tt> object's image.

       @return The image
     */
    public BufferedImage getImage() { return image; }

    /**
       Returns the x-tile number of the <tt>Treasure</tt>.

       @return The x-tile number
     */
    public int getX() { return xTile; }

    /**
       Returns the y-tile number of the <tt>Treasure</tt>.

       @return The y-tile number
     */
    public int getY() { return yTile; }

    /**
       Returns the name of the <tt>Treasure</tt> object.

       @return The name
     */
    public String getName() { return name; }

    /**
       Returns whether the <tt>Treasure</tt> has been found.

       @return Whether the <tt>Treasure</tt> has been found
     */
    public boolean getFound() { return found; }

    /**
       Sets the state of the <tt>Treasure</tt> to be found.
     */
    public void setFoundTrue() { found = true; }

    /**
       Resets the location of the <tt>Treasure</tt> object. The new x-/y-values for the tiles are randomly generated.
     */
    public void resetXY() {
	this.xTile = (int)(Math.random()*12);
	this.yTile = (int)(Math.random()*9);
    }
}
