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
    private int playerLocR;
    private int playerLocC;
    private int goalLocR;
    private int goalLocC;
    private JLabel status;
    private DirectionListener movementListener;

    /**
     *
     */
    public MazePanel() {
        this.rows = 15;
        this.columns = 15;
        this.level = "level0.txt"; // set default level to blank
        this.playerLocR = 1;
        this.playerLocC = 1;
        this.goalLocR = 13;
        this.goalLocC = 13;
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

        mazeLabels[playerLocR][playerLocC].setIcon(playerIcon);
        mazeLabels[goalLocR][goalLocC].setIcon(playerIcon);
        setMazeColor();
        add(mazePanel);
    }

    private void movePlayer(int deltaR, int deltaC) {
        mazeLabels[playerLocR][playerLocC].setIcon(null);
        playerLocR += deltaR;
        playerLocC += deltaC;
        mazeLabels[playerLocR][playerLocC].setIcon(playerIcon);
        setMazeColor();
        handleGameOver();
    }

    private void setMazeColor() {
        mazeLabels[playerLocR][playerLocC].setBackground(mazeColor);
        if (!mazeObj.getCell(playerLocR + 1, playerLocC).isWall())
            mazeLabels[playerLocR + 1][playerLocC].setBackground(mazeColor);
        if (!mazeObj.getCell(playerLocR, playerLocC + 1).isWall())
            mazeLabels[playerLocR][playerLocC + 1].setBackground(mazeColor);
        if (!mazeObj.getCell(playerLocR - 1, playerLocC).isWall())
            mazeLabels[playerLocR - 1][playerLocC].setBackground(mazeColor);
        if (!mazeObj.getCell(playerLocR, playerLocC - 1).isWall())
            mazeLabels[playerLocR][playerLocC - 1].setBackground(mazeColor);
    }

    public void handleGameOver() {
        if (playerLocR == goalLocR && playerLocC == goalLocC) {
            removeKeyListener(movementListener);
            status.setText("DONE");
        }
    }

    private class DirectionListener implements KeyListener {
        public void keyPressed(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_UP) {
                if (!mazeObj.getCell(playerLocR - 1, playerLocC).isWall()) {
                    movePlayer(-1, 0);
                }
            } else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                if (!mazeObj.getCell(playerLocR + 1, playerLocC).isWall()) {
                    movePlayer(+1, 0);
                }
            } else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                if (!mazeObj.getCell(playerLocR, playerLocC - 1).isWall()) {
                    movePlayer(0, -1);
                }
            } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (!mazeObj.getCell(playerLocR, playerLocC + 1).isWall()) {
                    movePlayer(0, +1);
                }
            }
        }

        public void keyTyped (KeyEvent event) {}
        public void keyReleased (KeyEvent event) {}
    }
}
