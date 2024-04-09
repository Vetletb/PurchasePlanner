package no.ntnu.idatt1002.demo.data;

import no.ntnu.idatt1002.demo.data.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    event = new Event(1, 2, 240501);
  }

  /**
   * This class contains positive tests for the Event class.
   */
  @Nested
  @DisplayName("Positive tests for the Event class.")
  class PositiveEventTest {

    /**
     * This method tests the getAttributes method in the Event class. The result is expected to be true.
     */
    @Test
    void testGetAttributesPositive() {
      List<String> expectedAttributes = Arrays.asList("2", "240501");
      assertEquals(expectedAttributes, event.getAttributes());
    }

    /**
     * This method tests the getAttributeNames method in the Event class. The result is expected to be true.
     */
    @Test
    void testGetAttributeNamesPositive() {
      List<String> expectedAttributeNames = Arrays.asList("recipe_id", "date");
      assertEquals(expectedAttributeNames, event.getAttributeNames());
    }

    /**
     * This method tests the getId method in the Event class. The result is expected to be true.
     */
    @Test
    void testGetIdPositive() {
      assertEquals(1, event.getId());
    }

    /**
     * This method tests the getEvent_id method in the Event class. The result is expected to be true.
     */
    @Test
    void testGetEvent_id() {
      assertEquals(1, event.getEvent_id());
    }

    /**
     * This method tests the getRecipe_id method in the Event class. The result is expected to be true.
     */
    @Test
    void testGetIdNamePositive() {
      assertEquals("event_id", event.getIdName());
    }

    /**
     * This method tests the getRecipe_id method in the Event class. The result is expected to be true.
     */
    @Test
    void testGetRecipeIdPositive() {
      assertEquals(2, event.getRecipe_id());
    }

    /**
     * This method tests the getDate method in the Event class. The result is expected to be true.
     */
    @Test
    void testGetDatePositive() {
      assertEquals(240501, event.getDate());
    }

    /**
     * This method tests the toString method in the Event class. The result is expected to be true.
     */
    @Test
    void testToStringPositive() {
      assertEquals("Event ID: 1, Recipe ID: 2, Date: 240501", event.toString());
    }
  }

  /**
   * This class contains negative tests for the Event class.
   */
  @Nested
  @DisplayName("Negative tests for the Event class.")
  class NegativeEventTest {

    /**
     * This method tests that an IllegalArgumentException is thrown when the recipe_id is a lower integer than 1..
     */
    @Test
    void testVerifyRecipe_idIntegerException() {
      try {
        new Event(1, 0, 240501);
        fail("This test failed, since it should have thrown an exception");
      } catch (IllegalArgumentException e) {
        assertEquals("The input for the parameter 'recipe_id' must be a positive number equal to or greater than 1", e.getMessage());
      }
    }

    /**
     * This method tests that an IllegalArgumentException is thrown when the date is a negative number.
     */
    @Test
    void testVerifyDateIntegerException() {
      try {
        new Event(1, 2, -12345);
        fail("This test failed, since it should have thrown an exception");
      } catch (IllegalArgumentException e) {
        assertEquals("The input for the parameter 'date' must be a positive number", e.getMessage());
      }
    }

    /**
     * This method tests that an IllegalArgumentException is thrown when the date is not six figures..
     */
    @Test
    void testVerifyDateLengthException() {
      try {
        new Event(1, 2, 24050);
        fail("This test failed, since it should have thrown an exception");
      } catch (IllegalArgumentException e) {
        assertEquals("The input for the parameter 'date' must have six figures", e.getMessage());
      }
    }


  }
}
