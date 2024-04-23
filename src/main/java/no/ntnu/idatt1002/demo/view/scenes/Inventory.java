package no.ntnu.idatt1002.demo.view.scenes;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.UpdateableScene;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.dao.DBConnectionProvider;
import no.ntnu.idatt1002.demo.data.InventoryItem;
import no.ntnu.idatt1002.demo.data.Item;
import no.ntnu.idatt1002.demo.repo.InventoryRegister;
import no.ntnu.idatt1002.demo.repo.ItemRegister;
import no.ntnu.idatt1002.demo.view.components.AddPopup;
import no.ntnu.idatt1002.demo.view.components.Field;
import no.ntnu.idatt1002.demo.view.components.ItemBanner;
import no.ntnu.idatt1002.demo.view.components.ItemPane;
import no.ntnu.idatt1002.demo.view.components.ItemPopup;
import no.ntnu.idatt1002.demo.view.components.ListHeader;
import no.ntnu.idatt1002.demo.view.components.ListItem;
import no.ntnu.idatt1002.demo.view.components.Toast;
import no.ntnu.idatt1002.demo.view.components.ToastProvider;
import no.ntnu.idatt1002.demo.view.components.ViewModeButton;
import no.ntnu.idatt1002.demo.view.components.ViewModeButton.ViewMode;

/**
 * The inventory page.
 */
public class Inventory extends VBox implements UpdateableScene {
  private VBox inventoryContainer = new VBox();
  private ScrollPane scrollPane;
  private List<InventoryItem> inventoryItems;
  private ViewMode mode;
  private InventoryRegister inventoryRegister;

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
    inventoryRegister = new InventoryRegister(new DAO(new DBConnectionProvider()));
    inventoryRegister.getAllInventoryItems();
    inventoryItems = inventoryRegister.getInventoryItems();

    ListHeader inventoryHeader = new ListHeader();
    inventoryHeader.setOnViewModeChange(this::loadInventory);
    inventoryHeader.setOnSearch(this::fullSearch);
    inventoryHeader.setOnSearchQueryChange(this::search);

    inventoryHeader.setOnAdd(this::addInventoryItem);

    super.getChildren().addAll(inventoryHeader, scrollPane);
    loadInventory(ViewModeButton.ViewMode.GRID); // Default view mode
  }

  private void addInventoryItem() {
    AddPopup addPopup = new AddPopup("Item");

    ItemRegister itemRegister = new ItemRegister(new DAO(new DBConnectionProvider()));
    itemRegister.getAllItems();

    addPopup.addField(Field.ofList(
        "Item",
        itemRegister.getItems().values().stream().toList()));

    addPopup.addField(Field.ofString("Unit"));
    addPopup.addField(Field.ofNumber("Amount"));
    addPopup.addField(Field.ofNumber("Expiration date"));
    addPopup.setSpecialPrompt(new Text("Is an item missing? Add it here"), v -> {
      this.addItem();
    });

    addPopup.show(this.getScene().getWindow());
    addPopup.setOnAdd((Object[] o) -> {
      Integer id;
      String unit;
      Number amount;
      Number expirationDate;

      try {
        id = (Integer) o[0];
        unit = (String) o[1];
        amount = (Number) o[2];
        expirationDate = (Number) o[3];

        if (id == null || unit.isEmpty() || amount.intValue() < 0 || expirationDate.intValue() < 0) {
          throw new Exception("Invalid input");
        }

      } catch (Exception e) {
        ToastProvider.enqueue(new Toast("Failed to add item", "One or more fields were empty", Toast.ToastType.ERROR));
        e.printStackTrace();
        return;
      }

      try {
        inventoryRegister.addInventoryItem(id, (int) amount, unit, expirationDate.intValue());

      } catch (Exception e) {
        ToastProvider.enqueue(new Toast("Failed to add item", e.getMessage(), Toast.ToastType.ERROR));
        e.printStackTrace();
        return;
      }
      inventoryRegister.getAllInventoryItems();
      inventoryItems = inventoryRegister.getInventoryItems();
      ToastProvider.enqueue(new Toast("Success", "The item was successfully added", Toast.ToastType.SUCCESS));
      loadInventory(mode);
    });
  }

  private void addItem() {
    AddPopup addPopup = new AddPopup("Item");
    addPopup.show(this.getScene().getWindow());
    addPopup.addField(Field.ofString("Name"));
    addPopup.addField(Field.ofString("Category"));
    addPopup.addField(Field.ofString("Allergies"));
    addPopup.setOnAdd((Object[] o) -> {
      try {
        String name = (String) o[0];
        String category = (String) o[1];
        String allergies = (String) o[2];
        try {
          ItemRegister itemRegister = new ItemRegister(new DAO(new DBConnectionProvider()));
          itemRegister.addItem(name, category, allergies);
          itemRegister.getAllItems();
        } catch (Exception e) {
          Logger.fatal("Failed to add item");
          e.printStackTrace();
          ToastProvider.enqueue(
              new Toast("Failed to add item", e.getMessage(), Toast.ToastType.ERROR));
          return;
        }
        ToastProvider.enqueue(
            new Toast("Success", "The item was successfully added", Toast.ToastType.SUCCESS));
        loadInventory(mode);
      } catch (Exception e) {
        ToastProvider.enqueue(
            new Toast("Failed to add item", "One or more fields were empty", Toast.ToastType.ERROR));
        e.printStackTrace();
      }
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
    this.mode = mode; // save the mode for later use

    inventoryContainer.getChildren().clear();

    if (mode == ViewModeButton.ViewMode.GRID) {
      // get all categories
      String[] categories = inventoryItems.stream()
          .map(Item::getCategory)
          .distinct()
          .toArray(String[]::new);

      // group items based on category
      for (String category : categories) {
        // create a banner for each category
        ItemBanner inventoryBanner = new ItemBanner();
        inventoryBanner.setTitle(category);

        // add all items of the category to the banner
        inventoryItems.stream()
            .filter(item -> item.getCategory().equals(category))
            .forEach(item -> {
              ItemPane pane = new ItemPane(
                  item.getItemId(),
                  item.getName(),
                  0,
                  0,
                  "items");
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
      inventoryItems.forEach(item -> {
        ListItem<InventoryItem> listItem = new ListItem<>(item);
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
    inventoryRegister.searchInventoryByName(query);
    inventoryItems = inventoryRegister.getInventoryItems();
    loadInventory(mode);
  }

  private void updateItem(int id, int itemId, String name, String category, String allergies, String unit, int amount,
      int expirationDate) {
    inventoryRegister.getAllInventoryItems();

    ItemRegister itemRegister = new ItemRegister(new DAO(new DBConnectionProvider()));
    itemRegister.getAllItems();
    itemRegister.updateItem(itemId, name, category, allergies);

    try {
      inventoryRegister.updateInventoryItem(id, itemId, amount, unit, expirationDate);
      inventoryRegister.getAllInventoryItems();
      inventoryItems = inventoryRegister.getInventoryItems();
      ToastProvider.enqueue(
          new Toast("Success", "The item was successfully updated", Toast.ToastType.SUCCESS));
      loadInventory(mode);
    } catch (Exception e) {
      ToastProvider.enqueue(
          new Toast("Failed to update item", e.getMessage(), Toast.ToastType.ERROR));
      e.printStackTrace();
    }
  }

  private void deleteItem(int id, int itemId) {

    try {
      inventoryRegister.getAllInventoryItems();
      inventoryRegister.deleteInventoryItem(id);

      inventoryRegister.getAllInventoryItems();
      inventoryItems = inventoryRegister.getInventoryItems();
      ToastProvider.enqueue(
          new Toast("Success", "The item was successfully deleted", Toast.ToastType.SUCCESS));
      loadInventory(mode);
    } catch (Exception e) {
      ToastProvider.enqueue(
          new Toast("Failed to delete item", e.getMessage(), Toast.ToastType.ERROR));
      e.printStackTrace();
    }
  }

  public void updateScene() {
    inventoryRegister.getAllInventoryItems();
    inventoryItems = inventoryRegister.getInventoryItems();
    loadInventory(mode);
  }

  public VBox createScene() {
    return this;
  }
}
