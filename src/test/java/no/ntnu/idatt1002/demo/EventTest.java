package no.ntnu.idatt1002.demo;

import no.ntnu.idatt1002.demo.data.Event;
import no.ntnu.idatt1002.demo.data.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
  private Event event;

  @BeforeEach
  void setUp() {
    event = new Event(1, 2, 240501);
  }

  @Nested
  @DisplayName("Positive tests for the Event class.")
  class PositiveEventTest {

    @Test
    void testGetAttributesPositive() {
      List<String> expectedAttributes = Arrays.asList("2", "240501");
      assertEquals(expectedAttributes, event.getAttributes());
    }

    @Test
    void testGetAttributeNamesPositive() {
      List<String> expectedAttributeNames = Arrays.asList("recipe_id", "date");
      assertEquals(expectedAttributeNames, event.getAttributeNames());
    }

    @Test
    void testGetIdPositive() {
      assertEquals(1, event.getId());
    }

    @Test
    void testGetEvent_id() {
      assertEquals(1, event.getEvent_id());
    }

    @Test
    void testGetIdNamePositive() {
      assertEquals("event_id", event.getIdName());
    }

    @Test
    void testGetRecipeIdPositive() {
      assertEquals(2, event.getRecipe_id());
    }

    @Test
    void testGetDatePositive() {
      assertEquals(240501, event.getDate());
    }

    @Test
    void testToStringPositive() {
      assertEquals("Event ID: 1, Recipe ID: 2, Date: 240501", event.toString());
    }
  }

  @Nested
  @DisplayName("Negative tests for the Event class.")
  class NegativeEventTest {

    @Test
    void testVerifyRecipe_idIntegerException() {
      try {
        new Event(1, 0, 240501);
        fail("This test failed, since it should have thrown an exception");
      } catch (IllegalArgumentException e) {
        assertEquals("The input for the parameter 'recipe_id' must be a positive number equal to or greater than 1", e.getMessage());
      }
    }

    @Test
    void testVerifyDateIntegerException() {
      try {
        new Event(1, 2, -12345);
        fail("This test failed, since it should have thrown an exception");
      } catch (IllegalArgumentException e) {
        assertEquals("The input for the parameter 'date' must be a positive number", e.getMessage());
      }
    }
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
