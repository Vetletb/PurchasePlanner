package no.ntnu.idatt1002.demo.data;

import no.ntnu.idatt1002.demo.util.VerifyInput;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a recipe ingredient connected to a recipe.
 */
public class RecipeIngredient extends ShoppingListItem{

  private int recipeIngredient_id;
  private int recipe_id;

  /**
   * Constructor for the RecipeIngredient class.
   * @param item_id the id of the item
   * @param name the name of the item
   * @param category the category of the item
   * @param allergy the allergy information of the item
   * @param quantity the quantity of the item
   * @param unit the unit of the item
   * @param recipe_id the id of the recipe
   */
  public RecipeIngredient(int item_id, String name, String category, String allergy, int quantity, String unit, int recipe_id) {
    super(item_id, name, category, allergy, quantity, unit);
    VerifyInput.verifyPositiveNumberZeroNotAccepted(recipe_id, "recipe_id");
    this.recipe_id = recipe_id;
  }

  /**
   * Constructor for the RecipeIngredient class with recipe ingredient id.
   * @param recipeIngredient_id the id of the recipe ingredient
   * @param item_id the id of the item
   * @param name the name of the item
   * @param category the category of the item
   * @param allergy the allergy information of the item
   * @param quantity the quantity of the item
   * @param unit the unit of the item
   * @param recipe_id the id of the recipe
   */
  public RecipeIngredient(int recipeIngredient_id, int item_id, String name, String category, String allergy, int quantity, String unit, int recipe_id) {
    this(item_id, name, category, allergy, quantity, unit, recipe_id);
    this.recipeIngredient_id = recipeIngredient_id;
  }

  /**
   * Returns the attributes of the recipe ingredient.
   * @return the attributes of the recipe ingredient
   */
  @Override
  public List<String> getAttributes() {
    List<String> attributes = new ArrayList<>();
    attributes.add(Integer.toString(super.getItem_id()));
    attributes.add(Integer.toString(super.getQuantity()));
    attributes.add(super.getUnit());
    attributes.add(Integer.toString(recipe_id));
    return attributes;
  }

  /**
   * Returns the attribute names of the recipe ingredient.
   * @return the attribute names of the recipe ingredient
   */
  @Override
  public List<String> getAttributeNames() {
    List<String> attributes = new ArrayList<>();
    attributes.add("item_id");
    attributes.add("quantity");
    attributes.add("unit");
    attributes.add("recipe_id");
    return attributes;
  }

  /**
   * Returns the id of the recipe ingredient.
   * @return the id of the recipe ingredient
   */
  @Override
  public int getId() {
    return recipeIngredient_id;
  }

  /**
   * Returns the id name of the recipe ingredient.
   * @return the id name of the recipe ingredient
   */
  @Override
  public String getIdName() {
    return "recipeIngredient_id";
  }
}
