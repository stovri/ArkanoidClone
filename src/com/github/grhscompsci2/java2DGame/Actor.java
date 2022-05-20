package com.github.grhscompsci2.java2DGame;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This is the base class for anything that needs to move/act on the screen. You
 * should write classes that extend the actor class to make your game work. The
 * player class, enemy classes, bullet classes, and obstacle classes should all
 * extend the Actor class.
 */
public class Actor {
  private int dx;
  private int dy;
  private int x;
  private int y;

  private BufferedImage sprite;
  private String fileName;
  private int speed;

  /**
   * Constructor that will set the image to the fileName, and set the position to
   * (0,0). This correspnds to the center of the sprite.
   */
  public Actor() {
    this("images/no_sprite.png", 0, 0);
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
    this(fileName, x, y, 2);
    loadImage();
  }

  /**
   * Constructor that will set the image to the fileName and the position to the
   * provided coordinates, and set the speed of the actor.
   * 
   * @param fileName the relative location of the file ("images/filename.png")
   * @param x        the x coordinate of the sprite
   * @param y        the y coordinate of the sprite
   * @param speed    the speed of the sprite, in scaled pixels
   */
  public Actor(String fileName, int x, int y, int speed) {
    this.fileName = fileName;
    this.x = x;
    this.y = y;
    this.speed = speed;
    this.dx = 0;
    this.dy = 0;
    loadImage();
  }

  /**
   * Using the fileName attribute, load the image into the sprite attribute
   */
  private void loadImage() {
    // Load the image
    try {
      this.sprite = ImageIO.read(Actor.class.getResource(fileName));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Update the position based on the change in x and y attributes
   */
  public void act() {
    x += dx;
    y += dy;
    
  }

  /**
   * Returns the x attribute
   * 
   * @return the x attribute
   */
  public int getX() {
    return x;
  }

  /**
   * Returns the y attribute
   * 
   * @return the y attribute
   */
  public int getY() {
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
  public int getDX() {
    return dx;
  }

  /**
   * Updates the value for dx
   * 
   * @param dx the new value for dx
   */
  public void setDX(int dx) {
    this.dx = dx;
  }

  /**
   * Returns the dy
   * 
   * @return the attribute dy
   */
  public int getDY() {
    return dy;
  }

  /**
   * Updates the value of dy
   * 
   * @param dy the new value for dy
   */
  public void setDY(int dy) {
    this.dy = dy;
  }

  /**
   * Returns the speed
   * 
   * @return the attribute speed
   */
  public int getSpeed() {
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
   * Draws the sprite centered at the x, y location. Adapts to scale of the JFrame.
   * 
   * @param g Graphics2D object to draw the image
   * @param i the JPanel where the sprite will be drawn
   */
  public void draw(Graphics2D g, ImageObserver i) {
    int offsetX = x - sprite.getWidth() / 2;
    int offsetY = y - sprite.getHeight() / 2;
    g.drawImage(sprite, Utility.scale(offsetX), Utility.scale(offsetY), Utility.scale(sprite.getWidth()),
        Utility.scale(sprite.getHeight()), i);
  }

  /**
   * This method is called when a key is pressed. The dx and dy attributes are
   * modified based on what keys are pressed
   * 
   * @param e the KeyEvent passed in from the KeyAdapter
   */
  public void keyPressed(KeyEvent e) {

    // Get which key was pressed.
    int key = e.getKeyCode();
    // if it was left, subtract speed from dx.
    if (key == KeyEvent.VK_LEFT) {
      dx = -1 * speed;
    }

    // if it was right, add speed to dx
    if (key == KeyEvent.VK_RIGHT) {
      dx = speed;
    }

    // if it was up, subtract speed from dy.
    if (key == KeyEvent.VK_UP) {
      dy = -1 * speed;
    }

    // if it was down, add speed to dy
    if (key == KeyEvent.VK_DOWN) {
      dy = speed;
    }
  }

  /**
   * This method is called when a key is released
   * 
   * @param e the KeyEvent generated by the KeyAdapter
   */
  public void keyReleased(KeyEvent e) {

    // which key was released?
    int key = e.getKeyCode();

    // if the key was left or right, reset the dx to zero
    if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
      dx = 0;
    }

    // if the key was up or down, reset the dy to zero
    if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
      dy = 0;
    }
  }

  /**
   * Returns the bounds of the sprite. Used for collision dectection.
   * 
   * @return a rectangle in the position and size of the sprite
   */
  public Rectangle getBounds() {
    return new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
  }
}
