package com.github.grhscompsci2.java2DGame;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.Graphics;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.*;

public class Board extends JPanel implements Runnable {
  private final int DELAY = 25;
  private final String BACKGROUND_FILE_NAME = "images/background.png";
  private BufferedImage background;
  private Thread animator;
  private Astronaut actor;

  /**
   * Initialize the board
   */
  public Board() {
    initBoard();
  }

  /**
   * This method will initialize the key listener, load the background image, set
   * the window size to the size of the image, and initialize an actor
   */
  private void initBoard() {
    // add the custom key adapter to the panel
    addKeyListener(new TAdapter());
    // load the background image
    loadBackground();
    // set the size of the panel to the size of the background
    setPreferredSize(getPreferredSize());
    setFocusable(true);
    actor = new Astronaut();
  }

  public void manageBullets(){
    ArrayList<Bullet> bullets=actor.getBullets();
    for (int i = 0; i < bullets.size(); i++) {
      Bullet bill = bullets.get(i);
      bill.act();
      //is Bill out of bounds?
      if (bill.getX() < 0 || bill.getX() > Utility.gameWidth
          || bill.getY() < 0 || bill.getY() > Utility.gameHeight) {
        bullets.remove(i);
      }
    }

  }

  private void loadBackground() {
    // Load the image
    try {
      this.background = ImageIO.read(Board.class.getResource(BACKGROUND_FILE_NAME));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public Dimension getPreferredSize() {
    // if there is no image, give a default size
    if (background == null) {
      return new Dimension(400, 300);
    }
    // give a size based on the background image
    return new Dimension(background.getWidth(), background.getHeight());
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    Utility.updateScale(new Dimension(background.getWidth(), background.getHeight()), getSize());

    // Always draw this first so it will be on the bottom
    g2d.drawImage(background, 0, 0, Utility.scale(background.getWidth()), Utility.scale(background.getHeight()), this);

    // call other drawing stuff here
    actor.draw(g2d, this);
    //get the array list of bullets
    ArrayList<Bullet> bullets=actor.getBullets();
    //draw them all
    for(Bullet bill:bullets){
      bill.draw(g2d, this);
    }

    // This method will ensure that the display is up to date
    Toolkit.getDefaultToolkit().sync();
  }

  private void act() {
    actor.act();
    manageBullets();
    checkCollisions();
  }

  // This will start the thread for animation
  @Override
  public void addNotify() {
    super.addNotify();

    animator = new Thread(this);
    animator.start();
  }

  @Override
  public void run() {
    long beforeTime;
    long timeDiff;
    long sleep;
    while (true) {
      // Sample the current time before act() and repaint() are called
      beforeTime = System.currentTimeMillis();

      // update all actors
      act();
      // force a repaint
      repaint();

      // how long did that take?
      timeDiff = System.currentTimeMillis() - beforeTime;
      // we want to wait a consistent amount of time, so subtract timeDiff from DELAY
      sleep = DELAY - timeDiff;

      // If we went too long, only wait 2 milliseconds
      if (sleep < 0) {
        sleep = 2;
      }

      // go to sleep
      try {
        Thread.sleep(sleep);
      } catch (InterruptedException e) {
        // Show a message if something goes wrong
        String msg = String.format("Thread interrupted: %s", e.getMessage());
        JOptionPane.showMessageDialog(this, msg, "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  public void checkCollisions() {
    // check player against all other objects
    /*
     * Use this code as a sample. You will need an ArrayList of things to run into
     * Rectangle r3 = actor.getBounds();
     * for (Alien alien : aliens) {
     * 
     * Rectangle r2 = alien.getBounds();
     * 
     * if (r3.intersects(r2)) {
     * 
     * spaceship.setVisible(false);
     * alien.setVisible(false);
     * ingame = false;
     * }
     * }
     * }
     */

  }

  private class TAdapter extends KeyAdapter {

    @Override
    public void keyReleased(KeyEvent e) {
      // add all objects that care about keys being released here
      actor.keyReleased(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
      // add all objects that care about keys being pressed here
      actor.keyPressed(e);
    }
  }
}