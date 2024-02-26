package no.ntnu.idatt1002.demo;

/**
 * A singleton logger class for purchase planner
 * 
 * <p>
 * The logger class is used to log messages to the console. It has five
 * different log levels:
 * <ul>
 * <li>FATAL (4)</li>
 * <li>ERROR (3)</li>
 * <li>WARNING (2)</li>
 * <li>INFO (1)</li>
 * <li>DEBUG (0)</li>
 * </ul>
 * 
 * To set the log level, use the setLevel method. The default log level is
 * FATAL.
 * 
 * It is singleton, meaning that the logger level persists across the entire
 * application.
 * </p>
 *
 * @author Markus Stuevold Madsbakken
 * @version 1.0
 * @see Level
 */
public class Logger {

  private static Logger instance = null;
  private Level level = Level.FATAL;

  private static String reset = "\u001B[0m";
  private static String red = "\u001B[31m";
  private static String yellow = "\u001B[33m";
  private static String blue = "\u001B[34m";

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
    FATAL(4), ERROR(3), WARNING(2), INFO(1), DEBUG(0);

    private int severity;

    Level(int severity) {
      this.severity = severity;
    }

    public int getSeverity() {
      return severity;
    }
  }

  private Logger() {
    level = Level.FATAL;
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
   * Sets the log level to the specified level.
   *
   * @param level The log level to set.
   */

  public static void setLevel(Level level) {
    getLogger().level = level;
  }

  /**
   * Set the log level to the specified level.
   * 
   * <p>
   * A log level of 0 is DEBUG, 1 is INFO, 2 is WARNING, 3 is ERROR, and 4 is
   * FATAL.
   * </p>
   *
   * @param level The log level to set.
   */

  public static void setLevel(int level) {
    getLogger().level = Level.values()[4 - level];
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
      System.out.println("DEBUG: " + message);
    }
  }

  /**
   * Logs an info message.
   *
   * @param message The message to log.
   */
  public static void info(String message) {
    if (Level.INFO.getSeverity() >= getLogger().getLevelSeverity()) {
      System.out.println(blue + "INFO: " + reset + message);
    }
  }

  /**
   * Logs a warning message.
   *
   * @param message The message to log.
   */
  public static void warning(String message) {
    if (Level.WARNING.getSeverity() >= getLogger().getLevelSeverity()) {
      System.out.println(yellow + "WARNING: " + reset + message);
    }
  }

  /**
   * Logs an error message.
   *
   * @param message The message to log.
   */
  public static void error(String message) {
    if (Level.ERROR.getSeverity() >= getLogger().getLevelSeverity()) {
      System.out.println(red + "ERROR: " + reset + message);
    }
  }

  /**
   * Logs a fatal message.
   *
   * @param message The message to log.
   */
  public static void fatal(String message) {
    if (Level.FATAL.getSeverity() >= getLogger().getLevelSeverity()) {
      System.out.println(red + "FATAL: " + reset + message);
    }
  }

}
