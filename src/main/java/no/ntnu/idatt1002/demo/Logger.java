package no.ntnu.idatt1002.demo;

/**
 * A simple singleton logger class.
 */
public class Logger {

  private static Logger instance = null;
  private Level level = Level.FATAL;

  /**
   * Enum for the different log levels.
   *
   * <p>
   * SEVERE: A severe error message
   * ERROR: An error message
   * WARNING: A warning message
   * INFO: An info message
   * DEBUG: A debug message
   * </p>
   */
  public enum Level {
    FATAL(5), ERROR(4), WARNING(3), INFO(1), DEBUG(0);

    private int severity;

    Level(int severity) {
      this.severity = severity;
    }

    public int getSeverity() {
      return severity;
    }
  }

  private Logger() {
  }

  /**
   * Get the logger instance if it exists, otherwise create a new instance.
   *
   * @return the logger instance
   */

  private static Logger getLogger() {
    if (instance == null) {
      instance = new Logger();
    }
    return instance;
  }

  /**
   * Set the log level.
   *
   * @param level The log level to set.
   */

  public void setLevel(Level level) {
    this.level = level;
  }

  /**
   * Set the log level.
   *
   * @param level The log level to set.
   */

  public static void setLevel(int level) {
    getLogger().level = Level.values()[level];
  }

  public int getLevelSeverity() {
    return level.getSeverity();
  }

  /**
   * Logs a debug message.
   *
   * @param message The message to log.
   */
  public static void debug(String message) {
    if (Level.DEBUG.getSeverity() == getLogger().getLevelSeverity()) {
      System.out.println("INFO: " + message);
    }
  }

  /**
   * Logs an info message.
   *
   * @param message The message to log.
   */
  public static void info(String message) {
    if (Level.INFO.getSeverity() >= getLogger().getLevelSeverity()) {
      System.out.println("INFO: " + message);
    }
  }

  /**
   * Logs a warning message.
   *
   * @param message The message to log.
   */
  public static void warning(String message) {
    if (Level.WARNING.getSeverity() >= getLogger().getLevelSeverity()) {
      System.out.println("WARNING: " + message);
    }
  }

  /**
   * Logs an error message.
   *
   * @param message The message to log.
   */
  public static void error(String message) {
    if (Level.ERROR.getSeverity() >= getLogger().getLevelSeverity()) {
      System.out.println("ERROR: " + message);
    }
  }

  /**
   * Logs a fatal message.
   *
   * @param message The message to log.
   */
  public static void fatal(String message) {
    if (Level.FATAL.getSeverity() >= getLogger().getLevelSeverity()) {
      System.out.println("FATAL: " + message);
    }
  }

}
