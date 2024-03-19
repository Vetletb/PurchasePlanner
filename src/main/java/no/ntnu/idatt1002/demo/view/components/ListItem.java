package no.ntnu.idatt1002.demo.view.components;

import java.util.ArrayList;

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

    ArrayList<HBox> containers = new ArrayList<>();

    // add the id of the item
    HBox idContainer = new HBox();
    idContainer.getStyleClass().add("list-item-attribute");
    idContainer.getChildren().add(new Text(String.valueOf(item.getId())));
    containers.add(idContainer);

    // add the attributes of the item
    for (String attribute : getAttributes()) {
      HBox container = new HBox();
      container.getStyleClass().add("list-item-attribute");
      container.getChildren().add(new Text(attribute));

      containers.add(container);
    }

    this.getChildren().addAll(containers);
  }

  private String[] getAttributes() {
    return item.getAttributes().toArray(new String[0]);
  }

  public T getItem() {
    return item;
  }
}
