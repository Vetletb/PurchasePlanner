package no.ntnu.idatt1002.demo.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an event with a recipe id and a date.
 */
public class Event implements Storable {
  private int event_id;
  private int recipe_id;
  private String name;
  private int date;
  private String category;
  private int cooking_time;






  /**
   * Constructor for the Event class.
   * @param recipe_id the id of the recipe
   * @param date the date of the event
   */
  public Event(int recipe_id, int date) {
    this.recipe_id = recipe_id;
    this.date = date;
  }

  /**
   * Constructor for the Event class.
   * @param event_id the id of the event
   * @param recipe_id the id of the recipe
   * @param date the date of the event
   */
  public Event(int event_id, int recipe_id, int date) {
    this(recipe_id, date);
    this.event_id = event_id;
  }

  public Event(int event_id, int recipe_id, String name, int date, String category, int cooking_time) {
    this(event_id, recipe_id, date);
    this.name = name;
    this.category = category;
    this.cooking_time = cooking_time;
  }


  /**
   * Returns the attributes of the event.
   * @return the attributes of the event
   */
  @Override
  public List<String> getAttributes() {
    List<String> attributes = new ArrayList<>();
    attributes.add(Integer.toString(recipe_id));
    attributes.add(Integer.toString(date));
    return attributes;
  }

  /**
   * Returns the attribute names of the event.
   * @return the attribute names of the event
   */
  @Override
  public List<String> getAttributeNames() {
    List<String> attributes = new ArrayList<>();
    attributes.add("recipe_id");
    attributes.add("date");
    return attributes;
  }

  /**
   * Returns the id of the event.
   * @return the id of the event
   */
  @Override
  public int getId() {
    return event_id;
  }

  /**
   * Returns the name of the id.
   * @return the name of the id
   */
  @Override
  public String getIdName() {
    return "event_id";
  }

  /**
   * Getter method for the event id.
   * @return the event id
   */
  public int getEvent_id() {
    return event_id;
  }

  /**
   * Getter method for the recipe id of the event.
   * @return the recipe id of the event
   */
  public int getRecipe_id() {
    return recipe_id;
  }

  /**
   * Getter method for the date of the event.
   * @return the date of the event
   */
  public int getDate() {
    return date;
  }

  @Override
  public String toString() {
    return "Event ID: " + event_id + ", Recipe ID: " + recipe_id + ", Date: ";
  }
}
