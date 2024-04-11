package no.ntnu.idatt1002.demo.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.dao.DBConnectionProvider;
import no.ntnu.idatt1002.demo.data.Date;
import no.ntnu.idatt1002.demo.data.Event;
import no.ntnu.idatt1002.demo.repo.EventRegister;
import no.ntnu.idatt1002.demo.repo.RecipeRegister;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EventPopup extends Popup {

  /**
   * Interface for when the user saves the form.
   */
  public interface OnSave {
    void cb(Object[] values);
  }

  /**
   * Interface for when the user deletes the item.
   */
  public interface OnDelete {
    void cb(int event_id);
  }

  private OnSave onSave;
  private OnDelete onDelete;

  /**
   * Constructor for the AddPopup.
   */
  public EventPopup(Event event) {
    super();
    this.setAutoHide(true); // close the popup automatically

    // Create a container for the popup
    StackPane container = new StackPane();

    // Create a VBox to hold the content of the popup
    VBox background = new VBox();
    background.getStyleClass().add("popup-background");
    background.setSpacing(20);
    this.getContent().add(container);
    container.getChildren().add(background);

    // create a cross button to close the popup
    CrossButton crossButton = new CrossButton();
    crossButton.setOnAction(e -> this.hide());
    crossButton.setPadding(new Insets(10));

    container.getChildren().add(crossButton);
    StackPane.setAlignment(crossButton, Pos.TOP_RIGHT);

    // Add a title to the popup
    Text title = new Text(event.getName());
    title.getStyleClass().addAll("popup-title", "centered");

//    EventRegister eventRegister = new EventRegister(new DAO(new DBConnectionProvider()));
//    eventRegister.getAllEvents();
//    Map<Integer, Event> events = eventRegister
//            .getEventsAsList()
//            .stream()
//            .filter(
//                    distinctByKey(e -> e.getName()))
//            .collect(
//                    Collectors.toMap(
//                            Event::getId, e -> e));
    RecipeRegister recipeRegister = new RecipeRegister(new DAO(new DBConnectionProvider()));
    recipeRegister.getAllRecipes();


    // Add input Fields
    Field<Integer[]> recipeField = Field.ofMap("Recipe", recipeRegister.getRecipes());

    // Format the date
    Map<Integer, Date> dates = new HashMap<>();
    for (int i = 0; i < 7; i++) {
      LocalDate date = LocalDate.now().plusDays(i);
      String dateStr = Integer.toString(date.getYear() - 2000)
              + (date.getMonthValue() < 10 ? "0" + date.getMonthValue() : date.getMonthValue())
              + (date.getDayOfMonth() < 10 ? "0" + date.getDayOfMonth() : date.getDayOfMonth());
      dates.put(Integer.parseInt(dateStr), new Date(date));
    }

    Field<Integer[]> dateField = Field.ofMap("Date", dates);


    // Add a button to submit the form
    PrimaryButton submitButton = new PrimaryButton("Save");
    submitButton.setButtonType(PrimaryButton.Type.SECONDARY);
    submitButton.setOnAction(e -> {
      if (onSave != null) {
        onSave.cb(new Object[]{
                event.getId(),
                recipeField.getValue(),
                dateField.getValue()
        });
      }
      this.hide();
    });

    submitButton.setPrefWidth(200);
    submitButton.setPrefHeight(50);

    // Add a button to delete the item
    PrimaryButton deleteButton = new PrimaryButton("Delete");
    deleteButton.setButtonType(PrimaryButton.Type.RED);
    deleteButton.setOnAction(e -> {
      if (onDelete != null) {
        onDelete.cb(event.getId());
      }
      this.hide();
    });

    deleteButton.setPrefWidth(200);
    deleteButton.setPrefHeight(50);

    // Add the content to the VBox
    background.getChildren().addAll(
            title,
            recipeField.getRenderedField(),
            dateField.getRenderedField(),
            submitButton,
            deleteButton
    );
  }

  public EventPopup setOnSave(OnSave cb) {
    this.onSave = cb;
    return this;
  }

  public EventPopup setOnDelete(OnDelete cb) {
    this.onDelete = cb;
    return this;
  }

  public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
  {
    Map<Object, Boolean> map = new ConcurrentHashMap<>();
    return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }


}
