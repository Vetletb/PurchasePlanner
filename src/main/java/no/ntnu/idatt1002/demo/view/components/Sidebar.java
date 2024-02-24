package no.ntnu.idatt1002.demo.view.components;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.demo.SceneLoader;

/**
 * The sidebar for the application.
 */
public class Sidebar extends VBox {

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

    Button inventoryButton = new Button("Inventory");
    inventoryButton.setOnAction(e -> sceneLoader.switchScene("inventory"));

    Button cookBookButton = new Button("CookBook");
    cookBookButton.setOnAction(e -> sceneLoader.switchScene("cookbook"));

    Button planButton = new Button("Plan");
    planButton.setOnAction(e -> sceneLoader.switchScene("plan"));

    Button shoppingListButton = new Button("Shopping list");
    shoppingListButton.setOnAction(e -> sceneLoader.switchScene("shoppinglist"));

    Button cookingModeButton = new Button("Cooking mode");
    cookingModeButton.setOnAction(e -> sceneLoader.switchScene("cookingmode"));

    super.getChildren().addAll(
        inventoryButton,
        cookBookButton,
        planButton,
        shoppingListButton,
        cookingModeButton);

  }
}
