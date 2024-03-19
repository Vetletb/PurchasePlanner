package no.ntnu.idatt1002.demo.repo;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.data.ShoppingListItem;

/**
 * This class represents a register for shopping list items.
 * Allowing for communication between the database and the user interface.
 */
public class ShoppingList {
  private List<ShoppingListItem> shoppingListItems;
  private final DAO dao;

  public ShoppingList(DAO dao) {
    this.dao = dao;
    this.shoppingListItems =  new ArrayList<>();
  }

  /**
   * This method retrieves all the shopping list items from the register and returns them as a list.
   *
   * @return the shopping list items in the register as a map
   */
  public List<ShoppingListItem> getShoppingListItems() {
    return shoppingListItems;
  }


  /**
   * Gets all shopping list items from the database and
   * packages them into a list of shopping list items.
   */
  public void getAllShoppingListItems() {
    shoppingListItems = new ArrayList<>();
    List<List<String>> items = dao.getAllFromTable("ShoppingListItem", "Item", "item_id");
    packageToShoppingListItem(items);
  }

  /**
   * Helper method to package the shopping list items into a list of shopping list items.
   *
   * @param items the items to package
   */
  private void packageToShoppingListItem(List<List<String>> items) {
    for (List<String> item : items) {
      shoppingListItems.add(new ShoppingListItem(
          Integer.parseInt(item.get(0)),
          Integer.parseInt(item.get(1)),
          item.get(5),
          item.get(6),
          item.get(7),
          Integer.parseInt(item.get(2)),
          item.get(3)));
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
    dao.addToDatabase(new ShoppingListItem(item_id, null, null, null, quantity, unit));
  }

  /**
   * Method for deleting a shopping list item from the database.
   *
   * @param id the id of the shopping list item to delete
   */
  public void deleteFromShoppingList(int id) {
    int index = getShoppingListItemFromId(id);
    dao.deleteFromDatabase(shoppingListItems.get(index));
  }

  /**
   * Helper method to get the index of the shopping list item with the given id.
   *
   * @param id the id of the shopping list item
   * @return the index of the shopping list item with the given id
   */
  private int getShoppingListItemFromId(int id) {
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
   * @param item_id the id of the item
   * @param name the name of the item
   * @param category the category of the item
   * @param allergy the allergy of the item
   * @param quantity the quantity of the item
   * @param unit the unit of the item
   */
  public void updateShoppingListItem(int id, int item_id, String name, String category,
                                     String allergy, int quantity, String unit) {
    dao.updateDatabase(new ShoppingListItem(id, item_id, name, category, allergy, quantity, unit));
  }

}
