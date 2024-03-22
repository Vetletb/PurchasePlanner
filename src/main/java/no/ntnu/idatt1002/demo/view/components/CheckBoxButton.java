package no.ntnu.idatt1002.demo.view.components;

import no.ntnu.idatt1002.demo.Logger;

/**
 * A custom button that acts as a checkbox.
 */
public class CheckBoxButton extends PrimaryButton {

  private boolean selected;

  /**
   * Constructor for the CheckBoxButton class.
   *
   * @param icon The icon to be displayed on the button.
   */
  public CheckBoxButton(Icon icon) {
    super(icon);
    super.setButtonType(Type.TRANSPARENT);
    this.selected = false;

    setOnAction(e -> {
      Logger.info("CheckBoxButton clicked");
      if (selected) {
        super.setIcon(new Icon("checkboxUnSelected"));

      } else {
        super.setIcon(new Icon("checkboxSelected"));

      }
      selected = !selected;
    });
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }
}
