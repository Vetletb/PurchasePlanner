package no.ntnu.idatt1002.demo.dao;

import java.sql.*;

/**
 * This class provides a connection to the database.
 */
public class DBConnectionProvider {
  private final String url;
  private static DBConnectionProvider databaseConnectionProvider;

  private static final String DB_PATH = "src/main/resources/no/ntnu/idatt1002/database/database.sqlite";

  /**
   * Constructor for the DBConnectionProvider class.
   */
  public DBConnectionProvider() {
    this.url = "jdbc:sqlite:" + DB_PATH;
  }

  /**
   * This method returns a connection to the database.
   * @return a connection to the database
   */
  Connection getConnection() {
    try {
      return DriverManager.getConnection(url);
    } catch (SQLException e) {
      throw new RuntimeException("Error connecting to the database", e);
    }
  }

  /**
   * This method returns an instance of the DBConnectionProvider.
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
