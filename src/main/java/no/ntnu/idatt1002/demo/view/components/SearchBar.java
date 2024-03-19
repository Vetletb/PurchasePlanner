package no.ntnu.idatt1002.demo.view.components;

import javafx.scene.layout.StackPane;
import no.ntnu.idatt1002.demo.Logger;

/**
 * Searchbar class for the Application.
 */
public class SearchBar extends StackPane {
  public static final int DEFAULT_WIDTH = 200;
  public static final int DEFAULT_HEIGHT = 30;

  /**
   * Callback for when the user presses enter in the search bar.
   */
  public interface OnSearch {
    void cb(String query);
  }

  /**
   * Callback for when the search bar gains focus.
   */
  public interface OnFocus {
    void cb();
  }

  /**
   * Callback for when the search bar loses focus.
   */
  public interface OnBlur {
    void cb();
  }

  /**
   * Callback for when the contents of the search bar changes.
   */
  public interface OnChange {
    void cb(String query);
  }

  private OnChange onChange;
  private OnSearch onSearch;
  private OnFocus onFocus;
  private OnBlur onBlur;
  private Icon searchIcon;
  private InputField inputField;

  /**
   * Constructor for the SearchBar class.
   */
  public SearchBar() {
    super();
    this.getStyleClass().addAll("centered", "search-bar-container");
    setPrefSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    setMaxSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    setMinSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

    inputField = new InputField("Search");
    inputField.getStyleClass().addAll("centered", "search-bar");

    // focus the input field when the search icon is clicked
    searchIcon = new Icon("search", 12);
    searchIcon.setOnMouseClicked(e -> activate());

    // listen for changes in the input field
    inputField.focusedProperty().addListener(v -> {
      if (inputField.isFocused()) {
        activate();
      } else {
        deactivate();
      }
    });

    // Listen for search action
    inputField.setOnAction(e -> {
      if (onSearch == null) {
        Logger.error("No onSearch callback set for search bar");
        return;
      }
      onSearch.cb(inputField.getText());
    });

    // Listen for changes in the input field
    inputField.setOnKeyTyped(v -> {
      if (onChange == null) {
        Logger.error("No onChange callback set for search bar");
        return;
      }
      onChange.cb(inputField.getText());
    });

    searchIcon.setX(40);
    searchIcon.setY(5);

    this.getChildren().addAll(inputField, searchIcon);

  }

  /**
   * Activates the search bar.
   */
  private void activate() {
    this.getChildren().remove(searchIcon);
    inputField.requestFocus();

    if (onFocus == null) {
      Logger.error("No onFocus callback set for search bar");
      return;
    }
    onFocus.cb();
  }

  /**
   * Deactivates the search bar.
   */
  private void deactivate() {
    if (inputField.getText().isEmpty() && !this.getChildren().contains(searchIcon)) {
      this.getChildren().add(searchIcon);
    }

    if (onBlur == null) {
      Logger.error("No onBlur callback set for search bar");
      return;
    }
    onBlur.cb();
  }

  /**
   * Sets the on search callback for the search bar.
   *
   * @param onSearch The callback
   * @return The search bar
   */

  public SearchBar setOnSearch(OnSearch onSearch) {
    this.onSearch = onSearch;
    return this;
  }

  /**
   * Sets the on change callback for the search bar.
   *
   * @param onChange The callback
   * @return The search bar
   */
  public SearchBar setOnChange(OnChange onChange) {
    this.onChange = onChange;
    return this;
  }

  /**
   * Sets the on focus callback for the search bar.
   *
   * @param onFocus The callback
   * @return The search bar
   */
  public SearchBar setOnFocus(OnFocus onFocus) {
    this.onFocus = onFocus;
    return this;
  }

  /**
   * Sets the on blur callback for the search bar.
   *
   * @param onBlur The callback
   * @return The search bar
   */
  public SearchBar setOnBlur(OnBlur onBlur) {
    this.onBlur = onBlur;
    return this;
  }
}
