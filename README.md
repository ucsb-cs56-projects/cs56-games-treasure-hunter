cs56-games-treasure-hunter
==========================
A GUI game called Treasure-Hunter.

README file updated by Mena Iskander & Miguel Delgado, CS56, W14, Proj1.

README file updated in 2016 by Danielle Dodd and George Lieu, CS56, W16, Lab09

Project History
===============
```
 W14 | jcneally 5pm | migueld37, menaiskander | Treasure Hunter Game
 W16 | vincenicoara 6pm | danielledodd, georgelieu | Treasure Hunter Game
```

=======
# File Explanation
=================

The project creates an instance of the game which allows the user to control a player in search of multiple pieces of treasure in a map made up of grass and bush tiles. The game is fully functional and allows the user to explore the map even after finding the multiple pieces of treasure.

# Developer Notes
================

-GameComponent.java contains most of the important implementations of the game. Any problems with the player movement, loading of map and player or the actual paintComponent method are found in that file.

-GameGui.java contains the code for the creation of the GUI and different instances of treasure and player.

-Player.java contains the code for the actual implementation of the player. Any problem occurring with the player moving in the wrong direction or not being created, a look should be taken at this file.

-PlayerTest.java contains JUnit tests for the Player class. You can run it using ant test

-MessageThread.java contains a thread class that allows the "treasure found" message to disappear after 5 seconds. 

# Running the Game
==================

In order to run the game you must do the following:
1. type "ant run"  

Once you start the game you the board will appear and your character will be at position 0,0 
![](http://i.imgur.com/qor9ibq.png)

There are 3 treasures randomly placed on the map. This is the result of finding one. 
![](http://i.imgur.com/9TiBmmL.png)

# W16 Final Remarks
===============
The treasure hunter game is a simple game with a GUI where the user controls a player sprite to look for 3 randomly placed treasures. There are 3 main files you will most likely be working with: GameComponent.java, GameGui.java, and Player.java. If you decide to edit the message thread then you can look into the MessageThread.java to make these changes. Refer to the developer notes in the README for further information. It would be a good idea to look into the issue of player movement lagtime, as it detracts from the game play. A bug that you can look into is the duration of the message box as it is inconsistent. For refactoring code, you could consider the (1) randomly placed treasure segment and (2) the treasures appearance after finding them. 