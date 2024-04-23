package no.ntnu.idatt1002.demo.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * This class is a test class for the Recipe class.
 */
public class RecipeTest {

  private Recipe recipe;

  /**
   * This method is executed before each test. Makes a test recipe.
   */
  @BeforeEach
  void setUp() {
    recipe = new Recipe(1, "pasta", 30, "Italian");
    recipe.addIngredient(1, 2, "carrot", "vegetable", "", 1, "kg", 3);
  }

  /**
   * This class contains positive tests for the Recipe class.
   */
  @Nested
  @DisplayName("Positive tests for the Recipe class.")
  class PositiveRecipeTest {

    /**
     * This method tests the getAttributes method in the Recipe class. The result is
     * expected to be true.
     */
    @Test
    void testGetAttributesPositive() {
      List<String> expectedAttributes = Arrays.asList("pasta", "30", "Italian");
      assertEquals(expectedAttributes, recipe.getAttributes());
    }

    /**
     * This method tests the getAttributeNames method in the Recipe class. The
     * result is expected to be true.
     */
    @Test
    void testGetAttributeNamesPositive() {
      List<String> expectedAttributeNames = Arrays.asList("name", "cooking_time", "category");
      assertEquals(expectedAttributeNames, recipe.getAttributeNames());
    }

    /**
     * This method tests the getId method in the Recipe class. The result is
     * expected to be true.
     */
    @Test
    void testGetIdPositive() {
      int expectedId = 1;
      assertEquals(expectedId, recipe.getId());
    }

    /**
     * This method tests the getIdName method in the Recipe class. The result is
     * expected to be true.
     */
    @Test
    void testGetIdNamePositive() {
      String expectedIdName = "recipe_id";
      assertEquals(expectedIdName, recipe.getIdName());
    }

    /**
     * This method tests the getRecipe_id method in the Recipe class. The result is
     * expected to be true.
     */
    @Test
    void testGetRecipe_idPositive() {
      int expectedRecipeId = 1;
      assertEquals(expectedRecipeId, recipe.getRecipeId());
    }

    /**
     * This method tests the getName method in the Recipe class. The result is
     * expected to be true.
     */
    @Test
    void testGetNamePositive() {
      String expectedName = "pasta";
      assertEquals(expectedName, recipe.getName());
    }

    /**
     * This method tests the getCategory method in the Recipe class. The result is
     * expected to be true.
     */
    @Test
    void testGetCategoryPositive() {
      String expectedCategory = "Italian";
      assertEquals(expectedCategory, recipe.getCategory());
    }

    /**
     * This method tests the getCooking_time method in the Recipe class. The result
     * is expected to be true.
     */
    @Test
    void testGetCooking_timePositive() {
      int expectedCookingTime = 30;
      assertEquals(expectedCookingTime, recipe.getCookingTime());
    }

    /**
     * This method tests the addIngredient method in the Recipe class. The result is
     * expected to be true.
     */
    @Test
    void testAddIngredientPositive() {
      int recipeIngredientId = 1;
      int itemId = 2;
      String name = "carrot";
      String category = "vegetable";
      String allergy = "";
      int quantity = 1;
      String unit = "kg";
      int recipeId = 3;

      recipe.addIngredient(
          recipeIngredientId, itemId, name, category, allergy, quantity, unit, recipeId);

      RecipeIngredient addedIngredient = recipe.getIngredientById(recipeIngredientId);

      assertNotNull(addedIngredient);

      assertEquals(recipeIngredientId, addedIngredient.getId());
      assertEquals(itemId, addedIngredient.getItemId());
      assertEquals(name, addedIngredient.getName());
      assertEquals(category, addedIngredient.getCategory());
      assertEquals(allergy, addedIngredient.getAllergy());
      assertEquals(quantity, addedIngredient.getQuantity());
      assertEquals(unit, addedIngredient.getUnit());
      // assertEquals(recipe_id, addedIngredient.getRecipe_id());
    }

    /**
     * This method tests the getIngredients method in the Recipe class. The result
     * is expected to be true.
     */
    @Test
    void testGetIngredientsPositive() {
      List<RecipeIngredient> ingredients = recipe.getIngredients();
      assertNotNull(ingredients);
      assertEquals(1, ingredients.size());

      RecipeIngredient ingredient = ingredients.get(0);
      assertEquals(1, ingredient.getId());
      assertEquals(2, ingredient.getItemId());
      assertEquals("carrot", ingredient.getName());
      assertEquals("vegetable", ingredient.getCategory());
      assertEquals("", ingredient.getAllergy());
      assertEquals(1, ingredient.getQuantity());
      assertEquals("kg", ingredient.getUnit());
      // assertEquals(3, ingredient.getRecipe_id());
    }

    /**
     * This method tests the getIngredientById method in the Recipe class. The
     * result is expected to be true.
     */
    @Test
    void testGetIngredientByIdPositive() {
      int recipeIngredientId = 1;
      int itemId = 2;

      RecipeIngredient addedIngredient = recipe.getIngredientById(recipeIngredientId);

      assertNotNull(addedIngredient);

      assertEquals(recipeIngredientId, addedIngredient.getId());
      assertEquals(itemId, addedIngredient.getItemId());
      assertEquals("carrot", addedIngredient.getName());
      assertEquals("vegetable", addedIngredient.getCategory());
      assertEquals("", addedIngredient.getAllergy());
      assertEquals(1, addedIngredient.getQuantity());
      assertEquals("kg", addedIngredient.getUnit());
      // assertEquals(recipe_id, addedIngredient.getRecipe_id());
    }

    /**
     * This method tests the toString method in the Recipe class. The result is
     * expected to be true.
     */
    @Test
    void testToStringPositive() {
      // String expectedString = "Recipe ID: 1, Name: pasta, Category: Italian,
      // Cooking Time: 30";
      String expectedString = "pasta";
      assertEquals(expectedString, recipe.toString());
    }
  }

  /**
   * This class contains negative tests for the Recipe class.
   */
  @Nested
  @DisplayName("Negative tests for the Recipe class.")
  class NegativeRecipeTest {
    /**
     * This method tests that an IllegalArgumentException is thrown when the name is
     * null or empty.
     */
    @Test
    void testVerifyNameNotNullException() {
      try {
        new Recipe(1, "", 30, "Italian");
        fail("This test failed, since it should have thrown an exception");
      } catch (IllegalArgumentException e) {
        assertEquals("The input for the parameter 'name' cannot be null or blank", e.getMessage());
      }
    }

    /**
     * This method tests that an IllegalArgumentException is thrown when the
     * category is null or empty.
     */
    @Test
    void testVerifyCategoryNotNullException() {
      try {
        new Recipe(1, "pasta", 30, "");
        fail("This test failed, since it should have thrown an exception");
      } catch (IllegalArgumentException e) {
        assertEquals(
            "The input for the parameter 'category' cannot be null or blank", e.getMessage());
      }
    }

    /**
     * This method tests that an IllegalArgumentException is thrown when the
     * cooking_time is a negative number.
     */
    @Test
    void testVerifyCooking_timeIntegerException() {
      try {
        new Recipe(1, "pasta", -1, "Italian");
        fail("This test failed, since it should have thrown an exception");
      } catch (IllegalArgumentException e) {
        assertEquals(
            "The input for the parameter 'cooking_time' must be a positive number or minus one",
            e.getMessage());
      }
    }
  }
}
