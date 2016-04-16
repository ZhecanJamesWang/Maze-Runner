# Maze-Runner
Data Structure Final Project



Technical Report: 
Philip Seger, Zhecan Wang, Ziyi Lan

User Manual
The main idea behind our project is to implement a maze in some capacity that a user can interact with. While we have not truly decided on what form the interaction will take, our two ideas are either the user can navigate the game as a maze, with helpful hints to help them if they get stuck, or to use the maze as a way of teaching about graph search techniques, illustrating breadth-first vs. depth-first search visually. Our general concept might look a bit like below:

First, a Level selection can be used to pick difficulty (from pre-built maze files (though depending on time we may “randomly” generate mazes)). If a user gets stuck while navigating a maze, they can use the Hint option to show them the best next move. Then, to show a visual representation of different search techniques, DFS and BFS will paint the maze, showing the progress to the end. Finally, the body of the screen is occupied by the Maze itself, and a Steps counter in the bottom corner shows you how you are doing.


ADTs
2D array (probably linked list, but can be decided) - read from text and print to GUI
Reason: it is easy to display something on a GUI using 2D arrays (think X, Y coordinates) instead of with a map
graph - built from previous array
Reason: it is easier to search using a graph, so using some time to copy our array to a graph is helpful
queue - user/computer movement or path
Reason: Process the user’s moves in the order they are inputted, so as not to process keystrokes in the wrong order or cause confusion

Classes:
Maze
Search
Visualization Classes
Frame Class
Panel Class
updateGraph Class
Driver/User

Actions:
Maze - everything maze related
readTextMap(): read a text file that contains 0s and 1s forming a maze
Input: String fileName
Output: Int [][] (2D array)
createNewMaze(): create a new “random” maze using search to verify it is solvable
Output: Int [][] (2D array)
generateGraph(): create a graph from the 2D maze array
Output: graph graphMap
getMap(): getter for the map object, returns in array format
Output: Int [][] (2D array)
getGraph(): getter for graph, returns in graph format
Output: graph graphMap

Search - generate and make solved paths
dfs(): perform the depth first search on the graph
Input: Point currentLocation
Output: LinkedQueue pathToEndPoint
bfs(): perform the breadth first search on the graph
Input: Point currentLocation
Output: LinkedQueue pathToEndPoint
optimal(): perform all the algorithms in the class, compare their steps and choose the fastest one
Input: N/A
Output: String Algname, LinkedQueue pathToEndPoint

Visualizations - colored paths on the GUI to show progress (note: multiple classes)
Frame Class
Panel Class
updateGraph Class
getSteps()
Input: Queue steps
	
Driver/User - take keyboard input or console commands to control movement (also deals with automatic computer input (for use in searches))
getInput(): receive commands from user keyboard
Input: keyboard presses (individually)
Output: LinkedQueue of saved commands
sendCommands(): used so other functions can send movement instructions
Input: command
Output: LinkedQueue of saved commands
