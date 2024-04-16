package no.ntnu.idatt1002.demo.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.dao.DBConnectionProvider;
import no.ntnu.idatt1002.demo.data.ShoppingListItem;
import no.ntnu.idatt1002.demo.repo.ItemRegister;

public class ShoppingListItemPopup extends Popup {

  public interface OnSave {
    void cb(Object[] values);
  }

  public interface OnDelete {
    void cb(int ShoppingListItem_id);
  }

  private OnSave onSave;
  private OnDelete onDelete;

  /**
   * Constructor for the ShoppingListItemPopup.
   */
  public ShoppingListItemPopup(ShoppingListItem shppingListItem) {
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
    Text title = new Text(shppingListItem.getName());
    title.getStyleClass().addAll("popup-title", "centered");


    ItemRegister itemRegister = new ItemRegister(new DAO(new DBConnectionProvider()));
    itemRegister.getAllItems();
    // Input fields
    Field<Integer[]> itemField = Field.ofMap("Item", itemRegister.getItems());
    Field<Number> quantityField = Field.ofNumber("Quantity");
    Field<String> unitField = Field.ofString("Unit");

    // Add a button to submit the form
    PrimaryButton submitButton = new PrimaryButton("Save");
    submitButton.setButtonType(PrimaryButton.Type.SECONDARY);
    submitButton.setOnAction(e -> {
      if (onSave != null) {
        onSave.cb(
            new Object[] {
                shppingListItem.getId(),
                itemField.getValue(),
                quantityField.getValue(),
                unitField.getValue()
            }
      );
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
        onDelete.cb(shppingListItem.getId());
      }
      this.hide();
    });

    deleteButton.setPrefWidth(200);
    deleteButton.setPrefHeight(50);

    // Add the content to the VBox
    background.getChildren().addAll(
            title,
            itemField.getRenderedField(),
            quantityField.getRenderedField(),
            unitField.getRenderedField(),
            submitButton,
            deleteButton);
  }

  public ShoppingListItemPopup setOnSave(OnSave cb) {
    this.onSave = cb;
    return this;
  }

  public ShoppingListItemPopup setOnDelete(OnDelete cb) {
    this.onDelete = cb;
    return this;
  }

}
