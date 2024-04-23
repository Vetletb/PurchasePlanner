package no.ntnu.idatt1002.demo.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class QuantityUnitTest {

  private QuantityUnit quantityUnit;

  @BeforeEach
  void setUp() {
    quantityUnit = new QuantityUnit(2, "dl");
  }

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {
    @Test
    @DisplayName("Test constructor")
    void testConstructor() {
      try {
        QuantityUnit quantityUnit = new QuantityUnit(2, "dl");
        assertEquals(2, quantityUnit.getQuantity());
      } catch (Exception e) {
        fail("Should not have thrown any exception");
      }
    }

    @Test
    @DisplayName("Test constructor with zero quantity")
    void testConstructorWithZeroQuantity() {
      try {
        QuantityUnit quantityUnit = new QuantityUnit(0, "dl");
        assertEquals(0, quantityUnit.getQuantity());
      } catch (Exception e) {
        fail("Should not have thrown any exception");
      }
    }

    @Test
    @DisplayName("Test getQuantity")
    void testGetQuantity() {
      assertEquals(2, quantityUnit.getQuantity());
    }

    @Test
    @DisplayName("Test getUnit")
    void testGetUnit() {
      assertEquals("dl", quantityUnit.getUnit());
    }

    @Test
    @DisplayName("Test addQuantity")
    void testAddQuantity() {
      quantityUnit.addQuantity(3);
      assertEquals(5, quantityUnit.getQuantity());
    }

    @Test
    @DisplayName("Test toString")
    void testToString() {
      assertEquals("2 : dl", quantityUnit.toString());
    }

    @Test
    @DisplayName("Test addQuantity with negative quantity")
    void testAddQuantityWithNegativeQuantity() {
      try {
        quantityUnit.addQuantity(-3);
        assertEquals(-1, quantityUnit.getQuantity());
      } catch (Exception e) {
        fail("Should not have thrown any exception");
      }
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {
    @Test
    @DisplayName("Test constructor with negative quantity")
    void testConstructorWithNegativeQuantity() {
      Executable executable = () -> new QuantityUnit(-2, "dl");
      assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    @DisplayName("Test constructor with empty unit")
    void testConstructorWithEmptyUnit() {
      Executable executable = () -> new QuantityUnit(2, "");
      assertThrows(IllegalArgumentException.class, executable);
    }
  }
}