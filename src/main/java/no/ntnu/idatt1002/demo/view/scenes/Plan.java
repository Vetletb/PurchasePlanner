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
import no.ntnu.idatt1002.demo.data.Date;
import no.ntnu.idatt1002.demo.data.Event;
import no.ntnu.idatt1002.demo.data.Recipe;
import no.ntnu.idatt1002.demo.repo.EventRegister;
import no.ntnu.idatt1002.demo.repo.RecipeRegister;
import no.ntnu.idatt1002.demo.view.components.*;

import java.time.LocalDate;
import java.util.HashMap;
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
    addButton.setOnAction(e -> addEvent());
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

  private void addEvent() {
    AddPopup addPopup = new AddPopup("Event");
    addPopup.show(this.getScene().getWindow());

    RecipeRegister recipeRegister = new RecipeRegister(new DAO(new DBConnectionProvider()));
    recipeRegister.getAllRecipes();
    addPopup.addField(Field.ofMap("Recipe", recipeRegister.getRecipes()));

    Map<Integer, Date> dates = new HashMap<>();
    for (int i = 0; i < 7; i++) {
      LocalDate date = LocalDate.now().plusDays(i);
      String dateStr = Integer.toString(date.getYear() - 2000)
              + (date.getMonthValue() < 10 ? "0" + date.getMonthValue() : date.getMonthValue())
              + (date.getDayOfMonth() < 10 ? "0" + date.getDayOfMonth() : date.getDayOfMonth());
      dates.put(Integer.parseInt(dateStr), new Date(date));
    }
    addPopup.addField(Field.ofMap("Date", dates));


    addPopup.setOnAdd((Object[] o) -> {
      for (Object object : o) {
        Logger.debug(object.getClass().getName());

      }
      Object[] recipe_id_As_List = (Object[])o[0];
      Object[] date_As_List = (Object[])o[1];
      int recipe_id = (int)recipe_id_As_List[0];
      int date = (int)date_As_List[0];

      EventRegister eventRegister = new EventRegister(new DAO(new DBConnectionProvider()));
      eventRegister.getAllEvents();

      eventRegister.addEvent(recipe_id, date);
      loadPlaner();
    });
  }

  private void updateEvent(Object[] values) {
    EventRegister eventRegister = new EventRegister(new DAO(new DBConnectionProvider()));
    eventRegister.getAllEvents();
    Object[] recipe_id_As_List = (Object[])values[1];
    Object[] date_As_List = (Object[])values[2];
    int event_id =  (int)values[0];
    int recipe_id = -1;
    int date = -1;
    if (recipe_id_As_List == null) {
      eventRegister.getEventById(event_id);
      Event event = eventRegister.getEvents().get(event_id);
      recipe_id = Integer.parseInt(event.getAttributes().get(0));
    } else recipe_id = (int)recipe_id_As_List[0];

    if (date_As_List == null) {
      eventRegister.getEventById(event_id);
      Event event = eventRegister.getEvents().get(event_id);
      date = Integer.parseInt(event.getAttributes().get(1));
    } else date = (int)date_As_List[0];

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
