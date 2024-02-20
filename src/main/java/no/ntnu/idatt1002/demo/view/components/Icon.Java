package no.ntnu.idatt1002.demo.view.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import no.ntnu.idatt1002.demo.Logger;

import java.util.Scanner;

import java.io.File;

/**
 * Icon class for the Application
 */
public class Icon extends StackPane {

    private final String path = "src/main/resources/icons/";
    // TODO: Find the correct path that we will have and change this to that
    public static final int DEFAULT_SIZE = 10;
    private final String iconName;
    private int x;
    private int y;
    private int size;

    /**
     * Constructor for the Icon class
     * @param iconName Name of the icon
     */
    public Icon(String iconName) {
        this(iconName, DEFAULT_SIZE);
    }

    /**
     * Constructor for the Icon class
     * @param iconName Name of the icon
     * @param size Size of the icon, width and height
     */
    public Icon(String iconName, int size) {

        this.iconName = iconName;
        this.size = size;

        String svgFilePath = this.path + iconName + ".svg";
        SVGPath svg = new SVGPath();
        svg.setContent(readSVGFromFile(svgFilePath));
        svg.setFill(Color.BLACK); // Change color as needed

        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);

        imageView.setImage(createImageFromSVG(svg));

        getChildren().add(imageView);
    }

    /**
     *  Reads the SVG file and returns the content as a string
     *  @param filePath Path to the SVG file
     *  @return Content of the SVG file as a string
     */
    private String readSVGFromFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            File file = new File(filePath);
            Scanner scanner = new java.util.Scanner(file);
            while (scanner.hasNextLine()) {
                contentBuilder.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
        } catch (java.io.IOException e) {
            Logger.getLogger().log("Error reading SVG file: " + filePath);
        }
        return contentBuilder.toString();
    }

    /**
     * Creates an image from the SVG
     * @param svg SVGPath object
     * @return Image object
     */
    private Image createImageFromSVG(SVGPath svg) {
        StackPane pane = new StackPane();
        pane.getChildren().add(svg);
        pane.setStyle("-fx-background-color: transparent");
        return pane.snapshot(null, null);
    }

    /**
     * Set the  x value of the icon
     * @param x X position
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set the  y value of the icon
     * @param y Y position
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Set the position of the icon
     * @param x X position
     * @param y Y position
     */
    public void setPostion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set the size of the icon
     * @param size Size of the icon
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Get the x value of the icon
     * @return x position
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y value of the icon
     * @return y position
     */
    public int getY() {
        return y;
    }

    /**
     * Get the size of the icon
     * @return size of the icon
     */
    public int getSize() {
        return size;
    }

    /**
     * Get the name of the icon
     * @return name of the icon
     */
    public String getIconName() {
        return iconName;
    }

}