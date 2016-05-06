import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by philip on 5/5/16.
 * Implementation that controls movement and maze events.
 */
public class Maze {
    private final int rows = 15; //TODO unconstrained the maze size, keeping at 15x15 for now
    private final int cols = 15; //TODO unconstrained the maze size, keeping at 15x15 for now
    private Cell[][] grid;

    public Maze(String level) {
        int[][] levelMap = readLevel(level);

        grid = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell();  // Initialize each Cell in maze

                if (levelMap[i][j] == 1) {
                    grid[i][j].setWall(true);
                }
                else
                    grid[i][j].setWall(false);
            }
        }
    }

    public int[][] readLevel(String level) {
        int[][] map = new int[15][15]; //TODO unconstrained the maze size, keeping at 15x15 for now

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

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }


}


/**
 * Created by philip on 5/5/16.
 * Represents one cell in the maze.
 */
class Cell {
    private boolean wall;

    public Cell() {
        this.wall = true;
    }

    public boolean isWall() {
        return wall;
    }

    public void setWall(boolean isWall) {
        wall = isWall;
    }

    public void addToMaze() {
        wall = false;
    }
}
