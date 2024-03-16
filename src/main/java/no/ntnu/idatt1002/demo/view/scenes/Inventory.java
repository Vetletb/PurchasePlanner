package no.ntnu.idatt1002.demo.view.scenes;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.demo.data.Item;
import no.ntnu.idatt1002.demo.view.components.ItemBanner;
import no.ntnu.idatt1002.demo.view.components.ItemPane;
import no.ntnu.idatt1002.demo.view.components.ListHeader;
import no.ntnu.idatt1002.demo.view.components.ListItem;
import no.ntnu.idatt1002.demo.view.components.ViewModeButton;

/**
 * The inventory page.
 */
public class Inventory extends VBox {
  private VBox inventoryContainer = new VBox();
  private ArrayList<Item> items = new ArrayList<>();

  public Inventory() {
    super();

    this.getStyleClass().add("full-width");

    // Temporary test data
    // TODO: replace this with a planningmanager call
    items.add(new Item("test1", "category1", "none"));
    items.add(new Item("test2", "category1", "none"));
    items.add(new Item("test3", "category1", "none"));
    items.add(new Item("test4", "category1", "none"));
    items.add(new Item("test5", "category2", "none"));
    items.add(new Item("test6", "category2", "none"));
    items.add(new Item("test7", "category2", "none"));
    items.add(new Item("test8", "category2", "none"));
    items.add(new Item("test9", "category2", "none"));

    ListHeader inventoryHeader = new ListHeader();
    inventoryHeader.setOnViewModeChange(this::loadInventory);

    super.getChildren().addAll(inventoryHeader, inventoryContainer);
    loadInventory(ViewModeButton.ViewMode.GRID); // Default view mode
  }

  private void loadInventory(ViewModeButton.ViewMode mode) {
    inventoryContainer.getChildren().clear();

    if (mode == ViewModeButton.ViewMode.GRID) {
      // get all categories
      String[] categories = items.stream().map(Item::getCategory).distinct().toArray(String[]::new);

      // group items based on category
      for (String category : categories) {
        // create a banner for each category
        ItemBanner inventoryBanner = new ItemBanner();

        // add all items of the category to the banner
        items.stream().filter(item -> item.getCategory().equals(category)).forEach(item -> {
          inventoryBanner.addItem(new ItemPane(item.getId(), item.getName(), 0, 0));
        });
        inventoryContainer.getChildren().add(inventoryBanner);
      }
    } else if (mode == ViewModeButton.ViewMode.LIST) {
      // add a header with the attributes of the items
      HBox header = new HBox();
      for (String attribute : items.get(0).getAttributeNames()) {
        Text text = new Text(attribute);
        text.getStyleClass().addAll("semibold", "text-lg");
        header.getChildren().add(text);
      }
      header.setSpacing(100);
      header.setPadding(new Insets(0, 0, 0, 5));
      inventoryContainer.getChildren().add(header);

      // add all items to the list - no grouping
      items.forEach(item -> inventoryContainer.getChildren().add(new ListItem<Item>(item)));
    }
  }
}
