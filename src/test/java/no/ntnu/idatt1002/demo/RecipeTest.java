package no.ntnu.idatt1002.demo;

import no.ntnu.idatt1002.demo.data.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
  }

  /**
   * This class contains positive tests for the Recipe class.
   */
  @Nested
  @DisplayName("Positive tests for the Recipe class.")
  class PositiveRecipeTest {

    /**
     * This method tests the getAttributes method in the Recipe class. The result is expected to be true.
     */
    @Test
    void testGetAttributesPositive() {
      List<String> expectedAttributes = Arrays.asList("pasta", "30", "Italian");
      assertEquals(expectedAttributes, recipe.getAttributes());
    }

    /**
     * This method tests the getAttributeNames method in the Recipe class. The result is expected to be true.
     */
    @Test
    void testGetAttributeNamesPositive() {
      List<String> expectedAttributeNames = Arrays.asList("name", "cooking_time", "category");
      assertEquals(expectedAttributeNames, recipe.getAttributeNames());
    }

    /**
     * This method tests the getId method in the Recipe class. The result is expected to be true.
     */
    @Test
    void testGetIdPositive() {
      int expectedId = 1;
      assertEquals(expectedId, recipe.getId());
    }

    /**
     * This method tests the getIdName method in the Recipe class. The result is expected to be true.
     */
    @Test
    void testGetIdNamePositive() {
      String expectedIdName = "recipe_id";
      assertEquals(expectedIdName, recipe.getIdName());
    }

    /**
     * This method tests the getRecipe_id method in the Recipe class. The result is expected to be true.
     */
    @Test
    void testGetRecipe_idPositive() {
      int expectedRecipe_id = 1;
      assertEquals(expectedRecipe_id, recipe.getRecipe_id());
    }

    /**
     * This method tests the getName method in the Recipe class. The result is expected to be true.
     */
    @Test
    void testGetNamePositive() {
      String expectedName = "pasta";
      assertEquals(expectedName, recipe.getName());
    }

    /**
     * This method tests the getCategory method in the Recipe class. The result is expected to be true.
     */
    @Test
    void testGetCategoryPositive() {
      String expectedCategory = "Italian";
      assertEquals(expectedCategory, recipe.getCategory());
    }

    /**
     * This method tests the getCooking_time method in the Recipe class. The result is expected to be true.
     */
    @Test
    void testGetCooking_timePositive() {
      int expectedCooking_time = 30;
      assertEquals(expectedCooking_time, recipe.getCooking_time());
    }

    /**
     * This method tests the toString method in the Recipe class. The result is expected to be true.
     */
    @Test
    void testToStringPositive() {
      String expectedString = "Recipe ID: 1, Name: pasta, Category: Italian, Cooking Time: 30";
      assertEquals(expectedString, recipe.toString());
    }
  }

  /**
   * This class contains negative tests for the Recipe class.
   */
  @Nested
  @DisplayName("Negative tests for the Recipe class.")
  class NegativeRecipeTest {
  }
}
