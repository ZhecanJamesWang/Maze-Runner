import javax.swing.*;

/**
 * Created by philip on 5/5/16.
 * Driver class for the program, creates the GUI frame and adds our Maze to it.
 */
public class Start {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Maze Runner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new MazePanel());

        frame.pack();
        frame.setVisible(true);
    }
}
