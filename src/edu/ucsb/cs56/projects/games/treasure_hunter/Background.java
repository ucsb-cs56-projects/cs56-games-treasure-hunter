package edu.ucsb.cs56.projects.games.treasure_hunter;

// Packages from LibGDX
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRender;

public class Background extends ApplicationAdapter implements InputProcessor {	
	Texture img;
	TiledMap map;
	OrthographicCamera cam;
	TiledMapRenderer mapRender;

	@Override 
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		cam = new OrthographicCamera();
		cam.setToOrtho(false, w, h);
		cam.update();
		map = new TmxMapLoader().load(".tmx");
		mapRender = new OrthogonalTiledMapRenderer(map);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render() {
		Gdx.g1.g1ClearColor(1, 0, 0, 1);
		Gdx.g1.g1BlendFunc(GL20, GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.g1.g1Clear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		mapRender.setView(cam);
		mapRender.render();	
	}

	 @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.LEFT)
            camera.translate(-32,0);
        if(keycode == Input.Keys.RIGHT)
            camera.translate(32,0);
        if(keycode == Input.Keys.UP)
            camera.translate(0,-32);
        if(keycode == Input.Keys.DOWN)
            camera.translate(0,32);
        if(keycode == Input.Keys.NUM_1)
            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
        if(keycode == Input.Keys.NUM_2)
            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}


/*
import java.net.URL;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Scanner; */

/**
   A component that draws the map for the treasure hunter game by Alex Wood
   Edited by Danielle Dodd and George Lieu
   Edited by Lisa Liao and Patrick Vidican
   Edited by Yusuf Alnawakhtha and Sang Min Oh

   @author Alex Wood (for CS56, W12, UCSB, 2/16/2012)
   @author Danielle Dodd and George Lieu
   @author Lisa Liao and Patrick Vidican
   @author Yusuf Alnawakhtha and Sang Min Oh
   @version for UCSB CS56, F17, 11/06/2017

*/

/*
public class GameComponent extends JComponent
{
    Player player;
    ArrayList<Treasure> theTreasures = new ArrayList<Treasure>();

    private ArrayList<BufferedImage> tiles;
    private ArrayList<Character> tiletypes;
    public String message = "";
    private String t ="";
    private String t1 ="";
    private String t2 ="";
    private int tilesWidth;
    private int tilesHeight;
	  private int foundTreasureNum = 0;

    /*
      paintComponent: It draws all of the tiles on the map. Also loads the player sprite.
      When player find the treasure, the message variable value changes and the "TREASURE # FOUND" message box is drawn onto the screen.

    
    public void paintComponent(Graphics g) {
  // probably draws the tiles
	for(int i = 0; i < tilesHeight; i++) {
	   for(int j = 0; j< tilesWidth; j++) {
		     g.drawImage(tiles.get(tilesWidth*i + j), j*50, i*50, null);
	    }
	}

////////////////////ACTUAL TREASURES////////////////////////////////
  for (int i = 0; i < theTreasures.size(); ++i){
      if(theTreasures.get(i).getFound()) {
          if (GameGui.debug) System.out.println("Drawing Treasure object " +
                                                 i + "\n" + "x = " +
                                                 theTreasures.get(i).getX() +
                                                 " y = " + theTreasures.get(i).getY());

          g.drawImage(theTreasures.get(i).getImage(),
                      theTreasures.get(i).getX()*50,
                      theTreasures.get(i).getY()*50,
                      null);
      }
  }
////////////////////////////////////////////////////////////////////

  // draw the actual player
  g.drawImage(player.getCurrentImage(), player.getXPos(), player.getYPos(), null);

	Graphics2D g2 = (Graphics2D) g;
	if(!message.equals("")) {
	    g2.setColor(new Color(1f,0f,0f,.5f));
	    g2.fill(new Rectangle(100,0,250,100));
	    g2.setFont(new Font(null,Font.BOLD, 20));
	    g2.setColor(Color.BLACK);
	    g2.drawString(message, 110, 50);
	}

    }

    /* Draws the player sprite onto a new tile 
    public void updatePlayer() {
	     paintImmediately(player.getXPos()-10, player.getYPos()-10,100,100);
    }

    /*
       loadMap first reads in the png files of the appropriate tile.
       It scans the text file map.txt and loads the appropriate png image into the instance variable tiles.
       Tiles is later used  by paintComponent to actually make the tiles appear.
     

    public void loadMap(String name){
	tiletypes = new ArrayList<Character>();
	try {

      String dir = "/resources/";
	    String imagefile1 = "bush.png";
	    String imagefile2 = "grass.png";
		  String imagefile3 = "stone.PNG";
	    URL url = (getClass().getResource(dir+name));

	    if(GameGui.debug){
		System.out.println("dir + name = " + (dir + name));
		System.out.println("url = " + url);  }

	    Scanner scanner = new Scanner(getClass().getResourceAsStream(dir+ name));

	    BufferedImage grassTile = ImageIO.read(getClass().getResource("/resources/grass.png"));
	    BufferedImage bushTile = ImageIO.read(getClass().getResource("/resources/bush.png"));
		  BufferedImage stoneTile = ImageIO.read(getClass().getResource("/resources/stone.PNG"));
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
		if(temp.equals(("S"))) {
		    tiles.add(stoneTile);
		    tiletypes.add('S');
		}
	    }

	} catch (IOException e) {
	    e.printStackTrace();}

    }

    /*
       Ensures that the player does not go outside the bounds of the map (0-11 by 0-9).
       If the player is standing on the same tile as a treasure, then the
       message variable will change which makes the "TREASURE # FOUND"
       message box appear
    
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
       //prevent player from move into stones
       else if(tiletypes.get(yTile*tilesWidth + xTile) == 'S')
          player.setMovable(false);
       else if(player.getXPos() != player.getXTile() * 50 || player.getYPos() != player.getYTile() * 50)
          player.setMovable(false);
       else
          player.setMovable(true);

       // loop through the Treasures and check if they are found
       for (int i = 0; i < theTreasures.size(); ++i){
          if(xTile == theTreasures.get(i).getX() &&
             yTile == theTreasures.get(i).getY() &&
             theTreasures.get(i).getFound() == false){
                setMessage(i);
                theTreasures.get(i).setFoundTrue();
                foundTreasureNum++;
                if( foundTreasureNum == theTreasures.size() ) setMessageFinal(true);
                if(GameGui.debug) System.out.println("foundTreasureNum++");
          }
       }
    }
    /* changes the message instance variable
     
    public void setMessage(int treasure_number) {
	     message = "TREASURE " + treasure_number + " FOUND!";
	     new Thread(new MessageThread(this)).start();
    }
    /* changes the message with final
     
	 public void setMessageFinal(boolean answer){
		 if (answer){
			 message = "YOU WIN!";
			 new Thread(new MessageThread(this)).start();
			 //TODO: fix the problem of the time a message box appear
			 //TODO: find a way to pause the game
		 }
	 }

    /* loadPlayer is being used by the go() method in GameGui.java.
       It initializes the 3 treasures and the player sprite.
     
    public void loadPlayer(Player player, String name) {
	    this.player = player;
    }

    public void loadTreasure(ArrayList<Treasure> treasures) {
      this.theTreasures = treasures;

      for ( int i = 0; i < treasures.size(); ++i){
        this.theTreasures.set( i, treasures.get(i));
      }
    }
}*/
