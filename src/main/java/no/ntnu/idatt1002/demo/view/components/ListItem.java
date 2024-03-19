package no.ntnu.idatt1002.demo.view.components;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.demo.data.Storable;

/**
 * A list item that can display a storable item.
 */
public class ListItem<T extends Storable> extends HBox {
  private T item;
  private static final int SPACING = 100;
  private static final int HEIGHT = 50;

  public ListItem(T item) {
    super();
    this.item = item;

    // set styles
    this.setPrefHeight(HEIGHT);
    this.getStyleClass().addAll("list-item");
    this.setSpacing(SPACING);
    HBox.setHgrow(this, Priority.ALWAYS);

    // add the attributes of the item
    for (String attribute : getAttributes()) {
      this.getChildren().add(new Text(attribute));
    }
  }

  private String[] getAttributes() {
    return item.getAttributes().toArray(new String[0]);
  }

  public T getItem() {
    return item;
  }
}
