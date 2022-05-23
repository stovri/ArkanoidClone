package com.github.grhscompsci2.java2DGame;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
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

  private Board board;

  public GameRunner() {
    initUI();
  }

  private void initUI() {
    board = new Board();
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setGamePanelKeyBindings();
    // Add the panel to the frame
    JPanel wrapperPanel = new JPanel(new SingleComponentAspectRatioKeeperLayout());
    wrapperPanel.add(board);
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
        board.running = true;
        board.gameLoop();
      }
    });
    loop.start();
  }

  private void setGamePanelKeyBindings() {
    InputMap inputMap = board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    inputMap.put(KeyStroke.getKeyStroke("W"), "up arrow");
    inputMap.put(KeyStroke.getKeyStroke("A"), "left arrow");
    inputMap.put(KeyStroke.getKeyStroke("S"), "down arrow");
    inputMap.put(KeyStroke.getKeyStroke("D"), "right arrow");
    inputMap.put(KeyStroke.getKeyStroke("UP"), "up arrow");
    inputMap.put(KeyStroke.getKeyStroke("LEFT"), "left arrow");
    inputMap.put(KeyStroke.getKeyStroke("DOWN"), "down arrow");
    inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "right arrow");

    inputMap.put(KeyStroke.getKeyStroke("SPACE"), "space");

    inputMap.put(KeyStroke.getKeyStroke("released W"), "up arrow released");
    inputMap.put(KeyStroke.getKeyStroke("released A"), "left arrow released");
    inputMap.put(KeyStroke.getKeyStroke("released S"), "down arrow released");
    inputMap.put(KeyStroke.getKeyStroke("released D"), "right arrow released");
    inputMap.put(KeyStroke.getKeyStroke("released UP"), "up arrow released");
    inputMap.put(KeyStroke.getKeyStroke("released LEFT"), "left arrow released");
    inputMap.put(KeyStroke.getKeyStroke("released DOWN"), "down arrow released");
    inputMap.put(KeyStroke.getKeyStroke("released RIGHT"), "right arrow released");

    inputMap.put(KeyStroke.getKeyStroke("released SPACE"), "space released");

    ActionMap actionMap = board.getActionMap();

    actionMap.put("up arrow", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        Utility.UP_ARROW = true;
      }
    });
    actionMap.put("left arrow", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        Utility.LEFT_ARROW = true;
      }
    });
    actionMap.put("down arrow", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        Utility.DOWN_ARROW = true;
      }
    });
    actionMap.put("right arrow", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        Utility.RIGHT_ARROW = true;
      }
    });
    actionMap.put("space", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        Utility.SPACE = true;
      }
    });

    actionMap.put("up arrow released", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        Utility.UP_ARROW = false;
      }
    });
    actionMap.put("left arrow released", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        Utility.LEFT_ARROW = false;
      }
    });
    actionMap.put("down arrow released", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        Utility.DOWN_ARROW = false;
      }
    });
    actionMap.put("right arrow released", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        Utility.RIGHT_ARROW = false;
      }
    });
    actionMap.put("space released", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        Utility.SPACE = false;
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
