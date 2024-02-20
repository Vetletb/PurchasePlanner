package no.ntnu.idatt1002.demo;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Class for loading properties from a config file.
 */
public class ConfigLoader {

  private ConfigLoader() {
  }

  /**
   * Loads properties from the config file.
   *
   * @return The properties.
   */
  public static Properties load() {
    String configFileName = "src/main/resources/no/ntnu/idatt1002/demo/config/app.config";
    Properties properties = new Properties();

    try (FileInputStream fileInputStream = new FileInputStream(configFileName)) {
      properties.load(fileInputStream);
    } catch (Exception e) {
      e.printStackTrace();
      return new Properties();
    }

    Logger.info("Loaded properties from " + configFileName);

    return properties;
  }
}
