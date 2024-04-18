package no.ntnu.idatt1002.demo;

import java.io.FileInputStream;
import java.net.URL;
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
   * @param path The path to the config file.
   * @return The properties.
   */
  public static Properties load(String path) {
    Properties properties = new Properties();
    System.out.println(path);

    try (FileInputStream fileInputStream = new FileInputStream(path)) {
      properties.load(fileInputStream);
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

    return properties;
  }
}
