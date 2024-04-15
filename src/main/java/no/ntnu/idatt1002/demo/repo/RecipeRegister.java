package no.ntnu.idatt1002.demo.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.data.Recipe;
import no.ntnu.idatt1002.demo.data.RecipeIngredient;
import no.ntnu.idatt1002.demo.util.VerifyInput;

/**
 * This class represents a register for recipes.
 * Allowing for communication between the database and the user interface.
 */
public class RecipeRegister {

  private List<Recipe> recipes;
  private final DAO dao;

  /**
   * Constructor for the RecipeRegister class.
   *
   * @param dao the data access object
   */
  public RecipeRegister(DAO dao) {
    this.dao = dao;
    this.recipes = new ArrayList<>();
  }

  /**
   * This method returns the recipes in the register in the form of lists.
   *
   * @return the recipes in the register as lists of strings
   */
  public Map<Integer, List<String>> getRecipes() {
    return recipes.stream()
        .collect(Collectors.toMap(Recipe::getId, Recipe::getAttributes));
  }

  /**
   * This method packages the recipes into a list of recipes.
   *
   * @param recipes the recipes to package
   */
  private void packagetoRecipe(List<List<String>> recipes) {
    for (List<String> recipe : recipes) {
      Recipe newRecipe = new Recipe(
          Integer.parseInt(recipe.get(0)),
          recipe.get(1),
          Integer.parseInt(recipe.get(2)),
          recipe.get(3));
      List<List<String>> ingredients = dao.filterFromTable(
          "RecipeIngredient", "recipe_id", Integer.toString(newRecipe.getId()), "Item", "item_id");
      for (List<String> ingredient : ingredients) {
        newRecipe.addIngredient(Integer.parseInt(ingredient.get(0)),
            Integer.parseInt(ingredient.get(1)), ingredient.get(6), ingredient.get(7),
            ingredient.get(8), Integer.parseInt(ingredient.get(3)), ingredient.get(4),
            Integer.parseInt(ingredient.get(2)));
      }
      this.recipes.add(newRecipe);
    }
  }

  /**
   * This method retrieves filtered recipes by category from the database.
   *
   * @param category the category to filter by
   */
  public void filterRecipesByCategory(String category) {
    VerifyInput.verifyNotEmpty(category, "category");
    recipes = new ArrayList<>();
    List<List<String>> recipes = dao.filterFromTable("Recipe", "category", category, null, null);
    packagetoRecipe(recipes);
  }

  /**
   * This method searches for recipes by name and retrieves them from the database.
   *
   * @param name the name to search by
   */
  public void searchRecipesByName(String name) {
    VerifyInput.verifyNotEmpty(name, "name");
    recipes = new ArrayList<>();
    List<List<String>> recipes = dao.searchFromTable("Recipe", name, null, null);
    packagetoRecipe(recipes);
  }

  /**
   * This method retrieves all recipes from the database.
   */
  public void getAllRecipes() {
    recipes = new ArrayList<>();
    List<List<String>> recipes = dao.getAllFromTable("Recipe", null, null);
    packagetoRecipe(recipes);
  }

  /**
   * This method adds a recipe to the database.
   *
   * @param name the name of the recipe
   * @param category the category of the recipe
   * @param cooking_time the cooking time of the recipe
   */
  public void addRecipe(String name, String category, int cooking_time) {
    dao.addToDatabase(new Recipe(name, cooking_time, category));
  }

  /**
   * This method retrieves the index of a recipe from the register by id.
   *
   * @param id the id of the recipe
   * @return the index of the recipe
   */
  public int getRecipesFromId(int id) {
    VerifyInput.verifyPositiveNumberMinusOneNotAccepted(id, "id");
    for (int i = 0; i < recipes.size(); i++) {
      if (recipes.get(i).getRecipe_id() == id) {
        return i;
      }
    }
    return -1;
  }

  /**
   * This method deletes a recipe from the database.
   *
   * @param id the id of the recipe to delete
   */
  public void deleteRecipe(int id) {
    int index = getRecipesFromId(id);
    if (index == -1) {
      throw new IllegalArgumentException("Recipe with id " + id + " does not exist.");
    }
    Recipe recipe = recipes.get(index);
    List<RecipeIngredient> recipeIngredient = recipe.getIngredients();
    for (RecipeIngredient ingredient : recipeIngredient) {
      dao.deleteFromDatabase(ingredient);
    }
    dao.deleteFromDatabase(recipes.get(index));
  }

  /**
   * This method updates a recipe in the database.
   *
   * @param name the name of the recipe
   * @param cooking_time the cooking time of the recipe
   * @param category the category of the recipe
   */
  public void updateRecipe(int recipe_id, String name, int cooking_time, String category) {
    Recipe recipe = new Recipe(recipe_id, name, cooking_time, category);
    dao.updateDatabase(recipe);
  }

  /**
   * This method adds an ingredient to the database.
   *
   * @param item_id the id of the item
   * @param quantity the quantity of the item
   * @param unit the unit of the item
   * @param recipe_id the id of the recipe
   */
  public void addIngredient(int item_id, int quantity, String unit, int recipe_id) {
    dao.addToDatabase(new RecipeIngredient(item_id, "dummy", "dummy", "dummy", quantity, unit, recipe_id));
  }

  /**
   * This method deletes an ingredient from the database.
   *
   * @param id the id of the ingredient to delete
   **/
  public void deleteIngredient(int id) {
    boolean found = false;
    for (Recipe recipe : recipes) {
      if (recipe.getIngredientById(id) != null) {
        dao.deleteFromDatabase(recipe.getIngredientById(id));
        found = true;
        break;
      }
    }
    if (!found) {
      throw new IllegalArgumentException("Ingredient with id " + id + " does not exist.");
    }
  }

  /**
   * This method updates an ingredient in the database.
   *
   * @param recipeIngredient_id the id of the recipe ingredient
   * @param item_id the id of the item
   * @param quantity the quantity of the item
   * @param unit the unit of the item
   */
  public void updateIngredient(int recipeIngredient_id, int item_id, int quantity, String unit) {
    int recipe_id = -1;
    for (Recipe recipe : recipes) {
      if (recipe.getIngredientById(recipeIngredient_id) != null) {
        recipe_id = recipe.getRecipe_id();
        break;
      }
    }
    if (recipe_id > 0) {
      RecipeIngredient recipeIngredient =
          new RecipeIngredient(recipeIngredient_id, item_id, "dummy", "dummy", "dummy", quantity, unit,
              recipe_id);
      dao.updateDatabase(recipeIngredient);
    }
  }

  /**
   * This method returns a string representation of the recipe register.
   *
   * @return a string representation of the recipe register
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Recipe recipe : recipes) {
      sb.append(recipe.toString()).append("\n");
    }
    return sb.toString();
  }
}
