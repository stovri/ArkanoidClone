package com.github.grhscompsci2.java2DGame.actors;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.grhscompsci2.java2DGame.Utility;

/**
 * This is the base class for anything that needs to move/act on the screen. You
 * should write classes that extend the actor class to make your game work. The
 * player class, enemy classes, bullet classes, and obstacle classes should all
 * extend the Actor class.
 */
public abstract class Actor {
  private double dx;
  private double dy;
  private double x;
  private double y;

  private BufferedImage sprite;
  private String fileName;
  private double speed;

  private boolean isDead;

  /**
   * Constructor that will set the image to the fileName, and set the position to
   * (0,0). This correspnds to the center of the sprite.
   */
  public Actor() {
    this("no_sprite.png", 0, 0);
  }

  /**
   * Constructor that will set the image to the fileName and the position to the
   * provided coordinates.
   * 
   * @param fileName the relative location of the file ("images/filename.png")
   * @param x        the x coordinate of the center of the sprite
   * @param y        the y coordinate of the center of the sprite
   */
  public Actor(String fileName, int x, int y) {
    this(fileName, x, y, 50);
  }

  /**
   * Constructor that will set the image to the fileName and the position to the
   * provided coordinates, and set the speed of the actor.
   * 
   * @param fileName the relative location of the file ("images/filename.png")
   * @param bulX        the x coordinate of the sprite
   * @param bulY        the y coordinate of the sprite
   * @param d    the speed of the sprite, in scaled pixels
   */
  public Actor(String fileName, double bulX, double bulY, double d) {
    this.fileName = fileName;
    this.x = bulX;
    this.y = bulY;
    this.speed = d;
    this.dx = 0;
    this.dy = 0;
    this.isDead=false;
    loadImage();
  }

  /**
   * Using the fileName attribute, load the image into the sprite attribute
   */
  private void loadImage() {
    // Load the image
    try {
      this.sprite = ImageIO.read(Utility.class.getResource(Utility.IMG_FOLDER+fileName));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Update the position based on the change in x and y attributes
   * 
   * @param deltaTime
   */ 
  public void act(double deltaTime) {
    x += dx * deltaTime;
    y += dy * deltaTime;
  }

  /**
   * Draws the sprite centered at the x, y location. Adapts to scale of the
   * JFrame.
   * 
   * @param g Graphics2D object to draw the image
   * @param i the JPanel where the sprite will be drawn
   */
  public void draw(Graphics2D g, ImageObserver i) {
    double offsetX = x - sprite.getWidth() / 2;
    double offsetY = y - sprite.getHeight() / 2;
    g.drawImage(sprite, Utility.scale(offsetX), Utility.scale(offsetY), Utility.scale(sprite.getWidth()),
        Utility.scale(sprite.getHeight()), i);
  }

  /**
   * Returns the x attribute
   * 
   * @return the x attribute
   */
  public double getX() {
    return x;
  }

  /**
   * Returns the y attribute
   * 
   * @return the y attribute
   */
  public double getY() {
    return y;
  }

  /**
   * Returns the width of the sprite
   * 
   * @return the width attribute.
   */
  public int getWidth() {
    return sprite.getWidth();
  }

  /**
   * Returns the height of the sprite
   * 
   * @return the height attribute
   */
  public int getHeight() {
    return sprite.getHeight();
  }

  /**
   * Returns the sprite
   * 
   * @return the sprite attribute
   */
  public BufferedImage getImage() {
    return sprite;
  }

  /**
   * Returns the file name
   * 
   * @return the fileName attribute
   */
  public String getFileName() {
    return fileName;
  }

  /**
   * Update the image for the actor sprite. Useful for animations.
   * 
   * @param fileName the relative location of the file ("images/filename.png")
   */
  public void setImage(String fileName) {
    this.fileName = fileName;
    loadImage();
  }

  /**
   * Returns the dx
   * 
   * @return the dx attribute
   */
  public double getDX() {
    return dx;
  }

  /**
   * Updates the value for dx
   * 
   * @param dx2 the new value for dx
   */
  public void setDX(double dx2) {
    this.dx = dx2;
  }

  /**
   * Returns the dy
   * 
   * @return the attribute dy
   */
  public double getDY() {
    return dy;
  }

  /**
   * Updates the value of dy
   * 
   * @param dy2 the new value for dy
   */
  public void setDY(double dy2) {
    this.dy = dy2;
  }

  /**
   * Returns the speed
   * 
   * @return the attribute speed
   */
  public double getSpeed() {
    return speed;
  }

  /**
   * Updates the value of speed
   * 
   * @param speed the new value for speed
   */
  public void setSpeed(int speed) {
    this.speed = speed;
  }

  /**
   * Returns the bounds of the sprite. Used for collision dectection.
   * 
   * @return a rectangle in the position and size of the sprite
   */
  public Rectangle getBounds() {
    return new Rectangle((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
  }

  public void die(){
    isDead=true;
  }

  public abstract void hitEdge();

  public boolean isDead() {
    return isDead;
  }
}
