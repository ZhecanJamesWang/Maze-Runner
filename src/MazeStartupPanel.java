//********************************************************************
//  MazeStartupPanel.java       CS230 Staff
//
//  Select level of difficulty for maze game.
//********************************************************************

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Created by jason.
 * GUI representation of the start of the maze, specifically level select.
 */
public class MazeStartupPanel extends JPanel {
    private JButton easy, medium, hard, quit;
    private JLabel caption, spacer, authors;
    private JFrame mazeWindow;
    private final Color textColor = new Color(0xECF0F1);

    /**
     * Constructor which takes no args and creates the main window.
     * Lots of GUI elements and spacers to look nice.
     */
    public MazeStartupPanel() {
        setBackground(Color.darkGray);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(500, 600));

        caption = new JLabel("The Maze Runner");
        caption.setAlignmentX(Component.CENTER_ALIGNMENT);
        caption.setForeground(textColor);
        caption.setFont(new Font("MS PGothic", Font.PLAIN, 36));

        easy = new JButton("Easy");
        easy.setAlignmentX(Component.CENTER_ALIGNMENT);
        easy.setBackground(Color.darkGray);
        easy.setForeground(Color.lightGray);
        easy.setFocusPainted(false);
        easy.setFont(new Font("Roboto", Font.PLAIN, 24));

        medium = new JButton("Medium");
        medium.setAlignmentX(Component.CENTER_ALIGNMENT);
        medium.setBackground(Color.darkGray);
        medium.setForeground(Color.lightGray);
        medium.setFocusPainted(false);
        medium.setFont(new Font("Roboto", Font.PLAIN, 24));

        hard = new JButton("Hard");
        hard.setAlignmentX(Component.CENTER_ALIGNMENT);
        hard.setBackground(Color.darkGray);
        hard.setForeground(Color.lightGray);
        hard.setFocusPainted(false);
        hard.setFont(new Font("Roboto", Font.PLAIN, 24));

        quit = new JButton("Quit");
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);
        quit.setBackground(Color.darkGray);
        quit.setForeground(Color.lightGray);
        quit.setFocusPainted(false);
        quit.setFont(new Font("Roboto", Font.PLAIN, 24));

        authors = new JLabel("Authors: Philip Seger      Zhecan James Wang      Ziyi Jason Lan");
        authors.setAlignmentX(Component.CENTER_ALIGNMENT);
        authors.setForeground(Color.lightGray);
        authors.setFont(new Font("MS PGothic", Font.PLAIN, 12));

        ButtonListener listener = new ButtonListener();
        easy.addActionListener(listener);
        medium.addActionListener(listener);
        hard.addActionListener(listener);
        quit.addActionListener(new CloseListener());

        add(spacer = new JLabel(" "), "span, grow");
        add(spacer = new JLabel(" "), "span, grow");
        add(caption);
        add(spacer = new JLabel(" "), "span, grow");
        add(spacer = new JLabel(" "), "span, grow");
        add(easy);
        add(spacer = new JLabel(" "), "span, grow");
        add(spacer = new JLabel(" "), "span, grow");
        add(medium);
        add(spacer = new JLabel(" "), "span, grow");
        add(spacer = new JLabel(" "), "span, grow");
        add(hard);
        add(spacer = new JLabel(" "), "span, grow");
        add(spacer = new JLabel(" "), "span, grow");
        add(quit);
        add(spacer = new JLabel(" "), "span, grow");
        add(spacer = new JLabel(" "), "span, grow");
        add(spacer = new JLabel(" "), "span, grow");
        add(spacer = new JLabel(" "), "span, grow");
        add(authors);

        mazeWindow = null;  // Until user selects difficultly level, there is no maze window.
    }

    /**
     * Button listener subclass to check for user input.
     */
    private class ButtonListener implements ActionListener {
        /**
         * If a button is pressed, perform a specific action for each button.
         * @param event
         */
        public void actionPerformed(ActionEvent event) {
            if (mazeWindow != null) mazeWindow.dispose();  // Close existing maze window, if it exists.
            String level = "";
            if (event.getSource() == easy) // We want to know which button was clicked!
                level = "easy";
            else if (event.getSource() == medium)
                level = "medium";
            else if (event.getSource() == hard)
                level = "hard";

            // Create new maze game with specified level of difficulty
            mazeWindow = new JFrame("Maze Runner " + level.toUpperCase());
            mazeWindow.getContentPane().add(new MazePanel(level));
            mazeWindow.pack();
            mazeWindow.setVisible(true);
        }
    }

    /**
     * Subclass that checks if the window is being closed and exits gracefully.
     */
    private class CloseListener implements ActionListener {
        /**
         * System exit in a graceful way.
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}