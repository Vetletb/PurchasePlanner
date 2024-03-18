package no.ntnu.idatt1002.demo.view.scenes;

import java.util.ArrayList;
import java.util.Map;

import javafx.geometry.Insets;
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
import no.ntnu.idatt1002.demo.view.components.ListHeader;
import no.ntnu.idatt1002.demo.view.components.ListItem;
import no.ntnu.idatt1002.demo.view.components.ViewModeButton;
import no.ntnu.idatt1002.demo.view.components.ViewModeButton.ViewMode;

/**
 * The inventory page.
 */
public class Inventory extends VBox {
  private VBox inventoryContainer = new VBox();
  private Map<Integer, Item> items;
  private ViewMode mode;

  public Inventory() {
    super();

    this.getStyleClass().add("full-width");

    // get all items
    ItemRegister itemRegister = new ItemRegister(new DAO(new DBConnectionProvider()));
    itemRegister.getAllItems();
    items = itemRegister.getItems();

    ListHeader inventoryHeader = new ListHeader();
    inventoryHeader.setOnViewModeChange(this::loadInventory);

    inventoryHeader.setOnAdd(this::AddItem);

    super.getChildren().addAll(inventoryHeader, inventoryContainer);
    loadInventory(ViewModeButton.ViewMode.GRID); // Default view mode
  }

  private void AddItem() {
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
              inventoryBanner.addItem(new ItemPane(item.getId(), item.getName(), 0, 0));
            });

        inventoryContainer.getChildren().add(inventoryBanner);
      }
    } else if (mode == ViewModeButton.ViewMode.LIST) {
      // add a header with the attributes of the items
      String[] attibutes = { "id", "name", "category", "allergies" };
      HBox header = new HBox();
      for (String attribute : attibutes) {
        Text text = new Text(attribute);
        text.getStyleClass().addAll("semibold", "text-lg");
        header.getChildren().add(text);
      }
      header.setSpacing(100);
      header.setPadding(new Insets(0, 0, 0, 5));
      inventoryContainer.getChildren().add(header);

      // add all items to the list - no grouping
      items.values().forEach(item -> inventoryContainer.getChildren()
          .add(new ListItem<Item>(item)));
    }
  }
}
