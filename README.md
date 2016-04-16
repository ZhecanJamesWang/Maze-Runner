# Maze-Runner
Data Structure Final Project

Philip Seger, Zhecan Wang, Ziyi Lan

## User Manual
The main idea behind our project is to implement a maze in some capacity that a user can interact with. While we have not truly decided on what form the interaction will take, our two ideas are either the user can navigate the game as a maze, with helpful hints to help them if they get stuck, or to use the maze as a way of teaching about graph search techniques, illustrating breadth-first vs. depth-first search visually. Our general concept might look a bit like below:

First, a Level selection can be used to pick difficulty (from pre-built maze files (though depending on time we may “randomly” generate mazes)). If a user gets stuck while navigating a maze, they can use the Hint option to show them the best next move. Then, to show a visual representation of different search techniques, DFS and BFS will paint the maze, showing the progress to the end. Finally, the body of the screen is occupied by the Maze itself, and a Steps counter in the bottom corner shows you how you are doing.

## Technical Report: 

### ADTs
1. 2D array (probably linked list, but can be decided) - read from text and print to GUI
Reason: it is easy to display something on a GUI using 2D arrays (think X, Y coordinates) instead of with a map
2. graph - built from previous array
Reason: it is easier to search using a graph, so using some time to copy our array to a graph is helpful
3. queue - user/computer movement or path
Reason: Process the user’s moves in the order they are inputted, so as not to process keystrokes in the wrong order or cause confusion

### Classes:
1. Maze
2. Search
3. Visualization Classes
 * Frame Class
 * Panel Class
 * updateGraph Class
 * Driver/User

### Actions:
1. Maze - everything maze related
 1. readTextMap(): read a text file that contains 0s and 1s forming a maze
   * Input: String fileName
   * Output: Int [][] (2D array)
 2. createNewMaze(): create a new “random” maze using search to verify it is solvable
   * Output: Int [][] (2D array)
 3. generateGraph(): create a graph from the 2D maze array
   * Output: graph graphMap
 4. getMap(): getter for the map object, returns in array format
   * Output: Int [][] (2D array)
 5. getGraph(): getter for graph, returns in graph format
   * Output: graph graphMap

2. Search - generate and make solved paths
 a. dfs(): perform the depth first search on the graph
  * Input: Point currentLocation
  * Output: LinkedQueue pathToEndPoint
 b. bfs(): perform the breadth first search on the graph
  * Input: Point currentLocation
  * Output: LinkedQueue pathToEndPoint
 c. optimal(): perform all the algorithms in the class, compare their steps and choose the fastest one
  * Input: N/A
  * Output: String Algname, LinkedQueue pathToEndPoint

3. Visualizations - colored paths on the GUI to show progress (note: multiple classes)
 a. Frame Class
 b. Panel Class
 c. updateGraph Class
 d. getSteps()
  * Input: Queue steps
	
4. Driver/User - take keyboard input or console commands to control movement (also deals with automatic computer input (for use in searches))
 a. getInput(): receive commands from user keyboard
  * Input: keyboard presses (individually)
  * Output: LinkedQueue of saved commands
 b. sendCommands(): used so other functions can send movement instructions
  * Input: command
  * Output: LinkedQueue of saved commands
