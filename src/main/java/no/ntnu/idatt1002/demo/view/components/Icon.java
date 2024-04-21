package no.ntnu.idatt1002.demo.view.components;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.SVGPath;
import no.ntnu.idatt1002.demo.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Icon class for the Application.
 */
public class Icon extends VBox {
  // TODO: Fix so that it scales correctly and that it does not move wierd when
  // scaled.
  // In addition make the icon get all the properties defined within the svg file
  // such as strokewidth, fill and so on.

  private final String filePath = "/no/ntnu/idatt1002/icons/";
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
    String FilePath = this.filePath + iconName + ".svg";
    // Set the content of the SVG path
    String svgFileContent = readSvgFromFile(FilePath);
    // Split the SVG content and get the data value from the path
    String svgPathString = "";

    try {
      String[] svgContent = svgFileContent.split("\n");
      for (String line : svgContent) {
        if (line.contains("<path")) {
          svgPathString = getSvgPathString(line);
        }
        if (line.contains("<rect")) {
          Rectangle svgRect = getSvgRect(line);
          this.getChildren().add(svgRect);
        }
        if (line.contains("<circle")) {
          SVGPath svgCircle = getSvgCircle(line);
          svgCircle.getStyleClass().add("centered");
          this.getChildren().add(svgCircle);
        }
      }

    } catch (Exception e) {
      Logger.debug(e.getMessage());
    }

    // Create a new SVGPath
    svgPath = new SVGPath();
    // Set the content of the SVG path
    svgPath.setContent(svgPathString);
    // You can customize the appearance of the SVG path
    svgPath.setStrokeWidth(strokeWidth);
    svgPath.setStrokeType(javafx.scene.shape.StrokeType.CENTERED);
    svgPath.setStroke(Color.BLACK);

    getChildren().add(svgPath);

    Logger.debug("Icon created: " + iconName);

    this.svgPath.getStyleClass().addAll("icon-no-fill", "centered");
    this.getStyleClass().add("icon-container");
  }

  /**
   * Set the fill color of the icon.
   * 
   * <p>
   * Fills the icon with the given color. Pass null to remove the fill.
   * </p>
   *
   * @param color The color to fill the icon with
   */
  public Icon setFillColor(Color color) {

    if (color == null) {
      this.svgPath.getStyleClass().add("icon-no-fill");
      return this;
    }

    this.svgPath.getStyleClass().remove("icon-no-fill");
    svgPath.setFill(color);

    return this;
  }

  /**
   * Reads the SVG file and returns the content as a string.
   *
   * @param filePath Path to the SVG file
   * @return Content of the SVG file as a string
   */
  private String readSvgFromFile(String filePath) {
    try {
      return new String(getClass().getResourceAsStream(filePath).readAllBytes());
    } catch (Exception e) {
      Logger.debug(e.getMessage());
      return ""; // Return empty string if there's an error reading the file
    }
  }

  /**
   * Splits the SVG content and returns the value of the attribute.
   *
   * @param svgContent Content of the SVG file
   * @return Value of the attribute
   */
  private String getSvgPathString(String svgContent) {
    return svgContent.split("d" + "=\"")[1].split("\"")[0];
  }

  /**
   * Splits the provided line from the SVG file and returns the Rectangle that the
   * SVG is describing.
   * 
   * @param svgContent Content of the SVG file
   * @return The Rectangle described in the SVG file
   */
  private Rectangle getSvgRect(String svgContent) {
    String rectData = svgContent.split("<rect")[1].split("/>")[0];
    int width = Integer.parseInt(rectData.split("width=\"")[1].split("\"")[0]);
    int height = Integer.parseInt(rectData.split("height=\"")[1].split("\"")[0]);
    // int rx = Integer.parseInt(rectData.split("rx=\"")[1].split("\"")[0]);
    String fill = rectData.split("fill=\"")[1].split("\"")[0];
    Rectangle svgRect = new Rectangle(width, height);
    svgRect.setFill(Paint.valueOf(fill));

    return svgRect;
  }

  /**
   * Splits the provided line from the SVG file and returns the Circle that the
   * SVG is describing.
   * 
   * @param svgContent Content of the SVG file
   * @return The Circle described in the SVG file
   */
  private SVGPath getSvgCircle(String svgContent) {
    String circleData = svgContent.split("<circle")[1].split("/>")[0];
    int cx = Integer.parseInt(circleData.split("cx=\"")[1].split("\"")[0]);
    int cy = Integer.parseInt(circleData.split("cy=\"")[1].split("\"")[0]);
    double r = Double.parseDouble(circleData.split("r=\"")[1].split("\"")[0]);

    SVGPath svgCircle = new SVGPath();
    svgCircle.setContent("M" + cx + " " + cy + "m" + -r + " 0a" + r + " " + r + " 0 1,0 " + r * 2 + " 0a" + r + " " + r
        + " 0 1,0 " + -r * 2 + " 0");

    if (circleData.contains("stroke=\"")) {
      String stroke = circleData.split("stroke=\"")[1].split("\"")[0];
      int strokeWidth = Integer.parseInt(circleData.split("stroke-width=\"")[1].split("\"")[0]);
      svgCircle.setStroke(Color.valueOf(stroke));
      svgCircle.setStrokeWidth(strokeWidth);
    }
    if (circleData.contains("fill=\"")) {
      String fill = circleData.split("fill=\"")[1].split("\"")[0];
      svgCircle.setFill(Paint.valueOf(fill));
    } else {
      svgCircle.setFill(Color.TRANSPARENT);
    }

    return svgCircle;
  }

  public void setStrokeColor(Color color) {
    svgPath.setStroke(color);
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
   * Scales the icon.
   *
   * @param scale Scale of the icon
   */
  public void scale(double scale) {
    this.svgPath.setScaleX(scale);
    this.svgPath.setScaleY(scale);
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