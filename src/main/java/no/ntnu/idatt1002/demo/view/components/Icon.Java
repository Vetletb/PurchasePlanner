package no.ntnu.idatt1002.demo.view.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.util.Scanner;

import java.io.File;

/**
 * Icon class for the Application
 */
public class Icon extends StackPane {

    private final String path = "src/main/resources/icons/"; 
    // TODO: Find the correct path that we will have and change this to that 
    public final int DEFAULT_SIZE = 10;

    private String iconName;
    private int x;
    private int y;
    private int size;

    public Icon(String iconName) {
        this(this.iconName, this.DEFAULT_SIZE);
    }

    public Icon(Stirng iconName, size) {

        this.iconName = iconName;
        this.size = size;

        svgFilePath = this.path + iconName + ".svg";
        SVGPath svg = new SVGPath();
        svg.setContent(readSVGFromFile(svgFilePath));
        svg.setFill(Color.BLACK); // Change color as needed

        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);

        imageView.setImage(createImageFromSVG(svg, size));

        getChildren().add(imageView);
    }

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
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    private Image createImageFromSVG(SVGPath svg, double size) {
        StackPane pane = new StackPane();
        pane.getChildren().add(svg);
        pane.setStyle("-fx-background-color: transparent");
        return pane.snapshot(null, null);
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

}