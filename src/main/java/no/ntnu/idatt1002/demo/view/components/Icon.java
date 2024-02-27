package no.ntnu.idatt1002.demo.view.components;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.SVGPath;
import no.ntnu.idatt1002.demo.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Icon class for the Application.
 */
public class Icon extends VBox {
  // TODO: Fix so that it scales correctly and that it does not move wierd when
  // scaled.
  // In addition make the icon get all the properties defined within the svg file
  // such as strokewidth, fill and so on.

  private final String filePath = "src/main/resources/no/ntnu/idatt1002/icons/";
  private final SVGPath svgPath;
  public static final int DEFAULT_SIZE = 10;
  // private static final float DEFAULT_SCALE = 0.1f;
  private final double strokeWidth = 1.5;
  private final String iconName;
  private int x;
  private int y;
  private int size;

  /**
   * Constructor for the Icon class.
   *
   * @param iconName Name of the icon
   */
  public Icon(String iconName) {
    this(iconName, DEFAULT_SIZE);
  }

  /**
   * Constructor for the Icon class.
   *
   * @param iconName Name of the icon
   * @param size     Size of the icon, width and height
   */
  public Icon(String iconName, int size) {
    this.iconName = iconName;
    this.size = size;

    // Path to the SVG file
    String svgFilePath = this.filePath + iconName + ".svg";
    // Set the content of the SVG path
    String svgFileContent = readSvgFromFile(svgFilePath);
    // Split the SVG content and get the data value from the path
    String svgData = splitSvgContent(svgFileContent, "d");

    // Create a new SVGPath
    svgPath = new SVGPath();
    // Set the content of the SVG path
    svgPath.setContent(svgData);
    // You can customize the appearance of the SVG path
    svgPath.setStrokeWidth(strokeWidth);
    svgPath.setStrokeType(javafx.scene.shape.StrokeType.CENTERED);
    svgPath.setStroke(Color.BLACK);

    getChildren().add(svgPath);

    // setSize(size);
    Logger.debug("Icon created: " + iconName);

    this.svgPath.getStyleClass().addAll("icon", "centered");
    this.getStyleClass().add("icon-container");
  }

  /**
   * Reads the SVG file and returns the content as a string.
   *
   * @param filePath Path to the SVG file
   * @return Content of the SVG file as a string
   */
  private String readSvgFromFile(String filePath) {
    try {
      // Logger.getLogger().log(Files.readString(Paths.get(filePath)));
      return Files.readString(Paths.get(filePath));
    } catch (IOException e) {
      Logger.debug(e.getMessage());
      return ""; // Return empty string if there's an error reading the file
    }
  }

  /**
   * Splits the SVG content and returns the value of the attribute.
   *
   * @param svgContent Content of the SVG file
   * @param regex      Regex to split the content
   * @return Value of the attribute
   */
  private String splitSvgContent(String svgContent, String regex) {
    return svgContent.split(regex + "=\"")[1].split("\"")[0];
  }

  /**
   * Set the x value of the icon.
   *
   * @param x X position
   */
  public void setX(int x) {
    this.x = x;
    setTranslateX(x);
  }

  /**
   * Set the y value of the icon.
   *
   * @param y Y position
   */
  public void setY(int y) {
    this.y = y;
    setTranslateY(y);
  }

  /**
   * Set the position of the icon.
   *
   * @param x X position
   * @param y Y position
   */
  public void setPosition(int x, int y) {
    this.x = x;
    this.y = y;

    setTranslateX(x);
    setTranslateY(y);
  }

  /**
   * Set the size of the icon.
   *
   * @param size Size of the icon
   */
  public void setSize(int size) {

    // TODO: this """works""" but it still requires manual adjustment of the
    // position. Fix this
    this.size = size;

    this.svgPath.setScaleX(size);
    this.svgPath.setScaleY(size);
  }

  /**
   * Get the x value of the icon.
   *
   * @return x position
   */
  public int getX() {
    return x;
  }

  /**
   * Get the y value of the icon.
   *
   * @return y position
   */
  public int getY() {
    return y;
  }

  /**
   * Get the size of the icon.
   *
   * @return size of the icon
   */
  public int getSize() {
    return size;

  }

  /**
   * Get the name of the icon.
   *
   * @return name of the icon
   */
  public String getIconName() {
    return iconName;
  }
}