package no.ntnu.idatt1002.demo.data;

import java.time.LocalDate;
import java.util.List;

/**
 * This class represents a date that can be stored in the database.
 * 
 * <p>
 * The date is stored as a {@link LocalDate} object, and can be extraced as an
 * 8-digit integer via the {@link #getDateInt()} method.
 * 
 * The 8-digit integer is formatted as follows: YYYYMMDD
 * </p>
 */
public class Date implements Storable {
  private LocalDate date;

  public Date() {
    this.date = LocalDate.now();
  }

  /**
   * Constructs a date object with a given date number.
   *
   * @param date the date number
   */
  public Date(int date) {
    String dateStr = Integer.toString(date);
    this.date = LocalDate.of(
        Integer.parseInt(dateStr.substring(0, 4)),
        Integer.parseInt(dateStr.substring(4, 6)),
        Integer.parseInt(dateStr.substring(6, 8)));
  }

  /**
   * Constructs a date object with a given date.
   *
   * @param date the {@link LocalDate} object
   */
  public Date(LocalDate date) {
    this.date = date;
  }

  /**
   * Returns the date.
   *
   * @return the date
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * Gets the date as an 8-digit integer.
   *
   * @return the date as an 8-digit integer
   */
  public int getDateInt() {
    return Integer.parseInt(Integer.toString(date.getYear())
        + (date.getMonthValue() < 10 ? "0" + date.getMonthValue() : date.getMonthValue())
        + (date.getDayOfMonth() < 10 ? "0" + date.getDayOfMonth() : date.getDayOfMonth()));
  }

  /**
   * Sets the date.
   *
   * @param date the date
   */
  public void setDate(LocalDate date) {
    this.date = date;
  }

  @Override
  public List<String> getAttributeNames() {
    return List.of("date");
  }

  @Override
  public List<String> getAttributes() {
    return List.of(Integer.toString(date.getYear())
        + (date.getMonthValue() < 10 ? "0" + date.getMonthValue() : date.getMonthValue())
        + (date.getDayOfMonth() < 10 ? "0" + date.getDayOfMonth() : date.getDayOfMonth()));
  }

  @Override
  public String getIdName() {
    return "date";
  }

  @Override
  public int getId() {
    return this.getDateInt();
  }

  @Override
  public String toString() {
    int day = date.getDayOfMonth();
    String dayName = date.getDayOfWeek().toString().substring(0, 3);

    return dayName + " " + day;
  }
}
