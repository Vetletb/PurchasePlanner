package no.ntnu.idatt1002.demo.view.components;

import javafx.scene.paint.Color;

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

    private Icon icon;

    private ViewMode(String name) {
      this.icon = new Icon(name).setFillColor(Color.BLACK);
      this.icon.getStyleClass().add("centered");
    }
  }

  private ViewMode viewMode;

  public ViewModeButton(ViewMode viewMode) {
    super(Type.TRANSPARENT, viewMode.icon);
    this.viewMode = viewMode;
  }

  public ViewMode getViewMode() {
    return viewMode;
  }
}
