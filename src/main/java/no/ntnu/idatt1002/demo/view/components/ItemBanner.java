package no.ntnu.idatt1002.demo.view.components;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.HBox;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import no.ntnu.idatt1002.demo.Logger;

//TODO: change this class to generate

/**
 * A banner of items.
 */
public class ItemBanner extends HBox {
  private final ArrayList<ItemPane> items = new ArrayList<ItemPane>();
  private int itemListIndex = 0;

  ArrowButton leftArrowButton = new ArrowButton(ArrowButton.Direction.LEFT);
  ArrowButton rightArrowButton = new ArrowButton(ArrowButton.Direction.RIGHT);

  public ItemBanner() {
    this(new ItemPane[0]);
  }

  public ItemBanner(ItemPane item) {
    this(new ItemPane[] { item });
  }

  public ItemBanner(ItemPane[] items) {
    super();
    this.items.addAll(List.of(items));

    // Add action listeners to the arrow buttons to change the index and update the
    // page
    leftArrowButton.setOnAction(e -> {
      transitionTo(getItemListIndex() - 1);
      update();
    });

    rightArrowButton.setOnAction(e -> {
      transitionTo(getItemListIndex() + 1);
    });

    update();
  }

  public void addItem(ItemPane item) {
    items.add(item);
    update();
  }

  public void setItems(ItemPane[] items) {
    this.items.clear();
    this.items.addAll(List.of(items));
    update();
  }

  /**
   * Starts a transition to the specified item index.
   *
   * @param itemIndex The index of the item to transition to.
   */
  public void transitionTo(int itemIndex) {
    if (verifyIndex(itemIndex, items.size()) == itemListIndex) {
      return;
    }
    startTransition(itemIndex - itemListIndex, itemIndex);
  }

  private void startTransition(int itemsToMove, int moveIndex) {
    int animationTime = 300;

    if (itemsToMove == 0) {
      return;
    }
    for (int i = 0; i < Math.abs(itemsToMove) + 3; i++) {

      int currentItem = i + getItemListIndex() - 1;

      // Check if the item index is out of bounds
      if (currentItem < 0 || currentItem > items.size()) {
        continue;
      }

      // If the length of items is less than 3, don't move the items
      if (items.size() <= 3) {
        continue;
      }

      // Fade out the item if it is going to be moved out of the banner
      if (i <= itemsToMove || i > itemsToMove + 3) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(this.items.get(currentItem));
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setDuration(javafx.util.Duration.millis(animationTime / 2));
        fadeTransition.play();
      }

      // TODO: add a fade in transition for the new items

      TranslateTransition translateTransition = new TranslateTransition();
      translateTransition.setNode(this.items.get(currentItem));
      translateTransition.setByX(itemsToMove * -1 * ((this.getWidth() / 3) - this.rightArrowButton.getWidth()));
      translateTransition.setDuration(javafx.util.Duration.millis(animationTime));
      translateTransition.setInterpolator(javafx.animation.Interpolator.EASE_BOTH);
      translateTransition.play();

      // cleaning up after every single transition is dumb
      // TODO: fix this
      translateTransition.setOnFinished(e -> {
        animationCleanup(moveIndex);
      });
    }
  }

  private void animationCleanup(int moveIndex) {
    items.forEach(item -> {
      item.setTranslateX(0);
      item.setOpacity(1);
    });
    setItemListIndex(moveIndex);
    update();
  }

  /**
   * Updates the banner with the current items and index.
   * 
   * <p>
   * This method should be called whenever the items or index are changed.
   * </p>
   */
  public void update() {
    // Clear the container
    this.getChildren().clear();

    // Add the left arrow button
    this.getChildren().add(leftArrowButton);

    // Add the recipes to the container
    for (int i = 0; i < 3; i++) {
      if (itemListIndex + i < items.size()) {
        this.getChildren().add(items.get(itemListIndex + i));
      }
    }

    // Add the right arrow button
    this.getChildren().add(rightArrowButton);

    // If the length of items is less than 3, don't show the arrow buttons
    if (items.size() <= 3) {
      leftArrowButton.setInactiveColor();
      rightArrowButton.setInactiveColor();
    } else {
      leftArrowButton.setActiveColor();
      rightArrowButton.setActiveColor();
    }

    // If the index is at the start of the list, disable the left arrow button
    if (itemListIndex == 0) {
      leftArrowButton.setInactiveColor();
    } else {
      leftArrowButton.setActiveColor();
    }

    // If the index is at the end of the list, disable the right arrow button
    if (itemListIndex >= items.size() - 3) {
      rightArrowButton.setInactiveColor();
    } else {
      rightArrowButton.setActiveColor();
    }

    // Align the contents to the center
    this.alignmentProperty().setValue(javafx.geometry.Pos.CENTER);
  }

  public int getItemListIndex() {
    return this.itemListIndex;
  }

  public void setItemListIndex(int index) {
    this.itemListIndex = verifyIndex(index, items.size());
  }

  private int verifyIndex(int index, int listSize) {
    if (index < 0) {
      return 0;
    } else if (index >= listSize - 3) {
      return listSize - 3;
    }
    return index;
  }
}
