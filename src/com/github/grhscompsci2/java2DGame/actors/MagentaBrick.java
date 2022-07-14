package com.github.grhscompsci2.java2DGame.actors;

public class MagentaBrick extends Actor {

  private static final String img = "magenta_brick.png";

  public MagentaBrick(double x, double y) {
    super(img, x, y, 0, Type.brick);
  }

  @Override
  public void hitActor(Actor actor) {
    // TODO Auto-generated method stub
    die();
  }

}
