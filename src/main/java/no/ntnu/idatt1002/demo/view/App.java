package no.ntnu.idatt1002.demo.view;

import java.util.Properties;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import no.ntnu.idatt1002.demo.ConfigLoader;
import no.ntnu.idatt1002.demo.Logger;

/**
 * The main application class.
 */
public class App extends Application {
  private static Properties p = ConfigLoader.load();

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
    primaryStage.setScene(new Scene(root, bounds.getWidth(), bounds.getHeight()));
    primaryStage.show();

    Logger.info("Application started");
  }
}
