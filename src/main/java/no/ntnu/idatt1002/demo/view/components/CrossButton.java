package no.ntnu.idatt1002.demo.view.components;

import javafx.scene.paint.Color;

/**
 * A button with an arrow icon.
 * 
 * <p>
 * 
 * </p>
 */
public class CrossButton extends PrimaryButton {
  private static final String CROSSNAME = "cross";

  /**
   * Constructor for the CrossButton.
   * 
   * <p>
   * Creates a cross button.
   * </p>
   */
  public CrossButton() {
    super(Type.TRANSPARENT, new Icon(CROSSNAME).setFillColor(new Color(0, 0, 0, 1)));
  }
}
