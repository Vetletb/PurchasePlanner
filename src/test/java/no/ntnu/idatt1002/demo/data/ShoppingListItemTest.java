package no.ntnu.idatt1002.demo.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for the ShoppingListItem class. Contains tests for the constructor and
 * all the getter methods.
 * Both positive and negative.
 */
class ShoppingListItemTest {
  private ShoppingListItem shoppingListItem;

  @BeforeEach
  public void setUp() {
    shoppingListItem = new ShoppingListItem(2, 14, "milk", "dairy", "lactose", 2, "liters");
  }

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {
    @Test
    void testGetAttributes() {
      List<String> expected = List.of("14", "2", "liters");
      assertEquals(expected, shoppingListItem.getAttributes());
    }

    @Test
    @DisplayName("Test getAttributeNames")
    void testGetAttributeNames() {
      List<String> expected = List.of("item_id", "quantity", "unit");
      assertEquals(expected, shoppingListItem.getAttributeNames());
    }

    @Test
    @DisplayName("Test getId")
    void testGetId() {
      assertEquals(2, shoppingListItem.getId());
    }

    @Test
    @DisplayName("Test getName")
    void testGetName() {
      assertEquals("milk", shoppingListItem.getName());
    }

    @Test
    @DisplayName("Test getCategory")
    void testGetCategory() {
      assertEquals("dairy", shoppingListItem.getCategory());
    }

    @Test
    @DisplayName("Test getAllergy")
    void testGetAllergy() {
      assertEquals("lactose", shoppingListItem.getAllergy());
    }

    @Test
    void testGetQuantity() {
      assertEquals(2, shoppingListItem.getQuantity());
    }

    @Test
    void testGetUnit() {
      assertEquals("liters", shoppingListItem.getUnit());
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {
    @Test
    @DisplayName("Test constructor with Empty name")
    void testConstructorEmptyName() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new ShoppingListItem(14, "", "dairy", "lactose", 2, "liters"));
    }

    @Test
    @DisplayName("Test constructor with Blank category")
    void testConstructorBlankCategory() {
      assertThrows(IllegalArgumentException.class,
          () -> new ShoppingListItem(14, "milk", "  ", "lactose", 2, "liters"));
    }

    @Test
    @DisplayName("Test constructor with negative quantity")
    void testConstructorNegativeQuantity() {
      assertThrows(IllegalArgumentException.class,
          () -> new ShoppingListItem(14, "milk", "dairy", "lactose", -2, "liters"));
    }

    @Test
    @DisplayName("Test constructor with null unit")
    void testConstructorNullUnit() {
      assertThrows(IllegalArgumentException.class,
          () -> new ShoppingListItem(14, "milk", "dairy", "lactose", 2, null));
    }
  }
}