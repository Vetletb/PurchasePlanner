package no.ntnu.idatt1002.demo.repo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import no.ntnu.idatt1002.demo.dao.DAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Test class for the ShoppingListItemRegister class. Contains positive and negative tests for the methods.
 */
class ShoppingListItemRegisterTest {
  ShoppingListItemRegister shoppingListItemRegister;

  @Mock
  DAO dao;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    shoppingListItemRegister = new ShoppingListItemRegister(dao);
  }

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {

    @Test
    @DisplayName("Test constructor")
    public void constructorDoesNotThrowExceptionOnValidParameters() {
      assertDoesNotThrow(() -> new ShoppingListItemRegister(dao));
    }

    @Test
    @DisplayName("Test getItems")
    public void getItemsReturnsCorrectItems() {
      assertNotNull(shoppingListItemRegister.getItems());
    }

    @Test
    @DisplayName("Test getAllItems")
    public void getAllItemsReturnsCorrectAmountOfItems() {
      when(dao.getAllFromTable("ShoppingListItem", "Item", "item_id"))
          .thenReturn(List.of(
              List.of("1", "1", "1", "unit"),
              List.of("2", "2", "2", "unit"),
              List.of("3", "3", "3", "unit")));
      when(dao.filterFromTable("Item", "item_id", "1", null, null))
          .thenReturn(List.of(
              List.of("1", "name", "category", "allergy")));
      when(dao.filterFromTable("Item", "item_id", "2", null, null))
          .thenReturn(List.of(
              List.of("2", "name", "category", "allergy")));
      when(dao.filterFromTable("Item", "item_id", "3", null, null))
          .thenReturn(List.of(
              List.of("3", "name", "category", "allergy")));

      shoppingListItemRegister.getAllItems();
      assertEquals(3, shoppingListItemRegister.getItems().size());
    }

    @Test
    @DisplayName("Test addToShoppingList")
    public void addToShoppingListDoesNotThrowExceptionOnValidParameters() {
      assertDoesNotThrow(() -> shoppingListItemRegister.addToShoppingList(1, 2, "unit"));
    }

    @Test
    @DisplayName("Test searchShoppingListByName")
    public void searchShoppingListByNameSetsCorrectAmountOfItems() {
      when(dao.searchFromTable("ShoppingListItem", "cucumber", "Item", "item_id"))
          .thenReturn(List.of(
              List.of("1", "1", "1", "unit"),
              List.of("2", "2", "2", "unit")));
      when(dao.filterFromTable("Item", "item_id", "1", null, null))
          .thenReturn(List.of(
              List.of("1", "cucumber", "category", "allergy")));
      when(dao.filterFromTable("Item", "item_id", "2", null, null))
          .thenReturn(List.of(
              List.of("2", "cucumber", "category", "allergy")));

      shoppingListItemRegister.searchItemsByName("cucumber");
      assertEquals(2, shoppingListItemRegister.getItems().size());
    }

    @Nested
    @DisplayName("Setup items")
    class SetupItemsBeforeTests {
      @BeforeEach
      void setUp() {
        when(dao.getAllFromTable("ShoppingListItem", "Item", "item_id"))
            .thenReturn(List.of(
                List.of("1", "1", "1", "unit"),
                List.of("2", "2", "2", "unit"),
                List.of("3", "3", "3", "unit")));
        when(dao.filterFromTable("Item", "item_id", "1", null, null))
            .thenReturn(List.of(
                List.of("1", "name", "category", "allergy")));
        when(dao.filterFromTable("Item", "item_id", "2", null, null))
            .thenReturn(List.of(
                List.of("2", "name", "category", "allergy")));
        when(dao.filterFromTable("Item", "item_id", "3", null, null))
            .thenReturn(List.of(
                List.of("3", "name", "category", "allergy")));
        shoppingListItemRegister.getAllItems();
      }

      @Test
      @DisplayName("Test deleteFromShoppingList")
      public void deleteFromShoppingListDoesNotThrowExceptionOnValidParameters() {
        assertDoesNotThrow(() -> shoppingListItemRegister.deleteFromShoppingList(1));
      }

      @Test
      @DisplayName("Test updateShoppingListItem")
      public void updateShoppingListItemDoesNotThrowExceptionOnValidParameters() {
        assertDoesNotThrow(() -> shoppingListItemRegister.updateShoppingListItem(1, 2, "unit"));
      }
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {

    @Test
    @DisplayName("Test constructor")
    public void constructorThrowsExceptionOnNullDAO() {
      assertThrows(IllegalArgumentException.class, () -> new ShoppingListItemRegister(null));
    }

    @Test
    @DisplayName("Test deleteFromShoppingList")
    public void deleteFromShoppingListThrowsExceptionOnNonExistingId() {
      assertThrows(IllegalArgumentException.class, () -> shoppingListItemRegister.deleteFromShoppingList(4));
    }

    @Test
    @DisplayName("Test updateShoppingListItem")
    public void updateShoppingListItemThrowsExceptionOnNonExistingId() {
      assertThrows(IllegalArgumentException.class, () -> shoppingListItemRegister.updateShoppingListItem(4, 2, "unit"));
    }

    @Test
    @DisplayName("Test searchShoppingListByName")
    public void searchShoppingListByNameThrowsExceptionOnEmptyName() {
      assertThrows(IllegalArgumentException.class, () -> shoppingListItemRegister.searchItemsByName(""));
    }
  }
}