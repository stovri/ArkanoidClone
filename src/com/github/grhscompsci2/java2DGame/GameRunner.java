package com.github.grhscompsci2.java2DGame;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameRunner extends JFrame {
  public GameRunner() {
    initUI();
  }

  private void initUI() {
    // Add the panel to the frame
    JPanel wrapperPanel = new JPanel(new SingleComponentAspectRatioKeeperLayout());
    wrapperPanel.add(new Board());
    add(wrapperPanel);
    // resizes the game window to the preferred size of the Board
    pack();
    // set the title
    setTitle("YOUR GAME NAME HERE!");
    // Exit the game when you close the window
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // put the window in the center of the screen
    setLocationRelativeTo(null);
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      // TODO Auto-generated method stub
      JFrame ex = new GameRunner();
      ex.setVisible(true);
    });
  }
}
