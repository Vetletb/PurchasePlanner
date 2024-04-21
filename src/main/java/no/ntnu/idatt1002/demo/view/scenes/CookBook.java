package no.ntnu.idatt1002.demo.view.scenes;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
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
import no.ntnu.idatt1002.demo.data.Item;
import no.ntnu.idatt1002.demo.data.Recipe;
import no.ntnu.idatt1002.demo.data.RecipeIngredient;
import no.ntnu.idatt1002.demo.data.RecipeStep;
import no.ntnu.idatt1002.demo.repo.ItemRegister;
import no.ntnu.idatt1002.demo.repo.RecipeRegister;
import no.ntnu.idatt1002.demo.view.components.AddPopup;
import no.ntnu.idatt1002.demo.view.components.Field;
import no.ntnu.idatt1002.demo.view.components.ItemBanner;
import no.ntnu.idatt1002.demo.view.components.ItemPane;
import no.ntnu.idatt1002.demo.view.components.ItemPopup;
import no.ntnu.idatt1002.demo.view.components.ListHeader;
import no.ntnu.idatt1002.demo.view.components.ListItem;
import no.ntnu.idatt1002.demo.view.components.RecipePopup;
import no.ntnu.idatt1002.demo.view.components.Toast;
import no.ntnu.idatt1002.demo.view.components.ToastProvider;
import no.ntnu.idatt1002.demo.view.components.ViewModeButton;
import no.ntnu.idatt1002.demo.view.components.ViewModeButton.ViewMode;

/**
 * The inventory page.
 */
public class CookBook extends VBox implements UpdateableScene {
  private VBox inventoryContainer = new VBox();
  private ScrollPane scrollPane;
  private Map<Integer, Recipe> recipes;
  private ViewMode mode;

  /**
   * The constructor for the inventory page.
   */
  public CookBook() {
    super();

    this.getStyleClass().addAll("full-width", "no-focus");

    scrollPane = new ScrollPane();
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setContent(inventoryContainer);

    // get all items
    RecipeRegister recipeRegister = new RecipeRegister(new DAO(new DBConnectionProvider()));
    recipeRegister.getAllRecipes();
    recipes = recipeRegister.getRecipes();

    ListHeader header = new ListHeader();
    header.setOnViewModeChange(this::loadCookBook);
    header.setOnSearch(this::fullSearch);
    header.setOnSearchQueryChange(this::search);

    header.setOnAdd(this::addItem);

    super.getChildren().addAll(header, scrollPane);
    loadCookBook(ViewModeButton.ViewMode.GRID); // Default view mode
  }

  private void addItem() {
    AddPopup addPopup = new AddPopup("Item");
    addPopup.show(this.getScene().getWindow());

    addPopup.addField(Field.ofString("Name"));
    addPopup.addField(Field.ofString("Category"));
    addPopup.addField(Field.ofNumber("Cooking time"));
    // get all items
    ItemRegister itemRegister = new ItemRegister(new DAO(new DBConnectionProvider()));
    itemRegister.getAllItems();

    addPopup.addField(Field.ofMap("Ingredients", itemRegister.getItems()));
    addPopup.setOnAdd((Object[] o) -> {
      RecipeRegister recipeRegister = new RecipeRegister(new DAO(new DBConnectionProvider()));
      recipeRegister.getAllRecipes();
      Map<Integer, Recipe> recipes = recipeRegister.getRecipes();

      try {
        String a = (String) o[0];
        String b = (String) o[1];
        Integer c = (Integer) o[2];

        if (a.isEmpty() || b.isEmpty() || c == null) {
          throw new Exception();
        }

      } catch (Exception e) {
        Logger.fatal("Could not add recipe");
        ToastProvider
            .enqueue(new Toast("Could not add recipe", "One or more fields were empty", Toast.ToastType.ERROR));
        return;
      }

      recipeRegister.addRecipe((String) o[0], (String) o[1], (Integer) o[2]);
      recipeRegister.getAllRecipes();
      Map<Integer, Recipe> recipesNew = recipeRegister.getRecipes();

      Integer id = -1;
      for (Integer key : recipesNew.keySet()) {
        if (!recipes.containsKey(key)) {
          id = key;
          break;
        }
      }

      if (id == -1) {
        recipeRegister.getAllRecipes();
        this.recipes = recipeRegister.getRecipes();
        ToastProvider.enqueue(new Toast("Error",
            "Could not determine ID of new recipe. Recipe added, but not ingredients.",
            Toast.ToastType.ERROR));
        Logger.fatal("Could not determine ID of new recipe. Recipe added, but not ingredients.");
        return;
      }

      try {
        Object[] r = (Object[]) o[3];
        if (r.length == 0) {
          throw new Exception();
        }
      } catch (Exception e) {
        Logger.fatal("Could not add recipe");
        ToastProvider
            .enqueue(new Toast("Could not add recipe", "No ingredients were selected", Toast.ToastType.ERROR));
        return;
      }

      try {
        Object[] recipeIngredients = (Object[]) o[3];
        for (Object ingredient : recipeIngredients) {
          recipeRegister.addIngredient((int) ingredient, 1, "g", id);
        }

        recipeRegister.getAllRecipes();
        this.recipes = recipeRegister.getRecipes();
        ToastProvider.enqueue(new Toast("Success", "Recipe added", Toast.ToastType.SUCCESS));
        loadCookBook(mode);
      } catch (Exception e) {
        Logger.fatal("Could not add recipe");
        ToastProvider
            .enqueue(new Toast("Could not add recipe", "One or more fields were empty", Toast.ToastType.ERROR));
        return;
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
  private void loadCookBook(ViewModeButton.ViewMode mode) {
    Logger.debug("loading inventory");
    this.mode = mode; // save the mode for later use

    inventoryContainer.getChildren().clear();

    if (mode == ViewModeButton.ViewMode.GRID) {
      // get all categories
      String[] categories = recipes.values().stream()
          .map(Recipe::getCategory)
          .distinct()
          .toArray(String[]::new);

      // group items based on category
      for (String category : categories) {
        // create a banner for each category
        ItemBanner inventoryBanner = new ItemBanner();
        inventoryBanner.setTitle(category);

        // add all items of the category to the banner
        recipes.values().stream()
            .filter(item -> item.getCategory().equals(category))
            .forEach(item -> {
              ItemPane pane = new ItemPane(
                  item.getId(),
                  item.getName(),
                  0,
                  0,
                  "recipes");
              inventoryBanner.addItem(pane);
              pane.setOnMouseClicked(v -> {
                RecipePopup recipePopup = new RecipePopup(item);
                recipePopup.setOnSave(this::updateItem);
                recipePopup.setOnDelete(this::deleteItem);
                recipePopup.show(this.getScene().getWindow());
              });

            });
        inventoryContainer.getChildren().add(inventoryBanner);
      }
    } else if (mode == ViewModeButton.ViewMode.LIST) {
      // add a header with the attributes of the items
      String[] attibutes = { "id", "name", "category", "time" };
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
      recipes.values().forEach(item -> {
        ListItem<Recipe> listItem = new ListItem<>(item);
        listItem.setOnMouseClicked(v -> {
          RecipePopup recipePopup = new RecipePopup(item);
          recipePopup.setOnSave(this::updateItem);
          recipePopup.setOnDelete(this::deleteItem);
          recipePopup.show(this.getScene().getWindow());
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
    Logger.debug("search");
    RecipeRegister register = new RecipeRegister(new DAO(new DBConnectionProvider()));
    register.searchRecipesByName(query);
    recipes = register.getRecipes();
    loadCookBook(mode);
  }

  private void updateItem(int id, String name, String category, Integer cookingTime,
      Map<Integer, Integer> ingredients, Map<Integer, Integer> newIngredients, List<RecipeStep> steps,
      List<RecipeStep> newSteps) {
    RecipeRegister register = new RecipeRegister(new DAO(new DBConnectionProvider()));
    register.getAllRecipes();
    register.updateRecipe(id, name, cookingTime, category);

    steps.forEach(step -> register.updateInstrucution(
        step.getId(),
        step.getStepNumber(),
        step.getInstruction()));

    newSteps.forEach(step -> register.addInstruction(
        id,
        step.getStepNumber(),
        step.getInstruction()));

    register.getRecipes().get(id).getInstructions().forEach(step -> {
      if (steps.stream().noneMatch(s -> s.getId() == step.getId())) {
        register.deleteInstruction(step.getId());
      }
    });

    List<RecipeIngredient> oldIngredients = register.getRecipes().get(id).getIngredients();

    // Add all new ingredients
    newIngredients.forEach((k, v) -> {
      register.addIngredient(k, v, "g", id);
    });

    // Update all ingredients. New and old
    for (RecipeIngredient i : oldIngredients) {
      if (ingredients.containsKey(i.getId())) {
        register.updateIngredient(
            i.getId(),
            i.getItemId(),
            ingredients.get(i.getId()),
            i.getUnit());
      } else {
        register.deleteIngredient(i.getId());
      }
    }

    register.getAllRecipes();
    recipes = register.getRecipes();
    ToastProvider.enqueue(new Toast("Success", "Recipe updated", Toast.ToastType.SUCCESS));
    loadCookBook(mode);
  }

  private void deleteItem(int id) {
    RecipeRegister register = new RecipeRegister(new DAO(new DBConnectionProvider()));
    register.getAllRecipes();
    register.deleteRecipe(id);
    register.getAllRecipes();
    recipes = register.getRecipes();
    ToastProvider.enqueue(new Toast("Success", "Recipe deleted", Toast.ToastType.SUCCESS));
    loadCookBook(mode);
  }

  public void updateScene() {
    loadCookBook(mode);
  }

  public VBox createScene() {
    return this;
  }
}
