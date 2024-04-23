package no.ntnu.idatt1002.demo.data;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1002.demo.util.VerifyInput;

/**
 * This class represents an event with a recipe id and a date.
 */
public class Event implements Storable {
  private int eventId;
  private final int recipeId;
  private String name;
  private final int date;
  private String category;
  private int cookingTime;

  /**
   * Constructor for the Event class.
   *
   * @param recipeId the id of the recipe
   * @param date     the date of the event
   */
  public Event(int recipeId, int date) {
    VerifyInput.verifyPositiveNumberZeroNotAccepted(recipeId, "recipe_id");
    VerifyInput.verifyPositiveNumberMinusOneNotAccepted(date, "date");
    VerifyInput.verifyDateLength(date, "date");
    VerifyInput.verifyDateMonth(date, "date");
    VerifyInput.verifyDateDay(date, "date");
    this.recipeId = recipeId;
    this.date = date;
  }

  /**
   * Constructor for the Event class.
   *
   * @param eventId  the id of the event
   * @param recipeId the id of the recipe
   * @param date     the date of the event
   */
  public Event(int eventId, int recipeId, int date) {
    this(recipeId, date);
    this.eventId = eventId;
  }

  /**
   * Constructor for the Event class.
   *
   * @param eventId     the id of the event
   * @param recipeId    the id of the recipe
   * @param name        the name of the event
   * @param date        the date of the event
   * @param category    the category of the event
   * @param cookingTime the cooking time of the event
   */
  public Event(
      int eventId, int recipeId, String name, int date, String category, int cookingTime) {
    this(eventId, recipeId, date);
    this.name = name;
    this.category = category;
    this.cookingTime = cookingTime;
  }

  /**
   * Returns the attributes of the event.
   *
   * @return the attributes of the event
   */
  @Override
  public List<String> getAttributes() {
    List<String> attributes = new ArrayList<>();
    attributes.add(Integer.toString(recipeId));
    attributes.add(Integer.toString(date));
    return attributes;
  }

  /**
   * Returns the attribute names of the event.
   *
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
   *
   * @return the id of the event
   */
  @Override
  public int getId() {
    return getEventId();
  }

  /**
   * Returns the name of the id.
   *
   * @return the name of the id
   */
  @Override
  public String getIdName() {
    return "event_id";
  }

  /**
   * Getter method for the event id.
   *
   * @return the event id
   */
  public int getEventId() {
    return eventId;
  }

  /**
   * Getter method for the recipe id of the event.
   *
   * @return the recipe id of the event
   */
  public int getRecipeId() {
    return recipeId;
  }

  /**
   * Getter method for the date of the event.
   *
   * @return the date of the event
   */
  public int getDate() {
    return date;
  }

  /**
   * Getter method for the name of the event.
   *
   * @return the name of the event
   */
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return this.getName();
  }
}
