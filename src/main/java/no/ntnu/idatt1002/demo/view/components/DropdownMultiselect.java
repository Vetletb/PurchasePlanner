package no.ntnu.idatt1002.demo.view.components;

import java.util.List;
import java.util.Map;

import org.controlsfx.control.CheckComboBox;

import no.ntnu.idatt1002.demo.data.Storable;

/**
 * A styled combo box.
 */

public class DropdownMultiselect extends CheckComboBox<Storable> {

  /**
   * Constructor for the multiselect dropdown.
   *
   * @param title The title/placeholder of the dropdown
   * @param items The items in the dropdown
   */
  public DropdownMultiselect(Map<Integer, ? extends Storable> items) {

    super();

    this.getStyleClass().addAll("dropdown", "multiselect");
    this.getItems().addAll(items.values());
  }
}
