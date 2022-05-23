package com.github.grhscompsci2.java2DGame.actors;

/**
 * This is an example bullet class that extends the Actor class. It only needs
 * to set the image, position and speed of the actor. The rest is handled in the
 * Board class.
 */
public class Bullet extends Actor {
  public Bullet(float x, float y, float vX, float vY) {
    super("bullet.png", x, y, 100);
    setDX(vX * getSpeed());
    setDY(vY * getSpeed());
  }

  @Override
  public void hitEdge() {
    // TODO Auto-generated method stub
    die();
  }
}
