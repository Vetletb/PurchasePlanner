package no.ntnu.idatt1002.demo.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.data.Recipe;
import no.ntnu.idatt1002.demo.data.RecipeIngredient;
import no.ntnu.idatt1002.demo.data.RecipeStep;
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
   * Returns the recipes in the register in the form of lists.
   *
   * @return the recipes in the register as lists of strings
   */
  public Map<Integer, Recipe> getRecipes() {
    return recipes.stream()
        .collect(Collectors.toMap(Recipe::getId, recipe -> recipe));
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
      List<List<String>> instructions = dao.filterFromTable(
          "RecipeStep", "recipe_id", Integer.toString(newRecipe.getId()), null, null);
      for (List<String> instruction : instructions) {
        newRecipe.addInstruction(Integer.parseInt(instruction.get(0)),
            Integer.parseInt(instruction.get(3)), Integer.parseInt(instruction.get(1)),
            instruction.get(2));
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
    List<List<String>> filteredRecipes = dao.filterFromTable(
        "Recipe", "category", category, null, null);
    packagetoRecipe(filteredRecipes);
  }

  /**
   * This method searches for recipes by name and retrieves them from the
   * database.
   *
   * @param name the name to search by
   */
  public void searchRecipesByName(String name) {
    VerifyInput.verifyNotEmpty(name, "name");
    recipes = new ArrayList<>();
    List<List<String>> newRecipes = dao.searchFromTable(
        "Recipe", name, null, null);
    packagetoRecipe(newRecipes);
  }

  /**
   * This method retrieves all recipes from the database.
   */
  public void getAllRecipes() {
    recipes = new ArrayList<>();
    List<List<String>> allRecipes = dao.getAllFromTable("Recipe", null, null);
    packagetoRecipe(allRecipes);
  }

  /**
   * This method adds a recipe to the database.
   *
   * @param name        the name of the recipe
   * @param category    the category of the recipe
   * @param cookingTime the cooking time of the recipe
   */
  public void addRecipe(String name, String category, int cookingTime) {
    dao.addToDatabase(new Recipe(name, cookingTime, category));
  }

  /**
   * Retrieves the index of a recipe from the register by id.
   *
   * @param id the id of the recipe
   * @return the index of the recipe
   */
  public int getRecipesFromId(int id) {
    VerifyInput.verifyPositiveNumberMinusOneNotAccepted(id, "id");
    for (int i = 0; i < recipes.size(); i++) {
      if (recipes.get(i).getRecipeId() == id) {
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
   * @param recipeId    the id of the recipe
   * @param name        the name of the recipe
   * @param cookingTime the cooking time of the recipe
   * @param category    the category of the recipe
   */
  public void updateRecipe(int recipeId, String name, int cookingTime, String category) {
    int index = getRecipesFromId(recipeId);
    if (index == -1) {
      throw new IllegalArgumentException("Recipe with id " + recipeId + " does not exist.");
    }
    Recipe recipe = new Recipe(recipeId, name, cookingTime, category);
    dao.updateDatabase(recipe);
  }

  /**
   * Adds an ingredient to the database.
   *
   * @param itemId   the id of the item
   * @param quantity the quantity of the item
   * @param unit     the unit of the item
   * @param recipeId the id of the recipe
   */
  public void addIngredient(int itemId, int quantity, String unit, int recipeId) {
    dao.addToDatabase(new RecipeIngredient(itemId, "d", "d", "d", quantity, unit, recipeId));
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
   * @param recipeIngredientId the id of the recipe ingredient
   * @param itemId             the id of the item
   * @param quantity           the quantity of the item
   * @param unit               the unit of the item
   */
  public void updateIngredient(int recipeIngredientId, int itemId, int quantity, String unit) {
    int recipeId = -1;
    for (Recipe recipe : recipes) {
      if (recipe.getIngredientById(recipeIngredientId) != null) {
        recipeId = recipe.getRecipeId();
        break;
      }
    }
    if (recipeId > 0) {
      RecipeIngredient recipeIngredient = new RecipeIngredient(
          recipeIngredientId, itemId, "a", "a", "a", quantity, unit, recipeId);
      dao.updateDatabase(recipeIngredient);
    }
  }

  /**
   * This method adds an instruction to the database.
   *
   * @param recipeId    the id of the recipe
   * @param stepNumber  the number of the step
   * @param instruction the instruction of the step
   */
  public void addInstruction(int recipeId, int stepNumber, String instruction) {
    List<Recipe> currentRecipes = recipes;
    getAllRecipes();
    instructionNumbering(recipeId, stepNumber, true);
    recipes = currentRecipes;
    dao.addToDatabase(new RecipeStep(recipeId, stepNumber, instruction));
  }

  /**
   * Deletes an instruction from the database.
   *
   * @param id the id of the instruction to delete
   */
  public void deleteInstruction(int id) {
    for (Recipe recipe : recipes) {
      if (recipe.getInstructionById(id) != null) {
        dao.deleteFromDatabase(recipe.getInstructionById(id));
        List<Recipe> currentRecipes = recipes;
        getAllRecipes();
        instructionNumbering(
            recipe.getRecipeId(), recipe.getInstructionById(id).getStepNumber(), false);
        recipes = currentRecipes;
        break;
      }
    }
  }

  /**
   * This method updates an instruction in the database.
   *
   * @param stepId      the id of the step
   * @param stepNumber  the number of the step
   * @param instruction the instruction
   */
  public void updateInstrucution(int stepId, int stepNumber, String instruction) {
    int recipeId = -1;
    int previousStep = -1;
    for (Recipe recipe : recipes) {
      if (recipe.getInstructionById(stepId) != null) {
        recipeId = recipe.getRecipeId();
        previousStep = recipe.getInstructionById(stepId).getStepNumber();
        break;
      }
    }
    if (recipeId > 0) {
      instructionNumbering(recipeId, previousStep, false);
      List<Recipe> currentRecipes = recipes;
      getAllRecipes();
      instructionNumbering(recipeId, stepNumber, true);
      recipes = currentRecipes;
      RecipeStep recipeStep = new RecipeStep(stepId, recipeId, stepNumber, instruction);
      dao.updateDatabase(recipeStep);
    }
  }

  /**
   * This method adjusts step numbers when an instruction is changed.
   *
   * @param recipe_id the id of the recipe
   * @param step      the step number
   * @param add       whether to add or subtract
   */
  private void instructionNumbering(int recipeId, int step, boolean add) {
    Recipe recipe = recipes.get(getRecipesFromId(recipeId));
    List<RecipeStep> instructions = recipe.getInstructions();
    for (RecipeStep instruction : instructions) {
      if (instruction.getStepNumber() >= step) {
        if (add) {
          dao.updateDatabase(new RecipeStep(instruction.getStepId(), recipeId,
              instruction.getStepNumber() + 1, instruction.getInstruction()));
        } else if (instruction.getStepNumber() != 1) {
          dao.updateDatabase(new RecipeStep(instruction.getStepId(), recipeId,
              instruction.getStepNumber() - 1, instruction.getInstruction()));
        }
      }
    }
  }

  /**
   * Returns a string representation of the recipe register.
   * 
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
