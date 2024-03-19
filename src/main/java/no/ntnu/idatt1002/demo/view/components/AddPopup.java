package no.ntnu.idatt1002.demo.view.components;

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
import no.ntnu.idatt1002.demo.repo.ItemRegister;

/**
 * A popup for adding items to the inventory.
 * TODO: Make this class be able to add recipes. Maybe make a superclas?
 */
public class AddPopup extends Popup {
  private InputField nameField;
  private InputField categoryField;
  private InputField allergyField;
  private OnAdd onAdd;

  /**
   * A callback for when the form is submitted.
   */
  public interface OnAdd {
    void cb(String name, String category, String allergies);
  }

  /**
   * Constructor for the AddPopup.
   */
  public AddPopup(String type) {
    super();
    this.setAutoHide(true); // close the popup automatically

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

    // Add the input fields to the VBox
    nameField = new InputField("Name");
    categoryField = new InputField("Category");
    allergyField = new InputField("Allergies");

    // Add a button to submit the form
    PrimaryButton submitButton = new PrimaryButton("Add");
    submitButton.setOnAction(e -> {
      addItem();
    });
    submitButton.setPrefWidth(200);
    submitButton.setPrefHeight(50);

    // Add the content to the VBox
    background.getChildren().addAll(title, nameField, categoryField, allergyField, submitButton);
  }

  /**
   * Adds an item to the inventory.
   */
  private void addItem() {
    Logger.debug("Adding item to inventory");
    Logger.debug("Name: " + nameField.getText());
    Logger.debug("Category: " + categoryField.getText());
    Logger.debug("Allergies: " + allergyField.getText());
    // TODO: check fields!!

    if (onAdd == null) {
      Logger.error("onAdd callback is not set. Cannot add item.");
      return;
    }

    onAdd.cb(nameField.getText(), categoryField.getText(), allergyField.getText());

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
