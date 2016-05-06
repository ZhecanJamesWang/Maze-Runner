import javax.swing.*;
import java.awt.*;

/**
 * Created by philip on 5/5/16.
 * GUI representation of the Maze object.
 */
public class MazePanel extends JPanel {
    // All final declarations for maze graphic elements
    private final int sizeOfCell = 30;
    private final Color wallColor = Color.blue;
    private final Color mazeColor = Color.white;
    private final ImageIcon playerIcon = new ImageIcon("playerIcon.gif");
    private final ImageIcon goalIcon = new ImageIcon("goalIcon.gif");

    // Declarations for Maze elements
    private int rows, columns;
    private String level;
    private Maze mazeObj;
    private JLabel[][] mazeLabels;
    private int[][] playerLoc;
    private int[][] goalLoc;
    private JLabel status;
    private DirectionListener movementListener;
    private MouseOverListener mouseListener;

    /**
     *
     */
    public MazePanel(int rows, int columns, String level) {
        this.rows = rows;
        this.columns = columns;
        this.level = ""; // set default level to blank
        this.playerLoc = new int[1][0];
        this.goalLoc = new int[14][14];
        mouseListener = new MouseOverListener();
        mazeObj = new Maze(level);
    }
}
