package com.github.grhscompsci2.java2DGame.actors;

import java.util.ArrayList;

import com.github.grhscompsci2.java2DGame.Utility;

/**
 * This is a sample actor that will use the keyboard input to move. It uses four
 * images to perform simple animation. It also fires bullets by adding to a
 * bullet ArrayList.
 */
public class Astronaut extends Actor {
  private final static String upImg = "astronaut_up.png";
  private final static String downImg = "astronaut_down.png";
  private final static String leftImg = "astronaut_left.png";
  private final static String rightImg = "astronaut_right.png";

  private boolean fired;

  /**
   * Class constructor that will start the Astronaut off at (0,0), facing right
   */
  public Astronaut() {
    super(rightImg, Utility.gameWidth / 2, Utility.gameHeight / 2, 50);
    fired = false;
  }

  public void act(double deltaTime) {
    double dx = 0;
    double dy = 0;
    if (Utility.UP_ARROW) {
      setImage(upImg);
      dy = -1 * getSpeed();
    }
    if (Utility.DOWN_ARROW) {
      setImage(downImg);
      dy = getSpeed();
    }
    if (Utility.LEFT_ARROW) {
      setImage(leftImg);
      dx = -1 * getSpeed();
    }
    if (Utility.RIGHT_ARROW) {
      setImage(rightImg);
      dx = getSpeed();
    }
    if (!Utility.SPACE) {
      fired = false;
    }
    if (Utility.SPACE && !fired) {
      shoot();
      fired = true;
    }
    setDX(dx);
    setDY(dy);

    super.act(deltaTime);
  }

  /**
   * Creates a new bullet. The bullet will move in the direction the sprite is
   * facing.
   */
  private void shoot() {
    float vX = 0;
    float vY = 0;

    // based on the fileName, which direction are we facing?
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
    // Create a new bullet in the castAndCrew ArrayList that has the correct position
    // and movement direction.
    Utility.addActor(new Bullet(getX(), getY(), vX, vY));
  }

  @Override
  public void hitEdge() {
    // TODO Auto-generated method stub

  }

}
