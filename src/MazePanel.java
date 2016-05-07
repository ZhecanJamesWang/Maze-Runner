import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by philip on 5/5/16.
 * GUI representation of the Maze object.
 */
public class MazePanel extends JPanel {
    // All final declarations for maze graphic elements
    private final int sizeOfCell = 35;
    private final Color wallColor = Color.darkGray;
    private final Color mazeColor = Color.lightGray;
    private final Color visitedColor = new Color(0x3498DB);
    private final ImageIcon playerIcon = new ImageIcon("playerIcon.png");
    private final ImageIcon goalIcon = new ImageIcon("goalIcon.png");

    // Declarations for Maze elements
    private int rows, columns;
    private String level;
    private Maze mazeObj;
    private JLabel[][] mazeLabels;
    private int playerLocR;
    private int playerLocC;
    private int goalLocR;
    private int goalLocC;
    private JLabel status;
    private DirectionListener movementListener;

    /**
     * MazePanel constructor, sets the default size, level, locations of
     * different objects, and adds components to the panel
     */
    public MazePanel() {
        this.rows = 15;
        this.columns = 15;
        this.level = "level1.txt"; // TODO remove because testing
        this.playerLocR = 1;
        this.playerLocC = 1;
        this.goalLocR = 13;
        this.goalLocC = 13;
//        create the maze object with the specified level
        mazeObj = new Maze(level);
        addMazeComponents();

//        set the colors and attributes
        status = new JLabel(""); // TODO not sure if needed
        status.setFont(new Font("Roboto", Font.PLAIN, 24));
        status.setBackground(wallColor);
        status.setForeground(Color.gray);
        add(status);
//        movement listener to control with keyboard
        movementListener = new DirectionListener();
        addKeyListener(movementListener);

//        set size of window
        setBackground(wallColor);
        setPreferredSize(new Dimension(columns * sizeOfCell, rows * sizeOfCell + 200)); // TODO space for buttons
        setFocusable(true);
    }

    /**
     * Creates the maze panel and adds the player/goal icons, coloring of cells, and
     * general maze characteristics
     */
    private void addMazeComponents() {
        JPanel mazePanel = new JPanel();
        mazePanel.setLayout(new GridLayout(rows, columns));
        mazePanel.setBackground(wallColor);
        mazeLabels = new JLabel[rows][columns];

//        each "cell" is a JLabel, which we can color and change on the fly
//        here we go through the whole grid and create each JLabel object, sazing in mazeLabels
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

//        set goal and player icons in their positions
        mazeLabels[playerLocR][playerLocC].setIcon(playerIcon);
        mazeLabels[goalLocR][goalLocC].setIcon(goalIcon);
        add(mazePanel);
    }

    /**
     * Move the player using a change amount, updates the icon to the new position and overwrites the old
     * @param deltaR change in row amount (usually should only be 1 or 0)
     * @param deltaC change in column amount (usually should only be 1 or 0)
     */
    private void movePlayer(int deltaR, int deltaC) {
        mazeLabels[playerLocR][playerLocC].setIcon(null);
        playerLocR += deltaR;
        playerLocC += deltaC;
        mazeLabels[playerLocR][playerLocC].setIcon(playerIcon);
//        color over the changes, with the input we provide we can color the path of where we just visited
        setMazeColor(deltaR, deltaC);
//        check if game over after every move
        handleGameOver();
    }

    /**
     * Sets the cell color to draw a path from where the player has gone to where they are heading
     * @param deltaR row position of move
     * @param deltaC column position of move
     */
    private void setMazeColor(int deltaR, int deltaC) {
        if (deltaR != 0)
//            use -1 * delta to get position we just moved from
            mazeLabels[playerLocR + (-1 * deltaR)][playerLocC].setBackground(visitedColor);
        if (deltaC != 0)
            mazeLabels[playerLocR][playerLocC + (-1 * deltaC)].setBackground(visitedColor);
    }

    /**
     * If the player has reached the goal, stop listening to input and print win message
     */
    public void handleGameOver() {
        if (playerLocR == goalLocR && playerLocC == goalLocC) {
            removeKeyListener(movementListener);
            status.setText("Congratulations! You have solved the maze.");
        }
    }

    /**
     * Getter for player row position
     * @return row position
     */
    public int getPlayerRow() {
        return playerLocR;
    }

    /**
     * Getter for player column position
     * @return column position
     */
    public int getPlayerCol() {
        return playerLocC;
    }

    /**
     * Setter for a specific location's color, for use with search class
     * @param newRow row location
     * @param newCol column location
     * @throws Exception if the space is a wall/out of bounds
     */
    public void setLocColor(int newRow, int newCol) throws Exception {
//        check to make sure it is a legal move
        if (newRow < rows && newCol < columns && !mazeObj.getCell(newRow, newCol).isWall()) {
            mazeLabels[newRow][newCol].setBackground(visitedColor);
        }
        else {
            throw new Exception("That is an invalid space to color");
        }
    }

    /**
     * Class that listens to keyboard input
     */
    private class DirectionListener implements KeyListener {
        /**
         * Method checking keypress actions. We care about these keys:
         *  w/a/s/d and up/down/left/right - movement
         *  q and esc - close the game
         * @param event
         */
        public void keyPressed(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_W) {
                if (!mazeObj.getCell(playerLocR - 1, playerLocC).isWall()) {
                    movePlayer(-1, 0);
                }
            } else if (event.getKeyCode() == KeyEvent.VK_DOWN || event.getKeyCode() == KeyEvent.VK_S) {
                if (!mazeObj.getCell(playerLocR + 1, playerLocC).isWall()) {
                    movePlayer(+1, 0);
                }
            } else if (event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_A) {
                if (!mazeObj.getCell(playerLocR, playerLocC - 1).isWall()) {
                    movePlayer(0, -1);
                }
            } else if (event.getKeyCode() == KeyEvent.VK_RIGHT || event.getKeyCode() == KeyEvent.VK_D) {
                if (!mazeObj.getCell(playerLocR, playerLocC + 1).isWall()) {
                    movePlayer(0, +1);
                }
            } else if (event.getKeyCode() == KeyEvent.VK_ESCAPE || event.getKeyCode() == KeyEvent.VK_Q) {
                System.exit(0);
            }

        }

//        empty definitions for required methods we don't need
        public void keyTyped (KeyEvent event) {}
        public void keyReleased (KeyEvent event) {}
    }
}
