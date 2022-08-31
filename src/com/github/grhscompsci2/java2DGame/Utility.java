package com.github.grhscompsci2.java2DGame;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import com.github.grhscompsci2.java2DGame.actors.Actor;
import com.github.grhscompsci2.java2DGame.actors.Actor.Type;

/**
 * This is the Utility class used by the Java 2D Game. It holds useful methods
 * and global values for use by ALL classes in the game.
 * Scaling methods from
 * https://stackoverflow.com/questions/11959758/java-maintaining-aspect-ratio-of-jpanel-background-image/11959928#11959928
 */
public class Utility {
  // Default width and height
  public static int gameWidth = 600;
  public static int gameHeight = 400;
  /**
   * Globally accessable scale factor so game logic can stay constant despite
   * resizing JFrame.
   */
  public static double scaleFactor = 1;

  // ArrayList of all actors in the game
  public static ArrayList<Actor> castAndCrew = new ArrayList<>();
  // ArrayList of all actors that need to be added to the game so we can avoid the
  // "Concurrent modification" error
  public static ArrayList<Actor> newActors = new ArrayList<>();
  // booleans to hold the keypresses
  public static boolean UP_ARROW = false;
  public static boolean LEFT_ARROW = false;
  public static boolean DOWN_ARROW = false;
  public static boolean RIGHT_ARROW = false;
  public static boolean SPACE = false;
  public static int score = 0;
  public static int topScore;

  /**
   * This method will return the URL for the specified image
   * 
   * @param fileName the full name of the image
   * @return the URL of the image
   */
  public static URL getImageURL(String fileName) {
    return Utility.class.getResource("images/" + fileName);
  }

  public static URL getLevelName(int i) {
    return Utility.class.getResource("levels/level" + i + ".txt");
  }

  /**
   * Update the global scale factor using the background image dimensions and the
   * JFrame size
   * 
   * @param original the width and height of the background image
   * @param toFit    the width and height of the JFrame
   */
  public static void updateScale(Dimension original, Dimension toFit) {
    gameWidth = (int) original.getWidth();
    gameHeight = (int) original.getHeight();
    double dScaleWidth = getScaleFactor(original.width, toFit.width);
    double dScaleHeight = getScaleFactor(original.height, toFit.height);
    scaleFactor = Math.min(dScaleHeight, dScaleWidth);
  }

  /**
   * Gets the ratio of the target to master size.
   * 
   * @param iMasterSize the current size of the attribute
   * @param iTargetSize the desired size of the attribute
   * @return the scale factor
   */
  public static double getScaleFactor(int iMasterSize, int iTargetSize) {
    return (double) iTargetSize / (double) iMasterSize;
  }

  /**
   * Automatically scales and formats the provided int
   * 
   * @param offsetX the int to scale
   * @return the scaled int
   */
  public static int scale(double offsetX) {
    return (int) Math.round(offsetX * scaleFactor);
  }

  public static void addActor(Actor actor) {
    newActors.add(actor);
  }

  public static void clearDead() {
    Iterator<Actor> itr = castAndCrew.iterator();
    while (itr.hasNext()) {
      Actor actor = itr.next();
      if (actor.isDead()) {
        itr.remove();
      }
    }
  }

  public static void addNew() {
    castAndCrew.addAll(newActors);
    newActors.clear();
  }

  public static void updateScore(int points) {
    score += points;
    if (score > topScore) {
      topScore = score;
    }
  }

  public static void drawScore(Graphics2D g2d, Rectangle rect) {
    drawCenteredString(g2d, score + "", rect);
  }

  public static void drawTopScore(Graphics2D g2d, Rectangle rect) {
    drawCenteredString(g2d, topScore + "", rect);
  }

  /**
   * Draw a String centered in the middle of a Rectangle.
   *
   * @param g    The Graphics instance.
   * @param text The String to draw.
   * @param rect The Rectangle to center the text in.
   */
  public static void drawCenteredString(Graphics2D g2d, String text, Rectangle rect) {
    
    rect = new Rectangle(Utility.scale(rect.x), Utility.scale(rect.y), Utility.scale(rect.width),
        Utility.scale(rect.height));
    Font font = new Font("DialogInput", Font.BOLD, Utility.scale(10));
    // Get the FontMetrics
    FontMetrics metrics = g2d.getFontMetrics(font);
    // Determine the X coordinate for the text
    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
    // Determine the Y coordinate for the text (note we add the ascent, as in java
    // 2d 0 is top of the screen)
    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
    // Set the font
    g2d.setFont(font);
    //Set the color
    g2d.setColor(Color.white);
    // Draw the String
    g2d.drawString(text, x, y);
  }

  public static boolean checkDone() {
    for(Actor a:castAndCrew){
      if(a.getType()==Type.brick)
        return false;
    }
    return true;
  }

  public static void clearBoard() {
    for(Actor a:castAndCrew){
      a.die();
    }
  }
}
