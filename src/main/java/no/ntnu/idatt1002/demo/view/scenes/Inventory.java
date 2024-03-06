package no.ntnu.idatt1002.demo.view.scenes;

import java.util.List;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.demo.view.components.ArrowButton;
import no.ntnu.idatt1002.demo.view.components.Dropdown;
import no.ntnu.idatt1002.demo.view.components.ListHeader;

/**
 * The inventory page.
 */
public class Inventory extends VBox {

  public Inventory() {
    super();

    this.getStyleClass().add("full-width");

    super.getChildren().addAll(new Text("Inventory"));

    this.getChildren().add(new ListHeader());

  }

}
