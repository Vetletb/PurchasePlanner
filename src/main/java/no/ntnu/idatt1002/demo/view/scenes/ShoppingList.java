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
import no.ntnu.idatt1002.demo.view.components.CheckBoxButton;
import no.ntnu.idatt1002.demo.view.components.Icon;
import no.ntnu.idatt1002.demo.view.components.ListHeader;

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

    // Add the containers to the content container
    innerContentContainer.getChildren().addAll(staticListTopBar, shoppingListScrollPane, staticListBottomBar);
    contentContainer.getChildren().add(innerContentContainer);

    // Create the list header and add to the page
    ListHeader shoppingListHeader = new ListHeader();
    shoppingListHeader.setOnSearch(this::fullSearch);
    shoppingListHeader.setOnSearchQueryChange(this::search);


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
    items.values().forEach(this::addItemToList);


    // CSS styling
    contentContainer.getStyleClass().add("centered");
    shoppingListContainer.getStyleClass().addAll("shopping-list");
    innerContentContainer.getStyleClass().add("shopping-list");
    staticListTopBar.getStyleClass().addAll("static-label-container", "rounded-top");
    staticListBottomBar.getStyleClass().addAll("static-bottom-label-container", "rounded-bottom");

    // Labels
    topBarItemName.getStyleClass().add("static-label-container-label");
    topBarItemCategory.getStyleClass().add("static-label-container-label");
    topBarItemQuantity.getStyleClass().add("static-label-container-label");
    topBarItemUnit.getStyleClass().add("static-label-container-label");
    topBarItemCheckBox.getStyleClass().add("static-label-container-label");
    bottomBarTotalPrice.getStyleClass().add("static-label-container-label");
    bottomBarTotalQuantity.getStyleClass().add("static-label-container-label");
  }

  /**
   * This method adds an item to the shopping list.
   * @param item the item to be added
   */
  public void addItemToList(ShoppingListItem item) {
    // Item Container
    HBox shoppingListItemContainer = new HBox();

    // item Labels
    Label itemName = new Label(item.getName());
    Label itemCategory = new Label(item.getCategory());
    Label itemQuantity = new Label(Integer.toString(item.getQuantity()));
    Label itemUnit = new Label(item.getUnit());

    // Create the checkbox button
    CheckBoxButton checkBoxButton = new CheckBoxButton(new Icon("checkboxUnSelected"));
    checkBoxButton.setScaleX(0.6);
    checkBoxButton.setScaleY(0.6);

    // Add the labels and the checkbox button to the container
    shoppingListItemContainer.getChildren().addAll(itemName, itemCategory, itemQuantity, itemUnit, checkBoxButton);

    // CSS styling
    shoppingListItemContainer.getStyleClass().add("shopping-list-item-container");
    // Labels
    itemName.getStyleClass().add("shopping-list-text");
    itemCategory.getStyleClass().add("shopping-list-text");
    itemQuantity.getStyleClass().add("shopping-list-text");
    itemUnit.getStyleClass().add("shopping-list-text");

    // Add the item to the shopping list
    Logger.debug("Adding item to shopping list");
    shoppingListContainer.getChildren().add(shoppingListItemContainer);
  }

  /**
   * Method to add a new shoppingList item to the database and the shopping list.
   * <p>Uses the {@link ShoppingListItemRegister#addToShoppingList(int, int, String) addToShoppingList} method to add the item to the database.</p>
   * <p>Uses the {@link ShoppingList#addItemToList(ShoppingListItem) addItemToList} method to add the item to the shopping list.</p>
   * @param item the item to be added
   */
  public void addItem(ShoppingListItem item) {
    ShoppingListItemRegister register = new ShoppingListItemRegister(new DAO(new DBConnectionProvider()));
    register.addToShoppingList(item.getItemId(), item.getQuantity(), item.getUnit());
    addItemToList(item);
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
    if(!query.isEmpty()) {
      updatePage();
    }
    else {
      register.getAllItems();
      items = register.getItems();
      updatePage();
    }
  }

  /**
   * Method to update the page.
   * <p>Clears the displayed list and fills it with new items</p>
   */
  private void updatePage() {
    shoppingListContainer.getChildren().clear();
    items.values().forEach(this::addItemToList);
  }

}

