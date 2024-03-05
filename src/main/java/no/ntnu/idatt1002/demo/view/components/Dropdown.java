package no.ntnu.idatt1002.demo.view.components;

import java.util.List;
import javafx.scene.control.ComboBox;

/**
 * A styled combo box.
 */

public class Dropdown extends ComboBox<String> {

  /**
   * Constructor for the Dropdown.
   *
   * @param title The title/placeholder of the dropdown
   * @param items The items in the dropdown
   */
  public Dropdown(String title, List<String> items) {

    super();
    this.setPromptText(title);

    this.getStyleClass().add("dropdown");

    super.getItems().addAll(items);
  }
}
