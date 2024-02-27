package no.ntnu.idatt1002.demo.view.components;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import no.ntnu.idatt1002.demo.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * Searchbar class for the Application
 */
public class SearchBar extends TextField {

  private String fieldText;
  private static final int DEFAULT_WIDTH = 300;
  private static final int DEFAULT_HEIGHT = 30;
  private static final int DEFAULT_POSITION_X = 0;
  private static final int DEFAULT_POSITION_Y = 0;

  // TODO add a getBounds method to get the bounds of the screen to the app class
  private Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
  private final int OriginOffsetX = -((int) bounds.getMaxX() - DEFAULT_WIDTH) / 2;
  private final int OriginOffsetY = -((int) bounds.getMaxY() / 2 - 2 * DEFAULT_HEIGHT);

  /**
   * Constructor for the SearchBar with no provided information
   */
  public SearchBar() {
    this("", DEFAULT_POSITION_X, DEFAULT_POSITION_Y);
  }

  /**
   * Constructor for the SearchBar with only the position of the search bar
   * 
   * @param text the text to be initially displayed in the search bar
   */
  public SearchBar(String text) {
    this(text, DEFAULT_POSITION_X, DEFAULT_POSITION_Y);
  }

  /**
   * Constructor for the SearchBar with only the position of the search bar
   * 
   * @param positionX the x position of the search bar
   * @param positionY the y position of the search bar
   */
  public SearchBar(int positionX, int positionY) {
    this("", positionX, positionY);
  }

  /**
   * Constructor for the SearchBar
   * 
   * @param text the text to be initially displayed in the search bar
   *             <p>
   *             Uses the {@link #handleTextChange(String)} method to handle the
   *             text change
   *             </p>
   */
  public SearchBar(String text, int positionX, int positionY) {
    super(text);

    // Apply CSS styling for rounded corners
    setStyle("-fx-background-radius: 20;");

    // Set the position of the search bar
    setPosition(positionX, positionY);
    Logger.debug("Offset values:" + OriginOffsetX + " " + OriginOffsetY);

    setMaxWidth(DEFAULT_WIDTH);
    setMaxHeight(DEFAULT_HEIGHT);

    // Add a listener to the text property
    textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        handleTextChange(newValue);
      }
    });
  }

  /**
   * Method to get the value of the search bar
   * 
   * @return the value of the search bar
   */
  public String getValue() {
    return fieldText;
  }

  /**
   * Method to handle the text change
   * 
   * @param newValue the new value of the text
   */
  private void handleTextChange(String newValue) {
    fieldText = newValue;
    Logger.debug("Text changed to: " + getValue());
  }

  /**
   * Method to set the position of the search bar
   * 
   * @param positionX the x position of the search bar
   * @param positionY the y position of the search bar
   */
  public void setPosition(int positionX, int positionY) {
    setTranslateX(positionX + OriginOffsetX);
    setTranslateY(positionY + OriginOffsetY);
  }

}
