package no.ntnu.idatt1002.demo.repo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.data.Event;
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
                List.of("1", "1", "20240420", "1", "name", "10", "category"),
                List.of("2", "2", "20240421", "2", "name", "12", "category"),
                List.of("3", "3", "20240422", "3", "name", "9", "category"),
                List.of("4", "4", "20240423", "4", "name", "3", "category")));
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

      @Test
      @DisplayName("Test removePassedEvents")
      void removeOldEventsRemovesOldEvents() {
        Event event = new Event(1, 20240420);
        Event event2 = new Event(2, 20240421);
        Event event3 = new Event(3, 20240422);
        Event event4 = new Event(4, 20240423);

        List<Event> events = new java.util.ArrayList<>(List.of(event, event2, event3, event4));
        assertDoesNotThrow(() -> eventRegister.removePassedEvents());

        int weekStartDate = 20240422;

        assertTrue(event.getDate() < weekStartDate);
        assertTrue(event2.getDate() < weekStartDate);
        assertFalse(event3.getDate() < weekStartDate);
        assertFalse(event4.getDate() < weekStartDate);


        events.removeIf(e -> e.getDate() < weekStartDate);
        assertEquals(2, events.size());
        assertEquals(3, events.get(0).getRecipeId());
        assertEquals(4, events.get(1).getRecipeId());
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