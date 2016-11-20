package edu.ucsb.cs56.projects.games.treasure_hunter;

import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.util.ArrayList;


/**
 *
 * @author Alex Wood (UCSB CS56. W12. 02/16/2012)
 * @author Danielle Dodd and George Lieu
 * @version for UCSB CS56, W16, 02/18/2016
 */

// all instances of Player as a treasure should be Treasure as a treasure
public class GameGui{

    Player player;
    Player treasure;
    Player treasure1;
    Player treasure2;
    ArrayList<Treasure> theTreasures = new ArrayList<Treasure>();
    GameComponent component;

    public static boolean debug = true;
    public static final String resourcesDir = "/resources/";

    public static void main(String[] args)
    {
	    if(debug) { System.out.println("Starting main");}
	    GameGui gui = new GameGui();

	    if(debug) { System.out.println("In main calling gui.go()");}
	    gui.go();
    }

    public void go() {
	JFrame frame = new JFrame();

	// Set the name and frame size
	frame.setSize(608,480);
	frame.setTitle("Treasure Hunter");

	// Allows for game window to be closed
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// Randomly places 3 treasures on game map
  component = new GameComponent();

	player = new Player(0,0,16,8,"player");
	component.loadPlayer(player,"player");

  System.out.println( this.toString() + " is calling placeTheTreasures!!");
  this.placeTheTreasures(4);
  component.loadTreasure( theTreasures );

  Player [] treasures = placeTreasures(3);
  component.loadPlayer( treasures[0], "treasure" );
  component.loadPlayer( treasures[1], "treasure1" );
  component.loadPlayer( treasures[2], "treasure2" );

  component.loadMap("map.txt");
  addBindings();

	// adds game components and makes the window visible
	frame.add(component);
	frame.setVisible(true);
	component.validate();
	component.repaint();
    }


    class MoveAction extends AbstractAction {
	     int startingSprite = 0;
	     int x = 0;
	     int y = 0;
	/*
	  The MoveAction method changes the player png file so that it appears he turns in the direction the user wants him to walk in.
	 */
	     public MoveAction(int x, int y) {
	        if(x == -1)
	    	    startingSprite = 4;
	        if(x == 1)
	    	    startingSprite = 8;
	        if(y == -1)
	    	    startingSprite = 12;
	        if(y == 1)
	    	    startingSprite = 0;
	        this.x = x;
	        this.y = y;
	     }

	public void actionPerformed(ActionEvent e) {
	    player.setSprite(startingSprite);
	    component.checkMove(player.getXTile() + x, player.getYTile() + y);
	    if(player.isMovable()) {
		//player.setMovable(false);
		for(int i = 0; i < 50; i++) {
		    player.moveTo(player.getXPos() + x,player.getYPos()+y);
		    if(x !=0 || y!=0)
			player.setSprite(startingSprite+i/10);
		    if(player.getCurrentSprite() >= startingSprite + 4 && (x != 0 || y != 0))
			player.setSprite(startingSprite);
		    component.updatePlayer();
		    try{ Thread.sleep(5); }
		    catch(Exception ex) {}
		}
		player.setTiles(player.getXTile() + x, player.getYTile()+y);
	    }
	    component.validate();
	    component.repaint();
	}

    }

    /*
      addBindings takes in the user's keyboard input.

     */

    public void addBindings() {
        // https://docs.oracle.com/javase/7/docs/api/javax/swing/KeyStroke.html
	      component.registerKeyboardAction(new MoveAction(0,1), KeyStroke.getKeyStroke("DOWN"), JComponent.WHEN_FOCUSED);
        component.registerKeyboardAction(new MoveAction(0,-1), KeyStroke.getKeyStroke("UP"), JComponent.WHEN_FOCUSED);
        component.registerKeyboardAction(new MoveAction(-1,0), KeyStroke.getKeyStroke("LEFT"), JComponent.WHEN_FOCUSED);
        component.registerKeyboardAction(new MoveAction(1,0), KeyStroke.getKeyStroke("RIGHT"), JComponent.WHEN_FOCUSED);
    }

    public static Player[] placeTreasures( int howMany ){
        int treasure1X = (int)(Math.random()*12);
      	int treasure1Y = (int)(Math.random()*9);

      	int treasure2X = (int)(Math.random()*12);
      	int treasure2Y = (int)(Math.random()*9);

      	int treasure3X = (int)(Math.random()*12);
      	int treasure3Y = (int)(Math.random()*9);

      	while(treasure1X == treasure2X && treasure1Y == treasure2Y) {
      	    treasure2X = (int)(Math.random()*12);
      	    treasure2Y = (int)(Math.random()*9);
      	}
      	while((treasure1X == treasure3X && treasure1Y == treasure3Y)
      	      || (treasure2X == treasure3X && treasure2Y == treasure3Y)){
      	    treasure3X = (int)(Math.random()*12);
      	    treasure3Y = (int)(Math.random()*9);
      	}
        // prevents placement of treasures underneath stones //
      	while((treasure1X == 0 && treasure1Y == 6)||(treasure1X == 1 && treasure1Y == 6)||(treasure1X == 3 && treasure1Y == 8)
      		||(treasure1X == 4 && treasure1Y == 8)||(treasure1X == 5 && treasure1Y == 8)||(treasure1X == 8 && treasure1Y == 1)
      		||(treasure1X == 8 && treasure1Y == 6)||(treasure1X == 9 && treasure1Y == 6)||(treasure1X == 10 && treasure1Y == 2)
      		||(treasure1X == 11 && treasure1Y == 3)||(treasure1X == 11 && treasure1Y == 5)||(treasure1X == 11 && treasure1Y == 6)){
            treasure1X = (int)(Math.random()*12);
      	    treasure1Y = (int)(Math.random()*9);
      	}
      	while((treasure2X == 0 && treasure2Y == 6)||(treasure2X == 1 && treasure2Y == 6)||(treasure2X == 3 && treasure2Y == 8)
      		||(treasure2X == 4 && treasure2Y == 8)||(treasure2X == 5 && treasure2Y == 8)||(treasure2X == 8 && treasure2Y == 1)
      		||(treasure2X == 8 && treasure2Y == 6)||(treasure2X == 9 && treasure2Y == 6)||(treasure2X == 10 && treasure2Y == 2)
      		||(treasure2X == 11 && treasure2Y == 3)||(treasure2X == 11 && treasure2Y == 5)||(treasure2X == 11 && treasure2Y == 6)){
      	    treasure2X = (int)(Math.random()*12);
      	    treasure2Y = (int)(Math.random()*9);
      	}
      	while((treasure3X == 0 && treasure3Y == 6)||(treasure3X == 1 && treasure3Y == 6)||(treasure3X == 3 && treasure3Y == 8)
      		||(treasure3X == 4 && treasure3Y == 8)||(treasure3X == 5 && treasure3Y == 8)||(treasure3X == 8 && treasure3Y == 1)
      		||(treasure3X == 8 && treasure3Y == 6)||(treasure3X == 9 && treasure3Y == 6)||(treasure3X == 10 && treasure3Y == 2)
      		||(treasure3X == 11 && treasure3Y == 3)||(treasure3X == 11 && treasure3Y == 5)||(treasure3X == 11 && treasure3Y == 6)){
      	    treasure3X = (int)(Math.random()*12);
      	    treasure3Y = (int)(Math.random()*9);
      	}
        ////////////////////////////////////////////////////////////////////////
        Player[] treasures = new Player[]{ new Player(treasure1X,treasure1Y,1,0,"treasure"),
                                           new Player(treasure2X,treasure2Y,1,0,"treasure"),
                                           new Player(treasure3X,treasure3Y,1,0,"treasure")
                                         };
        return treasures;
    }

    public void placeTheTreasures( int howMany ){
        if ( howMany == 0 ) System.out.println("make at least one treasure!");
        Treasure first = new Treasure("treasure0");
        if ( first != null ) System.out.println("first treasure is not null!");
        theTreasures.add( first );

        for (int i = 1; i < howMany; i++){
          Treasure tempTreasure = new Treasure("treasure" + i);
          while (theTreasures.contains( tempTreasure )) {
            tempTreasure.resetXY();
          }
          theTreasures.add( tempTreasure );
          // use resetXY so we don't keep creating new objects on the heap
        }

        // prevents placement of treasures underneath stones //
        for ( int i = 0; i < theTreasures.size(); ++i){
          while((theTreasures.get(i).getX() == 0 && theTreasures.get(i).getY() == 6)
            ||(theTreasures.get(i).getX() == 1 && theTreasures.get(i).getY() == 6)
            ||(theTreasures.get(i).getX() == 3 && theTreasures.get(i).getY() == 8)
      		  ||(theTreasures.get(i).getX() == 4 && theTreasures.get(i).getY() == 8)
            ||(theTreasures.get(i).getX() == 5 && theTreasures.get(i).getY() == 8)
            ||(theTreasures.get(i).getX() == 8 && theTreasures.get(i).getY() == 1)
      		  ||(theTreasures.get(i).getX() == 8 && theTreasures.get(i).getY() == 6)
            ||(theTreasures.get(i).getX() == 9 && theTreasures.get(i).getY() == 6)
            ||(theTreasures.get(i).getX() == 10 && theTreasures.get(i).getY() == 2)
      		  ||(theTreasures.get(i).getX() == 11 && theTreasures.get(i).getY() == 3)
            ||(theTreasures.get(i).getX() == 11 && theTreasures.get(i).getY() == 5)
            ||(theTreasures.get(i).getX() == 11 && theTreasures.get(i).getY() == 6)){
              theTreasures.get(i).resetXY();
      	  }
        }
      }
}
