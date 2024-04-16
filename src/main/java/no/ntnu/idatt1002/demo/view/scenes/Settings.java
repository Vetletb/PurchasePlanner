package no.ntnu.idatt1002.demo.view.scenes;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.demo.UpdateableScene;

/**
 * The inventory page.
 */
public class Settings extends VBox implements UpdateableScene {

  public Settings() {
    super();

    super.getChildren().addAll(new Text("Settings"));
  }

  public void updateScene() {
    return;
  }

  public VBox createScene() {
    return this;
  }

}
