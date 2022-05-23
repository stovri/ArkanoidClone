package com.github.grhscompsci2.java2DGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import com.github.grhscompsci2.java2DGame.actors.Actor;

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

  public static ArrayList<Actor> castAndCrew = new ArrayList<>();
  public static ArrayList<Actor> newActors=new ArrayList<>();
  public static boolean UP_ARROW = false;
  public static boolean LEFT_ARROW = false;
  public static boolean DOWN_ARROW = false;
  public static boolean RIGHT_ARROW = false;
  public static boolean SPACE=false;

  public static final String IMG_FOLDER="images/";
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
  public static int scale(float offsetX) {
    return (int) Math.round(offsetX * scaleFactor);
  }

  public static void addActor(Actor actor) {
    newActors.add(actor);
  }

  public static void clearDead() {
    Iterator<Actor> itr=castAndCrew.iterator();
    while(itr.hasNext()){
      Actor actor=itr.next();
      if(actor.isDead()){
        itr.remove();
      }
    }
  }

  public static void addNew() {
    castAndCrew.addAll(newActors);
    newActors.clear();
  }
}
