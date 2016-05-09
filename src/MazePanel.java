import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * Created by philip, jason, james.
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
    private JPanel mazePanel;
    private MultithreadingColor multithreadingColor1;
    private MultithreadingColor multithreadingColor2;

    /**
     *
     */
    public MazePanel(String l) {
        multithreadingColor1 = new MultithreadingColor();
        multithreadingColor2 = new MultithreadingColor();

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
        this.search = new Search(mazeObj);
        search.uniqueMatrix();
        search.arraytoGraph();
        search.setStartPoint(1, 1);
        search.setEndPoint(13, 13);

        addMazeComponents();

//        set the colors and attributes
        status = new JLabel("Try first, then get hints!");
        status.setFont(new Font("Roboto", Font.PLAIN, 24));
        status.setBackground(wallColor);
        status.setForeground(Color.gray);
        add(status);
//        movement listener to control with keyboard
        movementListener = new DirectionListener();
        addKeyListener(movementListener);

//        set size of window
        setBackground(wallColor);
        setPreferredSize(new Dimension(columns * sizeOfCell, rows * sizeOfCell + 100));
        setFocusable(true);
    }

    /**
     * Creates the maze panel and adds the player/goal icons, coloring of cells, and
     * general maze characteristics
     */
    private void addMazeComponents() {
//        create all the panels/attributes
        JPanel mazePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel mainPanel = new JPanel();
        buttonPanel.setBackground(wallColor);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mazePanel.setLayout(new GridLayout(rows, columns));
        mazePanel.setBackground(wallColor);
        mazeLabels = new JLabel[rows][columns];

//        create all the attributes for the buttons
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

//        listen for inputs from the bfs/dfs/hint buttons
        ButtonListener buttonListener = new ButtonListener();
        BFS.addActionListener(buttonListener);
        DFS.addActionListener(buttonListener);
        Hint.addActionListener(buttonListener);

//        don't hold focus on the buttons so we can still control the maze
        BFS.setFocusable(false);
        DFS.setFocusable(false);
        Hint.setFocusable(false);

        buttonPanel.add(BFS);
        buttonPanel.add(DFS);
        buttonPanel.add(Hint);
        buttonPanel.add(remainHint);

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
        setMazeColor(0, 0);
        mainPanel.add(buttonPanel);
        mainPanel.add(mazePanel);
        add(mainPanel);
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
//        color over the changes, with the input we provide we can color the path of where we just visited
        mazeLabels[playerLocR][playerLocC].setIcon(playerIcon);
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
//            use -1 * delta to get position we just moved from
        if (deltaR != 0)
            mazeLabels[playerLocR + (-1 * deltaR)][playerLocC].setBackground(visitedColor);
        if (deltaC != 0)
            mazeLabels[playerLocR][playerLocC + (-1 * deltaC)].setBackground(visitedColor);
    }

    /**
     * Set a specific cell's color
     * @param x position
     * @param y position
     */
    public void setCellColor(int x, int y) {
        mazeLabels[y][x].setBackground(hintColor);
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
        } else {
            throw new Exception("That is an invalid space to color");
        }
    }

    public void clear(){
        for(int row = 0; row < mazeLabels.length; row++){
            for(int col = 0; col < mazeLabels[0].length; col++ ){
                if (!mazeObj.getCell(row, col).isWall()) {
                    mazeLabels[row][col].setBackground(mazeColor);
                }
            }
        }

    }

    /**
     * Created by philip.
     * Listens for user input and moves in the maze.
     */
    private class DirectionListener implements KeyListener {
        /**
         * Listen for up/down/left/right or w/s/a/d input for user movement.
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

        /**
         * Unnecessary methods.
         */
        public void keyTyped(KeyEvent event) {}
        public void keyReleased(KeyEvent event) {}
    }

    /**
     * Enables multithreading for coloring properly.
     */
    class MultithreadingColor extends Thread{
        private ArrayList<Tuple> al;

        public MultithreadingColor(){
        }

        public MultithreadingColor(ArrayList<Tuple> al){
            this.al = al;
        }

        public void run(){
            for (int i = 0; i < al.size(); i++) {
                setCellColor(al.get(i).getY(), al.get(i).getX());
                try{
                    TimeUnit.MILLISECONDS.sleep(200);
                }catch(InterruptedException ex){
                    System.out.println("time out exception");
                }
            }
        }
    }

    /**
     * Created by philip.
     * Listens for button input and performs DFS/BFS/Hint depending on input.
     */
    private class ButtonListener implements ActionListener {
        public void clearThreads(){
            if((multithreadingColor2 != null) && (multithreadingColor2.getState() != Thread.State.TERMINATED)){
                multithreadingColor2.stop();
            }

            if((multithreadingColor1 != null) && (multithreadingColor1.getState() != Thread.State.TERMINATED)){
                multithreadingColor1.stop();
            }
        }

        /**
         * Listens for button input for bfs and dfs and hints.
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == BFS) {
//                run the bfs traversal
                search.bfsTraversal();
                int x, y;
                ArrayList<Tuple> al = search.pathFinding("bfs");

                clearThreads();

                clear();

                multithreadingColor1 = new MultithreadingColor(al);
                multithreadingColor1.start();

            } else if (e.getSource() == DFS) {
//                same as bfs, but now run dfs traversal
                search.dfsTraversal();
                ArrayList<Tuple> al = search.pathFinding("dfs");

                clearThreads();

                clear();

                multithreadingColor2 = new MultithreadingColor(al);
                multithreadingColor2.start();

            } else if (e.getSource() == Hint) {
//                if you have used all hints, not allowed to use any more
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

                // search.setStartPoint(13, 13);
                // search.setEndPoint(playerLocR, playerLocC);

                search.dfsTraversal();
                ArrayList<Tuple> al = search.pathFinding("dfs");
                setCellColor(al.get(al.size() - 2 ).getY(), al.get( al.size() - 2 ).getX());
            }
        }
    }
}