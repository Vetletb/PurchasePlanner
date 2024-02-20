package no.ntnu.idatt1002.demo.view.components;

import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * Searchbar class for the Application
 */
public class SearchBar extends TextField {

  /**
   * Constructor for the SearchBar
   * @param text the text to be initially displayed in the search bar
   * <p>Uses the {@link #handleTextChange(String)} method to handle the text change</p>
   */
  public SearchBar(String text) {
    super(text);
    
    // Add a listener to the text property
    textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        handleTextChange(newValue);
      }
    });
  }

    /**
     * Method to handle the text change
     * @param newValue the new value of the text
     */
  private void handleTextChange(String newValue) {
    Logger.getLogger("Searchbar").info("Text changed to: " + newValue);
  }

}
