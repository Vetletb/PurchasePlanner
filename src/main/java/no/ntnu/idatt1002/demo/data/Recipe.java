package no.ntnu.idatt1002.demo.data;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1002.demo.util.VerifyInput;

/**
 * This class represents a recipe with a name, category and cooking time.
 */
public class Recipe implements Storable {
  private int recipeId;
  private final String name;
  private final int cookingTime;
  private final String category;
  private final List<RecipeIngredient> ingredients;
  private final List<RecipeStep> instructions;

  /**
   * Constructor for the Recipe class.
   *
   * @param name        the name of the recipe
   * @param category    the category of the recipe
   * @param cookingTime the cooking time of the recipe
   */
  public Recipe(String name, int cookingTime, String category) {
    VerifyInput.verifyNotEmpty(name, "name");
    VerifyInput.verifyNotEmpty(category, "category");
    VerifyInput.verifyPositiveNumberMinusOneAccepted(cookingTime, "cooking_time");
    this.name = name;
    this.cookingTime = cookingTime;
    this.category = category;
    this.ingredients = new ArrayList<>();
    this.instructions = new ArrayList<>();
  }

  /**
   * Constructor for the Recipe class.
   *
   * @param recipeId    the id of the recipe
   * @param name        the name of the recipe
   * @param cookingTime the cooking time of the recipe
   * @param category    the category of the recipe
   */
  public Recipe(int recipeId, String name, int cookingTime, String category) {
    this(name, cookingTime, category);
    this.recipeId = recipeId;
  }

  /**
   * Returns the attributes of the recipe.
   *
   * @return the attributes of the recipe
   */
  @Override
  public List<String> getAttributes() {
    List<String> attributes = new ArrayList<>();
    attributes.add(name);
    attributes.add(Integer.toString(cookingTime));
    attributes.add(category);
    return attributes;
  }

  /**
   * Returns the attribute names of the recipe.
   *
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
   *
   * @return the id of the recipe
   */
  @Override
  public int getId() {
    return getRecipeId();
  }

  /**
   * Returns the name of the id.
   *
   * @return the name of the id
   */
  @Override
  public String getIdName() {
    return "recipe_id";
  }

  /**
   * Returns the recipe id.
   *
   * @return the recipe id
   */
  public int getRecipeId() {
    return recipeId;
  }

  /**
   * Returns the name of the recipe.
   *
   * @return the name of the recipe
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the category of the recipe.
   *
   * @return the category of the recipe
   */
  public String getCategory() {
    return category;
  }

  /**
   * Returns the cooking time of the recipe.
   *
   * @return the cooking time of the recipe
   */
  public int getCookingTime() {
    return cookingTime;
  }

  /**
   * Adds an ingredient to the recipe.
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
  public void addIngredient(
      int recipeIngredientId, int itemId, String name, String category,
      String allergy, int quantity, String unit, int recipeId) {
    ingredients.add(
        new RecipeIngredient(
            recipeIngredientId, itemId, name, category, allergy, quantity, unit, recipeId));
  }

  /**
   * Returns the ingredients of the recipe.
   *
   * @return the ingredients of the recipe
   */
  public List<RecipeIngredient> getIngredients() {
    return ingredients;
  }

  /**
   * Returns the ingredient with the given id.
   *
   * @param id the id of the ingredient
   * @return the ingredient with the given id
   */
  public RecipeIngredient getIngredientById(int id) {
    for (RecipeIngredient ingredient : ingredients) {
      if (ingredient.getId() == id) {
        return ingredient;
      }
    }
    return null;
  }

  /**
   * Adds an instruction to the recipe.
   *
   * @param stepId      the id of the step
   * @param recipeId    the id of the recipe
   * @param stepNumber  the number of the step
   * @param instruction the instruction
   */
  public void addInstruction(int stepId, int recipeId, int stepNumber, String instruction) {
    instructions.add(new RecipeStep(stepId, recipeId, stepNumber, instruction));
  }

  /**
   * Returns the instructions of the recipe.
   *
   * @return the instructions of the recipe as a list
   */
  public List<RecipeStep> getInstructions() {
    return instructions;
  }

  /**
   * Returns the instruction with the given id.
   *
   * @param id the id of the instruction
   * @return the instruction with the given id
   */
  public RecipeStep getInstructionById(int id) {
    for (RecipeStep instruction : instructions) {
      if (instruction.getId() == id) {
        return instruction;
      }
    }
    return null;
  }

  /**
   * Returns the recipe as a string representation.
   *
   * @return the string representation of the recipe
   */
  @Override
  public String toString() {
    return this.getName();
  }
}
