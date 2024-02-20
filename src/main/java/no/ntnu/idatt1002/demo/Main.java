package no.ntnu.idatt1002.demo;

import javafx.application.Application;
import no.ntnu.idatt1002.demo.view.App;

/**
 * Use this class to start the application.
 */
public class Main {

  /**
   * Main method for the application.
   */
  public static void main(String[] args) {
    Logger.setLevel(0);
    Application.launch(App.class, args);
  }
}
