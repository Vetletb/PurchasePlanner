package no.ntnu.idatt1002.demo.view.components;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.data.RecipeIngredient;
import no.ntnu.idatt1002.demo.data.RecipeStep;

public class StepField extends HBox {

  public interface onDelete {
    void cb();
  }

  private onDelete onDelete;
  private int stepId;
  private RecipeStep step;
  private InputField stepText;

  public StepField(RecipeStep step) {
    super();
    this.step = step;
    this.stepId = step.getId();

    this.setPrefHeight(50);
    this.setPrefWidth(300);
    this.setSpacing(6);

    Text stepNumber = new Text(step.getStepNumber() + ".");
    stepNumber.setTextAlignment(TextAlignment.CENTER);
    stepNumber.getStyleClass().add("centered");
    this.getChildren().add(stepNumber);

    VBox fieldWrapper = new VBox();
    stepText = new InputField();
    stepText.setText(step.getInstruction());
    fieldWrapper.getChildren().add(stepText);
    VBox.setVgrow(fieldWrapper, Priority.ALWAYS);

    PrimaryButton removeButton = new PrimaryButton("Remove", PrimaryButton.Type.RED);
    removeButton.setOnAction(e -> {
      if (onDelete != null) {
        onDelete.cb();
      }
    });

    removeButton.setPrefHeight(35);
    removeButton.setPrefWidth(100);
    removeButton.getStyleClass().add("centered");

    this.getChildren().addAll(fieldWrapper, removeButton);
  }

  public StepField setOnDelete(onDelete onDelete) {
    this.onDelete = onDelete;
    return this;
  }

  public int getStepId() {
    return stepId;
  }

  /**
   * Gets the step from the field.
   * 
   * @return the step
   */
  public RecipeStep getStep() {

    return new RecipeStep(
        step.getId() == 0 ? 1 : step.getId(), // If the id is 0, it is a new step
        step.getRecipeId(),
        step.getStepNumber(),
        stepText.getText());
  }
}
