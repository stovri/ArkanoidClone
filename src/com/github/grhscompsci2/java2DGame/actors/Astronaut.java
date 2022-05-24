package com.github.grhscompsci2.java2DGame.actors;

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

  //Flag for controlling bullet generation
  private boolean fired;

  /**
   * Class constructor that will start the Astronaut off at (0,0), facing right
   */
  public Astronaut() {
    super(rightImg, Utility.gameWidth / 2, Utility.gameHeight / 2, 50);
    fired = false;
  }

  
  /** 
   * @param deltaTime
   */
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
    double futureX = getX() + dx * deltaTime;
    double futureY = getY() + dy * deltaTime;
    double max = (Math.max(getWidth(), getHeight()) / 2.0) + 1;
    if (futureX < max || futureX > Utility.gameWidth - max) {
      dx = 0;
    }
    if (futureY < max || futureY > Utility.gameHeight - max) {
      dy = 0;
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
    double vX = 0;
    double vY = 0;
    double bulX = getX();
    double bulY = getY();
    // based on the fileName, which direction are we facing?
    switch (getFileName()) {
      case upImg:
        // set the y velocity, and move the position to the image of the gun barrel
        vY = -1;
        bulX += 10;
        bulY -= getHeight() / 2;
        break;
      case downImg:
        // set the y velocity, and move the position to the image of the gun barrel
        vY = 1;
        bulX -= 10;
        bulY += getHeight() / 2;
        break;
      case leftImg:
        // set the x velocity, and move the position to the image of the gun barrel
        vX = -1;
        bulY -= 10;
        bulX -= getWidth() / 2;
        break;
      case rightImg:
        // set the x velocity, and move the position to the image of the gun barrel
        vX = 1;
        bulY += 10;
        bulX += getWidth() / 2;
        break;
    }
    // Create a new bullet in the castAndCrew ArrayList that has the correct
    // position
    // and movement direction.
    Utility.addActor(new Bullet(bulX, bulY, vX, vY));
  }

  @Override
  public void hitEdge() {
    // TODO Auto-generated method stub

  }

}
