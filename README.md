cs56-games-treasure-hunter
==========================
A GUI game called Treasure-Hunter.

README file updated by Mena Iskander & Miguel Delgado, CS56, W14, Proj1.

README file updated in 2016 by Danielle Dodd and George Lieu, CS56, W16, Lab09

README file updated in 2016 by Lisa L and Patrick V, CS56, F16, Lab09

README file updated in 2017 by Yusuf A and Sang Min O, CS56, F17, Lab09

Project History
===============
```
 W14 | jcneally 5pm	| migueld37, menaiskander	| Treasure Hunter Game
 W16 | vincenicoara 6pm | danielledodd, georgelieu	| Treasure Hunter Game
 F16 | alex 6pm     	| lisa,patrick			| Treasure Hunter Game
 F17 | sean 4pm	    	| yusuf, sang min		| Treasure Hunter Game
```

# File Explanation
=================

The project creates an instance of the game which allows the user to control a player in search of multiple pieces of treasure in a map made up of grass, bush and stone tiles. The game allows the player to move around the map using the four directional keys and allows the player to pause the game at any time by pressing the 'P' key. The player must find all of the treasures before the timer goes to 0 and the player is not allowed to move after the game is over.

# Developer Notes
================

* GameComponent.java creates the component of the GUI that holds all of the visual elemnets of the game. It contains code that loads the tiles into the map, loads the player sprite into the map, sets the treasures randomly in the map, and draws the messages onto the screen. It contains code that checks the validity of the player's move and redraws the graphics with the updates to the game. In addition, the timer is implemented in this class.

* GameGui.java creates the GUI for the game. It is responsible for creating the main menu and the main game component. It is also responsible for listening to user input in order to update the game. The directional keys moves the character and the 'P' key pauses the game. 

* MessageThread.java creates threads for displaying the message. It makes sure that each message is displayed for 5 seconds before it disappears.

* PauseAction.java creates the pause menu. It instantiates all of the components that compose the pause menu. It also creates a button that allows the user to exit the pause menu and go back into the game. The pause menu also keeps track of how much time it was up for (for correctly resuming the timer count). 

* Player.java is the class that creates the player and holds all of its information (such as its location, and its current sprite). 

* PlayerTest.java contains tests for the Player class in order to make sure that it is working properly. 

* Treasure.java is the class that creates the treasures and holds its information (such as its location, its sprite, and whether it has been found). It also contains a static class variable that counts how many total treasures have been found since the start of the game.

# Running the Game 
==================

In order to run the game you must do the following:
1. Type "ant run" from Treasure Hunter's git root directory (i.e. the directory that has the .git directory).
2. In the main menu that shows up, press the "START" button.

Once you start the game you the board will appear and your character will be at position 0,0. There will be 50 seconds on the timer for you to find all of the treasures.

Currently, there are 5 treasures (you can adjust the number of treasures in GameGUI.java) randomly placed on the map. There are some stones on the map, which the player cannot pass through.

When you find a treasure, a message that indicates the number of the treasure that was found. 

If the player finds all of the treasures before the timer goes to 0, a "YOU WIN" message will appear. If the player does not find all of the treasures before the timer goes to 0, a "YOU LOSE" message will appear.

# W16 Final Remarks
===============

The treasure hunter game is a simple game with a GUI where the user controls a player sprite to look for 3 randomly placed treasures. There are 3 main files you will most likely be working with: GameComponent.java, GameGui.java, and Player.java. If you decide to edit the message thread then you can look into the MessageThread.java to make these changes. Refer to the developer notes in the README for further information. It would be a good idea to look into the issue of player movement lagtime, as it detracts from the game play. A bug that you can look into is the duration of the message box as it is inconsistent. For refactoring code, you could consider the (1) randomly placed treasure segment and (2) the treasures appearance after finding them. 

# F16 Final Remarks
===============

Treasure Hunter now uses an independent Treasure class for its on-screen treasures, rather than an instance of Player. The code has been cleaned up significantly, but your work is not yet done. One particularly smelly bit is the code where Treasure objects are generated such that they don't appear underneath a Stone tile. The best thing to do here would probably be to redo the map generation code such that the environment is not static, like it is now. Another big issue is input latency. This might be solved by using a swing timer as described [here](http://stackoverflow.com/questions/22730715/java-keyboard-input-game-development)
HOWEVER: an even better solution might be to completely [replace Java Swing with libGDX](https://libgdx.badlogicgames.com/), a library based on LWJGL. This may not be an easy task, but it should improve the user experience significantly. You might even be able to run it on Android, if you do it right. Try to maintain feature parity with the original game, and once the whole project is migrated to libGDX, then work on making the game more fun. Treasure Hunter is pretty boring at the moment, so perhaps you guys could make some new design choices in regards to gameplay. Maybe you could implement some enemy mobs that chase you around the map as you hunt for treasure. Perhaps the treasures should be items that the player could use to defend themselves, or build their own structures with. Our advice is to clean up the code inside before adding new features, or expanding the scope of the game.

# F17 Final Remarks
===============

A Significant amount of bugs have been taken care of, such as the ones related to collision detection and message thread display. A timer has been added to provide a sense of urgency during the game, which is a start to making the game into something other than a boring map traversal. A pause screen and a basic main menu have also been added. The key-input is no longer extremely sensitive, but there is sometimes a delay when switching movement directions. This might be due to the delay caused by system when registering a new key being pressed, but this should still have a software solution (a similar delay happens between the first two inputs when a key is held, but we were able to provide a solution in the GUI class). The main menu should be updated, since we only added a basic version. The timer should be refactored to a different class, possibly by implementing an observer pattern. The game state now updates in a game loop as opposed to when the character moves, this allows the addition of game features that are independent of the player's movement such as enemies. Implementing a dumb (moves randomly) AI and adding collision detection between them and the player should be simple and would add great value to the game. Another feature that would make this game better is implementing a score system (as the game is right now, the score would probably only depend on the time the player has left) and saving the top score in the game. As the last group mentioned, it would be great to migrate this project to libGDX, but this will take a considerable amount of time. As the game gets larger, this will be harder to do. So, if you are able and up to the challenge, you should give it a shot.
