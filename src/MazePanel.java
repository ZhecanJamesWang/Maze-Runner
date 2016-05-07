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
    private int playerLocX;
    private int playerLocY;
    private int goalLocX;
    private int goalLocY;
    private JLabel status;
    private DirectionListener movementListener;

    /**
     *
     */
    public MazePanel(int rows, int columns, String level) {
        this.rows = rows;
        this.columns = columns;
        this.level = "level0.txt"; // set default level to blank
        this.playerLocX = 1;
        this.playerLocY = 0;
        this.goalLocX = 14;
        this.goalLocY = 14;
        mazeObj = new Maze(level);
        addMazeComponents();

        status = new JLabel(""); // TODO not sure if needed
        status.setFont(new Font("Roboto", Font.PLAIN, 24));
        status.setBackground(wallColor);
        status.setForeground(Color.gray);
        add(status);
        movementListener = new DirectionListener();
        addKeyListener(movementListener);

        setBackground(wallColor);
        setPreferredSize(new Dimension(columns * sizeOfCell, rows * sizeOfCell + 200)); // TODO space for buttons
        setFocusable(true);
    }

    private void addMazeComponents() {
        JPanel mazePanel = new JPanel();
        mazePanel.setLayout(new GridLayout(rows, columns));
        mazePanel.setBackground(wallColor);
        mazeLabels = new JLabel[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                mazeLabels[i][j] = new JLabel();
                mazeLabels[i][j].setPreferredSize(new Dimension(sizeOfCell, sizeOfCell));
                mazeLabels[i][j].setMinimumSize(new Dimension(sizeOfCell, sizeOfCell));
                Color color = wallColor;
                if (!mazeObj.getCell(i, j).isWall()) {
                    color = mazeColor;
                }
                mazeLabels[i][j].setBackground(color);
                mazeLabels[i][j].setOpaque(true);
                mazePanel.add(mazeLabels[i][j]);
            }
        }

        mazeLabels[playerLocX][playerLocY].setIcon(playerIcon);
        mazeLabels[goalLocX][goalLocY].setIcon(playerIcon);
        setMazeColor();
        add(mazePanel);
    }

    private void movePlayer(int deltaX, int deltaY) {
        mazeLabels[playerLocX][playerLocY].setIcon(null);
        playerLocX += deltaX;
        playerLocY += deltaY;
        mazeLabels[playerLocX][playerLocY].setIcon(playerIcon);
        setMazeColor();
        handleGameOver();
    }

    private void setMazeColor() {
        mazeLabels[playerLocX][playerLocY].setBackground(mazeColor);
        if (!mazeObj.getCell(playerLocX + 1, playerLocY).isWall())
            mazeLabels[playerLocX + 1][playerLocY].setBackground(mazeColor);
        if (!mazeObj.getCell(playerLocX, playerLocY + 1).isWall())
            mazeLabels[playerLocX][playerLocY + 1].setBackground(mazeColor);
        if (!mazeObj.getCell(playerLocX - 1, playerLocY).isWall())
            mazeLabels[playerLocX - 1][playerLocY].setBackground(mazeColor);
        if (!mazeObj.getCell(playerLocX, playerLocY - 1).isWall())
            mazeLabels[playerLocX][playerLocY - 1].setBackground(mazeColor);
    }

    public void handleGameOver() {
        if (playerLocX == goalLocX && playerLocY == goalLocY) {
            removeKeyListener(movementListener);
            status.setText("DONE");
        }
    }

    private class DirectionListener {

    }
}
