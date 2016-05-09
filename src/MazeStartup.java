import javax.swing.JFrame;

/**
 *
 */
public class MazeStartup {
    /**
     *
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