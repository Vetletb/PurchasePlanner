package no.ntnu.idatt1002.demo.view.scenes;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.dao.DBConnectionProvider;
import no.ntnu.idatt1002.demo.data.Event;
import no.ntnu.idatt1002.demo.repo.EventRegister;
import no.ntnu.idatt1002.demo.view.components.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * The inventory page.
 */
public class Plan extends VBox {

  private final HBox contentContainer;
  private final HBox buttonContainer;
  private final PrimaryButton addButton;

  private VBox dayContainer;
  private HBox labelContainer;
  private VBox eventContainer;


  public Plan() {
    super();
    contentContainer = new HBox();
    buttonContainer = new HBox();
    addButton = new PrimaryButton(new Icon("plus").setFillColor(Color.BLACK));
    buttonContainer.getChildren().add(addButton);
    loadPlaner();

    super.getChildren().addAll(buttonContainer, contentContainer);


    // CSS styling
//    buttonContainer. = HBox.setHgrow(buttonContainer, Priority.ALWAYS); TODO: Fix this
    buttonContainer.getStyleClass().add("event-add-button-container");
//    addButton.getStyleClass().add("event-add-button");
    contentContainer.getStyleClass().add("planer-content-container");
  }

  public void loadPlaner() {
    contentContainer.getChildren().clear();
    LocalDate nowDate = LocalDate.now();

    EventRegister eventRegister = new EventRegister(new DAO(new DBConnectionProvider()));

    for (int i = 0; i < 7; i++) {
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
      String dateStr = Integer.toString(date.getYear() - 2000)
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

  private void updateEvent(int event_id, int recipe_id, int date) {
    EventRegister eventRegister = new EventRegister(new DAO(new DBConnectionProvider()));
    eventRegister.getAllEvents();
    eventRegister.updateEvent(event_id, recipe_id, date);
    eventRegister.getAllEvents();
    loadPlaner();
  }

  private void deleteEvent(int event_id) {
    EventRegister eventRegister = new EventRegister(new DAO(new DBConnectionProvider()));
    eventRegister.getAllEvents();
    eventRegister.deleteEvent(event_id);
    eventRegister.getAllEvents();
    loadPlaner();
  }

}
