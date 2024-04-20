package no.ntnu.idatt1002.demo;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class for loading properties from a config file.
 */
public class ConfigLoader {

  private static ConfigLoader instance;
  private Properties properties;
  private String path;

  private ConfigLoader() {
  }

  private static ConfigLoader getInstance() {
    if (instance == null) {
      instance = new ConfigLoader();
    }

    return instance;
  }

  /**
   * Loads properties from the config file.
   *
   * @param path The path to the config file.
   * @return The properties.
   */
  public static Properties load(String path) {
    Properties properties = new Properties();

    try {
      InputStream inputStream = ConfigLoader.class.getResourceAsStream(path);
      properties.load(inputStream);

    } catch (Exception e) {
      e.printStackTrace();
      return new Properties();
    }

    try {
      Integer loggerLevel = Integer.parseInt(properties.getProperty("logger_level"));

      if (loggerLevel > 4) {
        throw new NumberFormatException();
      }

      Logger.setLevel(loggerLevel);

    } catch (NumberFormatException e) {
      Logger.fatal("logger_level not set to a valid number");
    } catch (Exception e) {
      Logger.fatal("Could not set logger level");
    }

    Logger.info("Loaded properties from " + path);

    getInstance().properties = properties;

    return properties;
  }

  /**
   * Returns the properties.
   *
   * @return The properties.
   */
  public static Properties getProperties() {
    return getInstance().properties;
  }

  /**
   * Sets a specified property.
   *
   * @param key   The key of the property.
   * @param value The value of the property.
   */
  public static void setProperty(String key, String value) {
    getInstance().properties.setProperty(key, value);
  }
}
