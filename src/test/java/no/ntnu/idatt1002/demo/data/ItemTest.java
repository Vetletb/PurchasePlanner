package no.ntnu.idatt1002.demo.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Item class. Contain tests for the constructor and all the
 * getter methods.
 * both positive and negative tests.
 */
public class ItemTest {
  private Item item;

  @BeforeEach
  public void setUp() {
    item = new Item(14, "milk", "dairy", "lactose");
  }

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {

    @Test
    @DisplayName("Test getAttributes")
    void testGetAttributes() {
      List<String> expected = List.of("milk", "dairy", "lactose");
      assertEquals(expected, item.getAttributes());
    }

    @Test
    @DisplayName("Test getAttributeNames")
    void testGetAttributeNames() {
      List<String> expected = List.of("name", "category", "allergy");
      assertEquals(expected, item.getAttributeNames());
    }

    @Test
    @DisplayName("Test geId")
    void testGetId() {
      assertEquals(14, item.getId());
    }

    @Test
    @DisplayName("Test getName")
    void testGetName() {
      assertEquals("milk", item.getName());
    }

    @Test
    @DisplayName("Test getCategory")
    void testGetCategory() {
      assertEquals("dairy", item.getCategory());
    }

    @Test
    @DisplayName("Test getAllergy")
    void testGetAllergy() {
      assertEquals("lactose", item.getAllergy());
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {

    @Test
    @DisplayName("Test constructor with null name")
    void testConstructorNullName() {
      assertThrows(IllegalArgumentException.class, () -> new Item(14, null, "dairy", "lactose"));
    }

    @Test
    @DisplayName("Test constructor with blank category")
    void testConstructorBlankCategory() {
      assertThrows(IllegalArgumentException.class, () -> new Item(14, "milk", "   ", "lactose"));
    }
  }
}
