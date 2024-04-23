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
 * This class is a test class for the Event class.
 */
public class EventTest {
  private Event event;

  /**
   * This method is executed before each test. Makes a test event.
   */
  @BeforeEach
  void setUp() {
    event = new Event(1, 2, 20240501);
  }

  /**
   * This class contains positive tests for the Event class.
   */
  @Nested
  @DisplayName("Positive tests for the Event class.")
  class PositiveEventTest {

    /**
     * This method tests the getAttributes method in the Event class. The result is
     * expected to be true.
     */
    @Test
    void testGetAttributesPositive() {
      List<String> expectedAttributes = Arrays.asList("2", "20240501");
      assertEquals(expectedAttributes, event.getAttributes());
    }

    /**
     * This method tests the getAttributeNames method in the Event class. The result
     * is expected to be true.
     */
    @Test
    void testGetAttributeNamesPositive() {
      List<String> expectedAttributeNames = Arrays.asList("recipe_id", "date");
      assertEquals(expectedAttributeNames, event.getAttributeNames());
    }

    /**
     * This method tests the getId method in the Event class. The result is expected
     * to be true.
     */
    @Test
    void testGetIdPositive() {
      assertEquals(1, event.getId());
    }

    /**
     * This method tests the getEvent_id method in the Event class. The result is
     * expected to be true.
     */
    @Test
    void testGetEvent_id() {
      assertEquals(1, event.getEventId());
    }

    /**
     * This method tests the getRecipe_id method in the Event class. The result is
     * expected to be true.
     */
    @Test
    void testGetIdNamePositive() {
      assertEquals("event_id", event.getIdName());
    }

    /**
     * This method tests the getRecipe_id method in the Event class. The result is
     * expected to be true.
     */
    @Test
    void testGetRecipeIdPositive() {
      assertEquals(2, event.getRecipeId());
    }

    /**
     * This method tests the getDate method in the Event class. The result is
     * expected to be true.
     */
    @Test
    void testGetDatePositive() {
      assertEquals(20240501, event.getDate());
    }
  }

  /**
   * This class contains negative tests for the Event class.
   */
  @Nested
  @DisplayName("Negative tests for the Event class.")
  class NegativeEventTest {

    /**
     * This method tests that an IllegalArgumentException is thrown when the
     * recipe_id is a lower integer than 1..
     */
    @Test
    void testVerifyRecipe_idIntegerException() {
      try {
        new Event(1, 0, 20240501);
        fail("This test failed, since it should have thrown an exception");
      } catch (IllegalArgumentException e) {
        assertEquals("The input for the parameter 'recipe_id' must be a positive number equal to or greater than 1",
            e.getMessage());
      }
    }

    /**
     * This method tests that an IllegalArgumentException is thrown when the date is
     * a negative number.
     */
    @Test
    void testVerifyDateIntegerException() {
      try {
        new Event(1, 2, -1234567);
        fail("This test failed, since it should have thrown an exception");
      } catch (IllegalArgumentException e) {
        assertEquals("The input for the parameter 'date' must be a positive number", e.getMessage());
      }
    }

    /**
     * This method tests that an IllegalArgumentException is thrown when the date is
     * not six figures..
     */
    @Test
    void testVerifyDateLengthException() {
      try {
        new Event(1, 2, 2024050);
        fail("This test failed, since it should have thrown an exception");
      } catch (IllegalArgumentException e) {
        assertEquals("The input for the parameter 'date' must have eight figures", e.getMessage());
      }
    }

    @Test
    void testVerifyDateMonthException() {
      try {
        new Event(1, 2, 20241413);
        fail("This test failed, since it should have thrown an exception");
      } catch (IllegalArgumentException e) {
        assertEquals("In the input for the parameter 'date', the 3rd and 4th figure must be a number between 01 and 12",
            e.getMessage());
      }
    }

    @Test
    void testVerifyDateDay31Exception() {
      try {
        new Event(1, 2, 20240532);
        fail("This test failed, since it should have thrown an exception");
      } catch (IllegalArgumentException e) {
        assertEquals("In the input for the parameter 'date', the 5th and 6th figure must be a number between 01 and 31",
            e.getMessage());
      }
    }

    @Test
    void testVerifyDateDay30Exception() {
      try {
        new Event(1, 2, 20240431);
        fail("This test failed, since it should have thrown an exception");
      } catch (IllegalArgumentException e) {
        assertEquals("In the input for the parameter 'date', the 5th and 6th figure must be a number between 01 and 30",
            e.getMessage());
      }
    }

    @Test
    void testVerifyDateDayFebLeapYearException() {
      try {
        new Event(1, 2, 20240230);
        fail("This test failed, since it should have thrown an exception");
      } catch (IllegalArgumentException e) {
        assertEquals("In the input for the parameter 'date', the 5th and 6th figure must be a number between 01 and 29",
            e.getMessage());
      }
    }

    @Test
    void testVerifyDateDayFebNotLeapYearException() {
      try {
        new Event(1, 2, 20250231);
        fail("This test failed, since it should have thrown an exception");
      } catch (IllegalArgumentException e) {
        assertEquals("In the input for the parameter 'date', the 5th and 6th figure must be a number between 01 and 28",
            e.getMessage());
      }
    }
  }
}
