package no.ntnu.idatt1002.demo.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Positive and negative tests for the DBConnectionProvider class.
 */
class DBConnectionProviderTest {

  @Nested
  @DisplayName("Positive tests for DBConnectionProvider")
  class PositiveTests {

    DBConnectionProvider dbConnectionProvider;

    @BeforeEach
    public void setUp() {
      DBConnectionProvider.setDbPath("/no/ntnu/idatt1002/database/database.sqlite");
      dbConnectionProvider = new DBConnectionProvider();
    }

    @Test
    @DisplayName("getConnection does not return null")
    public void GetConnectionDoesNotReturnNull() {
      assertNotNull(dbConnectionProvider.getConnection());
    }

    @Test
    @DisplayName("getInstance does not return null")
    public void GetInstanceDoesNotReturnNull() {
      assertNotNull(DBConnectionProvider.getInstance());
    }

    @Nested
    @DisplayName("Close tests")
    class CloseTests {
      Connection connection;

      @BeforeEach
      public void setUp() {
        connection = dbConnectionProvider.getConnection();
      }

      @Test
      @DisplayName("closeConnection closes the database connection")
      public void CloseConnectionClosesTheDatabaseConnection() throws SQLException {
        dbConnectionProvider.closeConnection(connection);
        assertTrue(connection.isClosed());
      }

      @Test
      @DisplayName("closePreparedStatement closes the prepared statement")
      public void ClosePreparedStatementClosesThePreparedStatement() throws SQLException {
        String sql = "SELECT * FROM item";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        dbConnectionProvider.closePreparedStatement(preparedStatement);
        assertTrue(preparedStatement.isClosed());
      }

      @Test
      @DisplayName("closeResultSet closes the result set")
      public void CloseResultSetClosesTheResultSet() throws SQLException {
        String sql = "SELECT * FROM item";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        dbConnectionProvider.closeResultSet(resultSet);
        assertTrue(resultSet.isClosed());
      }
    }
  }

  @Nested
  @DisplayName("Negative tests for DBConnectionProvider")
  class NegativeTests {
    @Test
    @DisplayName("getConnection throws exception")
    public void GetConnectionThrowsException() {
      DBConnectionProvider.setDbPath("src/test/resources/test");
      DBConnectionProvider dbConnectionProvider = new DBConnectionProvider();
      assertThrows(RuntimeException.class, () -> dbConnectionProvider.getConnection());
    }
  }
}