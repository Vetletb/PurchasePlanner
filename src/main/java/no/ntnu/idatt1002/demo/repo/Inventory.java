package no.ntnu.idatt1002.demo.repo;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.data.InventoryItem;

/**
 * This class represents a register for inventory items. Allowing for communication between the database and the user interface.
 */
public class Inventory {
  private List<InventoryItem> inventoryItems;
  private final DAO dao;

  /**
   * Constructor for the Inventory class.
   * @param dao the database access object
   */
  public Inventory(DAO dao) {
    this.dao = dao;
    this.inventoryItems = new ArrayList<>();
  }

  /**
   * This method returns the inventory items in the register in the form of lists.
   * @return the inventory items in the register as lists of strings
   */
  public List<InventoryItem> getInventoryItems() {
    return inventoryItems;
  }

  /**
   * This method retrieves all inventory items from the database.
   */
  public void getAllInventroyItems() {
    inventoryItems = new ArrayList<>();
    List<List<String>> items = dao.getAllFromTable("InventoryItem", "item", "item_id");
    packageToInventoryItems(items);
  }

  /**
   * This method retrieves filtered inventory items by category from the database.
   * @param category the category to filter by
   */
  public void filterInventoryByCategory(String category) {
    inventoryItems = new ArrayList<>();
    List<List<String>> inventoryItems = dao.filterFromTable("InventoryItem", "category", category, "item", "item_id");
    packageToInventoryItems(inventoryItems);
  }

  /**
   * This method searches for inventory items by name and retrieves them from the database.
   * @param name the name to search by
   */
  public void searchInventoryByName(String name) {
    inventoryItems = new ArrayList<>();
    List<List<String>> items = dao.searchFromTable("InventoryItem", name, "item", "item_id");
    packageToInventoryItems(items);
  }

  /**
   * This method packages lists of lists representing inventory items into InventoryItem objects.
   * @param inventoryItems the list of lists representing inventory items
   */
  private void packageToInventoryItems(List<List<String>> inventoryItems) {
    for (List<String> inventoryItem : inventoryItems) {
      this.inventoryItems.add(new InventoryItem(
          Integer.parseInt(inventoryItem.get(0)),
          Integer.parseInt(inventoryItem.get(2)),
          inventoryItem.get(6),
          inventoryItem.get(7),
          inventoryItem.get(8),
          Integer.parseInt(inventoryItem.get(3)),
          inventoryItem.get(4),
          Integer.parseInt(inventoryItem.get(1))
      ));
    }
  }

  /**
   * This method adds a new inventory item to the database.
   * @param item_id the id of the item
   * @param quantity the quantity of the item
   * @param unit the unit of the item
   * @param expirationDate the expiration date of the item
   */
  public void addInventoryItem(int item_id, int quantity, String unit, int expirationDate) {
    dao.addToDatabase(new InventoryItem(item_id, null, null, null, quantity, unit, expirationDate));
  }

  /**
   * This method retrieves the index of an inventory item by its id.
   * @param id the id of the inventory item
   * @return the index of the inventory item
   */
  private int getIndexFromId(int id) {
    for (int i = 0; i < inventoryItems.size(); i++) {
      if (inventoryItems.get(i).getId() == id) {
        return i;
      }
    }
    return -1;
  }

  /**
   * This method deletes an inventory item from the database.
   * @param id the id of the inventory item to delete
   */
  public void deleteInventoryItem(int id) {
    int index = getIndexFromId(id);
    dao.deleteFromDatabase(inventoryItems.get(index));
  }

  /**
   * This method updates an inventory item in the database.
   * @param inventory_id the id of the inventory item
   * @param item_id the id of the item
   * @param quantity the quantity of the item
   * @param unit the unit of the item
   * @param expirationDate the expiration date of the item
   */
   public void updateInventoryItem(int inventory_id, int item_id, int quantity, String unit, int expirationDate) {
    InventoryItem inventoryItem = new InventoryItem(inventory_id, item_id, null, null, null, quantity, unit, expirationDate);
    dao.updateDatabase(inventoryItem);
  }
}
