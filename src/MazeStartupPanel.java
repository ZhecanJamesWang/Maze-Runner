//********************************************************************
//  MazeStartupPanel.java       CS230 Staff
//
//  Select level of difficulty for maze game.
//********************************************************************

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MazeStartupPanel extends JPanel
{
  private JButton easy, medium, hard, caption;
  private JFrame mazeWindow;
  private final Color textColor = new Color(0xE76067);
  public MazeStartupPanel()
  {
    setBackground(Color.darkGray);
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setPreferredSize (new Dimension(600, 400));
    
    caption = new JButton("The Maze Runner");
    caption.setForeground(textColor);
    easy = new JButton("Easy");
    easy.setForeground(textColor);
    medium = new JButton("Medium");
    medium.setForeground(textColor);
    hard = new JButton("Hard");
    hard.setForeground(textColor);
    
    add(caption);
    add(easy);
    add(medium);
    add(hard);
    
    mazeWindow = null;  // Until user selects difficultly level, there is no maze window.
  }
  
}