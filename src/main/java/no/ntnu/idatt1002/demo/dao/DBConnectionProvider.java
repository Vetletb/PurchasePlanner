package no.ntnu.idatt1002.demo.dao;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.prefs.Preferences;

import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.Main;
import no.ntnu.idatt1002.demo.view.App;
import no.ntnu.idatt1002.demo.view.Installer;

/**
 * This class provides a connection to the database.
 */
public class DBConnectionProvider {
  private final String url;
  private static DBConnectionProvider databaseConnectionProvider;

  private static String DB_PATH = "/no/ntnu/idatt1002/database/database.sqlite";

  /**
   * Constructor for the DBConnectionProvider class.
   */
  public DBConnectionProvider() {
    Preferences preferences = Preferences.userNodeForPackage(Main.class);
    String installedPath = preferences.get("install_path", null);
    String relativePath;

    // If the application is not installed, the database is located in the resources
    // folder
    if (installedPath == null) {
      URL temp = this.getClass().getResource(DB_PATH);
      if (temp == null) {
        this.url = null;
        return;
      }

      relativePath = "src" +
          File.separator + "main"
          + File.separator + "resources"
          + DB_PATH;

    } else {
      relativePath = installedPath + "/database/database.sqlite";
    }

    this.url = "jdbc:sqlite:" + relativePath;
  }

  /**
   * This method sets the path to the database. Mainly used for testing purposes.
   *
   * @param dbPath the path to the database
   */
  public static void setDbPath(String dbPath) {
    DB_PATH = dbPath;
  }

  /**
   * This method returns a connection to the database.
   *
   * @return a connection to the database
   */
  Connection getConnection() {
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      statement.execute("PRAGMA foreign_keys = ON;");
      statement.close();
      return DriverManager.getConnection(url);
    } catch (SQLException e) {
      throw new RuntimeException("Error connecting to the database", e);
    }
  }

  /**
   * This method returns an instance of the DBConnectionProvider.
   *
   * @return an instance of the DBConnectionProvider
   */
  public static DBConnectionProvider getInstance() {
    if (databaseConnectionProvider == null) {
      databaseConnectionProvider = new DBConnectionProvider();
    }
    return databaseConnectionProvider;
  }

  /**
   * This method closes the connection to the database.
   *
   * @param connection the connection to the database
   */
  public void closeConnection(Connection connection) {
    try {
      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException("Error closing the database", e);
    }
  }

  /**
   * This method closes the PreparedStatement.
   *
   * @param preparedStatement the PreparedStatement
   */
  public void closePreparedStatement(PreparedStatement preparedStatement) {
    if (preparedStatement != null) {
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        throw new RuntimeException("Error closing PreparedStatement", e);
      }
    }
  }

  /**
   * This method closes the ResultSet.
   *
   * @param resultSet the ResultSet
   */
  public void closeResultSet(ResultSet resultSet) {
    if (resultSet != null) {
      try {
        resultSet.close();
      } catch (SQLException e) {
        throw new RuntimeException("Error closing ResultSet", e);
      }
    }
  }
}
