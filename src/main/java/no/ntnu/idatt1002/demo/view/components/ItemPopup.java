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
    void cb(int id, String name, String categories, String allergies);
  }

  /**
   * Interface for when the user deletes the item.
   */
  public interface OnDelete {
    void cb(int id);
  }

  private OnSave onSave;
  private OnDelete onDelete;

  /**
   * Constructor for the AddPopup.
   */
  public ItemPopup(Item item) {
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
    Text title = new Text(item.getName());
    title.getStyleClass().addAll("popup-title", "centered");

    InputField nameField = new InputField("Name");
    nameField.setText(item.getName());

    InputField categoryField = new InputField("Category");
    categoryField.setText(item.getCategory());

    InputField allergiesField = new InputField("Allergies");
    allergiesField.setText(item.getAllergy());

    // Add a button to submit the form
    PrimaryButton submitButton = new PrimaryButton("Save");
    submitButton.setButtonType(PrimaryButton.Type.SECONDARY);
    submitButton.setOnAction(e -> {
      if (onSave != null) {
        onSave.cb(
            item.getId(),
            nameField.getText(),
            categoryField.getText(),
            allergiesField.getText());
      }
      this.hide();
    });

    submitButton.setPrefWidth(200);
    submitButton.setPrefHeight(50);

    // Add a button to delete the item
    PrimaryButton deleteButton = new PrimaryButton("Delete");
    deleteButton.setButtonType(PrimaryButton.Type.RED);
    deleteButton.setOnAction(e -> {
      if (onDelete != null) {
        onDelete.cb(item.getId());
      }
      this.hide();
    });

    deleteButton.setPrefWidth(200);
    deleteButton.setPrefHeight(50);

    // Add the content to the VBox
    background.getChildren().addAll(
        title,
        nameField,
        categoryField,
        allergiesField,
        submitButton,
        deleteButton);
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
