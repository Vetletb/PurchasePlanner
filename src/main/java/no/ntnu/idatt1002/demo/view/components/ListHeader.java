package no.ntnu.idatt1002.demo.view.components;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.view.components.PrimaryButton.Type;

/**
 * A list header.
 * 
 * <p>
 * This class is used to create a header for a list. It contains view mode
 * buttns, an add buttona and a search bar.
 * </p>
 */
public class ListHeader extends HBox {

  private static final int INSET_AMOUNT = 50;

  private ViewModeButton listButton;
  private ViewModeButton gridButton;
  private PrimaryButton addButton;
  private SearchBar searchBar;
  private Dropdown filter;
  private Dropdown sort;

  /**
   * Callback for adding items.
   */
  public interface OnAdd {
    void cb();
  }

  /**
   * Callback for searching.
   */
  public interface OnSearch {
    void cb(String query);
  }

  /**
   * Callback for when the seach query changes.
   */
  public interface OnSearchQueryChange {
    void cb(String query);
  }

  /**
   * Callback for changing view mode.
   */
  public interface OnViewModeChange {
    void cb(ViewModeButton.ViewMode mode);
  }

  // change these to enums?
  /**
   * Callback for changing filter.
   */
  public interface onFilterChange {
    void cb(String filter);
  }

  /**
   * Callback for sorting.
   */
  public interface OnSortChange {
    void cb(String sort);
  }

  private OnAdd onAdd;
  private OnSearch onSearch;
  private OnSearchQueryChange onSearchQueryChange;
  private OnViewModeChange onViewModeChange;
  private onFilterChange onFilterChange;
  private OnSortChange onSortChange;

  public ListHeader setOnAdd(OnAdd onAdd) {
    this.onAdd = onAdd;
    return this;
  }

  /**
   * Set the onSearch callback.
   *
   * @param onSearch The callback
   * @return The list header
   */
  public ListHeader setOnSearch(OnSearch onSearch) {
    this.onSearch = onSearch;
    return this;

  }

  /**
   * Set the onSearchQueryChange callback.
   *
   * @param onSearchQueryChange The callback
   * @return The list header
   */
  public ListHeader setOnSearchQueryChange(OnSearchQueryChange onSearchQueryChange) {
    this.onSearchQueryChange = onSearchQueryChange;
    return this;
  }

  /**
   * Set the onViewModeChange callback.
   *
   * @param onViewModeChange The callback
   * @return The list header
   */
  public ListHeader setOnViewModeChange(OnViewModeChange onViewModeChange) {
    this.onViewModeChange = onViewModeChange;
    return this;
  }

  /**
   * Set the onFilterChange callback.
   *
   * @param onFilterChange The callback
   * @return The list header
   */
  public ListHeader setOnFilterChange(onFilterChange onFilterChange) {
    this.onFilterChange = onFilterChange;
    return this;
  }

  /**
   * Set the onSortChange callback.
   *
   * @param onSortChange The callback
   * @return The list header
   */
  public ListHeader setOnSortChange(OnSortChange onSortChange) {
    this.onSortChange = onSortChange;
    return this;
  }

  /**
   * Constructor for the list header.
   * 
   * <p>
   * Constructs and creates a list header.
   * </p>
   */
  public ListHeader() {
    super();

    this.getStyleClass().addAll("full-width", "list-header");

    HBox viewmodeContainer = new HBox();
    HBox.setHgrow(viewmodeContainer, Priority.ALWAYS);
    viewmodeContainer.setPadding(new Insets(0, 0, 0, INSET_AMOUNT));

    HBox filtersortContainer = new HBox();

    filter = new Dropdown("Filter", List.of("All", "Active", "Inactive"));
    filter.setOnAction(e -> {
      if (onFilterChange == null) {
        Logger.debug("No filter change callback set");
        return;
      }
      onFilterChange.cb(filter.getValue());
    });

    sort = new Dropdown("Sort", List.of("Name", "Date", "Type"));
    sort.setOnAction(e -> {
      if (onSortChange == null) {
        Logger.debug("No sort change callback set");
        return;
      }
      onSortChange.cb(sort.getValue());
    });

    filtersortContainer.getChildren().addAll(filter, sort);

    HBox listgridContaner = new HBox();

    listButton = new ViewModeButton(ViewModeButton.ViewMode.LIST);
    listButton.setOnAction(e -> {
      if (onViewModeChange == null) {
        Logger.debug("No view mode change callback set");
        return;
      }
      onViewModeChange.cb(ViewModeButton.ViewMode.LIST);
    });

    gridButton = new ViewModeButton(ViewModeButton.ViewMode.GRID);
    gridButton.setOnAction(e -> {
      if (onViewModeChange == null) {
        Logger.debug("No view mode change callback set");
        return;
      }
      onViewModeChange.cb(ViewModeButton.ViewMode.GRID);
    });

    listgridContaner.getChildren().addAll(listButton, gridButton);

    viewmodeContainer.setSpacing(20);
    viewmodeContainer.getChildren().addAll(filtersortContainer, listgridContaner);

    HBox actionContainer = new HBox();
    actionContainer.getStyleClass().add("right-centered");
    actionContainer.setPadding(new Insets(0, INSET_AMOUNT, 0, 0));

    addButton = new PrimaryButton(Type.PRIMARY, new Icon("plus").setFillColor(Color.BLACK));
    addButton.setPrefWidth(100);
    addButton.getIcon().getStyleClass().add("centered");

    addButton.setOnAction(e -> {
      if (onAdd == null) {
        Logger.error("No add callback set");
        return;
      }
      onAdd.cb();
    });

    searchBar = new SearchBar();
    searchBar.setOnSearch(query -> {
      if (onSearch == null) {
        return;
      }
      onSearch.cb(query);
    });

    searchBar.setOnChange(query -> {
      if (onSearchQueryChange == null) {
        return;
      }
      onSearchQueryChange.cb(query);
    });

    actionContainer.setSpacing(20);
    actionContainer.getChildren().addAll(addButton, searchBar);
    this.getChildren().addAll(viewmodeContainer, actionContainer);
  }

}
