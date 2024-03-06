package no.ntnu.idatt1002.demo.view.components;

import org.apache.commons.compress.archivers.dump.DumpArchiveEntry.TYPE;

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

  private interface OnAdd {
    void cb();
  }

  private interface OnSearch {
    void cb(String query);
  }

  private interface OnViewModeChange {
    void cb(ViewModeButton.ViewMode mode);
  }

  private OnAdd onAdd;
  private OnSearch onSearch;
  private OnViewModeChange onViewModeChange;

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

  public ListHeader() {
    super();

    this.getStyleClass().add("full-width");

    HBox viewmodeContainer = new HBox();
    HBox.setHgrow(viewmodeContainer, Priority.ALWAYS);
    viewmodeContainer.setPadding(new Insets(0, 0, 0, INSET_AMOUNT));

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

    viewmodeContainer.getChildren().addAll(listButton, gridButton);

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
