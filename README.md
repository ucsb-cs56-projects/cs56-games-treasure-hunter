cs56-games-treasure-hunter
==========================
A GUI game called Treasure-Hunter.

README file updated by Mena Iskander & Miguel Delgado, CS56, W14, Proj1.

README file updated in 2016 by Danielle Dodd and George Lieu, CS56, W16, Lab09

README file updated in 2016 by Lisa L and Patrick V, CS56, F16, Lab09

Project History
===============
```
 W14 | jcneally 5pm | migueld37, menaiskander | Treasure Hunter Game
 W16 | vincenicoara 6pm | danielledodd, georgelieu | Treasure Hunter Game
 F16 | alex 6pm | lisa,patrick  | Treasure Hunter Game
```

=======
# File Explanation
=================

The project creates an instance of the game which allows the user to control a player in search of multiple pieces of treasure in a map made up of grass, bush and stone tiles. The game is fully functional and allows the user to explore the map even after finding the multiple pieces of treasure.

# Developer Notes
================

* GameComponent.java contains most of the important implementations of the game. Any problems with the player movement, loading of map and player or the actual paintComponent method are found in that file.

* GameGui.java contains the code for the creation of the GUI and different instances of treasure and player.

* Player.java contains the code for the actual implementation of the player. Any problem occurring with the player moving in the wrong direction or not being created, a look should be taken at this file.

* Tresure.java contains the code for the actual implementation of the treasures. Any problem occuring with the treasures placing on the map could be found in this file.

* PlayerTest.java contains JUnit tests for the Player class. You can run it using ant test

* MessageThread.java contains a thread class that allows the "treasure found" message to disappear after 5 seconds. 

# Running the Game
==================

In order to run the game you must do the following:
1. type "ant run"  

Once you start the game you the board will appear and your character will be at position 0,0 

![](http://i.imgur.com/qor9ibq.png)

There are 3 treasures (or more, you can adjust them in the GameGUI.java) randomly placed on the map. This is the result of finding one. 

![](http://i.imgur.com/9TiBmmL.png)

There are some stones on the map, where you are unable to pass through.

After you find all the treasures, a "YOU WIN" message will appear.

# W16 Final Remarks
===============
The treasure hunter game is a simple game with a GUI where the user controls a player sprite to look for 3 randomly placed treasures. There are 3 main files you will most likely be working with: GameComponent.java, GameGui.java, and Player.java. If you decide to edit the message thread then you can look into the MessageThread.java to make these changes. Refer to the developer notes in the README for further information. It would be a good idea to look into the issue of player movement lagtime, as it detracts from the game play. A bug that you can look into is the duration of the message box as it is inconsistent. For refactoring code, you could consider the (1) randomly placed treasure segment and (2) the treasures appearance after finding them. 


# F16 Final Remarks
===============
Treasure Hunter now uses an independent Treasure class for its on-screen treasures, rather than an instance of Player. The code has been cleaned up significantly, but your work is not yet done. One particularly smelly bit is the code where Treasure objects are generated such that they don't appear underneath a Stone tile. The best thing to do here would probably be to redo the map generation code such that the environment is not static, like it is now. Another big issue is input latency. This might be solved by using a swing timer as described here: http://stackoverflow.com/questions/22730715/java-keyboard-input-game-development
HOWEVER: an even better solution might be to completely replace Java Swing with libGDX: https://libgdx.badlogicgames.com/ , a library based on LWJGL. This may not be an easy task, but it should improve the user experience significantly. You might even be able to run it on Android, if you do it right. Try to maintain feature parity with the original game, and once the whole project is migrated to libGDX, then work on making the game more fun. Treasure Hunter is pretty boring at the moment, so perhaps you guys could make some new design choices in regards to gameplay. Maybe you could implement some enemy mobs that chase you around the map as you hunt for treasure. Perhaps the treasures should be items that the player could use to defend themselves, or build their own structures with. Our advice is to clean up the code inside before adding new features, or expanding the scope of the game.
