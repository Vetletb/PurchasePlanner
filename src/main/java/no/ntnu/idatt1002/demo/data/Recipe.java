package no.ntnu.idatt1002.demo.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a recipe with a name, category and cooking time.
 */
public class Recipe implements Storable {
  private int recipe_id;
  private String name;
  private String category;
  private int cooking_time;

  /**
   * Constructor for the Recipe class.
   * @param name the name of the recipe
   * @param category the category of the recipe
   * @param cooking_time the cooking time of the recipe
   */
  public Recipe(String name, String category, int cooking_time) {
      this.name = name;
      this.cooking_time = cooking_time;
      this.category = category;
  }

  /**
   * Constructor for the Recipe class.
   * @param recipe_id
   * @param name
   * @param category
   * @param cooking_time
   */
  public Recipe(int recipe_id, String name, String category, int cooking_time) {
      this(name, category, cooking_time);
      this.recipe_id = recipe_id;
  }

  /**
   * Returns the attributes of the recipe.
   * @return the attributes of the recipe
   */
  @Override
  public List<String> getAttributes() {
      List<String> attributes = new ArrayList<>();
      attributes.add(name);
      attributes.add(Integer.toString(cooking_time));
      attributes.add(category);
      return attributes;
  }

  /**
   * Returns the attribute names of the recipe.
   * @return the attribute names of the recipe
   */
  @Override
  public List<String> getAttributeNames() {
      List<String> attributes = new ArrayList<>();
      attributes.add("name");
      attributes.add("cooking_time");
      attributes.add("category");
      return attributes;
  }

  /**
   * Returns the id of the recipe.
   * @return the id of the recipe
   */
  @Override
  public int getId() {
      return recipe_id;
  }

  /**
   * Returns the name of the id.
   * @return the name of the id
   */
  @Override
  public String getIdName() {
      return "recipe_id";
  }

  /**
   * Returns the recipe id.
   * @return the recipe id
   */
  public int getRecipe_id() {
      return recipe_id;
  }

  /**
   * Returns the name of the recipe.
   * @return the name of the recipe
   */
  public String getName() {
      return name;
  }

  /**
   * Returns the category of the recipe.
   * @return the category of the recipe
   */
  public String getCategory() {
      return category;
  }

  /**
   * Returns the cooking time of the recipe.
   * @return the cooking time of the recipe
   */
  public int getCooking_time() {
      return cooking_time;
  }
}
