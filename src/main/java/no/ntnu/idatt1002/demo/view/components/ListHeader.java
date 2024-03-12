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

  private static final int INSET_AMOUNT = 200;

  private ViewModeButton listButton;
  private ViewModeButton gridButton;
  private PrimaryButton addButton;
  private SearchBar searchBar;
  private Dropdown filter;
  private Dropdown sort;

  private interface OnAdd {
    void cb();
  }

  private interface OnSearch {
    void cb(String query);
  }

  private interface OnViewModeChange {
    void cb(ViewModeButton.ViewMode mode);
  }

  // change these to enums?
  private interface onFilterChange {
    void cb(String filter);
  }

  private interface OnSortChange {
    void cb(String sort);
  }

  private OnAdd onAdd;
  private OnSearch onSearch;
  private OnViewModeChange onViewModeChange;
  private onFilterChange onFilterChange;
  private OnSortChange onSortChange;

  public ListHeader setOnAdd(OnAdd onAdd) {
    this.onAdd = onAdd;
    return this;
  }

  public ListHeader setOnSearch(OnSearch onSearch) {
    this.onSearch = onSearch;
    return this;

  }

  public ListHeader setOnViewModeChange(OnViewModeChange onViewModeChange) {
    this.onViewModeChange = onViewModeChange;
    return this;
  }

  public ListHeader setOnFilterChange(onFilterChange onFilterChange) {
    this.onFilterChange = onFilterChange;
    return this;
  }

  public ListHeader setOnSortChange(OnSortChange onSortChange) {
    this.onSortChange = onSortChange;
    return this;
  }

  public ListHeader() {
    super();

    this.getStyleClass().add("full-width");

    HBox viewmodeContainer = new HBox();
    HBox.setHgrow(viewmodeContainer, Priority.ALWAYS);
    viewmodeContainer.setPadding(new Insets(0, 0, 0, INSET_AMOUNT));

    HBox filtersortContainer = new HBox();

    filter = new Dropdown("Filter", List.of("All", "Active", "Inactive"));
    filter.setOnAction(e -> {
      if (onFilterChange == null) {
        Logger.error("No filter change callback set");
        return;
      }
      onFilterChange.cb(filter.getValue());
    });

    sort = new Dropdown("Sort", List.of("Name", "Date", "Type"));
    sort.setOnAction(e -> {
      if (onSortChange == null) {
        Logger.error("No sort change callback set");
        return;
      }
      onSortChange.cb(sort.getValue());
    });

    filtersortContainer.getChildren().addAll(filter, sort);

    HBox listgridContaner = new HBox();

    listButton = new ViewModeButton(ViewModeButton.ViewMode.LIST);
    listButton.setOnAction(e -> {
      if (onViewModeChange == null) {
        Logger.error("No view mode change callback set");
        return;
      }
      onViewModeChange.cb(ViewModeButton.ViewMode.LIST);
    });

    gridButton = new ViewModeButton(ViewModeButton.ViewMode.GRID);
    gridButton.setOnAction(e -> {
      if (onViewModeChange == null) {
        Logger.error("No view mode change callback set");
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

    searchBar = new SearchBar("Search...");

    actionContainer.getChildren().addAll(addButton);
    this.getChildren().addAll(viewmodeContainer, actionContainer);
  }

}
