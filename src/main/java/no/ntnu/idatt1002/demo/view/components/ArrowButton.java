package no.ntnu.idatt1002.demo.view.components;

import javafx.scene.paint.Color;

/**
 * A button with an arrow icon.
 * 
 * <p>
 * 
 * </p>
 */
public class ArrowButton extends PrimaryButton {
  private static final String ARROWNAME = "arrow";

  /**
   * Enum for the different directions arrow directions.
   * 
   * <ul>
   * <li>LEFT: An arrow pointing left</li>
   * <li>RIGHT: An arrow pointing right</li>
   * <li>UP: An arrow pointing up</li>
   * <li>DOWN: An arrow pointing down</li>
   * </ul>
   */
  public enum Direction {
    LEFT(180), RIGHT(0), UP(270), DOWN(90);

    private int angle;

    private Direction(int angle) {
      this.angle = angle;
    }
  }

  /**
   * Constructor for the ArrowButton.
   * 
   * <p>
   * Creates an arrow button pointing to the right.
   * </p>
   */
  public ArrowButton() {
    this(Direction.RIGHT);
  }

  /**
   * Constructor for ArrowButton with a specified direction.
   *
   * @param direction The direction the arrow should point.
   */
  public ArrowButton(Direction direction) {
    super(Type.TRANSPARENT, new Icon(ARROWNAME).setFillColor(new Color(0, 0, 0, 1)));
    super.getIcon().setRotate(direction.angle);
  }

  public void setInactiveColor() {
    super.getIcon().setFillColor(new Color(0.4, 0.4, 0.4, 1));
    super.getIcon().setStrokeColor(new Color(0.4, 0.4, 0.4, 1));
  }

  public void setActiveColor() {
    super.getIcon().setFillColor(new Color(0, 0, 0, 1));
    super.getIcon().setStrokeColor(new Color(0, 0, 0, 1));
  }
}
