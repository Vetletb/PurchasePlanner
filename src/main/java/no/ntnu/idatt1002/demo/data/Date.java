package no.ntnu.idatt1002.demo.data;

import java.time.LocalDate;
import java.util.List;

public class Date implements Storable {

  int date_id;
  private LocalDate date;

  public Date() {
    this.date = LocalDate.now();
  }

  public Date(int date) {
    String dateStr = Integer.toString(date);
    this.date = LocalDate.of(
            Integer.parseInt(dateStr.substring(0, 2) + 2000),
            Integer.parseInt(dateStr.substring(2, 4)),
            Integer.parseInt(dateStr.substring(4, 6))
    );
  }
  public Date(LocalDate date) {
    this.date = date;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  @Override
  public List<String> getAttributeNames() {
    return List.of("date");
  }

  @Override
  public List<String> getAttributes() {
    return List.of(Integer.toString(date.getYear() - 2000) + (date.getMonthValue() < 10 ? "0" + date.getMonthValue() : date.getMonthValue()) + (date.getDayOfMonth() < 10 ? "0" + date.getDayOfMonth() : date.getDayOfMonth()));
  }

  @Override
  public String getIdName() {
    return "date";
  }

  @Override
  public int getId() {
    return date_id;
  }

  @Override
  public String toString() {
    int day = date.getDayOfMonth();
    String dayName = date.getDayOfWeek().toString().substring(0, 3);

    return dayName + " " + day;
  }
}
