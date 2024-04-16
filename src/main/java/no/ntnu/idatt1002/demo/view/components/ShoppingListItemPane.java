package no.ntnu.idatt1002.demo.view.components;

import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import no.ntnu.idatt1002.demo.dao.DAO;
import no.ntnu.idatt1002.demo.dao.DBConnectionProvider;
import no.ntnu.idatt1002.demo.data.ShoppingListItem;
import no.ntnu.idatt1002.demo.repo.ShoppingListItemRegister;

public class ShoppingListItemPane extends HBox {
  private final int shoppingListItemId;
  private String name;
  private String category;
  private int quantity;
  private String unit;
  private boolean isSelected;
  CheckBoxButton checkBoxButton;

  public ShoppingListItemPane(int shoppingListItemId,String name, String category, int quantity, String unit) {
    this.shoppingListItemId = shoppingListItemId;
    this.name = name;
    this.category = category;
    this.quantity = quantity;
    this.unit = unit;
    this.isSelected = false;
  }

  public ShoppingListItemPane(ShoppingListItem shoppingListItem) {
    this(shoppingListItem.getId(), shoppingListItem.getName(), shoppingListItem.getCategory(), shoppingListItem.getQuantity(), shoppingListItem.getUnit());

    Label itemNameLabel = new Label(this.name);
    Label categoryLabel = new Label(this.category);
    Label quantityLabel = new Label(Integer.toString(this.quantity));
    Label unitLabel = new Label(this.unit);
    checkBoxButton = new CheckBoxButton(new Icon("checkboxUnSelected"));
    checkBoxButton.setOnAction(e -> {
      isSelected = !isSelected;
      switchIcon();
    });
    checkBoxButton.setScaleX(0.6);
    checkBoxButton.setScaleY(0.6);

    // Css styling
    itemNameLabel.getStyleClass().add("shopping-list-text");
    categoryLabel.getStyleClass().add("shopping-list-text");
    quantityLabel.getStyleClass().add("shopping-list-text");
    unitLabel.getStyleClass().add("shopping-list-text");

    super.getChildren().addAll(itemNameLabel, categoryLabel, quantityLabel, unitLabel, checkBoxButton);
  }

  public int getShoppingListItemId() {
    return shoppingListItemId;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected() {
    this.isSelected = !isSelected;
    switchIcon();
  }
  public String getName() {
    return name;
  }

  public String getCategory() {
    return category;
  }

  public void update(ShoppingListItem item) {
    this.name = item.getName();
    this.category = item.getCategory();
    this.quantity = item.getQuantity();
    this.unit = item.getUnit();
  }

  private void switchIcon(){
    if (isSelected) {
      checkBoxButton.setIcon(new Icon("checkboxSelected"));
    } else {
      checkBoxButton.setIcon(new Icon("checkboxUnSelected"));
    }
  }
}

