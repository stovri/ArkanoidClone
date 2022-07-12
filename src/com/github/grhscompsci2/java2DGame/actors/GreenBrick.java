package com.github.grhscompsci2.java2DGame.actors;

public class GreenBrick extends Actor{

  private static final String img="green_brick.png";
  public GreenBrick(double x, double y) {
    super(img, x, y, 0, Type.brick);
  }

  @Override
  public void hitActor(Actor actor) {
    // TODO Auto-generated method stub
    die();
  }
  
}
