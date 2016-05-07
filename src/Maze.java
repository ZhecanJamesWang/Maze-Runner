import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by philip on 5/5/16.
 * Implementation that defines walls and parts of the maze, along with reading levels from txt.
 */
public class Maze {
    private final int ROWS = 15;
    private final int COLS = 15;
    private Cell[][] grid;

    /**
     * Constructor that takes a specific level to build the cells from, then store in grid
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
                }
                else
                    grid[i][j].setWall(false);
            }
        }
    }

    /**
     * Reads a maze level from a text file, then saves it into the map variable
     * @param level which level to read from
     * @return
     */
    public int[][] readLevel(String level) {
        int[][] map = new int[ROWS][COLS];

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
     * Returns the cell at a specific row and column location
     * @param row location
     * @param col location
     * @return
     */
    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    /**
     * Testing method for cell/maze objects
     * @param args
     */
    public static void main(String[] args) {
        Maze mz1 = new Maze("level0.txt");
        System.out.println(mz1.getCell(14, 14));
    }

}


/**
 * Created by philip on 5/5/16.
 * Represents one cell in the maze.
 */
class Cell {
    private boolean wall;

    /**
     * Constructor which sets the wall default to true
     */
    public Cell() {
        this.wall = true;
    }

    /**
     * Getter for if cell is a wall
     * @return
     */
    public boolean isWall() {
        return wall;
    }

    /**
     * Setter for making a cell a wall
     * @param isWall
     */
    public void setWall(boolean isWall) {
        wall = isWall;
    }

    /**
     * Adding a cell to maze will default the wall to false
     */
    public void addToMaze() {
        wall = false;
    }
}
