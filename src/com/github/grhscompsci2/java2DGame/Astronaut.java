package com.github.grhscompsci2.java2DGame;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Astronaut extends Actor {
  public ArrayList<Bullet> bullets = new ArrayList<>();
  private final static String upImg = "images/astronaut_up.png";
  private final static String downImg = "images/astronaut_down.png";
  private final static String leftImg = "images/astronaut_left.png";
  private final static String rightImg = "images/astronaut_right.png";

  private boolean fired = false;

  /**
   * Start the Astronaut off at 0,0. facing right
   */
  public Astronaut() {
    super(rightImg, 0, 0);
  }

  /**
   * Adds on to the Actor keyPressed method. It updates our image to be facing the
   * direction we are going.
   * 
   * @param e the KeyEvent
   */
  public void keyPressed(KeyEvent e) {
    super.keyPressed(e);
    // Get which key was pressed.
    int key = e.getKeyCode();
    if (key == KeyEvent.VK_LEFT) {
      setImage(leftImg);
    }
    if (key == KeyEvent.VK_RIGHT) {
      setImage(rightImg);
    }
    if (key == KeyEvent.VK_DOWN) {
      setImage(downImg);
    }
    if (key == KeyEvent.VK_UP) {
      setImage(upImg);
    }

    // if we pressed space bar, fire a bullet
    if (key == KeyEvent.VK_SPACE && !fired) {
      // Set the fired flag so we know we have fired during this key press
      fired = true;
      shoot();
    }
  }

  /**
   * Adds on to the Actor keyReleased method. It will cycle the firing flag to
   * ensure only one bullet is fired per press.
   * 
   * @param e the KeyEvent
   */
  public void keyReleased(KeyEvent e) {
    super.keyReleased(e);
    int key = e.getKeyCode();
    if (key == KeyEvent.VK_SPACE) {
      fired = false;
    }
  }

  /**
   * Creates a new bullet. The bullet will move in the direction the sprite is
   * facing.
   */
  private void shoot() {
    int vX = 0;
    int vY = 0;

    switch (getFileName()) {
      case upImg:
        vY = -1;
        break;
      case downImg:
        vY = 1;
        break;
      case leftImg:
        vX = -1;
        break;
      case rightImg:
        vX = 1;
        break;
    }
    // Create a new bullet in the bullets ArrayList that has the correct position
    // and movement direction.
    bullets.add(new Bullet(getX(), getY() , vX, vY));
  }

  public ArrayList<Bullet> getBullets() {
    return bullets;
  }
}
