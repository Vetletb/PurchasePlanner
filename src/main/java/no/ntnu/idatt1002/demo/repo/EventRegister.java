package no.ntnu.idatt1002.demo.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.data.Event;


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
   */
  public EventRegister(DAO dao) {
    this.dao = dao;
    this.events = new ArrayList<>();
  }

  /**
   * This method returns the events in the register in the form of lists.
   *
   * @return the events in the register as lists of strings
   */
  public Map<Integer, List<String>> getEvents() {
    return events.stream()
        .collect(Collectors.toMap(Event::getId, Event::getAttributes));
  }

  /**
   * This method retrieves all events from the database.
   */
  public void getAllEvents() {
    events = new ArrayList<>();
    List<List<String>> events = dao.getAllFromTable("Recipe", "Event", "recipe_id");
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
  private int getIdFromEvent(int id) {
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
   */
  public void deleteEvent(int id) {
    int index = getIdFromEvent(id);
    dao.deleteFromDatabase(events.get(index));
  }

  /**
   * This method updates an event in the database.
   *
   * @param event_id the id of the event
   * @param recipe_id the id of the recipe
   * @param date the date of the event
   */
  public void updateEvent(int event_id, int recipe_id, int date) {
    Event event = new Event(event_id, recipe_id, date);
    dao.updateDatabase(event);
  }

  /**
   * This method gets the events by the date.
   *
   * @param date the date of the event
   */
  public void getEventsByDate(int date) {
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
          Integer.parseInt(event.get(4)),
          Integer.parseInt(event.get(5)),
          event.get(1),
          Integer.parseInt(event.get(6)),
          event.get(3),
          Integer.parseInt(event.get(2))
      ));
    }
    this.events = newEvents;
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
