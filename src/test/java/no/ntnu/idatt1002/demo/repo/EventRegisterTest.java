package no.ntnu.idatt1002.demo.repo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
 * Test class for the EventRegister class. Contain positive and negative tests
 * for the methods.
 */
class EventRegisterTest {
  @Mock
  private DAO dao;

  private EventRegister eventRegister;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    eventRegister = new EventRegister(dao);
  }

  @Nested
  @DisplayName("Positive tests")
  class PositiveTest {

    @Test
    @DisplayName("Test constructor")
    void constructorDoesNotThrowExceptionOnValidParameters() {
      assertDoesNotThrow(() -> new EventRegister(dao));
    }

    @Test
    @DisplayName("Test getAllEvents")
    void getAllEventsSetsCorrectAmountOfItems() {
      when(dao.getAllFromTable("Event", "Recipe", "recipe_id"))
          .thenReturn(List.of(
              List.of("1", "1", "20241003", "1", "name", "10", "category"),
              List.of("2", "2", "20241003", "2", "name", "12", "category"),
              List.of("3", "3", "20241003", "3", "name", "9", "category"),
              List.of("4", "4", "20241003", "4", "name", "3", "category")));
      eventRegister.getAllEvents();
      assertEquals(4, eventRegister.getEventsAsList().size());
    }

    @Test
    @DisplayName("Test getEventsByDate")
    void getEventsByDateSetsCorrectAmountOfItems() {
      when(dao.filterFromTable("Event", "date", "20241013", "recipe", "recipe_id"))
          .thenReturn(List.of(
              List.of("1", "1", "20241013", "1", "name", "10", "category"),
              List.of("2", "2", "20241013", "2", "name", "12", "category"),
              List.of("3", "3", "20241013", "3", "name", "9", "category"),
              List.of("4", "4", "20241013", "4", "name", "3", "category")));
      eventRegister.getEventsByDate(20241013);
      assertEquals(4, eventRegister.getEventsAsList().size());
    }

    @Test
    @DisplayName("Test addEvent")
    void addEventDoesNotThrowExceptionOnValidParameters() {
      assertDoesNotThrow(() -> eventRegister.addEvent(1, 11111111));
    }

    @Test
    @DisplayName("Test getEvent")
    void getEventsReturnsCorrectItems() {
      assertNotNull(eventRegister.getEvents());
    }

    @Test
    @DisplayName("Test getEventAsList")
    void getEventsAsListReturnsCorrectItems() {
      assertNotNull(eventRegister.getEventsAsList());
    }

    @Test
    @DisplayName("Test getEventById")
    void getEventsByIdSetsCorrectAmountOfItems() {
      when(dao.filterFromTable("Event", "event_id", "1", "Recipe", "recipe_id"))
          .thenReturn(List.of(
              List.of("1", "1", "20241003", "1", "name", "10", "category")));
      eventRegister.getEventById(1);
      assertEquals(1, eventRegister.getEventsAsList().size());
    }

    @Nested
    @DisplayName("Setup events")
    class SetupEventsBeforeTests {
      @BeforeEach
      void setUp() {
        when(dao.getAllFromTable("Event", "Recipe", "recipe_id"))
            .thenReturn(List.of(
                List.of("1", "1", "20241003", "1", "name", "10", "category"),
                List.of("2", "2", "20241003", "2", "name", "12", "category"),
                List.of("3", "3", "20241003", "3", "name", "9", "category"),
                List.of("4", "4", "20241003", "4", "name", "3", "category")));
        eventRegister.getAllEvents();
      }

      @Test
      @DisplayName("Test deleteEvent")
      void deleteEventDoesNotThrowExceptionOnValidParameters() {
        assertDoesNotThrow(() -> eventRegister.deleteEvent(1));
      }

      @Test
      @DisplayName("Test updateEvent")
      void updateEventDoesNotThrowExceptionOnValidParameters() {
        assertDoesNotThrow(() -> eventRegister.updateEvent(3, 2, 20240808));
      }
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTest {

    @Test
    @DisplayName("Test constructor")
    void constructorThrowsExceptionOnNullDAO() {
      assertThrows(IllegalArgumentException.class, () -> new EventRegister(null));
    }

    @Test
    @DisplayName("Test getEventsByDate")
    void getEventsByDateThrowsExceptionOnWrongFormatDate() {
      assertThrows(IllegalArgumentException.class, () -> eventRegister.getEventsByDate(100));
    }

    @Test
    @DisplayName("Test deleteItem")
    void deleteItemThrowsExceptionOnNonExistingItem() {
      assertThrows(IllegalArgumentException.class, () -> eventRegister.deleteEvent(5));
    }

    @Test
    @DisplayName("Test updateItem")
    void updateItemThrowsExceptionOnNonExistingItem() {
      assertThrows(IllegalArgumentException.class, () -> eventRegister.updateEvent(5, 5, 101010));
    }
  }
}