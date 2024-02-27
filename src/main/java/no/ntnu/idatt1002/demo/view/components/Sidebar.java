package no.ntnu.idatt1002.demo.view.components;

import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.demo.SceneLoader;

/**
 * The sidebar for the application.
 */
public class Sidebar extends VBox {
  PrimaryButton selectedButton;
  private static int buttonWidth = 200;
  private static int buttonHeight = 50;

  /**
   * Constructor for the sidebar.
   * 
   * <p>
   * Creates a sidebar with buttons for the different pages in the application.
   * </p>
   *
   * @param sceneLoader The scene loader to use for switching scenes.
   */
  public Sidebar(SceneLoader sceneLoader) {
    super();

    // create the buttons

    PrimaryButton inventoryButton = new PrimaryButton(
        "Inventory",
        PrimaryButton.Type.WHITE,
        new Icon("inventory"));

    inventoryButton.setPrefSize(buttonWidth, buttonHeight);
    inventoryButton.setOnAction(e -> {
      sceneLoader.switchScene("inventory");
      setSelectedButton(inventoryButton);
    });

    PrimaryButton cookBookButton = new PrimaryButton(
        "CookBook",
        PrimaryButton.Type.WHITE,
        new Icon("cookbook"));
    cookBookButton.setPrefSize(buttonWidth, buttonHeight);
    cookBookButton.setOnAction(e -> {
      sceneLoader.switchScene("cookbook");
      setSelectedButton(cookBookButton);
    });

    PrimaryButton planButton = new PrimaryButton(
        "Plan",
        PrimaryButton.Type.WHITE,
        new Icon("plan"));
    planButton.setPrefSize(buttonWidth, buttonHeight);
    planButton.setOnAction(e -> {
      sceneLoader.switchScene("plan");
      setSelectedButton(planButton);
    });

    PrimaryButton shoppingListButton = new PrimaryButton(
        "Shopping list",
        PrimaryButton.Type.WHITE,
        new Icon("shoppinglist"));
    shoppingListButton.setPrefSize(buttonWidth, buttonHeight);
    shoppingListButton.setOnAction(e -> {
      sceneLoader.switchScene("shoppinglist");
      setSelectedButton(shoppingListButton);
    });

    PrimaryButton cookingModeButton = new PrimaryButton(
        "Cooking mode",
        PrimaryButton.Type.WHITE,
        new Icon("cookingmode"));
    cookingModeButton.setPrefSize(buttonWidth, buttonHeight);
    cookingModeButton.setOnAction(e -> {
      sceneLoader.switchScene("cookingmode");
      setSelectedButton(cookingModeButton);
    });

    // set the inventory button as the default selected button
    setSelectedButton(inventoryButton);

    VBox primaryWindowButtonsContainer = new VBox(); // container for all buttons stuck to the top
    primaryWindowButtonsContainer.getChildren().addAll(
        inventoryButton,
        cookBookButton,
        planButton,
        shoppingListButton,
        cookingModeButton);

    PrimaryButton settingsButton = new PrimaryButton(
        "Settings",
        PrimaryButton.Type.WHITE,
        new Icon("settings"));
    settingsButton.setPrefSize(buttonWidth, buttonHeight);
    settingsButton.setOnAction(e -> {
      sceneLoader.switchScene("settings");
      setSelectedButton(settingsButton);
    });

    VBox settingButtonContainer = new VBox(); // container for the settings button at the bottom
    settingButtonContainer.getChildren().add(settingsButton);
    // place the settings button at the bottom
    settingButtonContainer.getStyleClass().add("bottom-centered");

    // grow the primaryWindowButtonsContainer to fill the space between it and the
    // settings container
    VBox.setVgrow(primaryWindowButtonsContainer, Priority.ALWAYS);

    // add the containers to the sidebar
    super.getChildren().addAll(
        primaryWindowButtonsContainer,
        settingButtonContainer);

    // apply sidebar styling
    this.getStyleClass().add("sidebar");

  }

  /**
   * Sets the selected button and updates the styling of the buttons.
   *
   * @param button The button to set as selected
   */
  private void setSelectedButton(PrimaryButton button) {
    // if no button is selected, select the button
    if (selectedButton == null) {
      selectedButton = button;
      selectedButton.setButtonType(PrimaryButton.Type.PRIMARY);
      return;

    }

    // set the selected button to white and the new button to primary
    selectedButton.setButtonType(PrimaryButton.Type.WHITE);
    selectedButton = button;
    selectedButton.setButtonType(PrimaryButton.Type.PRIMARY);
  }
}
