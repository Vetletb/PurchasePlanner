package no.ntnu.idatt1002.demo.view.scenes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.UpdateableScene;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.dao.DBConnectionProvider;
import no.ntnu.idatt1002.demo.data.Date;
import no.ntnu.idatt1002.demo.data.Event;
import no.ntnu.idatt1002.demo.data.Recipe;
import no.ntnu.idatt1002.demo.repo.EventRegister;
import no.ntnu.idatt1002.demo.repo.RecipeRegister;
import no.ntnu.idatt1002.demo.view.components.PrimaryButton;
import no.ntnu.idatt1002.demo.view.components.PrimaryButton.Type;
import org.controlsfx.control.SearchableComboBox;

/**
 * The inventory page.
 */
public class CookingMode extends VBox implements UpdateableScene {
  private int currentStep = 0;
  private Recipe currentRecipe;
  private boolean isCooking = false;

  public CookingMode() {
    super();
    VBox.setVgrow(this, Priority.ALWAYS);
    selectMenu();
  }

  private void selectMenu() {
    this.getChildren().clear();

    LocalDate nowDate = LocalDate.now();

    RecipeRegister recipeRegister = new RecipeRegister(new DAO(new DBConnectionProvider()));
    recipeRegister.getAllRecipes();

    EventRegister eventRegister = new EventRegister(new DAO(new DBConnectionProvider()));
    eventRegister.getAllEvents();

    Date now = new Date(nowDate);

    List<Event> currentEvents = eventRegister.getEvents().values().stream()
        .filter(event -> event.getDate() == now.getDateInt())
        .collect(Collectors.toList());

    VBox mainContentWrapper = new VBox();
    VBox.setVgrow(mainContentWrapper, Priority.ALWAYS);
    mainContentWrapper.getStyleClass().addAll("full-height", "centered");

    HBox mainContent = new HBox();
    mainContentWrapper.getChildren().add(mainContent);
    mainContentWrapper.setSpacing(10);
    HBox.setHgrow(mainContent, Priority.ALWAYS);
    mainContent.getStyleClass().addAll("full-width", "cooking-mode-content");

    switch (currentEvents.size()) {
      case 0:
        Text noEventsText = new Text(
            "You have no meals planned for today");
        noEventsText.getStyleClass().addAll("centered", "one-event-text");
        mainContent.getChildren().add(noEventsText);

        Text orText0 = new Text("Do you still want to cook something?");
        orText0.getStyleClass().addAll("centered", "text-lg");

        mainContentWrapper.getChildren().add(orText0);
        mainContentWrapper.getChildren().add(createCookSomethingBox());

        break;
      case 1:
        Recipe recipe = recipeRegister.getRecipes().get(currentEvents.get(0).getRecipe_id());
        Text oneEventText = new Text("You have " + recipe.getName() + " planned for today");
        oneEventText.getStyleClass().addAll("centered", "one-event-text");

        PrimaryButton startCookingButton = new PrimaryButton("Start cooking", Type.PRIMARY);
        startCookingButton.setPrefSize(150, 60);
        startCookingButton.getStyleClass().add("text-lg");
        startCookingButton.setOnAction(e -> startCooking(recipe));

        oneEventText.getStyleClass().addAll("centered", "text-lg");
        mainContent.getChildren().addAll(oneEventText, startCookingButton);

        Text orText = new Text("or cook something else: ");
        orText.getStyleClass().addAll("centered", "text-lg");

        mainContentWrapper.getChildren().add(orText);
        mainContentWrapper.getChildren().add(createCookSomethingBox());
        break;
      default:
        Text multiplEventsText = new Text(
            "You have multiple meals planned for today. What would you like to start with?");
        multiplEventsText.getStyleClass().addAll("centered", "one-event-text");
        mainContent.getChildren().add(multiplEventsText);

        for (Event event : currentEvents) {
          Recipe eventRecipe = recipeRegister.getRecipes().get(event.getRecipe_id());
          PrimaryButton startButton = new PrimaryButton("" + eventRecipe.getName(),
              Type.PRIMARY);
          startButton.setPrefSize(150, 60);
          startButton.getStyleClass().add("text-lg");
          startButton.setOnAction(e -> startCooking(eventRecipe));
          mainContentWrapper.getChildren().add(startButton);
        }

        VBox orWrapper = new VBox();
        orWrapper.getStyleClass().addAll("or-text", "centered");
        Text orText2 = new Text("Or cook something else: ");
        orText2.getStyleClass().addAll("centered", "text-lg");
        orWrapper.getChildren().add(orText2);
        orWrapper.getChildren().add(createCookSomethingBox());
        mainContentWrapper.getChildren().add(orWrapper);
        break;
    }

    this.getChildren().add(mainContentWrapper);
  }

  private VBox createCookSomethingBox() {
    RecipeRegister recipeRegister = new RecipeRegister(new DAO(new DBConnectionProvider()));
    recipeRegister.getAllRecipes();

    VBox cookSomethingBox = new VBox();
    cookSomethingBox.setSpacing(15);
    cookSomethingBox.getStyleClass().add("cook-something-box");
    cookSomethingBox.getStyleClass().add("centered");

    List<Recipe> recipes = recipeRegister.getRecipes().values()
        .stream().collect(Collectors.toList());

    SearchableComboBox<Recipe> recipeDropdown = new SearchableComboBox<>();
    recipeDropdown.getItems().addAll(recipes);

    recipeDropdown.setOnAction(e -> {
      currentRecipe = recipeDropdown.getValue();
    });

    recipeDropdown.setMaxWidth(200);
    recipeDropdown.setPromptText("Select recipe");

    PrimaryButton startButton = new PrimaryButton("Start cooking", Type.PRIMARY);

    startButton.setOnAction(e -> {
      if (currentRecipe != null) {
        startCooking(currentRecipe);
      }
    });

    cookSomethingBox.getChildren().addAll(recipeDropdown, startButton);
    return cookSomethingBox;
  }

  private void startCooking(Recipe recipe) {
    this.isCooking = true;
    this.getChildren().clear();

    if (recipe.getInstructions().isEmpty()) {
      displayNoSteps();
      return;
    }

    HBox recipeHeader = new HBox();
    HBox.setHgrow(recipeHeader, Priority.ALWAYS);
    recipeHeader.getStyleClass().addAll("cooking-mode-header", "full-width");

    Text recipeName = new Text("Cooking " + recipe.getName());
    recipeName.getStyleClass().addAll("centered");
    recipeHeader.getChildren().add(recipeName);

    VBox mainContentWrapper = new VBox();
    VBox.setVgrow(mainContentWrapper, Priority.ALWAYS);
    mainContentWrapper.getStyleClass().addAll("full-height", "centered");

    HBox mainContent = new HBox();
    mainContentWrapper.getChildren().add(mainContent);
    HBox.setHgrow(mainContent, Priority.ALWAYS);
    mainContent.getStyleClass().addAll("full-width", "cooking-mode-content");

    Text stepNumber = new Text("Step " + (currentStep + 1) + "/" + recipe.getInstructions().size());
    stepNumber.getStyleClass().add("step-number");

    Text stepText = new Text(recipe.getInstructions().get(currentStep).getInstruction());
    stepText.setWrappingWidth(600);
    stepText.getStyleClass().add("step-text");
    stepText.textAlignmentProperty().set(javafx.scene.text.TextAlignment.CENTER);
    mainContent.getChildren().addAll(stepNumber, stepText);

    PrimaryButton nextStepButton = new PrimaryButton("Next step", Type.SECONDARY);
    mainContentWrapper.getChildren().add(nextStepButton);
    nextStepButton.setPrefSize(350, 100);
    nextStepButton.setOnAction(e -> {

      if (currentStep == recipe.getInstructions().size() - 2) {
        nextStepButton.setText("Finish");
      }

      if (currentStep < recipe.getInstructions().size() - 1) {
        currentStep++;
        stepNumber.setText("Step " + (currentStep + 1) + "/" + recipe.getInstructions().size());
        stepText.setText(recipe.getInstructions().get(currentStep).getInstruction());
        return;
      }
      this.isCooking = false;
      selectMenu();
    });

    HBox rightPaddingBox = new HBox();
    rightPaddingBox.setPrefWidth(stepNumber.getLayoutBounds().getWidth() + 20);
    mainContent.getChildren().add(rightPaddingBox);

    this.getChildren().addAll(recipeHeader, mainContentWrapper);
  }

  private void displayNoSteps() {
    VBox mainContentWrapper = new VBox();
    VBox.setVgrow(mainContentWrapper, Priority.ALWAYS);
    mainContentWrapper.getStyleClass().addAll("full-height", "centered");

    HBox mainContent = new HBox();
    mainContentWrapper.getChildren().add(mainContent);
    HBox.setHgrow(mainContent, Priority.ALWAYS);
    mainContent.getStyleClass().addAll("full-width", "cooking-mode-content");

    this.getChildren().clear();
    Text noStepsText = new Text("This recipe has no steps. Add some steps to start cooking.");
    noStepsText.getStyleClass().addAll("centered", "one-event-text");
    mainContentWrapper.getChildren().add(noStepsText);
    mainContentWrapper.setSpacing(15);

    PrimaryButton okButton = new PrimaryButton("Ok", Type.PRIMARY);
    okButton.setPrefSize(300, 50);
    okButton.setOnAction(e -> {
      selectMenu();
    });

    mainContentWrapper.getChildren().add(okButton);

    this.getChildren().add(mainContentWrapper);
    this.isCooking = false;
  }

  /**
   * Updates the scene.
   */
  public void updateScene() {

    // Updating the scene while cooking should not be done, so we exit early
    if (isCooking) {
      return;
    }
    selectMenu();
  }

  public VBox createScene() {
    return this;
  }
}
