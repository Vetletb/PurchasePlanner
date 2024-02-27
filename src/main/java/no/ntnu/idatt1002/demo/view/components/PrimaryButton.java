package no.ntnu.idatt1002.demo.view.components;

import javafx.scene.control.Button;

public class PrimaryButton extends Button {

  /**
   * Enum for the different types of primary buttons.
   *
   * <ul>
   * <li>PRIMARY: A primary button</li>
   * <li>SECONDARY: A secondary button</li>
   * <li>BLACK: A button with black background</li>
   * <li>WHITE: A button with white background/li>
   * </ul>
   */
  public enum Type {
    PRIMARY, SECONDARY, BLACK, WHITE;
  }

  // icon

  public PrimaryButton(String text) {
    super(text);
    this.setButtonType(Type.PRIMARY);
    this.getStyleClass().add("centered");
  }

  public PrimaryButton(String text, Type type) {
    super(text);
    this.setButtonType(type);
    this.getStyleClass().add("centered");
  }

  /**
   * Set the type of the button and clears all styles applied by the previous
   * type.
   *
   * @param type The type of the button
   * @return this
   * @see Type
   */
  public PrimaryButton setButtonType(Type type) {
    this.getStyleClass().removeIf(s -> s.endsWith("-button")); // Remove previous type
    this.addCustomStyle(type.toString().toLowerCase() + "-button"); // Add new type
    return this;
  }

  public void addCustomStyle(String style) {
    this.getStyleClass().add(style);
  }

  public void setCustomStyle(String style) {
    this.getStyleClass().clear();
    this.getStyleClass().add(style);
  }

  public void setCustomStyle(String[] styles) {
    this.getStyleClass().clear();
    this.getStyleClass().addAll(styles);
  }
}
