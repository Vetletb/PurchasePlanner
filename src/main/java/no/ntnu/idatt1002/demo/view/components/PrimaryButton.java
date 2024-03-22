package no.ntnu.idatt1002.demo.view.components;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.demo.Logger;

/**
 * The main button for the application.
 */
public class PrimaryButton extends Button {

  /**
   * Enum for the different types of primary buttons.
   *
   * <ul>
   * <li>PRIMARY: A primary button</li>
   * <li>SECONDARY: A secondary button</li>
   * <li>RED: A button with red background</li>
   * <li>WHITE: A button with white background/li>
   * </ul>
   */
  public enum Type {
    PRIMARY, SECONDARY, RED, WHITE, TRANSPARENT;
  }

  private Icon icon;

  public PrimaryButton(String text) {
    this(text, Type.PRIMARY, null);
  }

  public PrimaryButton(String text, Type type) {
    this(text, type, null);
  }

  public PrimaryButton(String text, Icon icon) {
    this(text, Type.PRIMARY, icon);
  }

  public PrimaryButton(Type type, Icon icon) {
    this(null, type, icon);
  }

  public PrimaryButton(Icon icon) {
    this(null, Type.PRIMARY, icon);
  }

  public PrimaryButton(String text, Type type, Icon icon) {
    super(text, icon);
    this.icon = icon;
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

    // remove old styles
    List.of(type.values()).forEach(t -> {
      this.getStyleClass().remove(t.toString().toLowerCase() + "-button");
    });

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

  public Icon getIcon() {
    return icon;
  }

  public void setIcon(Icon icon) {
    this.icon = icon;
    this.setGraphic(icon);
  }
}
