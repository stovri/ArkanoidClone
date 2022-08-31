package com.github.grhscompsci2.java2DGame.actors;

import java.awt.Rectangle;

import com.github.grhscompsci2.java2DGame.Utility;

public class Ball extends Actor {
  private static final String img = "ball.png";

  public Ball(double x, double y) {
    super(img, x, y - 7, 125, Type.bullet);
  }

  @Override
  public void hitActor(Actor actor) {
    switch (actor.getType()) {
      case player:
        bounce(actor);
        break;
      case brick:
        reflect(actor);
        break;
      case obstacle:
        reflect(actor);
        break;
      default:
        break;
    }
  }

  private void reflect(Actor actor) {
    Rectangle overlap = actor.getBounds().intersection(getBounds());
    if (overlap.getWidth() >= overlap.getHeight() || getDX() == 0) {
      setDY(-1 * getDY());
    } else {
      setDX(-1 * getDX());
    }
  }

  private void bounce(Actor actor) {
    // set up the angle as a percentage of the distance of the strike from the
    // center of the paddle, and force it to be between 15 and 75 degrees
    double angle = 60 - Math.abs((actor.getX() - getX())) / (actor.getWidth() / 2.0) * 60 + 15;
    if (actor.getX() > getX() + getWidth() / 2) {
      setDX(-1 * getSpeed() * Math.cos(Math.toRadians(angle)));
      setDY(-1 * getSpeed() * Math.sin(Math.toRadians(angle)));
    } else if (actor.getX() < getX() - getWidth() / 2) {
      setDX(getSpeed() * Math.cos(Math.toRadians(angle)));
      setDY(-1 * getSpeed() * Math.sin(Math.toRadians(angle)));
    } else {
      setDX(0);
      setDY(-1 * getSpeed());
    }
  }

  public void act(double deltaTime) {
    double dx = getDX();
    double dy = getDY();
    // check where our dx and dy values will send us
    double futureX = getX() + dx * deltaTime;
    double futureY = getY() + dy * deltaTime;
    // Get half the width or height of the sprite, whichever is largest
    double max = (getWidth() / 2.0) + getSpeed() * deltaTime / 2 + 4;
    // If the new position will send us off the screen, set the dx or dy to zero
    if (futureX < max || futureX > Utility.gameWidth - max) {
      dx *= -1;
    }
    double min = getHeight() / 2.0 + 20;
    if (futureY < min) {
      dy *= -1;
    }
    if (futureY >= Utility.gameHeight) {
      die();
    }
    // update the dx and dy officially
    setDX(dx);
    setDY(dy);
    super.act(deltaTime);
  }
}
