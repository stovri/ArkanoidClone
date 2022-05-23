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
import java.util.Iterator;
import java.awt.*;

/**
 * This class controls the JPanel where the game logic and rendering happen. You
 * will need to edit the attributes to add your enemies and player classes so
 * they can draw and act here.
 */
public class Board extends JPanel implements Runnable {
  private final String BACKGROUND_FILE_NAME = "images/background.png";
  private BufferedImage background;
  private int frameCount = 0;
  private int fps = 0;
  public static boolean running = false, paused = false;
  final ArrayList<Entity> entities = new ArrayList<>();

  // Initialize all of your actors here: players, enemies, obstacles, etc.
  private Astronaut actor;

  /**
   * Initialize the board
   */
  public Board() {
    // add the custom key adapter to the panel
    addKeyListener(new TAdapter());
    // load the background image
    loadBackground();
    // set the size of the panel to the size of the background
    setPreferredSize(getPreferredSize());
    // Update the scale based on the size of the JPanel
    Utility.updateScale(new Dimension(background.getWidth(), background.getHeight()), getSize());
    // Allow us to focus on this JPanel
    setFocusable(true);
    // Initialize all actors below here
    initBoard();
  }

  /**
   * This method will initialize the key listener, load the background image, set
   * the window size to the size of the image, and initialize an actor. When you
   * are modifying this for your game, you should initialize all of your actors
   * that need to be used in the game.
   */
  private void initBoard() {
    actor = new Astronaut();
  }

  /**
   * Returns the preferred size of the background. Used to set the starting size
   * of the JPanel window.
   */
  @Override
  public Dimension getPreferredSize() {
    // if there is no image, give a default size
    if (background == null) {
      return new Dimension(400, 300);
    }
    // give a size based on the background image
    return new Dimension(background.getWidth(), background.getHeight());
  }

  /**
   * This will step through all the bullets and have them act. If the bullet is
   * out of bounds, it will remove them from the ArrayList.
   * 
   * @param deltaTime
   */
  public void manageBullets(float deltaTime) {
    ArrayList<Bullet> bullets = actor.getBullets();
    for (Iterator<Bullet> i = bullets.iterator(); i.hasNext();) {
      Bullet bill = i.next();
      bill.act(deltaTime);
      // is Bill out of bounds?
      if (bill.getX() < 0 || bill.getX() > Utility.gameWidth
          || bill.getY() < 0 || bill.getY() > Utility.gameHeight) {
        i.remove();
      }
    }

  }

  /**
   * This method will assign the BACKGROUND_FILE_NAME as the background of the
   * JPanel. The background.png file will determine the resolution of your screen.
   * All of your other sprites should match the resolution of the background.
   */
  private void loadBackground() {
    // Load the image
    try {
      this.background = ImageIO.read(Board.class.getResource(BACKGROUND_FILE_NAME));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method will draw everything in JPanel. It will update the scale so when
   * the JFrame is resized, the image will resize to maintain the aspect ratio.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    // Graphics 2D offers greater control than Graphics, so use it instead.
    Graphics2D g2d = (Graphics2D) g;
    // Update the scale based on the size of the JPanel
    Utility.updateScale(new Dimension(background.getWidth(), background.getHeight()), getSize());

    // Always draw this first so it will be on the bottom
    g2d.drawImage(background, 0, 0, Utility.scale(background.getWidth()), Utility.scale(background.getHeight()), this);

    // call other drawing stuff here
    actor.draw(g2d, this);
    // get the array list of bullets
    ArrayList<Bullet> bullets = actor.getBullets();
    // draw them all
    for (Bullet bill : bullets) {
      bill.draw(g2d, this);
    }

    g2d.setColor(Color.BLACK);
    g2d.drawString("FPS: " + fps, 5, 10);

    frameCount++;
  }

  // Only run this in another Thread!
  public void gameLoop() {
    // This value would probably be stored elsewhere.
    final double GAME_HERTZ = 30.0;
    // Calculate how many ns each frame should take for our target game hertz.
    final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
    // At the very most we will update the game this many times before a new render.
    // If you're worried about visual hitches more than perfect timing, set this to
    // 1.
    final int MAX_UPDATES_BEFORE_RENDER = 5;
    // We will need the last update time.
    double lastUpdateTime = System.nanoTime();
    // Store the last time we rendered.
    double lastRenderTime = System.nanoTime();

    // If we are able to get as high as this FPS, don't render again.
    final double TARGET_FPS = 60;
    final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

    // Simple way of finding FPS.
    int lastSecondTime = (int) (lastUpdateTime / 1000000000);

    while (running) {
      double now = System.nanoTime();
      int updateCount = 0;

      if (!paused) {
        // Do as many game updates as we need to, potentially playing catchup.
        while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
          updateGame();
          lastUpdateTime += TIME_BETWEEN_UPDATES;
          updateCount++;
        }

        // If for some reason an update takes forever, we don't want to do an insane
        // number of catchups.
        // If you were doing some sort of game that needed to keep EXACT time, you would
        // get rid of this.
        if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
          lastUpdateTime = now - TIME_BETWEEN_UPDATES;
        }

        drawGame();
        lastRenderTime = now;

        // Update the frames we got.
        int thisSecond = (int) (lastUpdateTime / 1000000000);
        if (thisSecond > lastSecondTime) {
          System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
          fps = frameCount;
          frameCount = 0;
          lastSecondTime = thisSecond;
        }

        // Yield until it has been at least the target time between renders. This saves
        // the CPU from hogging.
        while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
          // allow the threading system to play threads that are waiting to run.
          Thread.yield();

          // This stops the app from consuming all your CPU. It makes this slightly less
          // accurate, but is worth it.
          // You can remove this line and it will still work (better), your CPU just
          // climbs on certain OSes.
          // FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a
          // look at different peoples' solutions to this.
          // On my OS it does not unpuase the game if i take this away
          try {
            Thread.sleep(1);
          } catch (Exception e) {
          }

          now = System.nanoTime();
        }
      }
    }
  }

  /**
   * This method is called once per frame. This will allow us to advance the game
   * logic every frame so the actors move and react to input.
   * 
   * @param deltaTime
   */
  private void act(float deltaTime) {
    // Check for collisions between actors. Do it before they act so you can handle
    // death and other cases appropriately
    checkCollisions();
    // Have all of your actor attributes act here.
    actor.act(deltaTime);
    // Manage your bullets
    manageBullets(deltaTime);
  }

  /**
   * This method will start the thread for the animation.
   */
  @Override
  public void addNotify() {
    super.addNotify();

    animator = new Thread(this);
    animator.start();
  }

  /**
   * This method is called once by the animator thread. It regulates the call to
   * Thread.sleep so that each cycle is very close to the same length, unless
   * something goes horribly wrong.
   */
  @Override
  public void run() {
    long beforeTime;
    long timeDiff;
    long sleep;
    float deltaTime = 0;
    while (true) {
      // Sample the current time before act() and repaint() are called
      beforeTime = System.nanoTime();

      // update all actors
      act(deltaTime);
      // force a repaint
      repaint();

      // how long did that take?
      timeDiff = System.nanoTime() - beforeTime;
      // we want to wait a consistent amount of time, so subtract timeDiff from DELAY
      sleep = (DELAY - timeDiff) / 1000000;

      // If we went too long, only wait 2 milliseconds for garbage collection.
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
      deltaTime = (System.nanoTime() - beforeTime) / 1000000000f;
    }
  }

  /**
   * This method will check each actor to see if the bounding box overlaps the
   * bounding box of any other actor. Not the most efficient, but will work for
   * small games.
   */
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

  /**
   * This class is used to handle the keyEvents. If you want an actor to respond
   * to the keyboard, they need to be called in this class.
   */
  private class TAdapter extends KeyAdapter {

    /**
     * When a key is released, this method is called and passed the ID of the key
     * that was released.
     * 
     * @param e the KeyEvent object generated by the KeyListener
     */
    @Override
    public void keyReleased(KeyEvent e) {
      // add all objects that care about keys being released here
      actor.keyReleased(e);
    }

    /**
     * When a key is pressed, this method is called and passed the ID of the key
     * that was pressed.
     * 
     * @param e the KeyEvent object generated by the KeyListener
     */
    @Override
    public void keyPressed(KeyEvent e) {
      // add all objects that care about keys being pressed here
      actor.keyPressed(e);
    }
  }
}