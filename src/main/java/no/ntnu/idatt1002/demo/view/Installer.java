package no.ntnu.idatt1002.demo.view;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.prefs.Preferences;

import org.apache.commons.io.FileUtils;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import no.ntnu.idatt1002.demo.ConfigLoader;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.Main;
import no.ntnu.idatt1002.demo.view.components.PrimaryButton;

/**
 * The installer page.
 * 
 * <p>
 * If the application is not installed, e.g. the installed_path property is not
 * set in the app.config file, the installer page is shown.
 * </p>
 */
public class Installer extends VBox {
  private String installationPath;
  private onInstalled cb;

  public interface onInstalled {
    void cb();
  }

  public Installer(onInstalled cb) {
    super();
    this.cb = cb;

    VBox.setVgrow(this, Priority.ALWAYS);
    this.setSpacing(20);
    this.getStyleClass().add("centered");

    showFolderSelection();
  }

  private void showFolderSelection() {
    this.getChildren().clear();

    HBox welcomeContainer = new HBox();
    this.getChildren().add(welcomeContainer);
    welcomeContainer.getStyleClass().add("centered");

    Text welcomeText = new Text("Welcome to " + ConfigLoader.getProperties().getProperty("app_name"));
    welcomeText.getStyleClass().add("welcome-text");
    welcomeContainer.getChildren().add(welcomeText);

    HBox welcomeSubTextContainer = new HBox();
    welcomeSubTextContainer.getStyleClass().add("centered");
    this.getChildren().add(welcomeSubTextContainer);

    Text welcomeSubText = new Text("Where should we install the application?");
    welcomeSubText.getStyleClass().add("welcome-sub-text");
    welcomeSubTextContainer.getChildren().add(welcomeSubText);

    HBox pathContainer = new HBox();
    pathContainer.getStyleClass().add("centered");
    this.getChildren().add(pathContainer);

    PrimaryButton selectPathButton = new PrimaryButton("Select path");
    selectPathButton.setPrefSize(200, 65);
    selectPathButton.setOnAction(e -> {
      DirectoryChooser directoryChooser = new DirectoryChooser();
      directoryChooser.setTitle("Select installation path");
      File selectedDirectory = directoryChooser.showDialog(null);
      if (selectedDirectory != null) {
        installationPath = selectedDirectory.getAbsolutePath();
        install();
      }
    });
    pathContainer.getChildren().add(selectPathButton);
  }

  public void install() {
    this.getChildren().clear();

    HBox progressbarContainer = new HBox();
    progressbarContainer.getStyleClass().add("centered");
    this.getChildren().add(progressbarContainer);

    ProgressBar progressBar = new ProgressBar();
    progressBar.setPrefWidth(400);
    progressbarContainer.getChildren().add(progressBar);

    HBox progressTextContainer = new HBox();
    progressTextContainer.getStyleClass().add("centered");
    this.getChildren().add(progressTextContainer);

    Text progressText = new Text("Installing...");
    progressText.getStyleClass().add("welcome-sub-text");
    progressTextContainer.getChildren().add(progressText);

    // Install the application
    Logger.info("Installing application to: " + installationPath);

    progressText.setText("Makind directories");
    // Make directories
    File basedir = new File(
        installationPath
            + "/"
            + ConfigLoader.getProperties().getProperty("app_name")
            + "/");

    if (!basedir.exists()) {
      basedir.mkdirs();
    }

    progressBar.setProgress(0.5);
    progressText.setText("Copying database");

    // Copy database
    File dbFile = new File(basedir.getAbsolutePath() + "/database/database.sqlite");
    if (!dbFile.exists()) {
      Logger.info("Copying database from resources to: " + dbFile.getAbsolutePath());
      // Copy database file

      try {
        FileUtils.copyURLToFile(
            getClass().getResource("/no/ntnu/idatt1002/database/database.sqlite"),
            dbFile);
      } catch (Exception e) {
        Logger.fatal("Error copying database file: " + e.getMessage());
        System.exit(1);
      }
    }

    progressBar.setProgress(0.9);
    progressText.setText("Copying images");
    // Copy images

    File imagesDir = new File(basedir.getAbsolutePath() + "/images");
    if (!imagesDir.exists()) {
      imagesDir.mkdirs();
    }

    try {
      Files.copy(getClass().getResourceAsStream("/no/ntnu/idatt1002/images/image-not-found.png"),
          new File(imagesDir.getAbsolutePath() + "/image-not-found.png").toPath(),
          StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      Logger.fatal("Error copying image file: " + e.getMessage());
      System.exit(1);
    }

    progressBar.setProgress(0.99);
    progressText.setText("Finishing up");

    // Set the installed path
    Preferences preferences = Preferences.userNodeForPackage(Main.class);
    preferences.put("install_path", installationPath
        + "/"
        + ConfigLoader.getProperties().getProperty("app_name"));

    HBox finishContainer = new HBox();
    finishContainer.getStyleClass().add("centered");
    this.getChildren().add(finishContainer);

    PrimaryButton finishButton = new PrimaryButton("Finish");
    finishButton.setPrefSize(200, 65);
    finishButton.setOnAction(e -> {
      this.cb.cb();
    });
    this.getChildren().add(finishButton);

    progressBar.setProgress(1);
    progressText.setText("Done! The application has been installed to " + installationPath);
  }
}
