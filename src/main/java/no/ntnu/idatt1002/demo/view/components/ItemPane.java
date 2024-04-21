package no.ntnu.idatt1002.demo.view.components;

import java.io.File;
import java.util.prefs.Preferences;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.Main;

/**
 * ItemPane class.
 * Goal: Display the items and recipes to the user.
 */
public class ItemPane extends VBox {
  private final String filePath = "/no/ntnu/idatt1002/images/";
  private final int itemId;
  private final String itemName;
  private final ImageView image;
  private int positionX = 0;
  private int positionY = 0;
  private static final int DEFAULT_WIDTH = 400;
  private static final int DEFAULT_HEIGHT = 200;

  /**
   * Constructor for the ItemPane class.
   * 
   * @param itemId    id of the item
   * @param itemName  name of the item
   * @param imageName name of the image
   * @param positionX the position on the x-axis
   * @param positionY the position on the y-axis
   *                  <p>
   *                  Uses the {@link #createImageView(String)} method to create
   *                  the image
   *                  </p>
   *                  <p>
   *                  Uses the {@link #setPosition(int, int)} method to set the
   *                  position of the item
   *                  </p>
   *                  <p>
   *                  Uses the {@link #setScale(double, double)} method to set the
   *                  scale of the item
   *                  </p>
   */
  public ItemPane(int itemId, String itemName, int positionX, int positionY, String imageDir) {
    this.itemId = itemId;
    this.itemName = itemName;
    this.image = createImageView(itemId, imageDir);

    setPosition(positionX, positionY);

    Label label = new Label(itemName);

    // Add image style
    assert image != null;
    image.getStyleClass().add("item-pane-image");

    // Add item pane style
    this.getStyleClass().add("item-pane");

    // Add label style
    label.getStyleClass().add("item-pane-label");

    // Add the label and image to the item pane
    getChildren().add(label);
    getChildren().add(image);
  }

  /**
   * Method for creating an image view.
   *
   * @param id       id of the image
   * @param imageDir the name of the directory the image can be found in
   * @return The image view
   */
  private ImageView createImageView(int id, String imageDir) {
    try {

      Preferences preferences = Preferences.userNodeForPackage(Main.class);
      String installedPath = preferences.get("install_path", null);

      String finalFilePath;

      // If the application is not installed (e.g. running from IDE) we use the
      // project file structure
      if (installedPath == null) {

        if (getClass().getResource(filePath + imageDir + File.separator + id + ".png") == null) {
          finalFilePath = this.filePath + "image-not-found.png";
        } else {
          finalFilePath = this.filePath + imageDir + File.separator + id + ".png";
        }

      } else {

        // Get the image as a file
        File file = new File(
            installedPath
                + File.separator + "images"
                + File.separator + imageDir
                + File.separator + id + ".png");

        // If the file does not exist, we use the image-not-found image
        if (!file.exists() && !file.isDirectory()) {
          finalFilePath = "file:" + installedPath + "/images/image-not-found.png";
        } else {
          finalFilePath = "file:"
              + installedPath
              + File.separator + "images"
              + File.separator
              + imageDir
              + File.separator + id + ".png";
        }
      }

      Image img = new Image(finalFilePath);

      ImageView imageView = new ImageView(img);
      imageView.setFitWidth(DEFAULT_WIDTH);
      imageView.setFitHeight(DEFAULT_HEIGHT);
      return imageView;

    } catch (Exception e) {
      Logger.error(e.getMessage());

    }
    return null;
  }

  /**
   * Set the x value of the item.
   * 
   * @param x X position
   * @param y Y position
   *
   */
  public void setPosition(int x, int y) {
    this.positionX = x;
    this.positionY = y;
    this.setTranslateX(x);
    this.setTranslateY(y);
  }

  /**
   * Set the scale of the item.
   * 
   * @param x X scale
   * @param y Y scale
   */
  public void setScale(double x, double y) {
    setScaleX(x);
    setScaleY(y);
  }
}
