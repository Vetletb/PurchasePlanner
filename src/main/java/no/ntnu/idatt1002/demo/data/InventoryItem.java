package no.ntnu.idatt1002.demo.data;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1002.demo.util.VerifyInput;

/**
 * This class is a simple bean for an inventory item.
 * This is an item with a quantity, a unit and an expiration date.
 */
public class InventoryItem extends ShoppingListItem {
  private int inventoryId;
  private final int expirationDate;

  /**
   * Constructor for the InventoryItem class.
   *
   * @param name           the name of the item
   * @param category       the category of the item
   * @param allergy        the allergy of the item
   * @param quantity       the quantity of the item
   * @param unit           the unit of the item
   * @param expirationDate the expiration date of the item
   */
  public InventoryItem(
      int itemId, String name, String category, String allergy,
      int quantity, String unit, int expirationDate) {
    super(itemId, name, category, allergy, quantity, unit);
    VerifyInput.verifyDateZeroAndMinusOneAccepted(expirationDate,
        "expirationDate");
    this.expirationDate = expirationDate;
  }

  /**
   * Constructor for the InventoryItem class.
   *
   * @param inventoryId    the id of the inventory item
   * @param itemId         the id of the item
   * @param name           the name of the item
   * @param category       the category of the item
   * @param allergy        the allergy of the item
   * @param quantity       the quantity of the item
   * @param unit           the unit of the item
   * @param expirationDate the expiration date of the item
   */
  public InventoryItem(
      int inventoryId, int itemId, String name, String category,
      String allergy, int quantity, String unit, int expirationDate) {
    this(itemId, name, category, allergy, quantity, unit, expirationDate);
    this.inventoryId = inventoryId;
  }

  /**
   * Returns the attributes of the inventory item.
   *
   * @return the attributes of the inventory item
   */
  @Override
  public List<String> getAttributes() {
    List<String> attributes = new ArrayList<>();
    attributes.add(Integer.toString(super.getItemId()));
    attributes.add(Integer.toString(super.getQuantity()));
    attributes.add(super.getUnit());
    attributes.add(Integer.toString(expirationDate));
    return attributes;
  }

  /**
   * Returns the attribute names of the inventory item.
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
   * Returns the id of the inventory item.
   *
   * @return the id of the inventory item
   */
  @Override
  public int getId() {
    return getInventoryId();
  }

  /**
   * Returns the name of the id.
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
  public int getInventoryId() {
    return inventoryId;
  }
}
