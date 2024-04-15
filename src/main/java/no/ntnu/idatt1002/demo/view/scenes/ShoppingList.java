package no.ntnu.idatt1002.demo.view.scenes;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.dao.DBConnectionProvider;
import no.ntnu.idatt1002.demo.data.Item;
import no.ntnu.idatt1002.demo.data.ShoppingListItem;
import no.ntnu.idatt1002.demo.repo.ItemRegister;
import no.ntnu.idatt1002.demo.repo.ShoppingListItemRegister;
import no.ntnu.idatt1002.demo.view.components.*;

import java.util.Map;

/**
 * The inventory page.
 */
public class ShoppingList extends VBox {

  // Positioning containers
  private final HBox contentContainer;
  private final VBox innerContentContainer;

  // Shopping list containers
  private final VBox shoppingListContainer;
  private final ScrollPane shoppingListScrollPane;

  // Static top and bottom bars
  private final HBox staticListTopBar;
  private final HBox staticListBottomBar;

  // Items list
  private Map<Integer, ShoppingListItem> items;

  /**
   * Constructor for the ShoppingList class.
   */
  public ShoppingList() {
    super();

    // Initialize the containers
    contentContainer = new HBox(); // Page content container
    innerContentContainer = new VBox(); // Inner content container
    shoppingListContainer = new VBox(); // Shopping list container
    shoppingListScrollPane = new ScrollPane(); // Shopping list scroll pane
    staticListTopBar = new HBox(); // Static top bar
    staticListBottomBar = new HBox(); // Static bottom bar

    // Top bar labels
    Label topBarItemName = new Label("Name");
    Label topBarItemCategory = new Label("Category");
    Label topBarItemQuantity = new Label("Quantity");
    Label topBarItemUnit = new Label("Unit");
    Label topBarItemCheckBox = new Label("Done");

    // Bottom bar labels TODO: Implement the functionality
    Label bottomBarTotalQuantity = new Label("Total quantity: 100");
    Label bottomBarTotalPrice = new Label("Total price: 1200");

    // Add the labels to the top bar
    staticListTopBar.getChildren().addAll(topBarItemName, topBarItemCategory, topBarItemQuantity, topBarItemUnit, topBarItemCheckBox);

    // Add the labels to the bottom bar
    staticListBottomBar.getChildren().addAll(bottomBarTotalQuantity, bottomBarTotalPrice);

    // Scroll pane settings
    shoppingListScrollPane.setContent(shoppingListContainer);
    shoppingListScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    shoppingListScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

    // Clear all button and clear completed button container
    HBox clearButtonsContainer = new HBox();

    // Clear all button and clear completed button
    PrimaryButton clearAllButton = new PrimaryButton("Clear all");
    clearAllButton.setOnAction(e -> {
      ShoppingListItemRegister shoppingListItemRegister = new ShoppingListItemRegister(new DAO(new DBConnectionProvider()));
      shoppingListItemRegister.getAllItems();
      shoppingListItemRegister.clearShoppingList();
      items = shoppingListItemRegister.getItems();
      loadShoppingList();
    });

    PrimaryButton clearCompletedButton = new PrimaryButton("Clear completed");

    clearCompletedButton.setOnAction(e -> {
      ShoppingListItemRegister shoppingListItemRegister = new ShoppingListItemRegister(new DAO(new DBConnectionProvider()));
      shoppingListItemRegister.getAllItems();
      items = shoppingListItemRegister.getItems();

      shoppingListContainer.getChildren().forEach(node -> {
        ShoppingListItemPane shoppingListItemPane = (ShoppingListItemPane) node;

        if (shoppingListItemPane.isSelected()) {
          shoppingListItemRegister.deleteFromShoppingList(
                  shoppingListItemRegister.getShoppingListItemById(
                          shoppingListItemPane.getShoppingListItemId()).getId());
        }
        shoppingListItemRegister.getAllItems();
        items = shoppingListItemRegister.getItems();
      });
      loadShoppingList();
    });

    clearButtonsContainer.getChildren().addAll(clearAllButton, clearCompletedButton);

    // Add the containers to the content container
    innerContentContainer.getChildren().addAll(clearButtonsContainer, staticListTopBar, shoppingListScrollPane, staticListBottomBar);
    contentContainer.getChildren().add(innerContentContainer);

    // Create the list header and add to the page
    ListHeader shoppingListHeader = new ListHeader();
    shoppingListHeader.setOnSearch(this::fullSearch);
    shoppingListHeader.setOnSearchQueryChange(this::search);
    shoppingListHeader.setOnAdd(this::addItem);


    super.getChildren().addAll(shoppingListHeader, contentContainer);
    // TODO: Create a connection to the database for the add button
    // TODO remove test data after testing
//    ItemRegister itemRegister = new ItemRegister(new DAO(new DBConnectionProvider()));
//    itemRegister.getAllItems();
//    Map<Integer, Item> inventoryItems = itemRegister.getItems();
//    // Add the items to the shopping list TODO fix the unit to match the item
//    inventoryItems.values().forEach(item -> addItem(new ShoppingListItem(item.getId(), item.getName(), item.getCategory(), item.getAllergy(), 1, "kg")));


    // Initialize the itemsList
    ShoppingListItemRegister shoppingListItemRegister = new ShoppingListItemRegister(new DAO(new DBConnectionProvider()));
    shoppingListItemRegister.getAllItems();
    items = shoppingListItemRegister.getItems();
    loadShoppingList();


    // CSS styling
    contentContainer.getStyleClass().add("centered");
    shoppingListContainer.getStyleClass().addAll("shopping-list");
    innerContentContainer.getStyleClass().add("shopping-list");
    staticListTopBar.getStyleClass().addAll("static-label-container", "rounded-top");
    staticListBottomBar.getStyleClass().addAll("static-bottom-label-container", "rounded-bottom");
    clearButtonsContainer.getStyleClass().add("clear-button-container");

    // Labels
    topBarItemName.getStyleClass().add("static-label");
    topBarItemCategory.getStyleClass().add("static-label");
    topBarItemQuantity.getStyleClass().add("static-label");
    topBarItemUnit.getStyleClass().add("static-label");
    topBarItemCheckBox.getStyleClass().add("static-label");
    bottomBarTotalPrice.getStyleClass().add("static-label");
    bottomBarTotalQuantity.getStyleClass().add("static-label");

    // Buttons
    clearAllButton.getStyleClass().addAll("red-button", "clear-button");
    clearCompletedButton.getStyleClass().addAll("red-button", "clear-button");
  }


  /**
   * Method to add a new shoppingList item to the database and the shopping list.
   * <p>Uses the {@link ShoppingListItemRegister#addToShoppingList(int, int, String) addToShoppingList} method to add the item to the database.</p>
xs   * @param item the item to be added
   */
  public void addItem() {
    AddPopup addPopup = new AddPopup("ShoppingListItem");
    addPopup.show(this.getScene().getWindow());

    ItemRegister itemRegister = new ItemRegister(new DAO(new DBConnectionProvider()));
    itemRegister.getAllItems();

    addPopup.addField(Field.ofMap("Item", itemRegister.getItems()));
    addPopup.addField(Field.ofNumber("Quantity"));
    addPopup.addField(Field.ofString("Unit"));

    addPopup.setOnAdd((Object[] o) -> {
      Object[] item_id_as_list = (Object[]) o[0];

      itemRegister.getAllItems();
      int item_id = (int) item_id_as_list[0];
      int quantity = (int) o[1];
      String unit = (String) o[2];
      try {
        ShoppingListItemRegister shoppingListItemRegister = new ShoppingListItemRegister(new DAO(new DBConnectionProvider()));
        shoppingListItemRegister.addToShoppingList(item_id, quantity, unit);
        shoppingListItemRegister.getAllItems();
        items = shoppingListItemRegister.getItems();
      } catch (Exception e) {
        Logger.fatal("Failed to add item");
        e.printStackTrace();
      }
      loadShoppingList();
    });
  }

  public void updateShoppingListItem(Object[] values) {
    // get the register
    ShoppingListItemRegister shoppingListItemRegister = new ShoppingListItemRegister(new DAO(new DBConnectionProvider()));
    // get all items
    shoppingListItemRegister.getAllItems();

    // get the values
    int shoppingListItemId = (int) values[0]; // id of the shopping list item
    ShoppingListItem shoppingListItem = shoppingListItemRegister.getShoppingListItemById(shoppingListItemId); // shopping list item

    // Item id
    Object[] item_id_as_list = (Object[]) values[1]; // id of the item as a list
    int item_id = -1; // id of the item

    if (item_id_as_list == null) {
      item_id = shoppingListItem.getItemId();
    } else item_id = (int) item_id_as_list[0];

    // Quantity
    int quantity = -1; // quantity of the item

    if (values[2] == null) {
      quantity = shoppingListItem.getQuantity();
    } else quantity = (int) values[2];

    // Unit
    String unit = null; // unit of the item

    if (values[3] == null) {
      Logger.info("Unit is null");
      unit = shoppingListItem.getUnit();
    } else unit = (String) values[3];

    shoppingListItemRegister.updateShoppingListItem(
            shoppingListItemId,
            item_id,
            quantity,
            unit
    );
    shoppingListItemRegister.getAllItems();
    items = shoppingListItemRegister.getItems();
    loadShoppingList();
  }

  public void deleteShoppingListItem(int shoppingListItemId) {
    ShoppingListItemRegister shoppingListItemRegister = new ShoppingListItemRegister(new DAO(new DBConnectionProvider()));
    shoppingListItemRegister.getAllItems();
    shoppingListItemRegister.deleteFromShoppingList(shoppingListItemId);
    shoppingListItemRegister.getAllItems();
    items = shoppingListItemRegister.getItems();
    loadShoppingList();
  }

  /**
   * Method to search for items in the shopping list.
   *
   * @param query string to search for
   */
  public void fullSearch(String query) {
    search(query);
    this.requestFocus();
  }

  /**
   * Method to search for items in the shopping list updates search on query change.
   * @param query string to search for
   */
  public void search(String query) {
    ShoppingListItemRegister register = new ShoppingListItemRegister(new DAO(new DBConnectionProvider()));
    register.searchItemsByName(query);
    items = register.getItems();
    if(query.isEmpty()) {
      register.getAllItems();
      items = register.getItems();
      loadShoppingList();
    }
    else {
      loadShoppingList();
    }
  }

  /**
   * Method to update the page.
   * <p>Clears the displayed list and fills it with new items</p>
   */
  private void loadShoppingList() {
    shoppingListContainer.getChildren().clear();

    items.values().forEach(item -> {
      ShoppingListItemPane shoppingListItemPane = new ShoppingListItemPane(item);
      shoppingListItemPane.setOnMouseClicked(v -> {
        ShoppingListItemPopup shoppingListItemPopup = new ShoppingListItemPopup(item);
        shoppingListItemPopup.setOnSave(this::updateShoppingListItem);
        shoppingListItemPopup.setOnDelete(this::deleteShoppingListItem);
        shoppingListItemPopup.show(this.getScene().getWindow());
      });


      shoppingListItemPane.getStyleClass().add("shopping-list-item-pane");
      shoppingListContainer.getChildren().add(shoppingListItemPane);
//      shoppingListScrollPane.setContent(shoppingListContainer);
    });


    staticListBottomBar.getChildren().clear();
    int itemCount = items.size();
    int totalPrice = 0;

    Label bottomBarTotalQuantity = new Label("Total quantity: " + itemCount);
    Label bottomBarTotalPrice = new Label("Total price: " + totalPrice);

    bottomBarTotalQuantity.getStyleClass().add("static-label");
    bottomBarTotalPrice.getStyleClass().add("static-label");

    staticListBottomBar.getChildren().addAll(bottomBarTotalQuantity, bottomBarTotalPrice);

  }
}

