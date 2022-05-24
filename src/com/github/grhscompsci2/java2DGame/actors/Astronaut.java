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

  // Flag for controlling bullet generation
  private boolean fired;

  /**
   * Class constructor that will start the Astronaut off at (0,0), facing right
   */
  public Astronaut() {
    super(rightImg, Utility.gameWidth / 2, Utility.gameHeight / 2, 50, Type.player);
    fired = false;
  }

  /**
   * This method is called each tick to update the sprite of the astronaut and use
   * the keyboard input to change the speed in each direction. It also checks the
   * limits and makes sure the astronaut does not exit the board.
   * 
   * @param deltaTime the number of seconds since the last tick.
   */
  public void act(double deltaTime) {
    // create new changes in x and y
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
    // If we have fired a bullet and released the space bar, set the fired flag to
    // false
    if (fired && !Utility.SPACE) {
      fired = false;
    }
    // If we have pressed the space bar and not set the fired flag yet, shoot
    if (Utility.SPACE && !fired) {
      shoot();
      fired = true;
    }

    // check where our dx and dy values will send us
    double futureX = getX() + dx * deltaTime;
    double futureY = getY() + dy * deltaTime;
    // Get half the width or height of the sprite, whichever is largest
    double max = (Math.max(getWidth(), getHeight()) / 2.0) + 1;
    // If the new position will send us off the screen, set the dx or dy to zero
    if (futureX < max || futureX > Utility.gameWidth - max) {
      dx = 0;
    }
    if (futureY < max || futureY > Utility.gameHeight - max) {
      dy = 0;
    }
    // update the dx and dy officially
    setDX(dx);
    setDY(dy);

    // actually move the astronaut
    super.act(deltaTime);
  }

  /**
   * Creates a new bullet. The bullet will move in the direction the sprite is
   * facing.
   */
  private void shoot() {
    // vX and vY are the directions the bullet will go
    double vX = 0;
    double vY = 0;
    // set the bullet position to the current position
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
    // position and movement direction.
    Utility.addActor(new Bullet(bulX, bulY, vX, vY));
  }

  /**
   * This method is called during collision dection when the actor bounds have hit
   * the board bounds
   */
  @Override
  public void hitEdge() {
    // If we hit the edge of the screen, what do we do?

  }

  /**
   * This method is called during collision detection when the actor bounds have
   * intersected with another actor's bounds
   * 
   * @param actor the collided actor
   */
  @Override
  public void hitActor(Actor actor) {
    // TODO Auto-generated method stub
    // nothing yet...
  }

}
