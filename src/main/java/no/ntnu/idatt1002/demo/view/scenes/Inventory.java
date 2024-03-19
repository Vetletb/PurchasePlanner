package no.ntnu.idatt1002.demo.view.scenes;

import java.util.Map;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.dao.DBConnectionProvider;
import no.ntnu.idatt1002.demo.data.Item;
import no.ntnu.idatt1002.demo.repo.ItemRegister;
import no.ntnu.idatt1002.demo.view.components.AddPopup;
import no.ntnu.idatt1002.demo.view.components.ItemBanner;
import no.ntnu.idatt1002.demo.view.components.ItemPane;
import no.ntnu.idatt1002.demo.view.components.ItemPopup;
import no.ntnu.idatt1002.demo.view.components.ListHeader;
import no.ntnu.idatt1002.demo.view.components.ListItem;
import no.ntnu.idatt1002.demo.view.components.ViewModeButton;
import no.ntnu.idatt1002.demo.view.components.ViewModeButton.ViewMode;

/**
 * The inventory page.
 */
public class Inventory extends VBox {
  private VBox inventoryContainer = new VBox();
  private ScrollPane scrollPane;
  private Map<Integer, Item> items;
  private ViewMode mode;

  /**
   * The constructor for the inventory page.
   */
  public Inventory() {
    super();

    this.getStyleClass().addAll("full-width", "no-focus");

    scrollPane = new ScrollPane();
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setContent(inventoryContainer);

    // get all items
    ItemRegister itemRegister = new ItemRegister(new DAO(new DBConnectionProvider()));
    itemRegister.getAllItems();
    items = itemRegister.getItems();

    ListHeader inventoryHeader = new ListHeader();
    inventoryHeader.setOnViewModeChange(this::loadInventory);
    inventoryHeader.setOnSearch(this::fullSearch);
    inventoryHeader.setOnSearchQueryChange(this::search);

    inventoryHeader.setOnAdd(this::addItem);

    super.getChildren().addAll(inventoryHeader, scrollPane);
    loadInventory(ViewModeButton.ViewMode.GRID); // Default view mode
  }

  private void addItem() {
    AddPopup addPopup = new AddPopup("Item");
    addPopup.show(this.getScene().getWindow());
    addPopup.setOnAdd((name, category, allergies) -> {
      ItemRegister itemRegister = new ItemRegister(new DAO(new DBConnectionProvider()));
      itemRegister.addItem(name, category, allergies);
      itemRegister.getAllItems();
      items = itemRegister.getItems();
      loadInventory(mode);
    });
  }

  /**
   * Loads the inventory based on the mode given. This method does not handle
   * fetching items from the database. This must be done before calling this
   * method, or nothing will display.
   *
   * @param mode The mode to display the inventory in.
   */
  private void loadInventory(ViewModeButton.ViewMode mode) {
    Logger.debug("loading inventory");
    this.mode = mode; // save the mode for later use

    inventoryContainer.getChildren().clear();

    if (mode == ViewModeButton.ViewMode.GRID) {
      Logger.debug("loading grid");
      Logger.debug(items.values().toString());
      // get all categories
      String[] categories = items.values().stream()
          .map(Item::getCategory)
          .distinct()
          .toArray(String[]::new);

      Logger.debug("categories: " + categories.length);

      // group items based on category
      for (String category : categories) {
        // create a banner for each category
        ItemBanner inventoryBanner = new ItemBanner();

        // add all items of the category to the banner
        items.values().stream()
            .filter(item -> item.getCategory().equals(category))
            .forEach(item -> {
              ItemPane pane = new ItemPane(
                  item.getId(),
                  item.getName(),
                  0,
                  0);
              inventoryBanner.addItem(pane);
              pane.setOnMouseClicked(v -> {
                ItemPopup itemPopup = new ItemPopup(item);
                itemPopup.setOnSave(this::updateItem);
                itemPopup.setOnDelete(this::deleteItem);
                itemPopup.show(this.getScene().getWindow());
              });

            });

        inventoryContainer.getChildren().add(inventoryBanner);
      }
    } else if (mode == ViewModeButton.ViewMode.LIST) {
      // add a header with the attributes of the items
      String[] attibutes = { "id", "name", "category", "allergies" };
      HBox header = new HBox();
      for (String attribute : attibutes) {
        Text text = new Text(attribute);
        HBox container = new HBox();
        text.getStyleClass().addAll("semibold", "text-lg", "centered");
        container.getChildren().add(text);
        container.getStyleClass().add("list-item-attribute");
        header.getChildren().add(container);
      }
      header.setSpacing(100);
      header.setPadding(new Insets(0, 0, 0, 10));
      inventoryContainer.getChildren().add(header);

      // add all items to the list - no grouping
      items.values().forEach(item -> {
        ListItem<Item> listItem = new ListItem<Item>(item);
        listItem.setOnMouseClicked(v -> {
          ItemPopup itemPopup = new ItemPopup(item);
          itemPopup.setOnSave(this::updateItem);
          itemPopup.setOnDelete(this::deleteItem);
          itemPopup.show(this.getScene().getWindow());
        });
        inventoryContainer.getChildren().add(listItem);
      });
    }
  }

  private void fullSearch(String query) {
    search(query);
    this.requestFocus();

  }

  private void search(String query) {
    ItemRegister itemRegister = new ItemRegister(new DAO(new DBConnectionProvider()));
    itemRegister.searchItemsByName(query);
    items = itemRegister.getItems();
    loadInventory(mode);
  }

  private void updateItem(int id, String name, String category, String allergies) {
    ItemRegister itemRegister = new ItemRegister(new DAO(new DBConnectionProvider()));
    itemRegister.getAllItems();
    itemRegister.updateItem(id, name, category, allergies);
    itemRegister.getAllItems();
    items = itemRegister.getItems();
    loadInventory(mode);
  }

  private void deleteItem(int id) {
    ItemRegister itemRegister = new ItemRegister(new DAO(new DBConnectionProvider()));
    itemRegister.getAllItems();
    itemRegister.deleteItem(id);
    itemRegister.getAllItems();
    items = itemRegister.getItems();
    loadInventory(mode);
  }
}
