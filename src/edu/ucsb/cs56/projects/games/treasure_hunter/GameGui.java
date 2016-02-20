package edu.ucsb.cs56.projects.games.treasure_hunter;

import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;


/** 
 *  
 * @author Alex Wood (UCSB CS56. W12. 02/16/2012)
 * @author Danielle Dodd and George Lieu
 * @version for UCSB CS56, W16, 02/18/2016
 */

public class GameGui
{
    Player player;
    Player treasure;
    Player treasure1;
    Player treasure2;
    GameComponent component;
    
    public static boolean debug = false; 
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
	player = new Player(0,0,16,8,"player");

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
	
	// creates game components (player + treasures) 
	treasure = new Player(treasure1X,treasure1Y,1,0,"treasure");
	treasure1 = new Player(treasure2X,treasure2Y,1,0,"treasure");
	treasure2 = new Player(treasure3X,treasure3Y,1,0,"treasure"); 
	component = new GameComponent();
	component.loadPlayer(player,"player");
	component.loadPlayer(treasure2, "treasure2");
	component.loadPlayer(treasure1, "treasure1");
	component.loadPlayer(treasure,"treasure");
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
}
