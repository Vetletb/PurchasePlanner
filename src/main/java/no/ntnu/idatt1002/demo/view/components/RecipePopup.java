package no.ntnu.idatt1002.demo.view.components;

import java.util.ArrayList;
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
import no.ntnu.idatt1002.demo.data.RecipeStep;
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
        Map<Integer, Integer> newIngredients, List<RecipeStep> steps, List<RecipeStep> newSteps);

  }

  private enum Page {
    MAIN,
    ADD_INGREDIENT,
    ADD_STEP
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
  private Page currentPage = Page.MAIN;
  private VBox background = new VBox();

  private InputField categoryField = new InputField("Category");
  private InputField nameField = new InputField("Name");
  private NumberField timefield = new NumberField();
  private List<RecipeIngredientField> ingredientsField;
  private PrimaryButton deleteButton;
  private PrimaryButton submitButton;
  private Text title;
  private PrimaryButton addIngredientButton;
  private VBox addIngredientBox;
  private PageSwitcher pageSwitcher;
  private List<StepField> stepsField;
  private List<StepField> newSteps;
  private PrimaryButton addStepButton;

  /**
   * Constructor for the AddPopup.
   */
  public RecipePopup(Recipe recipe) {
    super();
    this.setAutoHide(true); // close the popup automatically

    // Create a container for the popup
    StackPane container = new StackPane();

    // Create a VBox to hold the content of the popup
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
    title = new Text(recipe.getName());
    title.getStyleClass().addAll("popup-title", "centered");

    nameField.setText(recipe.getName());
    categoryField.setText(recipe.getCategory());
    timefield.setText(Integer.toString(recipe.getCooking_time()));

    List<RecipeIngredient> recipes = recipe.getIngredients();

    ingredientsField = recipes.stream()
        .map(RecipeIngredientField::new)
        .collect(Collectors.toList());

    ingredientsField.forEach(field -> {
      field.setOnDelete(() -> {
        ingredientsField.remove(field);
        background.getChildren().remove(field);
      });
    });

    addIngredientBox = new VBox();
    Text addIngredientTitle = new Text("Added ingredients:");
    HBox box = new HBox();
    box.setSpacing(2);

    if (newIngredients != null) {
      newIngredients.forEach((k, v) -> {
        box.getChildren().add(new Text(Integer.toString(k)));
      });
      addIngredientBox.getChildren().addAll(addIngredientTitle, box);
    }

    addIngredientButton = new PrimaryButton("Add Ingredient");
    addIngredientButton.setOnAction(e -> {
      AddPopup addPopup = new AddPopup("Ingredient");

      ItemRegister itemRegister = new ItemRegister(new DAO(new DBConnectionProvider()));
      itemRegister.getAllItems();
      Map<Integer, Item> items = itemRegister.getItems();

      // Remove the items that are already in the recipe
      items = items.entrySet().stream()
          .filter(entry -> recipe.getIngredients().stream()
              .noneMatch(ingredient -> ingredient.getItemId() == entry.getKey()))
          .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

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

    stepsField = recipe.getInstructions().stream()
        .map(StepField::new)
        .collect(Collectors.toList());

    stepsField.forEach(field -> field.setOnDelete(() -> {
      stepsField.remove(field);
      background.getChildren().remove(field);
    }));

    newSteps = new ArrayList<>();

    addStepButton = new PrimaryButton("Add Step");

    addStepButton.setOnAction(e -> {
      AddPopup addPopup = new AddPopup("Step");
      addPopup.addField(Field.ofString("Instruction"));
      addPopup.setOnAdd((Object[] o) -> {
        String instruction = (String) o[0];
        RecipeStep step = new RecipeStep(
            recipe.getId(),
            recipe.getInstructions().size() + newSteps.size() + 1,
            instruction);

        StepField field = new StepField(step);
        newSteps.add(field);
        switchPopupPage(currentPage);
      });
      addPopup.show(this.getScene().getWindow());
    });

    newSteps.forEach(field -> field.setOnDelete(() -> {
      stepsField.remove(field);
      background.getChildren().remove(field);
    }));

    // Add a button to submit the form
    submitButton = new PrimaryButton(
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
            this.newIngredients,
            stepsField.stream()
                .map(StepField::getStep)
                .collect(Collectors.toList()),
            newSteps.stream().map(StepField::getStep).collect(Collectors.toList()));
      }
      this.hide();
    });

    submitButton.setPrefWidth(300);
    submitButton.setPrefHeight(50);

    // Add a button to delete the item
    deleteButton = new PrimaryButton(
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

    pageSwitcher = new PageSwitcher();
    pageSwitcher.setOnSwitch(i -> {
      int currentPageIndex = currentPage.ordinal();
      currentPageIndex += i;

      if (Page.values().length <= currentPageIndex) {
        currentPageIndex = 0;
      } else if (currentPageIndex < 0) {
        currentPageIndex = Page.values().length - 1;
      }

      currentPage = Page.values()[currentPageIndex];
      switchPopupPage(currentPage);
    });

    switchPopupPage(currentPage);
  }

  private void switchPopupPage(Page page) {
    background.getChildren().clear();
    background.getChildren().add(title);
    switch (page) {
      case MAIN:
        background.getChildren().addAll(nameField, categoryField, timefield);
        break;
      case ADD_INGREDIENT:
        Text subtitle = new Text("Ingredients");
        background.getChildren().add(subtitle);
        background.getChildren().addAll(ingredientsField);
        background.getChildren().addAll(addIngredientButton, addIngredientBox);
        break;
      case ADD_STEP:
        Text stepTitle = new Text("Steps");
        background.getChildren().addAll(stepTitle);
        background.getChildren().addAll(stepsField);
        Text newStepTitle = new Text("New Steps");
        background.getChildren().add(newStepTitle);
        background.getChildren().addAll(newSteps);
        background.getChildren().add(addStepButton);
        break;

      default:
        Logger.fatal("Unknown page: " + page);
        break;
    }
    background.getChildren().addAll(submitButton, deleteButton, pageSwitcher);
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