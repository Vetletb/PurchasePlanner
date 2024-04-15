package no.ntnu.idatt1002.demo.view.components;

import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public class EventPane extends HBox {
  private final int event_id;
  private final String name;

  public EventPane(int event_id, String name) {
    super();
    this.name = name;
    this.event_id = event_id;

    Label nameLabel = new Label(name);
    super.getChildren().add(nameLabel);
    super.getStyleClass().add("event-pane");
  }

  public String getName() {
    return name;
  }
}