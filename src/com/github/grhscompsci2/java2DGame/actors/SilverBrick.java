package com.github.grhscompsci2.java2DGame.actors;

public class SilverBrick extends Actor {

  private static final String img = "silver_brick.png";
  private int hits;

  public SilverBrick(double x, double y) {
    super(img, x, y, 0, Type.brick);
    hits = 0;
  }

  @Override
  public void hitActor(Actor actor) {
    hits++;
    // TODO Auto-generated method stub
    if (hits > 1) {
      die();
    }
  }

}
