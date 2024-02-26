package no.ntnu.idatt1002.demo.view.scenes;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The inventory page.
 */
public class ShoppingList extends VBox {

  public ShoppingList() {
    super();

    super.getChildren().addAll(new Text("Shopping list"));
  }

}
