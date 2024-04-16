package no.ntnu.idatt1002.demo;

import javafx.scene.layout.VBox;

public interface UpdateableScene {

  /**
   * Method for updating the scene.
   */
  void updateScene();

  /**
   * Method for creating the scene.
   *
   * @return The scene
   */
  VBox createScene();
}
