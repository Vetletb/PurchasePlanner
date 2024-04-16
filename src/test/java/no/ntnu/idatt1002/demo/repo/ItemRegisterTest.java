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
 * Test class for the ItemRegister class. Contain positive and negative tests for the methods.
 */
class ItemRegisterTest {
  ItemRegister itemRegister;

  @Mock
  DAO dao;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    itemRegister = new ItemRegister(dao);
  }

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {

    @Test
    @DisplayName("Test constructor")
    public void constructorDoesNotThrowExceptionOnValidParameters() {
      assertDoesNotThrow(() -> new ItemRegister(dao));
    }

    @Test
    @DisplayName("Test getItems")
    public void getItemsReturnsCorrectItems() {
      assertNotNull(itemRegister.getItems());
    }

    @Test
    @DisplayName("Test filterItemsByCategory")
    public void filterItemsByCategorySetsCorrectAmountOfItems() {
      when(dao.filterFromTable("Item", "category", "vegetable", null, null))
          .thenReturn(List.of(
              List.of("1", "name", "vegetable", "allergy"),
              List.of("2", "name", "vegetable", "description"),
              List.of("3", "name", "vegetable", "description")));
      itemRegister.filterItemsByCategory("vegetable");
        assertEquals(3, itemRegister.getItems().size());
    }

    @Test
    @DisplayName("Test searchItemsByName")
    public void searchItemsByNameSetsCorrectAmountOfItems() {
      when(dao.searchFromTable("Item", "cucumber", null, null))
          .thenReturn(List.of(
              List.of("1", "cucumber", "category", "allergy"),
              List.of("2", "cucumber", "category", "description")));
      itemRegister.searchItemsByName("cucumber");
        assertEquals(2, itemRegister.getItems().size());
    }

    @Test
    @DisplayName("Test getAllItems")
    public void getAllItemsSetsCorrectAmountOfItems() {
      when(dao.getAllFromTable("Item", null, null))
          .thenReturn(List.of(
              List.of("1", "name", "category", "allergy"),
              List.of("2", "name", "category", "allergy"),
              List.of("3", "name", "category", "allergy"),
              List.of("4", "name", "category", "allergy")));
      itemRegister.getAllItems();
        assertEquals(4, itemRegister.getItems().size());
    }

    @Test
    @DisplayName("Test addItem")
    public void addItemDoesNotThrowExceptionOnValidParameters() {
      assertDoesNotThrow(() -> itemRegister.addItem("name", "category", "allergy"));
    }

    @Nested
    @DisplayName("Setup items")
    class SetupItemsBeforeTests {
      @BeforeEach
      void setUp() {
          when(dao.getAllFromTable("Item", null, null))
              .thenReturn(List.of(
                  List.of("1", "name", "category", "allergy"),
                  List.of("2", "name", "category", "allergy"),
                  List.of("3", "name", "category", "allergy"),
                  List.of("4", "name", "category", "allergy")));
          itemRegister.getAllItems();
      }

      @Test
      @DisplayName("Test deleteItem")
      public void deleteItemDoesNotThrowExceptionOnValidParameters() {
        assertDoesNotThrow(() -> itemRegister.deleteItem(1));
      }

      @Test
      @DisplayName("Test updateItem")
      public void updateItemDoesNotThrowExceptionOnValidParameters() {
        assertDoesNotThrow(() -> itemRegister.updateItem(1, "name2", "category2", "allergy2"));
      }
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {

    @Test
    @DisplayName("Test constructor")
    public void constructorThrowsExceptionOnNullDAO() {
      assertThrows(IllegalArgumentException.class, () -> new ItemRegister(null));
    }

    @Test
    @DisplayName("Test filterItemsByCategory")
    public void filterItemsByCategoryThrowsExceptionOnEmptyCategory() {
      assertThrows(IllegalArgumentException.class, () -> itemRegister.filterItemsByCategory(" "));
    }

    @Test
    @DisplayName("Test searchItemsByName")
    public void searchItemsByNameThrowsExceptionOnEmptyName() {
      assertThrows(IllegalArgumentException.class, () -> itemRegister.searchItemsByName(""));
    }

    @Test
    @DisplayName("Test deleteItem")
    public void deleteItemThrowsExceptionOnNonExistingItem() {
      assertThrows(IllegalArgumentException.class, () -> itemRegister.deleteItem(5));
    }

    @Test
    @DisplayName("Test updateItem")
    public void updateItemThrowsExceptionOnNonExistingItem() {
      assertThrows(IllegalArgumentException.class, () -> itemRegister.updateItem(5, "name", "category", "allergy"));
    }
  }
}