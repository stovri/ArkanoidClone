package com.github.grhscompsci2.java2DGame.actors;

import com.github.grhscompsci2.java2DGame.Utility;

public class Brick extends Actor {

  private int points = 0;

  public Brick(String fileName, double x, double y,int points) {
    super(fileName, x, y, 0, Type.brick);
    this.points = points;
  }

  @Override
  public void hitActor(Actor actor) {
    // TODO Auto-generated method stub
    Utility.updateScore(points);
    die();
  }

}
