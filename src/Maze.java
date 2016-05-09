import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by philip.
 * Implementation that controls movement and maze events.
 */
public class Maze {
    private final int ROWS = 15; //TODO unconstrained the maze size, keeping at 15x15 for now
    private final int COLS = 15; //TODO unconstrained the maze size, keeping at 15x15 for now
    private Cell[][] grid;
    private int[][] map;

    /**
     * Constructor for the maze, creates the level by reading from text file.
     * @param level
     */
    public Maze(String level) {
        int[][] levelMap = readLevel(level);

        grid = new Cell[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid[i][j] = new Cell();

                if (levelMap[i][j] == 1) {
                    grid[i][j].setWall(true);
                } else
                    grid[i][j].setWall(false);
            }
        }
    }

    /**
     * Read the level from a txt file and save in an 2D array.
     * @param level
     * @return
     */
    public int[][] readLevel(String level) {
        this.map = new int[ROWS][COLS]; //TODO unconstrained the maze size, keeping at 15x15 for now

        try {
            Scanner scan = new Scanner(new FileReader(level));

            int row = 0;
            while (scan.hasNextLine()) {
                String line = scan.nextLine();

                for (int column = 0; column < line.length(); column++) {
                    map[row][column] = line.charAt(column) - 48;
                }
                row++;
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Unable to locate text file. Check the name again.");
        }

        return map;
    }

    /**
     * Getters and setters for cell, map.
     * @param row
     * @param col
     * @return
     */
    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public int[][] getMap() {
        return map;
    }

    /**
     * Testing main for array bound issue.
     * @param args
     */
    public static void main(String[] args) {
        Maze mz1 = new Maze("level0.txt");
        System.out.println(mz1.getCell(14, 14));
    }

}


/**
 * Created by philip.
 * Represents one cell in the maze.
 */
class Cell {
    private boolean wall;

    /**
     * Create a new cell with wall being true by default.
     */
    public Cell() {
        this.wall = true;
    }

    /**
     * Method returning if a cell is a wall.
     * @return
     */
    public boolean isWall() {
        return wall;
    }

    /**
     * Sets the sepcific cell as a wall.
     * @param isWall
     */
    public void setWall(boolean isWall) {
        wall = isWall;
    }

    /**
     * Adds a new cell to the maze that is by default false.
     */
    public void addToMaze() {
        wall = false;
    }
}
