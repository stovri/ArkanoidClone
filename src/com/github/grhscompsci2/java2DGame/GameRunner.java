package com.github.grhscompsci2.java2DGame;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This is the main class that will run the game. You should not have to edit
 * anything here. As a matter of fact, editing this file should be thought about
 * VERY HARD before you do it, with the exception of "YOUR GAME NAME HERE". That
 * you should change to match the name of your game.
 */
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
    setTitle("FROGGER");
    // Exit the game when you close the window
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // put the window in the center of the screen
    setLocationRelativeTo(null);
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      JFrame ex = new GameRunner();
      ex.setVisible(true);
    });
  }
}
