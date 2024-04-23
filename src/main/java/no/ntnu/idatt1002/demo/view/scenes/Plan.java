package no.ntnu.idatt1002.demo.view.scenes;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.UpdateableScene;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.dao.DBConnectionProvider;
import no.ntnu.idatt1002.demo.data.Date;
import no.ntnu.idatt1002.demo.data.Event;
import no.ntnu.idatt1002.demo.repo.EventRegister;
import no.ntnu.idatt1002.demo.repo.RecipeRegister;
import no.ntnu.idatt1002.demo.view.components.AddPopup;
import no.ntnu.idatt1002.demo.view.components.EventPane;
import no.ntnu.idatt1002.demo.view.components.EventPopup;
import no.ntnu.idatt1002.demo.view.components.Field;
import no.ntnu.idatt1002.demo.view.components.Icon;
import no.ntnu.idatt1002.demo.view.components.PrimaryButton;
import no.ntnu.idatt1002.demo.view.components.Toast;
import no.ntnu.idatt1002.demo.view.components.ToastProvider;

/**
 * The planning page.
 */
public class Plan extends VBox implements UpdateableScene {

  private final HBox contentContainer;
  private final HBox buttonContainer;
  private final PrimaryButton addButton;

  private VBox dayContainer;;
  private HBox labelContainer;
  private VBox eventContainer;

  /**
   * Constructor for the inventory page.
   */
  public Plan() {
    super();
    contentContainer = new HBox();
    buttonContainer = new HBox();
    addButton = new PrimaryButton(new Icon("plus").setFillColor(Color.BLACK));
    addButton.setOnAction(e -> addEvent());
    buttonContainer.getChildren().add(addButton);
    loadPlaner();

    super.getChildren().addAll(buttonContainer, contentContainer);

    buttonContainer.getStyleClass().add("event-add-button-container");
    contentContainer.getStyleClass().add("centered");
  }

  /**
   * Loads the planer.
   */
  public void loadPlaner() {
    contentContainer.getChildren().clear();
    LocalDate nowDate = LocalDate.now();

    EventRegister eventRegister = new EventRegister(new DAO(new DBConnectionProvider()));

    int dayOfWeekAsInt = nowDate.getDayOfWeek().getValue() - 1;
    for (int i = -dayOfWeekAsInt; i < 7 - dayOfWeekAsInt; i++) {
      dayContainer = new VBox();
      labelContainer = new HBox();
      eventContainer = new VBox();

      LocalDate date = nowDate.plusDays(i);
      Label dayLabel = new Label(date.getDayOfWeek().toString().substring(0, 3)
          + " "
          + date.getDayOfMonth());

      if (i == 0) {
        dayLabel.getStyleClass().add("today-label");
      } else {
        dayLabel.getStyleClass().add("day-label");
      }
      labelContainer.getChildren().add(dayLabel);

      // Format date to match the database
      String dateStr = Integer.toString(date.getYear())
          + (date.getMonthValue() < 10 ? "0" + date.getMonthValue() : date.getMonthValue())
          + (date.getDayOfMonth() < 10 ? "0" + date.getDayOfMonth() : date.getDayOfMonth());
      eventRegister.getEventsByDate(Integer.parseInt(dateStr));

      Map<Integer, Event> events = eventRegister.getEvents();

      events.forEach((id, event) -> {
        EventPane eventPane = new EventPane(id, event.getName());
        eventContainer.getChildren().add(eventPane);
        eventPane.setOnMouseClicked(v -> {
          EventPopup eventPopup = new EventPopup(event);
          eventPopup.setOnSave(this::updateEvent);
          eventPopup.setOnDelete(this::deleteEvent);
          eventPopup.show(this.getScene().getWindow());
        });
      });

      dayContainer.getChildren().add(labelContainer);
      dayContainer.getChildren().add(eventContainer);
      contentContainer.getChildren().add(dayContainer);

      // CSS styling
      eventContainer.getStyleClass().add("event-container");
      dayContainer.getStyleClass().add("day-container");
      labelContainer.getStyleClass().add("label-container");
    }
  }

  private void addEvent() {
    AddPopup addPopup = new AddPopup("Event");
    addPopup.show(this.getScene().getWindow());

    RecipeRegister recipeRegister = new RecipeRegister(new DAO(new DBConnectionProvider()));
    recipeRegister.getAllRecipes();
    addPopup.addField(Field.ofMap("Recipe", recipeRegister.getRecipes()));

    Map<Integer, Date> dates = new HashMap<>();

    LocalDate nowDate = LocalDate.now();
    int dayOfWeekAsInt = nowDate.getDayOfWeek().getValue() - 1;
    for (int i = -dayOfWeekAsInt; i < 7 - dayOfWeekAsInt; i++) {
      LocalDate date = nowDate.plusDays(i);
      String dateStr = Integer.toString(date.getYear())
          + (date.getMonthValue() < 10 ? "0" + date.getMonthValue() : date.getMonthValue())
          + (date.getDayOfMonth() < 10 ? "0" + date.getDayOfMonth() : date.getDayOfMonth());
      dates.put(Integer.parseInt(dateStr), new Date(date));
    }
    addPopup.addField(Field.ofMap("Date", dates));

    addPopup.setOnAdd((Object[] o) -> {
      Object[] recipeIdAsList;
      Object[] dateAsList;
      int recipeId;
      int date;

      try {
        recipeIdAsList = (Object[]) o[0];
        dateAsList = (Object[]) o[1];
        recipeId = (int) recipeIdAsList[0];
        date = (Integer) dateAsList[0];
      } catch (Exception e) {
        Logger.error(e.getMessage());
        ToastProvider.enqueue(
            new Toast("Failed to add event",
                "One or more of your fields were empty", Toast.ToastType.ERROR));
        return;
      }

      try {
        EventRegister eventRegister = new EventRegister(new DAO(new DBConnectionProvider()));
        eventRegister.getAllEvents();

        eventRegister.addEvent(recipeId, date);
        ToastProvider.enqueue(new Toast("Success",
            "The event was successfully added", Toast.ToastType.SUCCESS));
        loadPlaner();
      } catch (Exception e) {
        ToastProvider.enqueue(new Toast("Failed to add event",
            e.getMessage(), Toast.ToastType.ERROR));
        e.printStackTrace();
      }
    });
  }

  private void updateEvent(Object[] values) {
    EventRegister eventRegister = new EventRegister(new DAO(new DBConnectionProvider()));
    eventRegister.getAllEvents();
    Object[] recipeIdAsList = (Object[]) values[1];
    Object[] dateAsList = (Object[]) values[2];
    int eventId = (int) values[0];
    int recipeId = -1;
    int date = -1;
    if (recipeIdAsList == null) {
      eventRegister.getEventById(eventId);
      Event event = eventRegister.getEvents().get(eventId);
      recipeId = Integer.parseInt(event.getAttributes().get(0));
    } else {
      recipeId = (int) recipeIdAsList[0];
    }

    if (dateAsList == null) {
      eventRegister.getEventById(eventId);
      Event event = eventRegister.getEvents().get(eventId);
      date = Integer.parseInt(event.getAttributes().get(1));
    } else {
      date = (int) dateAsList[0];
    }

    eventRegister.updateEvent(eventId, recipeId, date);
    eventRegister.getAllEvents();
    ToastProvider.enqueue(new Toast("Success",
        "The event was successfully updated", Toast.ToastType.SUCCESS));
    loadPlaner();
  }

  private void deleteEvent(int eventId) {
    EventRegister eventRegister = new EventRegister(new DAO(new DBConnectionProvider()));
    eventRegister.getAllEvents();
    eventRegister.deleteEvent(eventId);
    eventRegister.getAllEvents();
    ToastProvider.enqueue(new Toast("Success",
        "The event was successfully deleted", Toast.ToastType.SUCCESS));
    loadPlaner();
  }

  public void updateScene() {
    loadPlaner();
  }

  public VBox createScene() {
    return this;
  }

}
