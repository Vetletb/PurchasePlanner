package no.ntnu.idatt1002.demo.view.scenes;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.data.ShoppingListItem;
import no.ntnu.idatt1002.demo.view.components.CheckBoxButton;
import no.ntnu.idatt1002.demo.view.components.Icon;

/**
 * The inventory page.
 */
public class ShoppingList extends VBox {


  private final HBox shoppingListContainter;
  private final TableView<ShoppingListItem> shoppingListTable;
  private final VBox checkBoxButtons;

  /**
   * Constructor for the ShoppingList class.
   */
  public ShoppingList() {
    super();

    shoppingListContainter = new HBox();

    // Shopping list table
    shoppingListTable = new TableView<ShoppingListItem>();

    // checkbox container
    checkBoxButtons = new VBox();

    // Shopping list table columns
    TableColumn<ShoppingListItem, Integer> idColumn
            = new TableColumn<>("Item Id");
    idColumn.setCellValueFactory(new PropertyValueFactory<ShoppingListItem, Integer>("Id"));

    TableColumn<ShoppingListItem, String> nameColumn
            = new TableColumn<>("Name");
    nameColumn.setCellValueFactory(new PropertyValueFactory<ShoppingListItem, String>("Name"));

    TableColumn<ShoppingListItem, String> categoryColumn
            = new TableColumn<>("Category");
    categoryColumn.setCellValueFactory(new PropertyValueFactory<ShoppingListItem, String>("Category"));

    TableColumn<ShoppingListItem, String> allergyColumn
            = new TableColumn<>("Allergy");
    allergyColumn.setCellValueFactory(new PropertyValueFactory<ShoppingListItem, String>("Allergy"));

    TableColumn<ShoppingListItem, Integer> quantityColumn
            = new TableColumn<>("Quantity");
    quantityColumn.setCellValueFactory(new PropertyValueFactory<ShoppingListItem, Integer>("Quantity"));

    TableColumn<ShoppingListItem, String> unitColumn
            = new TableColumn<>("Unit");
    unitColumn.setCellValueFactory(new PropertyValueFactory<ShoppingListItem, String>("Unit"));


    // Add the columns to the tables
    shoppingListTable.getColumns().add(idColumn);
    shoppingListTable.getColumns().add(nameColumn);
    shoppingListTable.getColumns().add(categoryColumn);
    shoppingListTable.getColumns().add(allergyColumn);
    shoppingListTable.getColumns().add(quantityColumn);
    shoppingListTable.getColumns().add(unitColumn);

    // Set the columns to be non-resizable
    shoppingListTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    shoppingListTable.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight()*0.8 );
    shoppingListTable.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth()*0.7 );
//    shoppingListTable.getStyleClass().add("shopping-list-table");
    shoppingListTable.setStyle("-fx-padding: 20px;" +
            "    -fx-alignment: center;" +
            "    -fx-font-size: 20px;" +
            "    -fx-font-family: 'Inter';");

    // Make sure the shopping list table is not editable
    shoppingListTable.setEditable(false);


    shoppingListContainter.getStyleClass().add("centered");

    checkBoxButtons.getStyleClass().add("checkbox-button-container");
    checkBoxButtons.setStyle("-fx-spacing: -24px;\n" +
            "    -fx-padding: 45px;");

    shoppingListContainter.getChildren().addAll(shoppingListTable, checkBoxButtons);
    super.getChildren().addAll(new Text("Shopping list"), shoppingListContainter);

    this.addItem(new ShoppingListItem(1, "Milk", "Dairy", "Lactose", 1, "l"));
    this.addItem(new ShoppingListItem(2, "Bread", "Bread", "Gluten", 1, "pcs"));
    this.addItem(new ShoppingListItem(3, "Eggs", "Eggs", "Eggs", 12, "pcs"));
    this.addItem(new ShoppingListItem(3, "Holy shit a really long name", "Eggs", "Eggs", 12, "pcs"));
  }

  /**
   * This method adds an item to the shopping list.
   * @param item the item to be added
   */
  public void addItem(ShoppingListItem item) {
    shoppingListTable.getItems().add(item);
    Icon icon = new Icon("checkboxUnSelected");
    CheckBoxButton checkBoxButton = new CheckBoxButton(icon);
    checkBoxButton.setScaleX(0.58);
    checkBoxButton.setScaleY(0.58);

    checkBoxButtons.getChildren().add(checkBoxButton);
  }

}

