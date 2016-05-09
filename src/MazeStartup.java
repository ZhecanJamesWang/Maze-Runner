import javax.swing.JFrame;

/**
 * Created by philip.
 * Starts all JPanel objects and starts the main game window.
 */
public class MazeStartup {
    /**
     * Main method which creates the maze, panels, and other necessary components.
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Maze Runner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new MazeStartupPanel());

        frame.pack();
        frame.setVisible(true);
    }
}