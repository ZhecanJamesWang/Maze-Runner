import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;

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
    private final Color hintColor = new Color(0xE74C3C);
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
    private JLabel status, remainHint;
    private DirectionListener movementListener;
    private int hintCount;
    private JButton Hint, BFS, DFS;
    private Search search;

    /**
     *
     */
    public MazePanel(String l) {
        if (l == "easy") {
            this.level = "level1.txt";
        } else if (l == "medium") {
            this.level = "level2.txt";
        } else if (l == "hard") {
            this.level = "level3.txt";
        }
        this.rows = 15;
        this.columns = 15;
        this.playerLocR = 1;
        this.playerLocC = 1;
        this.goalLocR = 13;
        this.goalLocC = 13;
        mazeObj = new Maze(level);

//        perform search for later use
//        TODO cleanup
        this.search = new Search(mazeObj);
        search.uniqueMatrix();
        search.arraytoGraph();
        search.setStartPoint(1, 1);
        search.setEndPoint(13, 13);

        addMazeComponents();

        status = new JLabel("Try first, then get hints!"); // TODO not sure if needed
        status.setFont(new Font("Roboto", Font.PLAIN, 24));
        status.setBackground(wallColor);
        status.setForeground(Color.gray);
        add(status);
        movementListener = new DirectionListener();
        addKeyListener(movementListener);

        setBackground(wallColor);
        setPreferredSize(new Dimension(columns * sizeOfCell, rows * sizeOfCell + 100)); // TODO space for buttons
        setFocusable(true);
    }

    private void addMazeComponents() {
        JPanel mazePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel mainPanel = new JPanel();
        buttonPanel.setBackground(wallColor);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mazePanel.setLayout(new GridLayout(rows, columns));
        mazePanel.setBackground(wallColor);
        mazeLabels = new JLabel[rows][columns];

        Hint = new JButton("Hint");
        hintCount = 3;
        remainHint = new JLabel(hintCount + " hints remaining");
        remainHint.setForeground(Color.lightGray);
        remainHint.setFont(new Font("Roboto", Font.PLAIN, 16));
        BFS = new JButton("BFS");
        DFS = new JButton("DFS");
        BFS.setBackground(Color.darkGray);
        BFS.setForeground(Color.lightGray);
        BFS.setFocusPainted(false);
        BFS.setFont(new Font("Roboto", Font.PLAIN, 24));
        DFS.setBackground(Color.darkGray);
        DFS.setForeground(Color.lightGray);
        DFS.setFocusPainted(false);
        DFS.setFont(new Font("Roboto", Font.PLAIN, 24));
        Hint.setBackground(Color.darkGray);
        Hint.setForeground(Color.lightGray);
        Hint.setFocusPainted(false);
        Hint.setFont(new Font("Roboto", Font.PLAIN, 24));

        ButtonListener buttonListener = new ButtonListener();
        BFS.addActionListener(buttonListener);
        DFS.addActionListener(buttonListener);
        Hint.addActionListener(buttonListener);

//        TODO Check that this isn't too hacky
        BFS.setFocusable(false);
        DFS.setFocusable(false);
        Hint.setFocusable(false);

        buttonPanel.add(BFS);
        buttonPanel.add(DFS);
        buttonPanel.add(Hint);
        buttonPanel.add(remainHint);

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
        mazeLabels[goalLocR][goalLocC].setIcon(goalIcon);
        setMazeColor(0, 0);
        mainPanel.add(buttonPanel);
        mainPanel.add(mazePanel);
        add(mainPanel);
    }

    private void movePlayer(int deltaR, int deltaC) {
        mazeLabels[playerLocR][playerLocC].setIcon(null);
        playerLocR += deltaR;
        playerLocC += deltaC;
        mazeLabels[playerLocR][playerLocC].setIcon(playerIcon);
        setMazeColor(deltaR, deltaC);
        handleGameOver();
    }

    private void setMazeColor(int deltaR, int deltaC) {
        if (deltaR != 0)
            mazeLabels[playerLocR + (-1 * deltaR)][playerLocC].setBackground(visitedColor);
        if (deltaC != 0)
            mazeLabels[playerLocR][playerLocC + (-1 * deltaC)].setBackground(visitedColor);
    }

    public void setCellColor(int x, int y) {
        mazeLabels[y][x].setBackground(hintColor);
    }

    public void handleGameOver() {
        if (playerLocR == goalLocR && playerLocC == goalLocC) {
            removeKeyListener(movementListener);
            status.setText("Congratulations! You have solved the maze.");
        }
    }

    public int getPlayerRow() {
        return playerLocR;
    }

    public int getPlayerCol() {
        return playerLocC;
    }

    public void setLocColor(int newRow, int newCol) throws Exception {
//        check to make sure it is a legal move
        if (newRow < rows && newCol < columns && !mazeObj.getCell(newRow, newCol).isWall()) {
            mazeLabels[newRow][newCol].setBackground(visitedColor);
        } else {
            throw new Exception("That is an invalid space to color");
        }
    }

    private class DirectionListener implements KeyListener {
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

        public void keyTyped(KeyEvent event) {
        }

        public void keyReleased(KeyEvent event) {
        }
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == BFS) {
                search.bfsTraversal();
                ArrayList<Tuple> al = search.pathFinding("bfs");
                for (int i = 0; i < al.size(); i++) {
                    setCellColor(al.get(i).getY(), al.get(i).getX());
                }
            } else if (e.getSource() == DFS) {
                search.dfsTraversal();
                ArrayList<Tuple> al = search.pathFinding("dfs");
                for (int i = 0; i < al.size(); i++) {
                    setCellColor(al.get(i).getY(), al.get(i).getX());
                }
            } else if (e.getSource() == Hint) {
                if (hintCount == 0) {
                    status.setText("No more hints availbale");
                    return;
                }
                hintCount -= 1;
                remainHint.setText(hintCount + " hints remaining");

//                do a new search from the current player location
                search.uniqueMatrix();
                search.arraytoGraph();
                search.setStartPoint(playerLocR, playerLocC);
                search.setEndPoint(13, 13);
                search.dfsTraversal();
                ArrayList<Tuple> al = search.pathFinding("dfs");
                setCellColor(al.get(1).getY(), al.get(1).getX());
            }
        }
    }
}
