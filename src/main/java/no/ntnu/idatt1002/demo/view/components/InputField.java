package no.ntnu.idatt1002.demo.view.components;

import javafx.scene.control.TextField;

/**
 * A component for input fields.
 */
public class InputField extends TextField {

  /**
   * A callback for when the form is submitted.
   */
  public interface OnSubmit {
    void cb(String value);
  }

  /**
   * A callback for when the value of the input field changes.
   */
  public interface OnChange {
    void cb(String value);
  }

  public InputField(String promptText) {
    this.setPromptText(promptText);
    this.getStyleClass().add("input-field");
  }

  /**
   * Set the callback for when the form is submitted.
   *
   * @param cb the callback
   * @return this input field
   */
  public InputField setOnSubmit(OnSubmit cb) {
    this.setOnAction(e -> cb.cb(this.getText()));
    return this;
  }

  /**
   * Set the callback for when the value of the input field changes (eg. when the
   * user types something in the field).
   *
   * @param cb the callback
   * @return this input field
   */
  public InputField setOnChange(OnChange cb) {
    // TODO: remove old listener
    this.textProperty().addListener((observable, oldValue, newValue) -> cb.cb(newValue));
    return this;
  }

}
