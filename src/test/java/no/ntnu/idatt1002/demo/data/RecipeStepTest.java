package no.ntnu.idatt1002.demo.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Test class for the RecipeStep class. Contain tests for the constructor and
 * all the getter methods. Both positive and negative tests.
 */
class RecipeStepTest {

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {
    RecipeStep recipeStep;

    @BeforeEach
    public void setUp() {
      recipeStep = new RecipeStep(1, 2, 3, "Instruction");
    }

    @Test
    @DisplayName("Test constructor")
    void constructorDoesNotThrowExceptionOnValidParameters() {
      assertDoesNotThrow(() -> new RecipeStep(1, 1, 1, "Instruction"));
    }

    @Test
    @DisplayName("Test getStepId")
    void getStepIdReturnsCorrectStepId() {
      assertEquals(1, recipeStep.getStepId());
    }

    @Test
    @DisplayName("Test getRecipeId")
    void getRecipeIdReturnsCorrectRecipeId() {
      assertEquals(2, recipeStep.getRecipeId());
    }

    @Test
    @DisplayName("Test getStepNumber")
    void getStepNumberReturnsCorrectStepNumber() {
      assertEquals(3, recipeStep.getStepNumber());
    }

    @Test
    @DisplayName("Test getInstruction")
    void getInstructionReturnsCorrectInstruction() {
      assertEquals("Instruction", recipeStep.getInstruction());
    }

    @Test
    @DisplayName("Test getAttributes")
    void getAttributesReturnsCorrectAttributes() {
      List<String> expected = List.of("2", "3", "Instruction");
      assertEquals(expected, recipeStep.getAttributes());
    }

    @Test
    void getAttributeNamesReturnsCorrectAttributeNames() {
      List<String> expected = List.of("recipe_id", "step_number", "instructions");
      assertEquals(expected, recipeStep.getAttributeNames());
    }

    @Test
    @DisplayName("Test getId")
    void getIdReturnsCorrectId() {
      assertEquals(1, recipeStep.getId());
    }

    @Test
    @DisplayName("Test getIdName")
    void getIdNameReturnsCorrectIdName() {
      assertEquals("step_id", recipeStep.getIdName());
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {

    @Test
    @DisplayName("Test constructor with zero or negative step id")
    void constructorThrowsExceptionOnZeroOrLowerStepId() {
      assertThrows(IllegalArgumentException.class, () -> new RecipeStep(0, 1, 1, "Instruction"));
    }

    @Test
    @DisplayName("Test constructor with negative recipe id")
    void constructorThrowsExceptionOnNegativeRecipeId() {
      assertThrows(IllegalArgumentException.class, () -> new RecipeStep(1, -1, 1, "Instruction"));
    }

    @Test
    @DisplayName("Test constructor with negative step number")
    void constructorThrowsExceptionOnNegativeStepNumber() {
      assertThrows(IllegalArgumentException.class, () -> new RecipeStep(1, 1, -2, "Instruction"));
    }

    @Test
    @DisplayName("Test constructor with null instruction")
    void constructorThrowsExceptionOnNullInstruction() {
      assertThrows(IllegalArgumentException.class, () -> new RecipeStep(1, 1, 1, null));
    }
  }
}