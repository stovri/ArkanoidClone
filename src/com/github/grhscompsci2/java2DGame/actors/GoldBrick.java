package com.github.grhscompsci2.java2DGame.actors;

public class GoldBrick extends Brick {

  public GoldBrick(double x, double y) {
    super("gold_brick.png", x, y, 0);
  }

  @Override
  public void hitActor(Actor actor) {
    // I AM INVINCIBLE!!!
  }
}
