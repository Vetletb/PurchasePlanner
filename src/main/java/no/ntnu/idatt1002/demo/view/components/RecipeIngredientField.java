package no.ntnu.idatt1002.demo.view.components;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import no.ntnu.idatt1002.demo.data.RecipeIngredient;

public class RecipeIngredientField extends HBox {

  public interface onDelete {
    void cb();
  }

  private onDelete onDelete;
  private int recipeId;
  private int recipeAmount;
  private String unit;

  public RecipeIngredientField(RecipeIngredient ingredient) {
    super();
    this.recipeId = ingredient.getId();

    this.setPrefHeight(50);
    this.setPrefWidth(300);

    this.setSpacing(6);
    VBox nameWrapper = new VBox();
    Text ingredientName = new Text("(" + ingredient.getId() + ") " + ingredient.getName());
    ingredientName.setWrappingWidth(100);
    nameWrapper.getChildren().add(ingredientName);
    VBox.setVgrow(nameWrapper, Priority.ALWAYS);
    nameWrapper.getStyleClass().add("centered");
    NumberField ingredientAmount = new NumberField(ingredient.getQuantity(), v -> {
      this.recipeAmount = v;
    });

    ingredientAmount.setText(Integer.toString(ingredient.getQuantity()));
    this.unit = ingredient.getUnit();

    ingredientAmount.setPrefHeight(35);
    ingredientAmount.setPrefWidth(100);

    TextField unitField = new TextField();

    unitField.setText(ingredient.getUnit());

    unitField.setPrefHeight(35);
    unitField.setPrefWidth(100);

    unitField.textProperty().addListener((observable, oldValue, newValue) -> {
      this.unit = newValue;
    });

    PrimaryButton removeButton = new PrimaryButton("Remove", PrimaryButton.Type.RED);
    removeButton.setOnAction(e -> {
      if (onDelete != null) {
        onDelete.cb();
      }
    });

    removeButton.setPrefHeight(35);
    removeButton.setPrefWidth(100);

    this.getChildren().addAll(nameWrapper, unitField, ingredientAmount, removeButton);
  }

  public RecipeIngredientField setOnDelete(onDelete onDelete) {
    this.onDelete = onDelete;
    return this;
  }

  public int getRecipeId() {
    return recipeId;
  }

  public int getRecipeAmount() {
    return recipeAmount;
  }

  public String getUnit() {
    return unit;
  }
}
