package no.ntnu.idatt1002.demo.view.scenes;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.demo.view.components.Icon;

/**
 * The inventory page.
 */
public class Inventory extends VBox {

  public Inventory() {
    super();

    super.getChildren().addAll(new Text("Inventory"));
    Icon icon = new Icon("testIcon");
    icon.setSize(2);
    super.getChildren().add(icon);
  }

}
