package no.ntnu.idatt1002.demo.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.data.Event;
import no.ntnu.idatt1002.demo.util.VerifyInput;

/**
 * This class represents a register for events.
 * Allowing for communication between the database and the user interface.
 */
public class EventRegister {
  private List<Event> events;
  private final DAO dao;

  /**
   * Constructor for the EventRegister class.
   *
   * @param dao the data access object
   * @throws IllegalArgumentException if dao is null
   */
  public EventRegister(DAO dao) {
    VerifyInput.verifyNotNull(dao, "dao");
    this.dao = dao;
    this.events = new ArrayList<>();
  }

  /**
   * This method retrieves all events from the database.
   */
  public void getAllEvents() {
    events = new ArrayList<>();
    List<List<String>> allEvents = dao.getAllFromTable("Event", "Recipe", "recipe_id");
    packageToEvent(allEvents);
  }

  /**
   * Adds an event to the database.
   *
   * @param recipeId the id of the recipe
   * @param date     the date of the event
   */
  public void addEvent(int recipeId, int date) {
    Event event = new Event(recipeId, date);
    dao.addToDatabase(event);
  }

  /**
   * Returns the index of the event with the given id.
   *
   * @param id the id of the event
   * @return the index of the event
   */
  private int getIndexFromEvent(int id) {
    for (int i = 0; i < events.size(); i++) {
      if (events.get(i).getId() == id) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Deletes an event from the database.
   *
   * @param id the id of the event to be deleted
   * @throws IllegalArgumentException if the event does not exist
   */
  public void deleteEvent(int id) {
    int index = getIndexFromEvent(id);
    if (index == -1) {
      throw new IllegalArgumentException("Event with id " + id + " does not exist.");
    }
    dao.deleteFromDatabase(events.get(index));
  }

  /**
   * Gets the events by the recipe id.
   *
   * @param id the id of the recipe
   */
  public void getEventById(int id) {
    events = new ArrayList<>();
    List<List<String>> filteredEvents = dao.filterFromTable(
        "Event", "event_id", Integer.toString(id), "Recipe", "recipe_id");
    packageToEvent(filteredEvents);
  }

  /**
   * Returns the events in the register as a list.
   *
   * @return the events in the register as a list
   */
  public List<Event> getEventsAsList() {
    return events;
  }

  /**
   * This method updates an event in the database.
   *
   * @param eventId  the id of the event
   * @param recipeId the id of the recipe
   * @param date     the date of the event
   * @throws IllegalArgumentException if the event does not exist
   */
  public void updateEvent(int eventId, int recipeId, int date) {
    if (getIndexFromEvent(eventId) == -1) {
      throw new IllegalArgumentException("Event with id " + eventId + " does not exist.");
    }
    Event event = new Event(eventId, recipeId, date);
    dao.updateDatabase(event);
  }

  /**
   * This method gets the events by the date.
   *
   * @param date the date of the event
   */
  public void getEventsByDate(int date) {
    VerifyInput.verifyDateLength(date, "date");
    events = new ArrayList<>();
    List<List<String>> filteredEvents = dao.filterFromTable(
        "Event", "date", Integer.toString(date), "recipe", "recipe_id");
    packageToEvent(filteredEvents);
  }

  /**
   * Helper method to package the events into the Event class.
   *
   * @param eventList the list of events
   */
  private void packageToEvent(List<List<String>> eventList) {
    List<Event> newEvents = new ArrayList<>();
    for (List<String> event : eventList) {
      newEvents.add(new Event(
          Integer.parseInt(event.get(0)),
          Integer.parseInt(event.get(1)),
          event.get(4),
          Integer.parseInt(event.get(2)),
          event.get(6),
          Integer.parseInt(event.get(5))));
    }
    this.events = newEvents;
  }

  /**
   * Returns the events in the register.
   *
   * @return the events in the register as a Map
   */
  public Map<Integer, Event> getEvents() {
    return events.stream().collect(Collectors.toMap(Event::getId, event -> event));
  }

  /**
   * Returns a string representation of the events.
   *
   * @return a string representation of the events
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Event event : events) {
      sb.append(event.toString()).append("\n");
    }
    return sb.toString();
  }
}
