package no.ntnu.idatt1002.demo.view.components;

import java.util.function.IntConsumer;

/**
 * NumberField. Allows the user to input a number into a text field.
 */
public class NumberField extends InputField {
  // ---------------------------------------------//
  // Fields //
  // ---------------------------------------------//
  static final String REGEX = "^[0-9]*$";
  private Integer lastValidValue;

  // ---------------------------------------------//
  // Constructor //
  // ---------------------------------------------//
  /**
   * Constructor.
   */
  public NumberField() {
    super();
  }

  /**
   * Constructor with default value.
   *
   * @param value The default value.
   */
  public NumberField(int value) {
    super(String.valueOf(value));
    this.lastValidValue = value;
  }

  /**
   * Constructor with on change listener.
   *
   * @param listener The on change listener. Only called when the input is a valid
   */
  public NumberField(IntConsumer listener) {
    this(null, listener);
  }

  /**
   * Constructor with default value and on change listener.
   *
   * @param value    The default value.
   * @param listener The on change listener. Only called when the input is a valid
   */
  public NumberField(int value, IntConsumer listener) {
    this(Integer.toString(value), listener);
    this.lastValidValue = value;
  }

  private IntConsumer listener;

  private NumberField(String value, IntConsumer listener) {
    super(value);
    this.listener = listener;

    this.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) {
        this.onSubmit(this.getText());
      }
    });

    // Handle changing the field
    this.setOnChange(
        newValue -> {
          // If the input is a valid number, set it as the last valid value
          if (this.isNumber()) {
            Integer parsedValue = Integer.parseInt(newValue);
            this.lastValidValue = parsedValue;
            listener.accept(parsedValue);
          }

          // Highlight the input if it is not a valid number
          this.highlightWrongInput();
        });

    // Handle submitting the field
    this.setOnSubmit(this::onSubmit);
  }

  private void onSubmit(String s) {
    // If the input is zero, set it as promt text and clear the field
    if (this.lastValidValue == 0) {
      this.setPromptText(lastValidValue.toString());
      this.setText("");
      return;
    }

    // If the input is a valid number, set it as the last valid value
    if (this.isNumber()) {
      this.setText(this.lastValidValue.toString());
      listener.accept(Integer.parseInt(s));
      return;
    }

    // If the input is not a valid number, set the last valid value as the text
    this.setText(this.lastValidValue.toString());
    listener.accept(this.lastValidValue);

  }

  /**
   * When called, checks if the input is a valid number and highlights the input
   * field if it is not.
   */
  private void highlightWrongInput() {
    final String fieldErrorClassName = "text-field-error";

    // Check if the input is a valid number
    if (!this.isNumber()) {
      // If the input already is highlighted, return
      if (this.getStyleClass().contains(fieldErrorClassName)) {
        return;
      }

      // Highlight the input field
      this.getStyleClass().add(fieldErrorClassName);
      return;
    }

    // Remove the highlight if the input is a valid number
    this.getStyleClass().remove(fieldErrorClassName);
  }

  // ---------------------------------------------//
  // Methods //
  // ---------------------------------------------//
  /**
   * Check if the input is a valid number.
   *
   * @return True if the input is a valid number.
   */
  public boolean isNumber() {
    return this.getText().matches(REGEX);
  }

  /**
   * Get the value of the number field.
   *
   * @return The value of the number field or null if the input is not a valid
   *         number.
   */
  public Integer getValue() {
    if (!this.isNumber()) {
      return null;
    }
    return Integer.parseInt(this.getText());
  }
}
