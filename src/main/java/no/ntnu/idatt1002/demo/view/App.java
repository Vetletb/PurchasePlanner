package no.ntnu.idatt1002.demo.view;

import java.util.Properties;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import no.ntnu.idatt1002.demo.ConfigLoader;
import no.ntnu.idatt1002.demo.Logger;

/**
 * The main application class.
 */
public class App extends Application {
  private static Properties p = ConfigLoader.load();

  public static void main(String[] args) {
    System.out.println(p.getProperty("app.name"));

    Logger.getLogger().log("Starting application");
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle(p.getProperty("app_name"));
    StackPane root = new StackPane();
    primaryStage.setScene(new Scene(root, 600, 300));
    primaryStage.show();
  }
}
