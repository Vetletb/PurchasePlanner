package no.ntnu.idatt1002.demo.view.scenes;

import java.util.*;
import java.util.stream.Collectors;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.UpdateableScene;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.dao.DBConnectionProvider;
import no.ntnu.idatt1002.demo.data.*;
import no.ntnu.idatt1002.demo.repo.*;
import no.ntnu.idatt1002.demo.view.components.AddPopup;
import no.ntnu.idatt1002.demo.view.components.Field;
import no.ntnu.idatt1002.demo.view.components.ListHeader;
import no.ntnu.idatt1002.demo.view.components.PrimaryButton;
import no.ntnu.idatt1002.demo.view.components.ShoppingListItemPane;
import no.ntnu.idatt1002.demo.view.components.ShoppingListItemPopup;

/**
 * The inventory page.
 */
public class ShoppingList extends VBox implements UpdateableScene {

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

  // List of selected items
  List<Integer> selectedItemsId = new ArrayList<>();
  // List header sort
  String sortBy = "Name";
  String filterBy = "All";

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
    Label bottomBarTotalQuantity = new Label("Total quantity: 0");
    Label bottomBarTotalPrice = new Label("Total price: 0");

    // Add the labels to the top bar
    staticListTopBar.getChildren().addAll(
            topBarItemName,
            topBarItemCategory,
            topBarItemQuantity,
            topBarItemUnit,
            topBarItemCheckBox);

    // Add the labels to the bottom bar
    staticListBottomBar.getChildren().addAll(bottomBarTotalQuantity, bottomBarTotalPrice);

    // Scroll pane settings
    shoppingListScrollPane.setContent(shoppingListContainer);
    shoppingListScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    shoppingListScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

    // Clear all button and clear completed button container
    HBox clearButtonsContainer = new HBox();

    // Clear all button and clear completed button
    PrimaryButton clearAllButton = new PrimaryButton("Delete all");
    clearAllButton.setOnAction(e -> {
      ShoppingListItemRegister shoppingListItemRegister = new ShoppingListItemRegister(
          new DAO(new DBConnectionProvider()));
      shoppingListItemRegister.getAllItems();
      shoppingListItemRegister.clearShoppingList();
      items = shoppingListItemRegister.getItems();
      loadShoppingList();
    });

    PrimaryButton clearCompletedButton = new PrimaryButton("Clear completed");

    clearCompletedButton.setOnAction(e -> {
      checkForSelectedItems();
      ShoppingListItemRegister shoppingListItemRegister = new ShoppingListItemRegister(
          new DAO(new DBConnectionProvider()));
      shoppingListItemRegister.getAllItems();
      items = shoppingListItemRegister.getItems();

      Logger.warning("Removing selected items from the shopping list. List length: "
              + shoppingListContainer.getChildren().size() + " items selected: "
              + selectedItemsId.size() + " items in the list: " + items.size());
      shoppingListContainer.getChildren().forEach(node -> {
        ShoppingListItemPane shoppingListItemPane = (ShoppingListItemPane) node;

        if (shoppingListItemPane.isSelected()) {
          // Get the item
          ShoppingListItem item = shoppingListItemRegister.getShoppingListItemById(
                  shoppingListItemPane.getShoppingListItemId());

          Logger.warning("Removing item: " + item.getName() + " from the shopping list");
          // Add the item to the Inventory
          InventoryRegister inventoryRegister = new InventoryRegister(
                  new DAO(new DBConnectionProvider()));

          inventoryRegister.addInventoryItem(
                  item.getItemId(),
                  item.getQuantity(),
                  item.getUnit(),
                  20240430);

          // Remove the item from the shopping list
          shoppingListItemRegister.deleteFromShoppingList(item.getId());
          selectedItemsId.removeIf(id -> id == item.getId());
        }

        shoppingListItemRegister.getAllItems();
        items = shoppingListItemRegister.getItems();
      });
      loadShoppingList();
    });

    clearButtonsContainer.getChildren().addAll(
            clearAllButton,
            clearCompletedButton
    );

    // Add the containers to the content container
    innerContentContainer.getChildren().addAll(
            clearButtonsContainer,
            staticListTopBar,
            shoppingListScrollPane,
            staticListBottomBar);

    contentContainer.getChildren().add(innerContentContainer);

    // Create the list header and add to the page
    ListHeader shoppingListHeader = new ListHeader();
    shoppingListHeader.setOnSearch(this::fullSearch);
    shoppingListHeader.setOnSearchQueryChange(this::search);
    shoppingListHeader.setOnAdd(this::addItem);
    shoppingListHeader.setOnSortChange(this::sortBy);

    super.getChildren().addAll(shoppingListHeader, contentContainer);
    // TODO: Create a connection to the database for the add button
    // TODO remove test data after testing
    // ItemRegister itemRegister = new ItemRegister(new DAO(new
    // DBConnectionProvider()));
    // itemRegister.getAllItems();
    // Map<Integer, Item> inventoryItems = itemRegister.getItems();
    // // Add the items to the shopping list TODO fix the unit to match the item
    // inventoryItems.values().forEach(item -> addItem(new
    // ShoppingListItem(item.getId(), item.getName(), item.getCategory(),
    // item.getAllergy(), 1, "kg")));

    // Initialize the itemsList
    ShoppingListItemRegister shoppingListItemRegister = new ShoppingListItemRegister(
        new DAO(new DBConnectionProvider()));
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
   * <p>
   * Uses the {@link ShoppingListItemRegister#addToShoppingList(int, int, String)
   * addToShoppingList} method to add the item to the database.
   * </p>
   * xs * @param item the item to be added
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
      Object[] itemIdAsList = (Object[]) o[0];

      itemRegister.getAllItems();
      int itemId = (int) itemIdAsList[0];
      int quantity = (int) o[1];
      String unit = (String) o[2];
      try {
        ShoppingListItemRegister shoppingListItemRegister = new ShoppingListItemRegister(
            new DAO(new DBConnectionProvider()));
        shoppingListItemRegister.addToShoppingList(itemId, quantity, unit);
        shoppingListItemRegister.getAllItems();
        items = shoppingListItemRegister.getItems();
      } catch (Exception e) {
        Logger.fatal("Failed to add item");
        e.printStackTrace();
      }
      loadShoppingList();
    });
  }

  /**
   * Method to update a shopping list item.
   * <p>
   * Uses the {@link ShoppingListItemRegister#updateShoppingListItem(int, int, int, String)
   * updateShoppingListItem} method to update the item in the database.
   * </p>
   *
   * @param values the values to update
   */
  public void updateShoppingListItem(Object[] values) {
    // get the register
    ShoppingListItemRegister shoppingListItemRegister = new ShoppingListItemRegister(
        new DAO(new DBConnectionProvider()));
    // get all items
    shoppingListItemRegister.getAllItems();

    // get the values
    int shoppingListItemId = (int) values[0]; // id of the shopping list item
    ShoppingListItem shoppingListItem = shoppingListItemRegister.getShoppingListItemById(
            shoppingListItemId); // shopping

    // Item id
    Object[] itemIdAsList = (Object[]) values[1]; // id of the item as a list
    int itemId = -1; // id of the item

    if (itemIdAsList == null) {
      itemId = shoppingListItem.getItemId();

    } else {
      itemId = (int) itemIdAsList[0];
    }

    // Quantity
    int quantity = -1; // quantity of the item

    if (values[2] == null) {
      quantity = shoppingListItem.getQuantity();

    } else {
      quantity = (int) values[2];
    }

    // Unit
    String unit = null; // unit of the item

    if (values[3] == null) {
      Logger.info("Unit is null");
      unit = shoppingListItem.getUnit();

    } else {
      unit = (String) values[3];
    }

    shoppingListItemRegister.updateShoppingListItem(
        shoppingListItemId,
        itemId,
        quantity,
        unit);
    shoppingListItemRegister.getAllItems();
    items = shoppingListItemRegister.getItems();
    loadShoppingList();
  }

  /**
   * Method to delete a shopping list item.

   * @param shoppingListItemId the id of the shopping list item to delete
   */
  public void deleteShoppingListItem(int shoppingListItemId) {
    ShoppingListItemRegister shoppingListItemRegister = new ShoppingListItemRegister(
        new DAO(new DBConnectionProvider()));
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
   * Method to search for items in the shopping list updates search on query
   * change.
   *
   * @param query string to search for
   */
  public void search(String query) {
    ShoppingListItemRegister register = new ShoppingListItemRegister(
            new DAO(new DBConnectionProvider()));

    register.searchItemsByName(query);
    items = register.getItems();
    if (query.isEmpty()) {
      register.getAllItems();
      items = register.getItems();
      loadShoppingList();
    } else {
      loadShoppingList();
    }
  }

  private void sortBy(String sortBy) {
    this.sortBy = sortBy;
    loadShoppingList();
  }

  /**
   * Method to switch the filter by.

   * @param filterBy the filter to switch by
   */
  private void filterBy(String filterBy) {
    this.filterBy = filterBy;
    loadShoppingList();
  }

  /**
   * Method to check for selected items.
   * <p>
   *   Clears the selected items list and adds the selected items to the list
   * </p>
   */
  private void checkForSelectedItems() {
    selectedItemsId.clear();
    shoppingListContainer.getChildren().forEach(node -> {
      ShoppingListItemPane shoppingListItemPane = (ShoppingListItemPane) node;
      if (shoppingListItemPane.isSelected()) {
        selectedItemsId.add(shoppingListItemPane.getShoppingListItemId());
      }
    });
  }

  /**
   * Method to update the page.
   * <p>
   * Clears the displayed list and fills it with new items
   * </p>
   */
  private void loadShoppingList() {
    checkForSelectedItems();
    shoppingListContainer.getChildren().clear();
    // Sort the items based on the sort by
    ShoppingListItemRegister shoppingListItemRegister = new ShoppingListItemRegister(
        new DAO(new DBConnectionProvider()));
    shoppingListItemRegister.getAllItems();
    items = shoppingListItemRegister.getItems();

    List<ShoppingListItem> sortedItems = new ArrayList<>(items.values());
    switch (sortBy) {
      case "Name":
        sortedItems.sort(Comparator.comparing(ShoppingListItem::getName));
        break;
      case "Date":
        sortedItems.sort(Comparator.comparing(ShoppingListItem::getQuantity));
        break;
      case "Type":
        sortedItems.sort(Comparator.comparing(ShoppingListItem::getCategory));
        break;
      default:
        break;
    }

    // Clear the shopping list container
    shoppingListContainer.getChildren().clear();

    sortedItems.forEach(item -> {
      ShoppingListItemPane shoppingListItemPane = new ShoppingListItemPane(item);
      shoppingListItemPane.setOnMouseClicked(v -> {
        ShoppingListItemPopup shoppingListItemPopup = new ShoppingListItemPopup(item);
        shoppingListItemPopup.setOnSave(this::updateShoppingListItem);
        shoppingListItemPopup.setOnDelete(this::deleteShoppingListItem);
        shoppingListItemPopup.show(this.getScene().getWindow());
      });

      if (selectedItemsId.contains(item.getId())) {
        shoppingListItemPane.setSelected();
      }

      shoppingListItemPane.getStyleClass().add("shopping-list-item-pane");
      shoppingListContainer.getChildren().add(shoppingListItemPane);
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

  public void updateScene() {
    getItemsToAddToList();
    loadShoppingList();
  }

  public VBox createScene() {
    return this;
  }

  private void getItemsToAddToList() {
    // Get all the necessary registers
    InventoryRegister inventoryRegister = new InventoryRegister(
            new DAO(new DBConnectionProvider()));
    ShoppingListItemRegister shoppingListItemRegister = new ShoppingListItemRegister(
            new DAO(new DBConnectionProvider()));
    EventRegister eventRegister = new EventRegister(
            new DAO(new DBConnectionProvider()));
    RecipeRegister recipeRegister = new RecipeRegister(
            new DAO(new DBConnectionProvider()));

    // Get the items from the database
    inventoryRegister.getAllInventroyItems();
    shoppingListItemRegister.getAllItems();
    eventRegister.getAllEvents();
    recipeRegister.getAllRecipes();

    // add the items to lists
    List<InventoryItem> inventoryItems = inventoryRegister.getInventoryItems();
    Map<Integer, ShoppingListItem> shoppingListItems = shoppingListItemRegister.getItems();
    Map<Integer, Event> eventItems = eventRegister.getEvents();
    Map<Integer, Recipe> recipeItems = recipeRegister.getRecipes();

    // Calculate the amount of each item in the inventory and shoppingList
    //    Map<Integer, Integer> existingItems = inventoryItems.stream()
    //            .collect(Collectors.toMap(
    //                    InventoryItem::getItemId,
    //                    InventoryItem::getQuantity,
    //                    Integer::sum // Merging function: sums the quantities of duplicate keys
    //            ));

    Map<Integer, QuantityUnit> existingItems = inventoryItems.stream()
          .collect(Collectors.toMap(
                    InventoryItem::getItemId, // Key mapper (Item ID)
                    item -> {
                      // Convert quantity to common unit and create QuantityUnit object
                      return convertQuantity(item.getUnit(), item.getQuantity());
                    },
                    // Merging function: add quantities of duplicate keys and keep the unit
                    (quantityUnit1, quantityUnit2) -> {
                      quantityUnit1.addQuantity(quantityUnit2.getQuantity());
                      return quantityUnit1;
                    }
          ));

    shoppingListItems.values().forEach(item -> {
      if (existingItems.containsKey(item.getItemId())) {
        QuantityUnit existingItem = convertQuantity(item.getUnit(), item.getQuantity());
        existingItems.get(item.getItemId()).addQuantity(existingItem.getQuantity());
      } else {
        QuantityUnit newItem = existingItems.put(
                item.getItemId(), convertQuantity(item.getUnit(), item.getQuantity()));
      }
    });

    Logger.warning("Existing items: " + existingItems.toString()); // TODO: REMOVE
    Map<Integer, QuantityUnit> neededItems = new HashMap<>();
    // Get the items needed from the events TODO: Fix this
//    eventItems.values().forEach(event -> {
//      recipeRegister.getRecipes().get(event.getRecipe_id()).getIngredients().forEach(ingredient -> {
//        if (neededItems.containsKey(ingredient.getItemId())) {
//          neededItems.get(ingredient.getItemId()).addQuantity(ingredient.getQuantity());
//        } else {
//          neededItems.put(ingredient.getItemId(), convertQuantity(ingredient.getUnit(), ingredient.getQuantity()));
//        }
//      });
//    });
    Logger.warning("Needed items: " + neededItems.toString()); // TODO: REMOVE
  }

  private static QuantityUnit convertQuantity(String unit, int quantity) {
    return switch (unit) {
      case "kg" -> new QuantityUnit(quantity * 1000, "g");
      case "hg" -> new QuantityUnit(quantity * 100, "g");
      case "l" -> new QuantityUnit(quantity * 1000, "ml");
      case "dl" -> new QuantityUnit(quantity * 10, "ml");
      case "pck" -> new QuantityUnit(quantity * 20, "pcs");
      default -> new QuantityUnit(quantity, unit);
    };
  }
}
