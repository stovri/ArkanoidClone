package com.github.grhscompsci2.java2DGame;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JPanel;

//@Slf4j
/**
 * A Swing Layout that will shrink or enlarge keep the content of a container
 * while keeping it's aspect ratio. The caveat is that only a single component
 * is supported or an exception will be thrown. This is the component's
 * getPreferredSize() method that must return the correct ratio. The
 * preferredSize will not be preserved but the ratio will.
 * 
 * @see <a
 *      href=
 *      "https://gist.github.com/fmarot/f04346d0e989baef1f56ffd83bbf764d">SingleComponentAspectRatioKeeperLayout.java</a>
 */
public class SingleComponentAspectRatioKeeperLayout implements LayoutManager {

  /**
   * Will be used for calculus in case no real component is in the parent
   */
  private static Component fakeComponent = new JPanel();

  /**
   * Default constructor that will initialize the fake component
   */
  public SingleComponentAspectRatioKeeperLayout() {
    fakeComponent.setPreferredSize(new Dimension(0, 0));
  }

  /**
   * This method will do all the math necessary to keep the component scaled
   * inside the parent container while maintaining aspect ratio
   * 
   * @param parent the container holding the component to be scaled.
   */
  @Override
  public void layoutContainer(Container parent) {
    Component component = getSingleComponent(parent);
    Insets insets = parent.getInsets();
    int maxWidth = parent.getWidth() - (insets.left + insets.right);
    int maxHeight = parent.getHeight() - (insets.top + insets.bottom);

    Dimension prefferedSize = component.getPreferredSize();
    Dimension targetDim = getScaledDimension(prefferedSize, new Dimension(maxWidth, maxHeight));

    double targetWidth = targetDim.getWidth();
    double targetHeight = targetDim.getHeight();

    double hgap = (maxWidth - targetWidth) / 2;
    double vgap = (maxHeight - targetHeight) / 2;

    // Set the single component's size and position.
    component.setBounds((int) hgap, (int) vgap, (int) targetWidth, (int) targetHeight);
  }

  /**
   * This method will return the single component in the container, or return the
   * fake component if there are none.
   * 
   * @param parent the container
   * @return Component the lone component
   */
  private Component getSingleComponent(Container parent) {
    int parentComponentCount = parent.getComponentCount();
    if (parentComponentCount > 1) {
      throw new IllegalArgumentException(this.getClass().getSimpleName()
          + " can not handle more than one component");
    }
    Component comp = (parentComponentCount == 1) ? parent.getComponent(0) : fakeComponent;
    return comp;
  }

  /**
   * This method will scale the provided image size using the provided boundary
   * and maintian aspect ratio of the image.
   * 
   * @param imageSize the size of the image
   * @param boundary  the size of the box
   * @return Dimension the new image size that maintains the aspect ratio
   */
  private Dimension getScaledDimension(Dimension imageSize, Dimension boundary) {
    double widthRatio = boundary.getWidth() / imageSize.getWidth();
    double heightRatio = boundary.getHeight() / imageSize.getHeight();
    double ratio = Math.min(widthRatio, heightRatio);
    return new Dimension((int) (imageSize.width * ratio), (int) (imageSize.height * ratio));
  }

  /**
   * Calculates the minimum size dimensions for the specified container, given the
   * components it contains.
   * 
   * @param parent the component to be laid out
   * @return Dimension the minimum dimensions
   */
  @Override
  public Dimension minimumLayoutSize(Container parent) {
    return preferredLayoutSize(parent);
  }

  /**
   * Calculates the preferred size dimensions for the specified container, given
   * the components it contains.
   * 
   * @param parent the container to be laid out
   * @return Dimension the size of the component.
   */
  @Override
  public Dimension preferredLayoutSize(Container parent) {
    return getSingleComponent(parent).getPreferredSize();
  }

  /**
   * This method is required to be implemented, but we do not use it
   * 
   * @param comp the component to be removed
   */
  @Override
  public void removeLayoutComponent(Component comp) {
  }

  /**
   * This method is required to be implemented, but we do not use it
   * 
   * @param name the string to be associated with the component
   * @param comp the component to be added
   */
  @Override
  public void addLayoutComponent(String name, Component comp) {

  }
}