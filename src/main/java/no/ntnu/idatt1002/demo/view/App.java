package no.ntnu.idatt1002.demo.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The main application class.
 */
public class App extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("purchase planner");
    StackPane root = new StackPane();
    primaryStage.setScene(new Scene(root, 600, 300));
    primaryStage.show();
  }
}
