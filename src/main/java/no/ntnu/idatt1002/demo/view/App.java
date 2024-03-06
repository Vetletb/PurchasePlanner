package no.ntnu.idatt1002.demo.view;

import java.util.Properties;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import no.ntnu.idatt1002.demo.ConfigLoader;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.SceneLoader;
import no.ntnu.idatt1002.demo.view.components.Sidebar;

/**
 * The main application class.
 */
public class App extends Application {
  private static Properties p = ConfigLoader.load();

  private static final int SIDEBAR_WIDTH = 200;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();
    primaryStage.setX(bounds.getMinX());
    primaryStage.setY(bounds.getMinY());

    primaryStage.setTitle(p.getProperty("app_name"));
    StackPane root = new StackPane();
    Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());
    scene.getStylesheets().add(getClass().getResource("/no/ntnu/idatt1002/demo/style/style.css")
        .toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.show();

    HBox mainContainer = new HBox();

    SceneLoader sceneLoader = new SceneLoader();
    sceneLoader.setPrefWidth(bounds.getWidth() - SIDEBAR_WIDTH);
    sceneLoader.setPrefHeight(bounds.getHeight());

    Sidebar sidebar = new Sidebar(sceneLoader);
    sidebar.setPrefHeight(bounds.getHeight());
    sidebar.setPrefWidth(SIDEBAR_WIDTH);

    mainContainer.getChildren().addAll(sidebar, sceneLoader);
    root.getChildren().add(mainContainer);

    Logger.info("Application started");
  }
}
