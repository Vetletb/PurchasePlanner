package no.ntnu.idatt1002.demo.view.scenes;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.demo.view.components.ItemBanner;
import no.ntnu.idatt1002.demo.view.components.ItemPane;

/**
 * The inventory page.
 */
public class CookBook extends VBox {
  /**
   * Constructor for the inventory page.
   * <p>
   * Adds the recipe panels to the inventory page.
   * </p>
   */
  public CookBook() {
    super();

    ItemBanner recipeBanner = new ItemBanner();
    ItemBanner favoriteRecipBanner = new ItemBanner();

    super.getChildren().addAll(new Text("CookBook"),
        recipeBanner,
        favoriteRecipBanner);
    // Temporary Test Data
    recipeBanner.addItem(new ItemPane(1, "test1", 0, 0));
    recipeBanner.addItem(new ItemPane(2, "test2", 0, 0));
    recipeBanner.addItem(new ItemPane(3, "test3", 0, 0));
    recipeBanner.addItem(new ItemPane(4, "test4", 0, 0));

    favoriteRecipBanner.addItem(new ItemPane(5, "test5", 0, 0));
    favoriteRecipBanner.addItem(new ItemPane(6, "test6", 0, 0));
    favoriteRecipBanner.addItem(new ItemPane(7, "test7", 0, 0));
    favoriteRecipBanner.addItem(new ItemPane(8, "test8", 0, 0));
    favoriteRecipBanner.addItem(new ItemPane(10, "test10", 0, 0));
  }
}
