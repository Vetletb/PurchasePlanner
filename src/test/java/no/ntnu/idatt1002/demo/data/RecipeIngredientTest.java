package no.ntnu.idatt1002.demo.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * This class is a test class for the RecipeIngredient class.
 */
public class RecipeIngredientTest {
  private RecipeIngredient recipeIngredient;

  /**
   * This method is executed before each test. Makes a test recipe ingredient.
   */
  @BeforeEach
  void setUp() {
    recipeIngredient = new RecipeIngredient(1, 2, "carrot", "vegetable", "", 1, "kg", 3);
  }

  /**
   * This class contains positive tests for the RecipeIngredient class.
   */
  @Nested
  @DisplayName("Positive tests for the RecipeIngredient class.")
  class PositiveRecipeIngredientTest {

    /**
     * This method tests the getAttributes method in the RecipeIngredient class. The
     * result is expected to be true.
     */
    @Test
    void testGetAttributesPositive() {
      List<String> expectedAttributes = Arrays.asList("2", "1", "kg", "3");
      assertEquals(expectedAttributes, recipeIngredient.getAttributes());
    }

    /**
     * This method tests the getAttributeNames method in the RecipeIngredient class.
     * The result is expected to be true.
     */
    @Test
    void testGetAttributeNamesPositive() {
      List<String> expectedAttributeNames = Arrays.asList(
          "item_id", "quantity", "unit", "recipe_id");
      assertEquals(expectedAttributeNames, recipeIngredient.getAttributeNames());
    }

    /**
     * This method tests the getId method in the RecipeIngredient class. The result
     * is expected to be true.
     */
    @Test
    void testGetIdPositive() {
      assertEquals(1, recipeIngredient.getId());
    }

    /**
     * This method tests the getIdName method in the RecipeIngredient class. The
     * result is expected to be true.
     */
    @Test
    void testGetIdNamePositive() {
      assertEquals("recipeIngredient_id", recipeIngredient.getIdName());
    }
  }

  /**
   * This class contains negative tests for the RecipeIngredient class.
   */
  @Nested
  @DisplayName("Negative tests for the RecipeIngredient class.")
  class NegativeRecipeIngredientTest {

    /**
     * This method tests that an IllegalArgumentException is thrown when the
     * recipe_id is a lower integer than 1.
     */
    @Test
    void testVerifyRecipe_idIntegerException() {
      try {
        new RecipeIngredient(1, 2, "carrot", "vegetable", "", 1, "kg", -1);
        fail("This test failed, since it should have thrown an exception");
      } catch (IllegalArgumentException e) {
        assertEquals("The input for the parameter 'recipe_id' must be a positive number equal to or greater than 1",
            e.getMessage());
      }
    }
  }
}
