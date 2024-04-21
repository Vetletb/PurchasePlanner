package no.ntnu.idatt1002.demo.view.components;

import javafx.scene.paint.Color;
import no.ntnu.idatt1002.demo.Logger;

/**
 * A button for changing the view mode of the application.
 */

public class ViewModeButton extends PrimaryButton {

  /**
   * Enum for the different view modes.
   * 
   * <ul>
   * <li>LIST: A list view mode</li>
   * <li>GRID: A grid view mode</li>
   * </ul>
   */
  public enum ViewMode {
    LIST("viewmodelist"),
    GRID("viewmodegrid");

    private String name;

    private ViewMode(String name) {
      this.name = name;
    }
  }

  private ViewMode viewMode;

  public ViewModeButton(ViewMode viewMode) {
    super(Type.TRANSPARENT, new Icon(viewMode.name).setFillColor(Color.BLACK));
    this.getGraphic().getStyleClass().add("centered");
    this.viewMode = viewMode;
  }

  public ViewMode getViewMode() {
    return viewMode;
  }
}
