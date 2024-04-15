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
    List<List<String>> events = dao.getAllFromTable("Event", "Recipe", "recipe_id");
    packageToEvent(events);
  }

  /**
   * This method adds an event to the database.
   *
   * @param recipe_id the id of the recipe
   * @param date the date of the event
   */
  public void addEvent(int recipe_id, int date) {
    Event event = new Event(recipe_id, date);
    dao.addToDatabase(event);
  }

  /**
   * This method returns the index of the event with the given id.
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
   * This method deletes an event from the database.
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
   * This method gets the events by the recipe id.
   *
   * @param id the id of the recipe
   */
  public void getEventById(int id) {
    events = new ArrayList<>();
    List<List<String>> events = dao.filterFromTable(
        "Event", "event_id", Integer.toString(id), "Recipe", "recipe_id");
    packageToEvent(events);
  }

  /**
   * This method returns the events in the register as a list.
   *
   * @return the events in the register as a list
   */
  public List<Event> getEventsAsList() {
    return events;
  }

  /**
   * This method updates an event in the database.
   *
   * @param event_id the id of the event
   * @param recipe_id the id of the recipe
   * @param date the date of the event
   * @throws IllegalArgumentException if the event does not exist
   */
  public void updateEvent(int event_id, int recipe_id, int date) {
    if (getIndexFromEvent(event_id) == -1) {
      throw new IllegalArgumentException("Event with id " + event_id + " does not exist.");
    }
    Event event = new Event(event_id, recipe_id, date);
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
    List<List<String>> events = dao.filterFromTable(
        "Event", "date", Integer.toString(date), "recipe", "recipe_id");
    packageToEvent(events);
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
          Integer.parseInt(event.get(5))
      ));
    }
    this.events = newEvents;
  }

  /**
   * This method returns the events in the register.
   *
   * @return the events in the register as a Map
   */
  public Map<Integer, Event> getEvents() {
    return events.stream().collect(Collectors.toMap(Event::getId, event -> event));
  }

  /**
   * This method returns a string representation of the events.
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
