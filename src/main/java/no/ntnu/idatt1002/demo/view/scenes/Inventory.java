package no.ntnu.idatt1002.demo.view.scenes;

import java.util.List;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.demo.view.components.ArrowButton;
import no.ntnu.idatt1002.demo.view.components.Dropdown;

/**
 * The inventory page.
 */
public class Inventory extends VBox {

  public Inventory() {
    super();

    super.getChildren().addAll(new Text("Inventory"));

    super.getChildren().add(new ArrowButton());

    super.getChildren().add(new Dropdown("Filter", List.of("Item 1", "Item 2", "Item 3")));
  }

}
