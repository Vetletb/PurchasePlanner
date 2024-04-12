package no.ntnu.idatt1002.demo.repo;

import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.data.InventoryItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class InventoryTest {

  @Mock
  private DAO dao;

  private Inventory inventory;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    inventory = new Inventory(dao);
  }

  @Nested
  @DisplayName("Positive tests")
  class PositiveInventoryTest {

    @Test
    void testConstructorDoesNotThrowExceptionOnValidParameters() {
      assertDoesNotThrow(() -> new Inventory(dao));
    }

    @Test
    void testGetInventoryItems() {
      assertNotNull(inventory.getInventoryItems());
    }

    @Test
    void testGetInventoryItemsByCategorySetsCorrectAmountOfInventoryItem() {
      when(dao.filterFromTable("InventoryItem", "category", "vegetable", "item", "item_id"))
          .thenReturn(List.of(
              List.of("1", "21012023", "1", "2", "kg", "1", "carrot", "vegetable", "allergy"),
              List.of("2", "21012023", "2", "2", "kg", "1", "potato", "vegetable", "allergy"),
              List.of("3", "21012023", "3", "2", "kg", "1", "onion", "vegetable", "allergy")));
      inventory.filterInventoryByCategory("vegetable");
      assertEquals(3, inventory.getInventoryItems().size());
    }

    @Test
    void testGetInventoryItemsByNameSetsCorrectAmountOfInventoryItem() {
      when(dao.searchFromTable("InventoryItem", "carrot", "item", "item_id"))
          .thenReturn(List.of(
              List.of("1", "21012023", "1", "2", "kg", "1", "carrot", "vegetable", "allergy")));
      inventory.searchInventoryByName("carrot");
      assertEquals(1, inventory.getInventoryItems().size());
    }

    @Test
    void testGetAllInventoryItemsSetsCorrectAmountOfInventoryItem() {
      when(dao.getAllFromTable("InventoryItem", "item", "item_id"))
          .thenReturn(List.of(
              List.of("1", "21012023", "1", "2", "kg", "1", "carrot", "vegetable", "allergy"),
              List.of("2", "21012023", "2", "2", "kg", "1", "potato", "vegetable", "allergy"),
              List.of("3", "21012023", "3", "2", "kg", "1", "onion", "vegetable", "allergy"),
              List.of("4", "21012023", "4", "2", "kg", "1", "apple", "fruit", "allergy")));
      inventory.getAllInventroyItems();
      assertEquals(4, inventory.getInventoryItems().size());
    }

    @Test
    void testAddInventoryItemDoesNotTrowExceptionOnValidParameters() {
      assertDoesNotThrow(() -> inventory.addInventoryItem(11, 1,"Kg", 21012023));
    }
  }

  @Test
  @DisplayName("Delete inventory item")
  void testDeleteInventoryItemDoesNotTrowExceptionOnValidParameters() {
    when(dao.getAllFromTable("InventoryItem", "item", "item_id"))
        .thenReturn(List.of(
            List.of("1", "21012023", "1", "2", "kg", "1", "carrot", "vegetable", "allergy"),
            List.of("2", "21012023", "2", "2", "kg", "1", "potato", "vegetable", "allergy"),
            List.of("3", "21012023", "3", "2", "kg", "1", "onion", "vegetable", "allergy"),
            List.of("4", "21012023", "4", "2", "kg", "1", "apple", "fruit", "allergy")));
    inventory.getAllInventroyItems();
    assertDoesNotThrow(() -> inventory.deleteInventoryItem(1));
  }

  @Test
  @DisplayName("Update inventory item")
  void testUpdateInventoryItemDoesNotTrowExceptionOnValidParameters() {
    when(dao.getAllFromTable("InventoryItem", "item", "item_id"))
        .thenReturn(List.of(
            List.of("1", "21012023", "1", "2", "kg", "1", "carrot", "vegetable", "allergy"),
            List.of("2", "21012023", "2", "2", "kg", "1", "potato", "vegetable", "allergy"),
            List.of("3", "21012023", "3", "2", "kg", "1", "onion", "vegetable", "allergy"),
            List.of("4", "21012023", "4", "2", "kg", "1", "apple", "fruit", "allergy")));
    inventory.getAllInventroyItems();
    assertDoesNotThrow(() -> inventory.updateInventoryItem(1, 1, 2, "kg", 21012023));
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {

    @Test
    @DisplayName("Test constructor")
    public void constructorThrowsExceptionOnNullDAO() {
      assertThrows(IllegalArgumentException.class, () -> new Inventory(null));
    }

    @Test
    @DisplayName("Test filterItemsByCategory")
    public void filterItemsByCategoryThrowsExceptionOnEmptyCategory() {
      assertThrows(IllegalArgumentException.class, () -> inventory.filterInventoryByCategory(" "));
    }

    @Test
    @DisplayName("Test searchItemsByName")
    public void searchItemsByNameThrowsExceptionOnEmptyName() {
      assertThrows(IllegalArgumentException.class, () -> inventory.searchInventoryByName(""));
    }

    @Test
    @DisplayName("Test deleteItem")
    public void deleteItemThrowsExceptionOnNonExistingItem() {
      assertThrows(IllegalArgumentException.class, () -> inventory.deleteInventoryItem(5));
    }

    @Test
    @DisplayName("Test updateItem")
    public void updateItemThrowsExceptionOnNonExistingItem() {
      assertThrows(IllegalArgumentException.class, () -> inventory.updateInventoryItem(5, 1, 2, "kg", 31022022));
    }
  }
}