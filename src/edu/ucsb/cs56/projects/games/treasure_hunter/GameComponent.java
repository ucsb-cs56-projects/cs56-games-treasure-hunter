package edu.ucsb.cs56.projects.games.treasure_hunter;

import java.net.URL;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Scanner;

/**
 * A component that draws a map by Alex Wood, edited for CS56 lab06/lab07 by Shayan Sadigh
 *
 * @author Alex Wood
 * @version for CS56, W12, UCSB, 2/16/2012
 */


public class GameComponent extends JComponent {

    /**
     * The paintComponent method is always required if you want
     * any graphics to appear in your JComponent.
     * <p/>
     * There is a paintComponent
     * method that is created for you in the JComponent class, but it
     * doesn't do what we want, so we have to "override" that method with
     * our own method.
     */
    Player player;
    Player treasure;
    Player treasure1;
    Player treasure2;
    private ArrayList<BufferedImage> tiles;
    private ArrayList<Character> tileTypes;
    static protected String message = "";
    private int tilesWidth;
    private int tilesHeight;
    messageRunnable mThread = new messageRunnable();

    public void paintComponent(Graphics g) {
        for (int i = 0; i < tilesHeight; i++) {
            for (int j = 0; j < tilesWidth; j++) {
                g.drawImage(tiles.get(tilesWidth * i + j), j * 50, i * 50, null);
            }
        }
        g.drawImage(treasure.getCurrentImage(), treasure.getXPos(), treasure.getYPos(), null);
        g.drawImage(player.getCurrentImage(), player.getXPos(), player.getYPos(), null);
        Graphics2D g2 = (Graphics2D) g;
        if (!message.equals("")) {
            Color transBlack = new Color(0, 0, 0, 128 );
            g2.setColor(transBlack);
            g2.fillRect(185, 25, 250, 30);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.setColor(Color.RED);
            g2.drawString(message, 192, 47);
        }
    }

    public void updatePlayer() {
        paintImmediately(player.getXPos() - 10, player.getYPos() - 10, 100, 100);
    }

    public void loadMap(String name) {
        tileTypes = new ArrayList<Character>();
        try {
            URL url = (getClass().getResource(GameGui.resourcesDir + name));

            if (GameGui.debug) {
                System.out.println("dir + name = " + (GameGui.resourcesDir + name));
                System.out.println("url = " + url);
            }

            Scanner scanner = new Scanner(getClass().getResourceAsStream(GameGui.resourcesDir + name));
            BufferedImage grassTile = ImageIO.read(getClass().getResource(GameGui.resourcesDir + "grass.png"));
            BufferedImage bushTile = ImageIO.read(getClass().getResource(GameGui.resourcesDir + "bush.png"));
            tilesWidth = scanner.nextInt();
            tilesHeight = scanner.nextInt();
            String temp;
            tiles = new ArrayList<BufferedImage>();
            while (scanner.hasNext()) {
                temp = scanner.nextLine();
                if (temp.equals(("G"))) {
                    tiles.add(grassTile);
                    tileTypes.add('G');
                }
                if (temp.equals(("B"))) {
                    tiles.add(bushTile);
                    tileTypes.add('B');
                }
            }
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void checkMove(int xTile, int yTile) {
        //limits where the player can move (ie. can move out of the box)
        if (xTile < 0 || xTile > 11 || yTile < 0 || yTile > 8)
            player.setMovable(false);
            //allows player to move after finding treasure
        else if (!message.equals(""))
            player.setMovable(true);
            //allows player to move into bushes
        else if (tileTypes.get(yTile * tilesWidth + xTile) == 'B')
            player.setMovable(true);
        else if (player.getXPos() != player.getXTile() * 50 || player.getYPos() != player.getYTile() * 50)
            player.setMovable(false);
        else
            player.setMovable(true);
        //if player finds treasure the string "Treasure Found is displayed"
        if (xTile == treasure.getXTile() && yTile == treasure.getYTile()) {
            mThread.startThread(1);
        } else if (xTile == treasure1.getXTile() && yTile == treasure1.getYTile()) {
            mThread.startThread(2);
        } else if (xTile == treasure2.getXTile() && yTile == treasure2.getYTile()) {
            mThread.startThread(3);
        }
    }

    public void loadPlayer(Player player, String name) {
        if (name.equals("player"))
            this.player = player;
        if (name.equals("treasure"))
            treasure = player;
        if (name.equals("treasure1"))
            treasure1 = player;
        if (name.equals("treasure2"))
            treasure2 = player;
    }

}
