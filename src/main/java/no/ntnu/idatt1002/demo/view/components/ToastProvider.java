package no.ntnu.idatt1002.demo.view.components;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.scene.layout.AnchorPane;
import no.ntnu.idatt1002.demo.Logger;

// TODO: finish this javadoc
/**
 * A provider for toasts.
 */
public class ToastProvider extends AnchorPane {
  private static ToastProvider instance;
  private final Queue<Toast> toastQueue = new LinkedList<>();
  private Toast currenToast;

  private ToastProvider() {
    super();
    this.setPickOnBounds(false);
  }

  private void next() {
    this.getChildren().clear();
    this.currenToast = null;

    if (!toastQueue.isEmpty()) {
      this.currenToast = toastQueue.poll();
      this.getChildren().add(this.currenToast);
      AnchorPane.setBottomAnchor(this.currenToast, 25.0);
      AnchorPane.setRightAnchor(this.currenToast, 25.0);
      this.animateIn();

      this.currenToast.setOnClose(() -> {
        this.next();
      });
    }

    if (this.currenToast == null) {
      return;
    }

    Task<Void> delay = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        try {
          Thread.sleep(Toast.DELFAULT_TOAST_DURATION);
        } catch (InterruptedException e) {
          Logger.error(e.getMessage());
        }
        return null;
      }
    };

    delay.setOnSucceeded(event -> {
      if (this.currenToast != null) {
        this.currenToast.animateOut();
      }
    });

    // Schedule the delay task
    Thread thread = new Thread(delay);

    // Make the thread a daemon thread so it doesn't prevent the application from
    // closing
    thread.setDaemon(true);
    thread.start();
  }

  /**
   * Gets the current instance of the toast provider.
   *
   * @return the instance
   */
  public static ToastProvider getInstance() {
    if (instance == null) {
      instance = new ToastProvider();
    }
    return instance;
  }

  private void animateIn() {
    final int animationTime = 400;

    // Fade in the toast
    FadeTransition fadeTransition = new FadeTransition();
    fadeTransition.setNode(currenToast);
    fadeTransition.setFromValue(0);
    fadeTransition.setToValue(1);
    fadeTransition.setDuration(javafx.util.Duration.millis(animationTime));
    fadeTransition.play();

    // Move the toast down out of view
    this.setTranslateY(100);

    // Move the toast up into view
    TranslateTransition translateTransition = new TranslateTransition();
    translateTransition.setNode(currenToast);
    translateTransition.setByY(-100);
    translateTransition.setDuration(javafx.util.Duration.millis(animationTime));
    translateTransition.setInterpolator(javafx.animation.Interpolator.EASE_OUT);
    translateTransition.play();
  }

  /**
   * Enqueues a toast.
   *
   * @param toast the toast to enqueue
   */
  public static void enqueue(Toast toast) {
    getInstance().toastQueue.add(toast);

    // If no toasts are currently being displayed, display the next one
    if (getInstance().currenToast == null) {
      getInstance().next();
    }
  }

}
