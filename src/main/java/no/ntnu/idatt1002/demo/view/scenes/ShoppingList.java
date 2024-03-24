package no.ntnu.idatt1002.demo.view.scenes;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.demo.data.ShoppingListItem;
import no.ntnu.idatt1002.demo.view.components.CheckBoxButton;
import no.ntnu.idatt1002.demo.view.components.Icon;
import no.ntnu.idatt1002.demo.view.components.ListHeader;

/**
 * The inventory page.
 */
public class ShoppingList extends VBox {

  private final HBox contentContainer;
  private final VBox innerContentContainer;
  private final VBox shoppingListContainter;
  private final ScrollPane shoppingListScrollPane;

  private final HBox staticListTopBar;
  private final HBox staticListBottomBar;


  /**
   * Constructor for the ShoppingList class.
   */
  public ShoppingList() {
    super();

    // Initialize the containers
    contentContainer = new HBox(); // Page content container
    innerContentContainer = new VBox(); // Inner content container
    shoppingListContainter = new VBox(); // Shopping list container
    shoppingListScrollPane = new ScrollPane(); // Shopping list scroll pane
    staticListTopBar = new HBox(); // Static top bar
    staticListBottomBar = new HBox(); // Static bottom bar

    // Top bar labels
    Label topBarItemName = new Label("Name");
    Label topBarItemCategory = new Label("Category");
    Label topBarItemQuantity = new Label("Quantity");
    Label topBarItemUnit = new Label("Unit");
    Label topBarItemCheckBox = new Label("Done");

    // Bottom bar labels TODO: Implement the functionality
    Label bottomBarTotalQuantity = new Label("Total quantity: 100");
    Label bottomBarTotalPrice = new Label("Total price: 1200");

    // Add the labels to the top bar
    staticListTopBar.getChildren().addAll(topBarItemName, topBarItemCategory, topBarItemQuantity, topBarItemUnit, topBarItemCheckBox);

    // Add the labels to the bottom bar
    staticListBottomBar.getChildren().addAll(bottomBarTotalQuantity, bottomBarTotalPrice);


    contentContainer.getStyleClass().add("centered");

    shoppingListContainter.getStyleClass().addAll("shopping-list");

    
    shoppingListScrollPane.setContent(shoppingListContainter);
    shoppingListScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    shoppingListScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    innerContentContainer.getChildren().addAll(staticListTopBar, shoppingListScrollPane, staticListBottomBar);
    contentContainer.getChildren().add(innerContentContainer);

    ListHeader listHeader = new ListHeader();
    super.getChildren().addAll(listHeader, contentContainer);

    this.addItem(new ShoppingListItem(1, "Milk", "Dairy", "Lactose", 1, "l"));
    this.addItem(new ShoppingListItem(2, "Bread", "Bread", "Gluten", 1, "pcs"));
    this.addItem(new ShoppingListItem(3, "Eggs", "Eggs", "Eggs", 12, "pcs"));
    this.addItem(new ShoppingListItem(3, "Holy shit a really long name", "Eggs", "Eggs", 12, "pcs"));
    this.addItem(new ShoppingListItem(3, "Holy shit a really long name", "Eggs", "Eggs", 12, "pcs"));
    this.addItem(new ShoppingListItem(3, "Holy shit a really long name", "Eggs", "Eggs", 12, "pcs"));
    this.addItem(new ShoppingListItem(3, "Holy shit a really long name", "Eggs", "Eggs", 12, "pcs"));
    this.addItem(new ShoppingListItem(3, "Holy shit a really long name", "Eggs", "Eggs", 12, "pcs"));
    this.addItem(new ShoppingListItem(3, "Holy shit a really long name", "Eggs", "Eggs", 12, "pcs"));
    this.addItem(new ShoppingListItem(3, "Holy shit a really long name", "Eggs", "Eggs", 12, "pcs"));
    this.addItem(new ShoppingListItem(3, "Holy shit a really long name", "Eggs", "Eggs", 12, "pcs"));
    this.addItem(new ShoppingListItem(3, "Holy shit a really long name", "Eggs", "Eggs", 12, "pcs"));
    this.addItem(new ShoppingListItem(3, "Holy shit a really long name", "Eggs", "Eggs", 12, "pcs"));
    this.addItem(new ShoppingListItem(3, "Holy shit a really long name", "Eggs", "Eggs", 12, "pcs"));
    this.addItem(new ShoppingListItem(3, "Holy shit a really long name", "Eggs", "Eggs", 12, "pcs"));
    this.addItem(new ShoppingListItem(3, "Holy shit a really long name", "Eggs", "Eggs", 12, "pcs"));
    this.addItem(new ShoppingListItem(3, "Holy shit a really long name", "Eggs", "Eggs", 12, "pcs"));
    this.addItem(new ShoppingListItem(3, "Holy shit a really long name", "Eggs", "Eggs", 12, "pcs"));
    this.addItem(new ShoppingListItem(3, "Holy shit a really long name", "Eggs", "Eggs", 12, "pcs"));
    this.addItem(new ShoppingListItem(3, "Holy shit a really long name", "Eggs", "Eggs", 12, "pcs"));


    // CSS styling
    innerContentContainer.getStyleClass().clear();
    innerContentContainer.getStyleClass().add("shopping-list");
    staticListTopBar.getStyleClass().addAll("static-label-container", "rounded-top");
    staticListBottomBar.getStyleClass().addAll("static-bottom-label-container", "rounded-bottom");

      // Labels
    topBarItemName.getStyleClass().add("static-label-container-label");
    topBarItemCategory.getStyleClass().add("static-label-container-label");
    topBarItemQuantity.getStyleClass().add("static-label-container-label");
    topBarItemUnit.getStyleClass().add("static-label-container-label");
    topBarItemCheckBox.getStyleClass().add("static-label-container-label");


    bottomBarTotalPrice.getStyleClass().add("static-label-container-label");
    bottomBarTotalQuantity.getStyleClass().add("static-label-container-label");
  }

  /**
   * This method adds an item to the shopping list.
   * @param item the item to be added
   */
  public void addItem(ShoppingListItem item) {
    // Item Container
    HBox shoppingListItemContainer = new HBox();

    // item Labels
    Label itemName = new Label(item.getName());
    Label itemCategory = new Label(item.getCategory());
    Label itemQuantity = new Label(Integer.toString(item.getQuantity()));
    Label itemUnit = new Label(item.getUnit());

    // Create the checkbox button
    CheckBoxButton checkBoxButton = new CheckBoxButton(new Icon("checkboxUnSelected"));
    checkBoxButton.setScaleX(0.6);
    checkBoxButton.setScaleY(0.6);

    // Add the labels and the checkbox button to the container
    shoppingListItemContainer.getChildren().addAll(itemName, itemCategory, itemQuantity, itemUnit, checkBoxButton);

    // CSS styling
    shoppingListItemContainer.getStyleClass().add("shopping-list-item-container");

    itemName.getStyleClass().add("shopping-list-text");
    itemCategory.getStyleClass().add("shopping-list-text");
    itemQuantity.getStyleClass().add("shopping-list-text");
    itemUnit.getStyleClass().add("shopping-list-text");

    // Add the item to the shopping list
    shoppingListContainter.getChildren().add(shoppingListItemContainer);
  }

}

