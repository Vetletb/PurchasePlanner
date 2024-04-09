package no.ntnu.idatt1002.demo.data;

import no.ntnu.idatt1002.demo.data.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Item class. Contain tests for the constructor and all the getter methods.
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
    public void testGetAttributes() {
      List<String> expected = List.of("milk", "dairy", "lactose");
      assertEquals(expected, item.getAttributes());
    }

    @Test
    @DisplayName("Test getAttributeNames")
    public void testGetAttributeNames() {
      List<String> expected = List.of("name", "category", "allergy");
      assertEquals(expected, item.getAttributeNames());
    }

    @Test
    @DisplayName("Test geId")
    public void testGetId() {
      assertEquals(14, item.getId());
    }

    @Test
    @DisplayName("Test getName")
    public void testGetName() {
      assertEquals("milk", item.getName());
    }

    @Test
    @DisplayName("Test getCategory")
    public void testGetCategory() {
      assertEquals("dairy", item.getCategory());
    }

    @Test
    @DisplayName("Test getAllergy")
    public void testGetAllergy() {
      assertEquals("lactose", item.getAllergy());
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {

    @Test
    @DisplayName("Test constructor with null name")
    public void testConstructorNullName() {
      assertThrows(IllegalArgumentException.class, () -> new Item(14, null, "dairy", "lactose"));
    }

    @Test
    @DisplayName("Test constructor with blank category")
    public void testConstructorBlankCategory() {
      assertThrows(IllegalArgumentException.class, () -> new Item(14, "milk", "   ", "lactose"));
    }
  }
}
