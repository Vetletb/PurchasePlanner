package no.ntnu.idatt1002.demo.data;

import no.ntnu.idatt1002.demo.util.VerifyInput;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a simple bean for a Shopping list item. This is an item with a quantity and a unit.
 */

public class ShoppingListItem extends Item {
  private int ShoppingListItem_id;
  private final int quantity;
  private final String unit;

  /**
   * Constructor for the ShoppingListItem class.
   * When creating a new ShoppingListItem, it is required that there is an
   * item in the database with the same name, category and allergy.
   *
   * @param name the name of the item
   * @param category the category of the item
   * @param allergy the allergy of the item
   * @param quantity the quantity of the item
   * @param unit the unit of the item
   */
  public ShoppingListItem(int item_id,
                          String name,
                          String category,
                          String allergy,
                          int quantity,
                          String unit) {
    super(item_id, name, category, allergy);
    VerifyInput.verifyPositiveNumberMinusOneNotAccepted(quantity, "quantity");
    VerifyInput.verifyNotEmpty(unit, "unit");
    this.quantity = quantity;
    this.unit = unit;
  }

  /**
   * Constructor for the ShoppingListItem class.
   * When creating a ShoppingListItem from the database, the id is known.
   *
   * @param ShoppingListItem_id the id of the ShoppingListItem
   * @param item_id the id of the item
   * @param name the name of the item
   * @param category  the category of the item
   * @param allergy the allergy of the item
   * @param quantity the quantity of the item
   * @param unit the unit of the item
   */
  public ShoppingListItem(int ShoppingListItem_id,
                          int item_id,
                          String name,
                          String category,
                          String allergy,
                          int quantity,
                          String unit) {
    this(item_id, name, category, allergy, quantity, unit);
    this.ShoppingListItem_id = ShoppingListItem_id;
  }

  /**
   * Returns the attributes of the shopping list item as saved in the database.
   *
   * @return the attributes of the quantity item
   */
  @Override
  public List<String> getAttributes() {
    List<String> attributes = new ArrayList<>();
    attributes.add(Integer.toString(super.getItem_id()));
    attributes.add(Integer.toString(quantity));
    attributes.add(unit);
    return attributes;
  }

  /**
   * Returns the attribute names of the shopping list item.
   *
   * @return the attribute names of the quantity item
   */
  @Override
  public List<String> getAttributeNames() {
    List<String> attributes = new ArrayList<>();
    attributes.add("item_id");
    attributes.add("quantity");
    attributes.add("unit");
    return attributes;
  }

  /**
   * Returns the id of the shopping list item.
   *
   * @return the id of the shopping list item
   */
  @Override
  public int getId() {
    return ShoppingListItem_id;
  }

  /**
   * Returns the id of the item
   *
   * @return the id of the item
   */
  public int getItemId() {
    return super.getItem_id();
  }

  /**
   * Returns the name of the id.
   *
   * @return the name of the id
   */
  @Override
  public String getIdName() {
    return "shoppinglistitem_id";
  }

  /**
   * Getter method for the quantity of the shopping list item.
   *
   * @return the quantity of the quantity item
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Getter method for the unit of the shopping list item.
   *
   * @return the unit of the quantity item
   */
  public String getUnit() {
    return unit;
  }
}
