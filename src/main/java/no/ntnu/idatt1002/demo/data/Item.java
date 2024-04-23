package no.ntnu.idatt1002.demo.data;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1002.demo.util.VerifyInput;

/**
 * This class is a simple bean for an item.
 */
public class Item implements Storable {
  private int itemId;
  private final String name;
  private final String category;
  private final String allergy;

  /**
   * Constructor for the Item class. When creating a new item, the id is not
   * known.
   *
   * @param name     the name of the item
   * @param category the category of the item
   * @param allergy  the allergy of the item
   */
  public Item(String name, String category, String allergy) {
    VerifyInput.verifyNotEmpty(name, "name");
    VerifyInput.verifyNotEmpty(category, "category");
    this.name = name;
    this.category = category;
    this.allergy = allergy;
  }

  /**
   * Constructor for the Item class. When creating an item from the database, the
   * id is known.
   *
   * @param itemId   the id of the item
   * @param name     the name of the item
   * @param category the category of the item
   * @param allergy  the allergy of the item
   */
  public Item(int itemId, String name, String category, String allergy) {
    this(name, category, allergy);
    this.itemId = itemId;
  }

  /**
   * Returns the attributes of the item.
   *
   * @return the attribute names of the item
   */
  @Override
  public List<String> getAttributes() {
    List<String> attributes = new ArrayList<>();
    attributes.add(name);
    attributes.add(category);
    attributes.add(allergy);
    return attributes;
  }

  /**
   * Returns the attribute names of the item.
   *
   * @return the attribute names of the item
   */
  @Override
  public List<String> getAttributeNames() {
    List<String> attributes = new ArrayList<>();
    attributes.add("name");
    attributes.add("category");
    attributes.add("allergy");
    return attributes;
  }

  /**
   * Returns the id of the item.
   *
   * @return the id of the item
   */
  @Override
  public int getId() {
    return getItemId();
  }

  /**
   * Returns the name of the id.
   *
   * @return the name of the id
   */
  @Override
  public String getIdName() {
    return "item_id";
  }

  /**
   * Returns the id of the item.
   *
   * @return the id of the item
   */
  public int getItemId() {
    return itemId;
  }

  /**
   * Returns the name of the item.
   *
   * @return the name of the item
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the category of the item.
   *
   * @return the category of the item
   */
  public String getCategory() {
    return category;
  }

  /**
   * Returns the allergy of the item.
   *
   * @return the allergy of the item
   */
  public String getAllergy() {
    return allergy;
  }

  /**
   * Returns a string representation of the item.
   *
   * @return a string representation of the item
   */
  @Override
  public String toString() {
    return "(" + itemId + "), " + name;
  }
}