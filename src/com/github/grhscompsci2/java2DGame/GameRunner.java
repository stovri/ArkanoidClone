package com.github.grhscompsci2.java2DGame;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * This is the main class that will run the game. You should not have to edit
 * anything here. As a matter of fact, editing this file should be thought about
 * VERY HARD before you do it, with the exception of "YOUR GAME NAME HERE". That
 * you should change to match the name of your game.
 */
public class GameRunner {
  public GameRunner() {
    initUI();
  }

  final Board board = new Board();

  private void initUI() {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setGamePanelKeyBindings(board);
    // Add the panel to the frame
    JPanel wrapperPanel = new JPanel(new SingleComponentAspectRatioKeeperLayout());
    wrapperPanel.add(new Board());
    frame.add(wrapperPanel);
    // resizes the game window to the preferred size of the Board
    frame.pack();
    // set the title
    frame.setTitle("YOUR GAME NAME HERE!");
    // Exit the game when you close the window
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // put the window in the center of the screen
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    // start the game loop which will repaint the screen
    runGameLoop();
  }

  // Starts a new thread and runs the game loop in it.

  public void runGameLoop() {
    Thread loop = new Thread(new Runnable() {
      @Override
      public void run() {
        // board.running = true;
        // board.gameLoop();
      }
    });
    loop.start();
  }

  private void setGamePanelKeyBindings(Board board) {
    board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "D pressed");
    board.getActionMap().put("D pressed", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        //entity.RIGHT = true;
      }
    });

    board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "A pressed");
    board.getActionMap().put("A pressed", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        //entity.LEFT = true;
      }
    });

    board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "W pressed");
    board.getActionMap().put("W pressed", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        //entity.UP = true;
      }
    });

    board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "S pressed");
    board.getActionMap().put("S pressed", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        //entity.DOWN = true;
      }
    });
    board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released D"), "D released");
    board.getActionMap().put("D released", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        //entity.RIGHT = false;
      }
    });

    board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released A"), "A released");
    board.getActionMap().put("A released", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        //entity.LEFT = false;
      }
    });

    board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released W"), "W released");
    board.getActionMap().put("W released", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        ////entity.UP = false;
      }
    });

    board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released S"), "S released");
    board.getActionMap().put("S released", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        //entity.DOWN = false;
      }
    });
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new GameRunner();
      }
    });
  }
}
