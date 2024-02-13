package no.ntnu.idatt1002.demo;

public class Logger {

  public static Logger instance = null;

  public enum Level {
    INFO, WARNING, ERROR
  }

  private Logger() {
  }

  public static Logger getLogger() {
    if (instance == null) {
      instance = new Logger();
    }
    return instance;
  }

  public void log(String message) {
    System.out.println(message);
  }

}
