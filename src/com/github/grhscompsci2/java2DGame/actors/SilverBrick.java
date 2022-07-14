package com.github.grhscompsci2.java2DGame.actors;

public class SilverBrick extends Brick {
  private int hp;

  public SilverBrick(double x, double y, int stage) {
    super("silver_brick.png", x, y, 50 * stage);
    hp = 2 + stage / 8;
  }

  public void hitActor(Actor actor) {
    hp--;
    // TODO Auto-generated method stub
    if (hp == 0) {
      super.hitActor(actor);
    }
  }

}
