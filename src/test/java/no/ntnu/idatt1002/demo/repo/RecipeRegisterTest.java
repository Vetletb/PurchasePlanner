package no.ntnu.idatt1002.demo.repo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import no.ntnu.idatt1002.demo.dao.DAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Test class for the RecipeRegister class. Contain positive and negative tests
 */
public class RecipeRegisterTest {
  private RecipeRegister recipeRegister;

  @Mock
  DAO dao;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    recipeRegister = new RecipeRegister(dao);
  }

  @Nested
  @DisplayName("Positive tests for the RecipeRegister class.")
  class PositiveRecipeRegisterTest {

    @Test
    void testConstructorPositive() {
      assertDoesNotThrow(() -> new RecipeRegister(dao));
    }

    @Test
    void testGetRecipesPositive() {
      assertNotNull(recipeRegister.getRecipes());
    }

    @Test
    void testFilterRecipesByCategory() {
      when(dao.filterFromTable("Recipe", "category", "category", null, null))
          .thenReturn(List.of(
              List.of("1", "name", "10", "category"),
              List.of("2", "name", "20", "category"),
              List.of("3", "name", "30", "category")));
      recipeRegister.filterRecipesByCategory("category");
      assertEquals(3, recipeRegister.getRecipes().size());
    }

    @Test
    void testSearchRecipesByName() {
      when(dao.searchFromTable("Recipe", "name", null, null))
          .thenReturn(List.of(
              List.of("1", "name", "10", "category"),
              List.of("2", "name", "20", "category"),
              List.of("3", "name", "30", "category")));
      recipeRegister.searchRecipesByName("name");
      assertEquals(3, recipeRegister.getRecipes().size());
    }

    @Test
    void testGetAllRecipes() {
      when(dao.getAllFromTable("Recipe", null, null))
          .thenReturn(List.of(
              List.of("1", "name", "10", "category"),
              List.of("2", "name", "20", "category"),
              List.of("3", "name", "30", "category")));
      recipeRegister.getAllRecipes();
      assertEquals(3, recipeRegister.getRecipes().size());
    }

    @Test
    void testAddRecipes() {
      assertDoesNotThrow(() -> recipeRegister.addRecipe("name", "category", 10));
    }

    @Nested
    @DisplayName("Setup recipes before tests delete and update tests.")
    class SetupRecipesBeforeTests {
      @BeforeEach
      void setUp() {
        when(dao.getAllFromTable("Recipe", null, null))
            .thenReturn(List.of(
                List.of("1", "name", "10", "category"),
                List.of("2", "name", "20", "category"),
                List.of("3", "name", "30", "category")));
        recipeRegister.getAllRecipes();
      }

      @Test
      void testDeleteRecipePositive() {
        recipeRegister.deleteRecipe(1);
        assertDoesNotThrow(() -> recipeRegister.deleteRecipe(1));
      }

      @Test
      void testUpdateRecipePositive() {
        assertDoesNotThrow(() -> recipeRegister.updateRecipe(1, "name", 20, "category"));
      }
    }

    @Test
    void testAddIngredientPositive() {
      assertDoesNotThrow(() -> recipeRegister.addIngredient(1, 2, "kg", 3));
    }

    @Nested
    @DisplayName("Setup ingredients before tests.")
    class SetUpIngredientsBeforeTests {
      @BeforeEach
      void setUp() {
        when(dao.getAllFromTable("Recipe", null, null))
            .thenReturn(List.of(
                List.of("1", "name", "10", "category")));
        when(dao.filterFromTable("RecipeIngredient", "recipe_id", "1", "Item", "item_id"))
            .thenReturn(List.of(
                List.of("1", "2", "1", "1", "kg", "1", "name", "category", "allergy"),
                List.of("2", "3", "1", "1", "kg", "1", "name", "category", "allergy")));

        when(dao.filterFromTable("RecipeStep", "recipe_id", "1", null, null))
            .thenReturn(List.of(
                List.of("1", "1", "instruction", "1"),
                List.of("2", "2", "instruction", "1"),
                List.of("3", "3", "instruction", "1")));
        recipeRegister.getAllRecipes();
      }

      @Test
      void testDeleteIngredientPositive() {
        assertDoesNotThrow(() -> recipeRegister.deleteIngredient(1));
      }

      @Test
      void testUpdateIngredientPositive() {
        assertDoesNotThrow(() -> recipeRegister.updateIngredient(1, 1, 2, "liter"));
      }
    }
  }

  @Nested
  @DisplayName("Negative tests for the RecipeRegister class.")
  class NegativeRecipeRegisterTest {
    @Test
    void testFilterRecipesByCategory() {
      assertThrows(IllegalArgumentException.class,
          () -> recipeRegister.filterRecipesByCategory(null));
    }

    @Test
    void testSearchRecipesByNameNegaticv() {
      assertThrows(IllegalArgumentException.class, () -> recipeRegister.searchRecipesByName(null));
    }

    @Test
    void testDeleteRecipeNegative() {
      assertThrows(IllegalArgumentException.class, () -> recipeRegister.deleteRecipe(5));
    }

    @Test
    void testUpdateRecipeNegative() {
      assertThrows(IllegalArgumentException.class,
          () -> recipeRegister.updateRecipe(5, "name", 10, "category"));
    }
  }
}
