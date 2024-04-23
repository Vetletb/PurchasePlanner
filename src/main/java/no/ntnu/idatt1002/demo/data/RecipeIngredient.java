package no.ntnu.idatt1002.demo.data;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1002.demo.util.VerifyInput;

/**
 * This class represents a recipe ingredient connected to a recipe.
 */
public class RecipeIngredient extends ShoppingListItem {

  private int recipeIngredientId;
  private final int recipeId;

  /**
   * Constructor for the RecipeIngredient class.
   *
   * @param itemId   the id of the item
   * @param name     the name of the item
   * @param category the category of the item
   * @param allergy  the allergy information of the item
   * @param quantity the quantity of the item
   * @param unit     the unit of the item
   * @param recipeId the id of the recipe
   */
  public RecipeIngredient(
      int itemId, String name, String category, String allergy,
      int quantity, String unit, int recipeId) {
    super(itemId, name, category, allergy, quantity, unit);
    VerifyInput.verifyPositiveNumberZeroNotAccepted(recipeId, "recipe_id");
    this.recipeId = recipeId;
  }

  /**
   * Constructor for the RecipeIngredient class with recipe ingredient id.
   *
   * @param recipeIngredientId the id of the recipe ingredient
   * @param itemId             the id of the item
   * @param name               the name of the item
   * @param category           the category of the item
   * @param allergy            the allergy information of the item
   * @param quantity           the quantity of the item
   * @param unit               the unit of the item
   * @param recipeId           the id of the recipe
   */
  public RecipeIngredient(
      int recipeIngredientId, int itemId, String name, String category,
      String allergy, int quantity, String unit, int recipeId) {
    this(itemId, name, category, allergy, quantity, unit, recipeId);
    this.recipeIngredientId = recipeIngredientId;
  }

  /**
   * Returns the attributes of the recipe ingredient.
   *
   * @return the attributes of the recipe ingredient
   */
  @Override
  public List<String> getAttributes() {
    List<String> attributes = new ArrayList<>();
    attributes.add(Integer.toString(super.getItemId()));
    attributes.add(Integer.toString(super.getQuantity()));
    attributes.add(super.getUnit());
    attributes.add(Integer.toString(recipeId));
    return attributes;
  }

  /**
   * Returns the attribute names of the recipe ingredient.
   *
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
   *
   * @return the id of the recipe ingredient
   */
  @Override
  public int getId() {
    return getRecipeIngredientId();
  }

  /**
   * Returns the id name of the recipe ingredient.
   *
   * @return the id name of the recipe ingredient
   */
  @Override
  public String getIdName() {
    return "recipeIngredient_id";
  }

  /**
   * Returns the recipe id of the recipe ingredient.
   *
   * @return the recipe id of the recipe ingredient
   */
  public int getRecipeId() {
    return recipeId;
  }

  /**
   * Returns the recipe ingredient id.
   *
   * @return the recipe ingredient id
   */
  public int getRecipeIngredientId() {
    return recipeIngredientId;
  }
}
