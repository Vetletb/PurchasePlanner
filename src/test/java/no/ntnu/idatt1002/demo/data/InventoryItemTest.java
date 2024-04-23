package no.ntnu.idatt1002.demo.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for InventoryItem class. Tests for the constructor and all the getter
 * methods.
 * Both positive and negative.
 */
class InventoryItemTest {
  private InventoryItem inventoryItem;

  @BeforeEach
  public void setUp() {
    inventoryItem = new InventoryItem(2, 14, "milk", "dairy", "lactose", 2, "liters", 20240524);
  }

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {
    @Test
    void testGetAttributes() {
      List<String> expected = List.of("14", "2", "liters", "20240524");
      assertEquals(expected, inventoryItem.getAttributes());
    }

    @Test
    @DisplayName("Test getAttributeNames")
    void testGetAttributeNames() {
      List<String> expected = List.of("item_id", "quantity", "unit", "expiration_date");
      assertEquals(expected, inventoryItem.getAttributeNames());
    }

    @Test
    @DisplayName("Test getId")
    void testGetId() {
      assertEquals(2, inventoryItem.getId());
    }

    @Test
    @DisplayName("Test getName")
    void testGetName() {
      assertEquals("milk", inventoryItem.getName());
    }

    @Test
    @DisplayName("Test getCategory")
    void testGetCategory() {
      assertEquals("dairy", inventoryItem.getCategory());
    }

    @Test
    @DisplayName("Test getAllergy")
    void testGetAllergy() {
      assertEquals("lactose", inventoryItem.getAllergy());
    }

    @Test
    @DisplayName("Test getQuantity")
    void testGetQuantity() {
      assertEquals(2, inventoryItem.getQuantity());
    }

    @Test
    @DisplayName("Test getUnit")
    void testGetUnit() {
      assertEquals("liters", inventoryItem.getUnit());
    }

    @Test
    @DisplayName("Test getExpirationDate")
    void testGetExpirationDate() {
      assertEquals(20240524, inventoryItem.getExpirationDate());
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {
    @Test
    @DisplayName("Test constructor with null name")
    void testConstructorNullName() {
      assertThrows(IllegalArgumentException.class,
          () -> new InventoryItem(14, 2, null, "dairy", "lactose", 2, "liters", 150524));
    }

    @Test
    @DisplayName("Test constructor with Empty  category")
    void testConstructorEmptyCategory() {
      assertThrows(IllegalArgumentException.class,
          () -> new InventoryItem(14, 2, "milk", " ", "lactose", 2, "liters", 150524));
    }

    @Test
    @DisplayName("Test constructor with zero quantity")
    void testConstructorNegativeQuantity() {
      assertThrows(IllegalArgumentException.class,
          () -> new InventoryItem(14, 2, "milk", "dairy", "lactose", 0, "liters", 150524));
    }

    @Test
    @DisplayName("Test constructor with null unit")
    void testConstructorNullUnit() {
      assertThrows(IllegalArgumentException.class,
          () -> new InventoryItem(14, 2, "milk", "dairy", "lactose", 2, null, 150524));
    }

    @Test
    @DisplayName("Test constructor with negative expiration date")
    void testConstructorNegativeExpirationDate() {
      assertThrows(IllegalArgumentException.class,
          () -> new InventoryItem(14, 2, "milk", "dairy", "lactose", 2, "liters", -150));
    }

    @Test
    @DisplayName("Test constructor with expiration date with wrong length")
    void testConstructorExpirationDateWrongLength() {
      assertThrows(IllegalArgumentException.class,
          () -> new InventoryItem(14, 2, "milk", "dairy", "lactose", 2, "liters", 1505241));
    }

  }
}