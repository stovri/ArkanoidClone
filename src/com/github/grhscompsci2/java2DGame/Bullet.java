package com.github.grhscompsci2.java2DGame;

/**
 * This is an example bullet class that extends the Actor class. It only needs
 * to set the image, position and speed of the actor. The rest is handled in the
 * Board class.
 */
public class Bullet extends Actor {
  public Bullet(int x, int y, int vX, int vY) {
    super("images/bullet.png", x, y, 10);
    setDX(vX * getSpeed());
    setDY(vY * getSpeed());
  }
}
