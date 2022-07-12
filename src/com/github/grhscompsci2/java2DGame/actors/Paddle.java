package com.github.grhscompsci2.java2DGame.actors;

import com.github.grhscompsci2.java2DGame.Utility;

/**
 * This is a sample actor that will use the keyboard input to move. It uses four
 * images to perform simple animation. It also fires bullets by adding to a
 * bullet ArrayList.
 */
public class Paddle extends Actor {
  private final static String img = "paddle.png";
  public Shadow shadow;

  /**
   * Class constructor that will start the Astronaut off at (0,0), facing right
   */
  public Paddle() {
    super(img, Utility.gameWidth / 2, Utility.gameHeight / 2, 50, Type.player);
    shadow=new Shadow("paddle_shadow.png",getX(),getY(),getWidth(),getHeight());
    Utility.addActor(shadow);
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

    // check where our dx and dy values will send us
    double futureX = getX() + dx * deltaTime;
    // Get half the width or height of the sprite, whichever is largest
    double max = (getHeight() / 2.0) + 1;
    // If the new position will send us off the screen, set the dx or dy to zero
    if (futureX < max || futureX > Utility.gameWidth - max) {
      dx = 0;
    }

    // update the dx and dy officially
    setDX(dx);
    shadow.setDX(dx+getWidth()/3);
    // actually move the astronaut
    super.act(deltaTime);
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
