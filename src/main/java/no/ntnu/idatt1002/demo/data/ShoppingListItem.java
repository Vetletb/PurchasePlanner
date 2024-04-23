package no.ntnu.idatt1002.demo.data;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1002.demo.util.VerifyInput;

/**
 * This class is a simple bean for a Shopping list item. This is an item with a
 * quantity and a unit.
 */

public class ShoppingListItem extends Item {
  private int shoppingListItemId;
  private final int quantity;
  private final String unit;

  /**
   * Constructor for the ShoppingListItem class.
   * When creating a new ShoppingListItem, it is required that there is an
   * item in the database with the same name, category and allergy.
   *
   * @param name     the name of the item
   * @param category the category of the item
   * @param allergy  the allergy of the item
   * @param quantity the quantity of the item
   * @param unit     the unit of the item
   */
  public ShoppingListItem(int itemId,
      String name,
      String category,
      String allergy,
      int quantity,
      String unit) {
    super(itemId, name, category, allergy);
    VerifyInput.verifyPositiveNumberMinusOneNotAccepted(quantity, "quantity");
    VerifyInput.verifyNotEmpty(unit, "unit");
    this.quantity = quantity;
    this.unit = unit;
  }

  /**
   * Constructor for the ShoppingListItem class.
   * When creating a ShoppingListItem from the database, the id is known.
   *
   * @param shoppingListItemId the id of the ShoppingListItem
   * @param itemId             the id of the item
   * @param name               the name of the item
   * @param category           the category of the item
   * @param allergy            the allergy of the item
   * @param quantity           the quantity of the item
   * @param unit               the unit of the item
   */
  public ShoppingListItem(int shoppingListItemId,
      int itemId,
      String name,
      String category,
      String allergy,
      int quantity,
      String unit) {
    this(itemId, name, category, allergy, quantity, unit);
    this.shoppingListItemId = shoppingListItemId;
  }

  /**
   * Returns the attributes of the shopping list item as saved in the database.
   *
   * @return the attributes of the quantity item
   */
  @Override
  public List<String> getAttributes() {
    List<String> attributes = new ArrayList<>();
    attributes.add(Integer.toString(super.getItemId()));
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
    return getShoppingListItemId();
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

  public int getShoppingListItemId() {
    return shoppingListItemId;
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
