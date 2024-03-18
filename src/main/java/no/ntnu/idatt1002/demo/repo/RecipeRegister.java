package no.ntnu.idatt1002.demo.repo;

import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.data.Item;
import no.ntnu.idatt1002.demo.data.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class represents a register for recipes. Allowing for communication between the database and the user interface.
 */
public class RecipeRegister {

  private List<Recipe> recipes;
  private final DAO dao;

  /**
   * Constructor for the RecipeRegister class.
   * @param dao the data access object
   */
  public RecipeRegister(DAO dao) {
    this.dao = dao;
    this.recipes = new ArrayList<>();
  }

  /**
   * This method returns the recipes in the register in the form of lists.
   * @return the recipes in the register as lists of strings
   */
  public Map<Integer, List<String>> getRecipes() {
    return recipes.stream()
        .collect(Collectors.toMap(Recipe::getId, Recipe::getAttributes));
  }

  /**
   * This method packages the recipes into a list of recipes.
   * @param recipes the recipes to package
   */
  private void packagetoRecipe(List<List<String>> recipes) {
    for (List<String> recipe : recipes) {
      this.recipes.add(new Recipe(Integer.parseInt(recipe.get(0)), recipe.get(1), Integer.parseInt(recipe.get(2)), recipe.get(3)));
    }
  }

  /**
   * This method retrieves filtered recipes by category from the database.
   * @param category the category to filter by
   */
  public void filterRecipesByCategory(String category) {
    recipes = new ArrayList<>();
    List<List<String>> recipes = dao.filterFromTable("Recipe", "category", category, null, null);
    packagetoRecipe(recipes);
  }

  /**
   * This method searches for recipes by name and retrieves them from the database.
   * @param name the name to search by
   */
  public void searchRecipesByName(String name) {
    recipes = new ArrayList<>();
    List<List<String>> recipes = dao.searchFromTable("Recipe", name, null, null);
    packagetoRecipe(recipes);
  }

  /**
   * This method retrieves all recipes from the database.
   */
  public void getAllRecipes() {
    recipes = new ArrayList<>();
    List<List<String>> recipes = dao.getAllFromTable("Recipe");
    packagetoRecipe(recipes);
  }

  /**
   * This method creates a recipe object.
   * @param name the name of the recipe
   * @param cooking_time the cooking time of the recipe
   * @param category the category of the recipe
   * @return a recipe object
   */
  public Recipe createRecipe(String name, int cooking_time, String category) {
    return new Recipe(name, cooking_time, category);
  }

  /**
   * This method adds a recipe to the database.
   * @param name the name of the recipe
   * @param category the category of the recipe
   * @param cooking_time the cooking time of the recipe
   */
  public void addRecipe(String name, String category, int cooking_time) {
    dao.addToDatabase(createRecipe(name, cooking_time, category));
  }

  /**
   * This method retrieves the index of a recipe from the register by id.
   * @param id the id of the recipe
   * @return the index of the recipe
   */
  public int getRecipesFromId(int id) {
    for (int i = 0; i < recipes.size(); i++) {
      if (recipes.get(i).getRecipe_id() == id) {
        return i;
      }
    }
    return -1;
  }

  /**
   * This method deletes a recipe from the database.
   * @param id the id of the recipe to delete
   */
  public void deleteRecipe(int id) {
    int index = getRecipesFromId(id);
    dao.deleteFromDatabase(recipes.get(index));
  }

  /**
   * This method updates a recipe in the database.
   * @param name the name of the recipe
   * @param cooking_time the cooking time of the recipe
   * @param category the category of the recipe
   */
  public void updateRecipe(String name, int cooking_time, String category ) {

    Recipe recipe = createRecipe(name, cooking_time, category);
    dao.updateDatabase(recipe);
  }

  /**
   * This method returns a string representation of the recipe register.
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