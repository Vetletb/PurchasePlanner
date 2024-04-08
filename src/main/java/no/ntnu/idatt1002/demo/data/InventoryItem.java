package no.ntnu.idatt1002.demo.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a simple bean for an inventory item. This is an item with a quantity, a unit and an expiration date.
 */
public class InventoryItem extends ShoppingListItem {
  private int inventory_id;
  private final int expirationDate;

  /**
   * Constructor for the InventoryItem class.
   *
   * @param name the name of the item
   * @param category the category of the item
   * @param allergy the allergy of the item
   * @param quantity the quantity of the item
   * @param unit the unit of the item
   * @param expirationDate the expiration date of the item
   */
  public InventoryItem(
      int item_id, String name, String category, String allergy,
      int quantity, String unit, int expirationDate) {
    super(item_id, name, category, allergy, quantity, unit);
    this.expirationDate = expirationDate;
  }

  /**
   * Constructor for the InventoryItem class.
   *
   * @param inventory_id the id of the inventory item
   * @param item_id the id of the item
   * @param name the name of the item
   * @param category the category of the item
   * @param allergy the allergy of the item
   * @param quantity the quantity of the item
   * @param unit the unit of the item
   * @param expirationDate the expiration date of the item
   */
  public InventoryItem(
      int inventory_id, int item_id, String name, String category,
      String allergy, int quantity, String unit, int expirationDate) {
    this(item_id, name, category, allergy, quantity, unit, expirationDate);
    this.inventory_id = inventory_id;
  }

  /**
   * This method returns the attributes of the inventory item.
   *
   * @return the attributes of the inventory item
   */
  @Override
  public List<String> getAttributes() {
    List<String> attributes = new ArrayList<>();
    attributes.add(Integer.toString(super.getItem_id()));
    attributes.add(Integer.toString(super.getQuantity()));
    attributes.add(super.getUnit());
    attributes.add(Integer.toString(expirationDate));
    return attributes;
  }

  /**
   * This method returns the attribute names of the inventory item.
   *
   * @return the attribute names of the inventory item
   */
  @Override
  public List<String> getAttributeNames() {
    List<String> attributes = new ArrayList<>();
    attributes.add("item_id");
    attributes.add("quantity");
    attributes.add("unit");
    attributes.add("expiration_date");
    return attributes;
  }

  /**
   * This method returns the id of the inventory item.
   *
   * @return the id of the inventory item
   */
  @Override
  public int getId() {
    return getInventory_id();
  }

  /**
   * This method returns the name of the id.
   *
   * @return the name of the id
   */
  @Override
  public String getIdName() {
    return "inventory_id";
  }

  /**
   * Getter method for the expiration date.
   *
   * @return the expiration date
   */
  public int getExpirationDate() {
    return expirationDate;
  }

  /**
   * Getter method for the inventory id.
   *
   * @return the inventory id
   */
  public int getInventory_id() {
    return inventory_id;
  }
}
