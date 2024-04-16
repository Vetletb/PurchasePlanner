package no.ntnu.idatt1002.demo.view.components;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Toast extends StackPane {
  private String title;
  private String message;
  private OnClose onClose;
  private ToastType type;
  private int duration = DELFAULT_TOAST_DURATION;
  public static final int DELFAULT_TOAST_DURATION = 5000;

  /**
   * Interface for the onClose event.
   */
  public interface OnClose {
    void close();
  }

  /**
   * Different types of toasts.
   */
  public enum ToastType {
    DEFAULT,
    SUCCESS,
    ERROR
  }

  public Toast(String title, String message) {
    this(title, message, ToastType.DEFAULT);
  }

  public Toast(String title, String message, ToastType type) {
    super();
    this.title = title;
    this.message = message;
    this.type = type;
    this.make();
  }

  private void make() {
    HBox titleContainer = new HBox();
    HBox.setHgrow(titleContainer, Priority.ALWAYS);
    titleContainer.getStyleClass().addAll("centered", "toast-title");
    Text titleText = new Text(this.title);
    titleText.setWrappingWidth(300);
    titleContainer.getChildren().add(titleText);

    HBox messageContainer = new HBox();
    HBox.setHgrow(messageContainer, Priority.ALWAYS);
    messageContainer.getStyleClass().addAll("centered", "toast-message");
    Text messageText = new Text(this.message);
    messageText.setWrappingWidth(300);
    messageContainer.getChildren().add(messageText);

    // Create the content wrapper
    VBox contentWrapper = new VBox();
    contentWrapper.getStyleClass().addAll("toast-content");
    contentWrapper.setSpacing(10);
    contentWrapper.getChildren().addAll(titleContainer, messageContainer);
    this.getChildren().add(contentWrapper);

    // Create an exit button
    CrossButton crossButton = new CrossButton();
    this.getChildren().add(crossButton);
    StackPane.setAlignment(crossButton, Pos.TOP_RIGHT);

    this.setMouseTransparent(false);

    this.getStyleClass().add("toast-wrapper");

    this.getStyleClass().add("toast-" + this.type.toString().toLowerCase());

    this.setPrefSize(400, 150);

    crossButton.toFront();
    crossButton.setOnAction(e -> {
      this.close();
    });
  }

  public int getDuration() {
    return duration;
  }

  /**
   * Animates the toast out and closes it.
   */
  public void animateOut() {
    final int animationTime = 400;

    // Fade out the toast
    FadeTransition fadeTransition = new FadeTransition();
    fadeTransition.setNode(this);
    fadeTransition.setFromValue(1);
    fadeTransition.setToValue(0);
    fadeTransition.setDuration(javafx.util.Duration.millis(animationTime));
    fadeTransition.setOnFinished(e -> {
      this.close();
    });
    fadeTransition.play();
  }

  public Toast setOnClose(OnClose onClose) {
    this.onClose = onClose;
    return this;
  }

  /**
   * Triggers the close event.
   */
  public void close() {
    if (this.onClose != null) {
      this.onClose.close();
    }
  }
}
