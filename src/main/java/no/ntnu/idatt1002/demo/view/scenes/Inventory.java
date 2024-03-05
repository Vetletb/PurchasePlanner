package no.ntnu.idatt1002.demo.view.scenes;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.demo.view.components.ArrowButton;

/**
 * The inventory page.
 */
public class Inventory extends VBox {

  public Inventory() {
    super();

    super.getChildren().addAll(new Text("Inventory"));

    super.getChildren().add(new ArrowButton());
  }

}
