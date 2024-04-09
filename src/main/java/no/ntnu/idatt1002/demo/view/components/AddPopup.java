package no.ntnu.idatt1002.demo.view.components;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.dao.DBConnectionProvider;
import no.ntnu.idatt1002.demo.data.Storable;
import no.ntnu.idatt1002.demo.repo.ItemRegister;

/**
 * A popup for adding items to the inventory.
 * TODO: Make this class be able to add recipes. Maybe make a superclas?
 */
public class AddPopup extends Popup {
  private OnAdd onAdd;
  private ArrayList<Field> fields = new ArrayList<>();
  private String type;

  /**
   * A callback for when the form is submitted.
   */
  public interface OnAdd {
    void cb(Object[] values);
  }

  /**
   * Constructor for the AddPopup.
   */
  public AddPopup(String type) {
    super();
    this.setAutoHide(true); // close the popup automatically
    this.type = type;
    render();
  }

  private void render() {
    // Create a container for the popup
    StackPane container = new StackPane();

    // Create a VBox to hold the content of the popup
    VBox background = new VBox();
    background.getStyleClass().add("popup-background");
    background.setSpacing(20);
    this.getContent().add(container);
    container.getChildren().add(background);

    // create a cross button to close the popup
    CrossButton crossButton = new CrossButton();
    crossButton.setOnAction(e -> this.hide());
    crossButton.setPadding(new Insets(10));

    container.getChildren().add(crossButton);
    StackPane.setAlignment(crossButton, Pos.TOP_RIGHT);

    // Add a title to the popup
    Text title = new Text("Adding " + type);
    title.getStyleClass().addAll("popup-title", "centered");

    // Add a button to submit the form
    PrimaryButton submitButton = new PrimaryButton("Add");
    submitButton.setOnAction(e -> {
      addItem();
    });
    submitButton.setPrefWidth(200);
    submitButton.setPrefHeight(50);
    background.getChildren().add(title);

    for (Field<?> field : fields) {
      background.getChildren().add(field.getRenderedField());
    }

    // Add the content to the VBox
    background.getChildren().add(submitButton);
  }

  /**
   * Adds a new field to the popup.
   *
   * @param <T>   the type of the field
   * @param field the field to add
   * @return the field
   */
  public <T> Field<T> addField(Field<T> field) {
    fields.add(field);
    render();
    return field;
  }

  /**
   * Adds an item to the inventory.
   */
  private void addItem() {
    // TODO: check fields!!

    if (onAdd == null) {
      Logger.error("onAdd callback is not set. Cannot add item.");
      return;
    }

    ArrayList<Object> values = new ArrayList<>();
    for (Field<?> field : fields) {
      values.add(field.getValue());
    }

    onAdd.cb(values.toArray());

    // If no errors, close the popup
    this.hide();

  }

  /**
   * Set the callback for when the form is submitted.
   *
   * @param cb the callback
   * @return this input field
   */
  public AddPopup setOnAdd(OnAdd cb) {
    this.onAdd = cb;
    return this;
  }
}
