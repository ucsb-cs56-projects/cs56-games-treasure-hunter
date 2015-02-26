cs56-games-treasure-hunter
==========================
A GUI game called Treasure-Hunter. Improved upon for CS56 Lab06 by Shayan Sadigh.
README file updated by Mena Iskander & Miguel Delgado, CS56, W14, Proj1

project history
===============
```
 W14 | jcneally 5pm | migueld37, menaiskander | Treasure Hunter Game
```

=================
# File Explanation
=================

The project creates an instance of the game which allows the user to control a player in search of multiple pieces of treasure in a map made up of grass and bush tiles. The game is fully functional and allows the user to explore the map even after finding the multiple pieces of treasure.

# Developer Notes
================

-GameComponent.java contains most of the important implementations of the game. Any problems with the player movement, loading of map and player or the actual paintComponent method are found in that file.

-GameGui.java contains the code for the creation of the GUI and different instances of treasure and player.

-Player.java contains the code for the actual implementation of the player. Any problem occurring with the player moving in the wrong direction or not being created, a look should be taken at this file.

# Running the Game
==================

In order to run the game you must do the following:
1. type "ant run"  

Once you start the game you the board will appear and your character will be at position 0,0 
![](http://i.imgur.com/TA9E8CG.png)

There are 3 treasures on the map. The first one is located here. 
![](http://i.imgur.com/j9XEtBM.png)

Treasure 2 is located here. 

![](http://i.imgur.com/NlbbtiM.png)

Treasure 3 is located here. 

![](http://i.imgur.com/P1P8uKm.png)
