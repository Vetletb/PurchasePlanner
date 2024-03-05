package no.ntnu.idatt1002.demo.data;

/**
 * This class is a simple bean for an item.
 */
public class Item {
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
    this.item_id = item_id;
    this.name = name;
    this.category = category;
    this.allergy = allergy;
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