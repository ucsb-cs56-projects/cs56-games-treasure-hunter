package edu.ucsb.cs56.projects.games.treasure_hunter;

import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Event;
import java.lang.Math;

/** 
 *  
 * @author Alex Wood
 * @version for UCSB CS56, W12, 02/16/2012
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
	
	// Set the size to whatever size you like (width, height)
	// For projects you turn in, lets not get any bigger than 640,480
	
	frame.setSize(608,480); // @@@ MODIFY THIS LINE IF YOU LIKE
	
	// Set your own title
	frame.setTitle("Treasure Hunter"); // @@@ MODIFY THIS LINE
	
	// Always do this so that the red X (or red circle) works
	// to close the window. 
	
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	// Instantiate your drawing as a "component"
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
	// Always add your component to the frame 
	// and then make the window visible
	
	frame.add(component);
	frame.setVisible(true);
	component.validate();
	component.repaint();
    }

    
    class MoveAction extends AbstractAction {
	int startingSprite = 0;
	int x = 0;
	int y = 0;
	
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
		player.setMovable(false);
		for(int i = 0; i < 50; i++) {
		    player.moveTo(player.getXPos() + x,player.getYPos()+y);
		    if(x !=0 || y!=0)
			player.setSprite(startingSprite+i/10);
		    if(player.getCurrentSprite() >= startingSprite + 4 && (x != 0 || y != 0))
			player.setSprite(startingSprite);
		    component.updatePlayer();
		    try{ Thread.sleep(1); }
		    catch(Exception ex) {}
		}
		player.setTiles(player.getXTile() + x, player.getYTile()+y);
	    }
	    component.validate();
	    component.repaint();
	}
	
    }
    
    
    
    public void addBindings() {
	//http://stackoverflow.com/questions/11171021/java-use-keystroke-with-arrow-key
	component.registerKeyboardAction(new MoveAction(0,1), KeyStroke.getKeyStroke("DOWN"), JComponent.WHEN_FOCUSED);
	component.registerKeyboardAction(new MoveAction(0,-1), KeyStroke.getKeyStroke("UP"), JComponent.WHEN_FOCUSED);
	component.registerKeyboardAction(new MoveAction(-1,0), KeyStroke.getKeyStroke("LEFT"), JComponent.WHEN_FOCUSED);
	component.registerKeyboardAction(new MoveAction(1,0), KeyStroke.getKeyStroke("RIGHT"), JComponent.WHEN_FOCUSED);
	
    }
}
