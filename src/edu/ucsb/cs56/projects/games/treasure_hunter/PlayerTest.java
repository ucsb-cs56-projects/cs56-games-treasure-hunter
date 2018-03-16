package edu.ucsb.cs56.projects.games.treasure_hunter;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * The test class PlayerTest, to test the Player class
 *
 * @author Alex Wood
 * @version CS56, Winter 2012, issue 0000573
 * @see Player
 */

public class PlayerTest 
{
        
    /**
       test constructor from PlayerTest
       @see Player#Player(int xTile, int yTile, int numSprites, int currentSprite, String name)

     */
   /*
   @Test public void testConstructor()
    {
        Player player = new Player(1,4,16,4,"player");
	Player treasure = new Player(2,5,1,0,"treasure");
        assertEquals(1,player.getXTile());
	assertEquals(4,player.getYTile());
	assertEquals(4,player.getCurrentSprite());

	assertEquals(2,treasure.getXTile());
	assertEquals(5,treasure.getYTile());
	assertEquals(0,treasure.getCurrentSprite());
    }


    /**
       test getCurrentSprite method
       @see Player#getCurrentSprite()
    *//*
    @Test public void testGetCurrentSprite()
    {
	Player p = new Player(5,5,16,0,"player");
	assertEquals(0,p.getCurrentSprite());
    }


    /**
       test setSprite method
       @see Player#setSprite(int sprite)

     *//*
   @Test public void testSetSprite()
   {
       Player p = new Player(5,5,16,0,"player");
       //change the current sprite to 10
       p.setSprite(10);

       assertEquals(10,p.getCurrentSprite());
    }


    /**
       test getXPos method
       @see Player#getXPos()
    *//*
    @Test public void testGetXPos()
    {
	Player p = new Player(5,5,16,0,"player");
	assertEquals(250,p.getXPos());
    }


    /**
       test getYPos method
       @see Player#getYPos()
    *//*
    @Test public void testGetYPos()
    {
	Player p = new Player(6,6,16,0,"player");
	assertEquals(300,p.getYPos());
    }


    /**
       test setTiles method
       @see Player#setTiles(int xTile, int yTile)
    *//*
    @Test public void testSetTiles()
    {
	Player p = new Player(0,0,16,0,"player");
	p.setTiles(4,7);

	assertEquals(4,p.getXTile());
	assertEquals(7,p.getYTile());
    }


    /**
       test moveTo method
       @see Player#moveTo(int xPos, int yPos)
    *//*
    @Test public void testMoveTo()
    {
	Player p = new Player(0,0,16,0,"player");
	p.moveTo(354,217);

	assertEquals(354,p.getXPos());
	assertEquals(217,p.getYPos());
    }

    /**
       test isMovable method
       @see Player#isMovable()
    *//*
    @Test public void testIsMovable()
    {
	Player p = new Player(0,0,16,0,"player");
	//movable should automatically be set to true
	assertEquals(true,p.isMovable());
    }


    /**
       test setMovable method
       @see Player#setMovable(boolean movable)
    *//*
    @Test public void testSetMovable()
    {
	Player p = new Player(5,5,16,0,"player");
	p.setMovable(false);
	assertEquals(false,p.isMovable());
    }*/
    
}    

