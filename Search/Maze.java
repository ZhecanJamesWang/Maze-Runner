import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by philip on 4/16/16.
 */
public class Maze {
    private int[][] maze;
//    TODO create a map variable

    /** TODO implement noargs Maze constructor
     * No args constructor, builds a random maze.
     *
     */
    public Maze() {

    }

    /** TODO implement Maze constructor(filename)
     * Constructor that takes a maze filename and generates
     * the necessary objects for a maze.
     * @param filename
     */
    public Maze(String filename) {
        maze = new int[15][15]; //TODO unconstrain the maze size, keeping at 15x15 for now

//        load the text file
        try {
            Scanner scan = new Scanner(new FileReader(filename));

//            save the contents of the maze file into the maze array
            int row = 0;
            while (scan.hasNextLine()) {
                String line = scan.nextLine();

                for (int column = 0; column < line.length(); column++) {
                    maze[row][column] = line.charAt(column) - 48;
                }
                row++;
            }
        }
        catch (FileNotFoundException exception) {
            System.out.println("Unable to locate text file. Check the name again.");
        }
    }

    /** TODO implement generateGraph
     * Generates a graph from a 2D (int[][]) array.
     */
    public void generateGraph() {

    }

    /** TODO implement getMap
     * Getter for map.
     * @return
     */
    public int[][] getMap() {
        return maze;
    }

    /** TODO implement setMap
     * Setter for map.
     * @param x coordinate
     * @param y coordinate
     * @param newVal value to replace at coords
     */
    public void setMap(int x, int y, int newVal) {
        maze[x][y] = newVal;
    }

    /** TODO implement getGraph
     * Getter for graph.
     */
    public void getGraph() {

    }

    /** TODO implement setGraph
     * Setter for graph.
     */
    public void setGraph() {

    }

    public String toString() {
        String result = "";
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                result += maze[i][j];
            }
            result += "\n";
        }
        return result;
    }

    /**
     * Main method for testing.
     */
    public static void main(String[] args) {
        Maze mz1 = new Maze("level1.txt");
        System.out.println(mz1);
        mz1.setMap(1, 1, 5);
        System.out.println(mz1);
    }
}