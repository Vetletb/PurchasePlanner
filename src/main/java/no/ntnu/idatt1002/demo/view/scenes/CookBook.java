package no.ntnu.idatt1002.demo.view.scenes;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.demo.view.components.ArrowButton;
import no.ntnu.idatt1002.demo.view.components.ItemPane;

import java.util.*;

/**
 * The inventory page.
 */
public class CookBook extends VBox {

  private final List<ItemPane> recipes = new ArrayList<ItemPane>();
  private final List<ItemPane> favoriteRecipes = new ArrayList<ItemPane>();
  private int favoriteRecipeListIndex = 0;
  private int recipeListIndex = 0;
  private final HBox recipeListContainer = new HBox();
  private final HBox favoriteRecipeListContainer = new HBox();
  private final ArrowButton recipesLeftArrowButton;
  private final ArrowButton recipesRightArrowButton;
  private final ArrowButton favoriteRecipesLeftArrowButton;
  private final ArrowButton favoriteRecipesRightArrowButton;

  /**
   * Constructor for the inventory page.
   * <p>
   * Adds the recipe panels to the inventory page.
   * </p>
   */
  public CookBook() {
    super();
    recipesLeftArrowButton = new ArrowButton(ArrowButton.Direction.LEFT);
    recipesRightArrowButton = new ArrowButton(ArrowButton.Direction.RIGHT);
    favoriteRecipesLeftArrowButton = new ArrowButton(ArrowButton.Direction.LEFT);
    favoriteRecipesRightArrowButton = new ArrowButton(ArrowButton.Direction.RIGHT);

    // Add action listeners to the arrow buttons to change the index and update the page
    recipesLeftArrowButton.setOnAction(e -> {
      setRecipeListIndex(getRecipeListIndex() - 1);
      update();
    });
    recipesRightArrowButton.setOnAction(e -> {
      setRecipeListIndex(getRecipeListIndex() + 1);
      update();
    });
    favoriteRecipesLeftArrowButton.setOnAction(e -> {
      setFavoriteRecipeListIndex(getFavoriteRecipeListIndex() - 1);
      update();
    });
    favoriteRecipesRightArrowButton.setOnAction(e -> {
      setFavoriteRecipeListIndex(getFavoriteRecipeListIndex() + 1);
      update();
    });

    super.getChildren().addAll(new Text("CookBook"),
            favoriteRecipeListContainer,
            recipeListContainer);
    // Temporary Test Data
    recipes.add(new ItemPane(1, "test1", 0, 0));
    recipes.add(new ItemPane(2, "test2", 0, 0));
    recipes.add(new ItemPane(3, "test3", 0, 0));
    recipes.add(new ItemPane(4, "test4", 0, 0));

    favoriteRecipes.add(new ItemPane(5, "test5", 0, 0));
    favoriteRecipes.add(new ItemPane(6, "test6", 0, 0));
    favoriteRecipes.add(new ItemPane(7, "test7", 0, 0));
    favoriteRecipes.add(new ItemPane(8, "test8", 0, 0));

    update();
  }

  public void update() {
    // Clear the containers
    favoriteRecipeListContainer.getChildren().clear();
    recipeListContainer.getChildren().clear();

    // Add the left arrow buttons
    favoriteRecipeListContainer.getChildren().add(favoriteRecipesLeftArrowButton);
    recipeListContainer.getChildren().add(recipesLeftArrowButton);

    // Add the recipes to the containers
    for (int i = 0; i < 3; i++) {
      if (favoriteRecipeListIndex + i < favoriteRecipes.size()) {
        favoriteRecipeListContainer.getChildren().add(favoriteRecipes.get(favoriteRecipeListIndex + i));
      }

      if (recipeListIndex + i < recipes.size()) {
        recipeListContainer.getChildren().add(recipes.get(recipeListIndex + i));
      }
    }

    // Add the right arrow buttons
    favoriteRecipeListContainer.getChildren().add(favoriteRecipesRightArrowButton);
    recipeListContainer.getChildren().add(recipesRightArrowButton);

    // Align the contents to the center
    favoriteRecipeListContainer.alignmentProperty().setValue(javafx.geometry.Pos.CENTER);
    recipeListContainer.alignmentProperty().setValue(javafx.geometry.Pos.CENTER);
  }

  public int getFavoriteRecipeListIndex() {
    return favoriteRecipeListIndex;
  }

  public int getRecipeListIndex() {
    return recipeListIndex;
  }

  public void setFavoriteRecipeListIndex(int index) {
    favoriteRecipeListIndex = verifyIndex(index, favoriteRecipes.size());
  }

  public void setRecipeListIndex(int index) {
    recipeListIndex = verifyIndex(index, recipes.size());
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
