package com.github.grhscompsci2.java2DGame.actors;

import com.github.grhscompsci2.java2DGame.Utility;

/**
 * This is a sample actor that will use the keyboard input to move. It uses four
 * images to perform simple animation. It also fires bullets by adding to a
 * bullet ArrayList.
 */
public class Paddle extends Actor {
  private final static String img = "paddle_long.png";
  private Ball ball;
  private boolean ballLaunched;

  /**
   * Class constructor that will start the paddle off at the bottom
   */
  public Paddle() {
    super(img, Utility.gameWidth / 2, Utility.gameHeight - 16, 150, Type.player);
    createBall();
  }

  private void createBall() {
    ballLaunched = false;
    ball = new Ball(getX(), getY());
    Utility.addActor(ball);
  }

  private void launchBall() {
    ball.setDY(-1 * ball.getSpeed());
    ball.setDX(0);
    ballLaunched = true;
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
    if (Utility.LEFT_ARROW) {
      dx = -1 * getSpeed();
    }
    if (Utility.RIGHT_ARROW) {
      dx = getSpeed();
    }
    if (Utility.SPACE && !ballLaunched) {
      launchBall();
    }
    // check where our dx and dy values will send us
    double futureX = getX() + dx * deltaTime;
    // Get half the width or height of the sprite, whichever is largest
    double max = (getWidth() / 2.0) + getSpeed() * deltaTime / 2 + 4;
    // If the new position will send us off the screen, set the dx or dy to zero
    if (futureX < max || futureX > Utility.gameWidth - max) {
      dx = 0;
    }
    // update the dx and dy officially
    setDX(dx);
    if (!ballLaunched) {
      ball.setDX(dx);
    }
    // actually move
    super.act(deltaTime);
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
