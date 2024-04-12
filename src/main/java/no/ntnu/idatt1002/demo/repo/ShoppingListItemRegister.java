package no.ntnu.idatt1002.demo.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.data.ShoppingListItem;
import no.ntnu.idatt1002.demo.util.VerifyInput;

/**
 * This class represents a register for shopping list items.
 * Allowing for communication between the database and the user interface.
 */
public class ShoppingListItemRegister {
  private List<ShoppingListItem> shoppingListItems;
  private final DAO dao;

  public ShoppingListItemRegister(DAO dao) {
    VerifyInput.verifyNotNull(dao, "dao");
    this.dao = dao;
    this.shoppingListItems =  new ArrayList<>();
  }

  /**
   * This method returns the items in the register in the form of lists.
   *
   * @return the items in the register as lists of strings
   */
  public Map<Integer, ShoppingListItem> getItems() {
    return shoppingListItems.stream()
        .collect(Collectors.toMap(ShoppingListItem::getId, item -> item));
  }


  /**
   * Gets all shopping list items from the database and
   * packages them into a list of shopping list items.
   */
  public void getAllItems() {
    shoppingListItems = new ArrayList<>();
    List<List<String>> items = dao.getAllFromTable("ShoppingListItem", "Item", "item_id");
    Logger.debug("Item count: " + Integer.toString(items.size())); //TODO: REMOVE THIS LATER
    Logger.debug("List of Items: " + items.toString());
    packageToShoppingListItem(items);
    Logger.info(Integer.toString(shoppingListItems.size()));
  }

  /**
   * Helper method to package the shopping list items into a list of shopping list items.
   *
   * @param items the items to package
   */
  private void packageToShoppingListItem(List<List<String>> items) {
    for (List<String> item : items) {
      List<List<String>> matchingItem = dao.filterFromTable(
          "Item", "item_id", item.get(1), null, null);
      Logger.warning("Package Item: " + item.toString() + " Matching Item: "
          + matchingItem.toString());
      shoppingListItems.add(new ShoppingListItem(
              Integer.parseInt(item.get(0)), // shoppingListItem_id
              Integer.parseInt(item.get(1)), // item_id
              matchingItem.get(0).get(1),    // name
              matchingItem.get(0).get(2),    // category
              matchingItem.get(0).get(3),    // allergy
              Integer.parseInt(item.get(2)), // quantity
              item.get(3)));                 // unit
    }
  }

  /**
   * Method for adding a shopping list item to the database.
   *
   * @param item_id the id of the item
   * @param quantity the quantity of the item
   * @param unit the unit of the item
   */
  public void addToShoppingList(int item_id,  int quantity, String unit) {
    dao.addToDatabase(new ShoppingListItem(item_id, "dummy", "dummy", "dummy", quantity, unit));
    Logger.info("Added item to shopping list with id "
        + item_id + ", quantity " + quantity + " and unit " + unit + "."); //TODO remove
  }

  /**
   * Method for deleting a shopping list item from the database.
   *
   * @param id the id of the shopping list item to delete
   * @throws IllegalArgumentException if the shopping list item with the given id does not exist
   */
  public void deleteFromShoppingList(int id) {
    int index = getIndexFromId(id);
    if (index == -1) {
      throw new IllegalArgumentException("Shopping list item with id " + id + " does not exist.");
    }
    dao.deleteFromDatabase(shoppingListItems.get(index));
  }

  /**
   * Helper method to get the index of the shopping list item with the given id.
   *
   * @param id the id of the shopping list item
   * @return the index of the shopping list item with the given id
   * @throws IllegalArgumentException if the id is less than 1
   */
  private int getIndexFromId(int id) {
    VerifyInput.verifyPositiveNumberMinusOneNotAccepted(id, "id");
    for (ShoppingListItem item : shoppingListItems) {
      if (item.getId() == id) {
        return shoppingListItems.indexOf(item);
      }
    }
    return -1;
  }

  /**
   * Method for updating a shopping list item in the database.
   *
   * @param id the id of the shopping list item to update
   * @param quantity the quantity of the item
   * @param unit the unit of the item
   * @throws IllegalArgumentException if the shopping list item with the given id does not exist
   */
  public void updateShoppingListItem(int id, int quantity, String unit) {
    if (getIndexFromId(id) == -1) {
      throw new IllegalArgumentException("Shopping list item with id " + id + " does not exist.");
    }
    dao.updateDatabase(new ShoppingListItem(
        id, "dummy", "dummy", "dummy", quantity, unit));
  }

  /**
   * Method to search for items in the shopping list by name.
   * <p>Uses the {@link #packageToShoppingListItem(List) packageToShoppingListItem}
   * to create ShoppingListItem instances from the returned list of data from the
   * {@link DAO#searchFromTable(String, String, String, String) DAO.searchFromTable}</p>
   *
   * @param name the name of the item to search for
   * @throws IllegalArgumentException if the name is empty
   */
  public void searchItemsByName(String name) {
    VerifyInput.verifyNotEmpty(name, "name");
    shoppingListItems = new ArrayList<>();
    List<List<String>> items = dao.searchFromTable("ShoppingListItem", name, "Item", "item_id");
    items.forEach(item -> Logger.warning("Searched Item: " + item.toString()));
    packageToShoppingListItem(items);
  }
}
