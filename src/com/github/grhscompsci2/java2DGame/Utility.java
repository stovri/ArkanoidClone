package com.github.grhscompsci2.java2DGame;
import java.awt.*;

/**
 * Scaling methods from
 * https://stackoverflow.com/questions/11959758/java-maintaining-aspect-ratio-of-jpanel-background-image/11959928#11959928
 */
public class Utility {
  public static int gameWidth=600;
  public static int gameHeight=400;
  /**
   * Globally accessable scale factor so game logic can stay constant despite
   * resizing JFrame.
   */
  public static double scaleFactor = 1;

  /**
   * Update the global scale factor using the background image dimensions and the
   * JFrame size
   * 
   * @param original the width and height of the background image
   * @param toFit    the width and height of the JFrame
   */
  public static void updateScale(Dimension original, Dimension toFit) {
    gameWidth=(int)original.getWidth();
    gameHeight=(int)original.getHeight();
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
   * @param i the int to scale
   * @return the scaled int
   */
  public static int scale(int i) {
    return (int) Math.round(i * scaleFactor);
  }
}
