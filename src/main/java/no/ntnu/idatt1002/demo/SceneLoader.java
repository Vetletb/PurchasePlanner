package no.ntnu.idatt1002.demo;

import java.util.HashMap;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.demo.view.scenes.CookBook;
import no.ntnu.idatt1002.demo.view.scenes.CookingMode;
import no.ntnu.idatt1002.demo.view.scenes.Inventory;
import no.ntnu.idatt1002.demo.view.scenes.Plan;
import no.ntnu.idatt1002.demo.view.scenes.Settings;
import no.ntnu.idatt1002.demo.view.scenes.ShoppingList;

/**
 * The scene loader for the application.
 */
public class SceneLoader extends VBox {
  private final HashMap<String, VBox> scenes = new HashMap<>();

  /**
   * Constructor for the scene loader.
   *
   * <p>
   * Adds the scenes to the scene loader.
   * </p>
   */
  public SceneLoader() {
    super();

    // Add the scenes to the scene loader
    scenes.put("inventory", new Inventory());
    scenes.put("cookbook", new CookBook());
    scenes.put("shoppinglist", new ShoppingList());
    scenes.put("plan", new Plan());
    scenes.put("cookingmode", new CookingMode());
    scenes.put("settings", new Settings());

    super.getChildren().add(scenes.get("inventory"));
  }

  /**
   * Switches the scene to the given scene.
   *
   * @param scene The scene to switch to
   * @return True if the scene exists, false otherwise
   */
  public boolean switchScene(String scene) {
    if (scenes.containsKey(scene)) {
      super.getChildren().clear();
      super.getChildren().add(scenes.get(scene));
      return true;
    }
    return false;
  }

}
