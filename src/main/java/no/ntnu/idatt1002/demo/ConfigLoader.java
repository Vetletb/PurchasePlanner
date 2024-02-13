package no.ntnu.idatt1002.demo;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {

  public static void main(String[] args) {
    Properties properties = load();
    System.out.println(properties);
  }

  public static Properties load() {
    String configFileName = "src/main/resources/no/ntnu/idatt1002/demo/config/app.config";
    Properties properties = new Properties();

    try (FileInputStream fileInputStream = new FileInputStream(configFileName)) {
      properties.load(fileInputStream);
    } catch (Exception e) {
      e.printStackTrace();
      return new Properties();
    }

    return properties;
  }
}
