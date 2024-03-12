package no.ntnu.idatt1002.demo.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a simple bean for an item.
 */
public class Item implements Storable{
  private int item_id;
  private String name;
  private String category;
  private String allergy;

  /**
   * Constructor for the Item class. When creating a new item, the id is not known.
   * @param name the name of the item
   * @param category the category of the item
   * @param allergy the allergy of the item
   */
  public Item(String name, String category, String allergy) {
    this.name = name;
    this.category = category;
    this.allergy = allergy;
  }

  /**
   * Constructor for the Item class. When creating an item from the database, the id is known.
   * @param item_id the id of the item
   * @param name the name of the item
   * @param category the category of the item
   * @param allergy the allergy of the item
   */
  public Item(int item_id, String name, String category, String allergy) {
    this(name, category, allergy);
    this.item_id = item_id;
  }

  /**
   * This method returns the attribute names of the item.
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
   * This method returns the attribute names of the item.
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
   * This method returns the id of the item.
   * @return the id of the item
   */
  @Override
  public int getId() {
    return getItem_id();
  }

  /**
   * This method returns the name of the id.
   * @return the name of the id
   */
  @Override
  public String getIdName() {
    return "item_id";
  }

  /**
   * This method returns the id of the item.
   * @return the id of the item
   */
  public int getItem_id() {
    return item_id;
  }

  /**
   * This method returns the name of the item.
   * @return the name of the item
   */
  public String getName() {
    return name;
  }

  /**
   * This method returns the category of the item.
   * @return the category of the item
   */
  public String getCategory() {
    return category;
  }

  /**
   * This method returns the allergy of the item.
   * @return the allergy of the item
   */
  public String getAllergy() {
    return allergy;
  }
}