package no.ntnu.idatt1002.demo.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.dao.DBConnectionProvider;
import no.ntnu.idatt1002.demo.data.InventoryItem;
import no.ntnu.idatt1002.demo.data.Item;
import no.ntnu.idatt1002.demo.data.Storable;
import no.ntnu.idatt1002.demo.repo.ItemRegister;

/**
 * A popup for adding items to the inventory.
 * TODO: Make this class be able to add recipes. Maybe make a superclas?
 */
public class ItemPopup extends Popup {

  /**
   * Interface for when the user saves the form.
   */
  public interface OnSave {
    void cb(int id, int itemId, String name, String categories, String allergies, String unit, int amount,
        int expirationDate);
  }

  /**
   * Interface for when the user deletes the item.
   */
  public interface OnDelete {
    void cb(int id, int itemId);
  }

  private OnSave onSave;
  private OnDelete onDelete;

  /**
   * Constructor for the AddPopup.
   */
  public ItemPopup(InventoryItem inventoryItem) {
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
    Text title = new Text(inventoryItem.getName());
    title.getStyleClass().addAll("popup-title", "centered");

    InputField nameField = new InputField("Name");
    nameField.setText(inventoryItem.getName());

    InputField categoryField = new InputField("Category");
    categoryField.setText(inventoryItem.getCategory());

    InputField allergiesField = new InputField("Allergies");
    allergiesField.setText(inventoryItem.getAllergy());

    InputField unitField = new InputField("Unit");
    unitField.setText(inventoryItem.getUnit());

    NumberField amountField = new NumberField();
    amountField.setPromptText("Amount");
    amountField.setText(Integer.toString(inventoryItem.getQuantity()));

    NumberField expirationField = new NumberField();
    expirationField.setPromptText("Expiration date");
    expirationField.setText(Integer.toString(inventoryItem.getExpirationDate()));

    // Add a button to submit the form
    PrimaryButton submitButton = new PrimaryButton("Save");
    submitButton.setButtonType(PrimaryButton.Type.SECONDARY);
    submitButton.setOnAction(e -> {
      if (onSave != null) {
        onSave.cb(
            inventoryItem.getId(),
            inventoryItem.getItemId(),
            nameField.getText(),
            categoryField.getText(),
            allergiesField.getText(),
            unitField.getText(),
            Integer.parseInt(amountField.getText()),
            Integer.parseInt(expirationField.getText()));
      }
      this.hide();
    });

    background.getStyleClass().add("centered");

    submitButton.setPrefWidth(200);
    submitButton.setPrefHeight(50);
    submitButton.getStyleClass().add("centered");

    // Add a button to delete the item
    PrimaryButton deleteButton = new PrimaryButton("Delete");
    deleteButton.setButtonType(PrimaryButton.Type.RED);
    deleteButton.setOnAction(e -> {
      if (onDelete != null) {
        onDelete.cb(inventoryItem.getId(), inventoryItem.getItemId());
      }
      this.hide();
    });

    deleteButton.setPrefWidth(200);
    deleteButton.setPrefHeight(50);
    deleteButton.getStyleClass().add("centered");

    // Add the content to the VBox
    background.getChildren().addAll(
        title,
        createField(nameField, "Name"),
        createField(categoryField, "Category"),
        createField(allergiesField, "Allergies"),
        createField(unitField, "Unit"),
        createField(amountField, "Amount"),
        createField(expirationField, "Expiration date"),
        submitButton,
        deleteButton);
  }

  /**
   * Creates a field with a label.
   * 
   * @param field the InputField to add
   * @param label the label of the field
   * @return the HBox containing the field and the label
   */
  private HBox createField(InputField field, String label) {
    HBox container = new HBox();
    HBox.setHgrow(container, Priority.ALWAYS);
    container.setSpacing(20);

    HBox textContainer = new HBox();
    Text text = new Text(label);
    text.setTextAlignment(TextAlignment.CENTER);
    textContainer.getStyleClass().add("left-centered");
    textContainer.getChildren().add(text);

    HBox fieldContainer = new HBox();
    HBox.setHgrow(fieldContainer, Priority.ALWAYS);
    fieldContainer.getChildren().add(field);
    fieldContainer.getStyleClass().add("right-centered");

    container.getStyleClass().addAll("full-width");
    container.getChildren().addAll(textContainer, fieldContainer);
    return container;
  }

  public ItemPopup setOnSave(OnSave onSave) {
    this.onSave = onSave;
    return this;
  }

  public ItemPopup setOnDelete(OnDelete onDelete) {
    this.onDelete = onDelete;
    return this;
  }

}
