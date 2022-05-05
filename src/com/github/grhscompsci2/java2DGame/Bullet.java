package com.github.grhscompsci2.java2DGame;

public class Bullet extends Actor {
  public Bullet(int x, int y, int vX, int vY) {
    super("images/bullet.png", x, y,10);
    setDX(vX*getSpeed());
    setDY(vY*getSpeed());
  }
}
