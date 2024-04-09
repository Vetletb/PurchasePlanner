package no.ntnu.idatt1002.demo.view.components;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.dao.DBConnectionProvider;
import no.ntnu.idatt1002.demo.data.Item;
import no.ntnu.idatt1002.demo.data.Recipe;
import no.ntnu.idatt1002.demo.data.RecipeIngredient;
import no.ntnu.idatt1002.demo.repo.ItemRegister;

/**
 * A popup for adding items to the inventory.
 */
public class RecipePopup extends Popup {

  /**
   * Interface for when the user saves the form.
   */
  public interface OnSave {
    void cb(int id, String name, String categories, int cookingTime, Map<Integer, Integer> ingredients,
        Map<Integer, Integer> newIngredients);

  }

  /**
   * Interface for when the user deletes the item.
   */
  public interface OnDelete {
    void cb(int id);
  }

  private OnSave onSave;
  private OnDelete onDelete;
  private Map<Integer, Integer> newIngredients = new java.util.HashMap<>();

  /**
   * Constructor for the AddPopup.
   */
  public RecipePopup(Recipe recipe) {
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
    Text title = new Text(recipe.getName());
    title.getStyleClass().addAll("popup-title", "centered");

    InputField nameField = new InputField("Name");
    nameField.setText(recipe.getName());

    InputField categoryField = new InputField("Category");
    categoryField.setText(recipe.getCategory());

    NumberField timefield = new NumberField();
    timefield.setText(Integer.toString(recipe.getCooking_time()));

    List<RecipeIngredient> recipes = recipe.getIngredients();

    List<RecipeIngredientField> ingredientsField = recipes.stream()
        .map(RecipeIngredientField::new)
        .collect(Collectors.toList());

    ingredientsField.forEach(field -> {
      field.setOnDelete(() -> {
        ingredientsField.remove(field);
        background.getChildren().remove(field);
      });
    });

    VBox addIngredientBox = new VBox();
    Text addIngredientTitle = new Text("Added ingredients:");
    HBox box = new HBox();
    box.setSpacing(2);

    if (newIngredients != null) {
      newIngredients.forEach((k, v) -> {
        box.getChildren().add(new Text(Integer.toString(k)));
      });
      addIngredientBox.getChildren().addAll(addIngredientTitle, box);
    }

    PrimaryButton addIngredientButton = new PrimaryButton("Add Ingredient");
    addIngredientButton.setOnAction(e -> {
      AddPopup addPopup = new AddPopup("Ingredient");

      ItemRegister itemRegister = new ItemRegister(new DAO(new DBConnectionProvider()));
      itemRegister.getAllItems();
      Map<Integer, Item> items = itemRegister.getItems();

      items.forEach((k, v) -> {
        // Remove the items that are already in the recipe
        if (recipe.getIngredients().stream()
            .anyMatch(ingredient -> ingredient.getId() == k)) {
          items.remove(k);
        }
      });

      addPopup.addField(Field.ofMap("Ingredients", items));
      addPopup.setOnAdd((Object[] o) -> {
        if (o == null) {
          return;
        }

        Object[] recipeIngredients = (Object[]) o[0];
        for (Object ingredient : recipeIngredients) {
          int id = (int) ingredient;
          newIngredients.put(id, 1);
          box.getChildren().add(new Text(Integer.toString(id)));
        }
      });

      addPopup.show(this.getScene().getWindow());
    });

    // Add a button to submit the form
    PrimaryButton submitButton = new PrimaryButton(
        "Save");
    submitButton.setButtonType(PrimaryButton.Type.SECONDARY);
    submitButton.setOnAction(e -> {
      if (onSave != null) {
        onSave.cb(
            recipe.getId(),
            nameField.getText(),
            categoryField.getText(),
            timefield.getValue(),
            ingredientsField.stream()
                .collect(Collectors.toMap(
                    RecipeIngredientField::getRecipeId,
                    RecipeIngredientField::getRecipeAmount)),
            this.newIngredients);
      }
      this.hide();
    });

    submitButton.setPrefWidth(300);
    submitButton.setPrefHeight(50);

    // Add a button to delete the item
    PrimaryButton deleteButton = new PrimaryButton(
        "Delete");
    deleteButton.setButtonType(PrimaryButton.Type.RED);
    deleteButton.setOnAction(e -> {
      if (onDelete != null) {
        onDelete.cb(recipe.getId());
      }
      this.hide();
    });

    deleteButton.setPrefWidth(300);
    deleteButton.setPrefHeight(50);

    // Add the contents to the VBox
    background.getChildren().addAll(title, nameField, categoryField, timefield);
    background.getChildren().addAll(ingredientsField);
    background.getChildren().addAll(addIngredientButton, addIngredientBox, submitButton, deleteButton);
  }

  public RecipePopup setOnSave(OnSave onSave) {
    this.onSave = onSave;
    return this;
  }

  public RecipePopup setOnDelete(OnDelete onDelete) {
    this.onDelete = onDelete;
    return this;
  }

}