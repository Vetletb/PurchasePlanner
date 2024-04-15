package no.ntnu.idatt1002.demo.view.components;

import java.util.function.Consumer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * A simple class for switching pages.
 */
public class PageSwitcher extends HBox {
  private BooleanProperty canSwitchLefProperty = new SimpleBooleanProperty(true);
  private BooleanProperty canSwitchRightProperty = new SimpleBooleanProperty(true);
  private Consumer<Integer> onSwitch;

  /**
   * Constructor for the page switcher.
   */
  public PageSwitcher() {
    super();
    this.getStyleClass().addAll("centered");

    ArrowButton leftArrow = new ArrowButton(ArrowButton.Direction.LEFT);
    leftArrow.disableProperty().bind(canSwitchLefProperty.not());
    leftArrow.getStyleClass().add("left-centered");
    leftArrow.setOnAction(e -> {
      if (onSwitch != null) {
        onSwitch.accept(-1);
      }

    });

    ArrowButton rightArrow = new ArrowButton(ArrowButton.Direction.RIGHT);
    rightArrow.disableProperty().bind(canSwitchRightProperty.not());
    rightArrow.getStyleClass().add("right-centered");
    rightArrow.setOnAction(e -> {
      if (onSwitch != null) {
        onSwitch.accept(1);
      }
    });

    HBox.setHgrow(this, Priority.ALWAYS);
    this.getChildren().addAll(leftArrow, rightArrow);
  }

  public BooleanProperty canSwitchLeftProperty() {
    return canSwitchLefProperty;
  }

  public BooleanProperty canSwitchRightProperty() {
    return canSwitchRightProperty;
  }

  public void setOnSwitch(Consumer<Integer> onSwitch) {
    this.onSwitch = onSwitch;
  }

}
