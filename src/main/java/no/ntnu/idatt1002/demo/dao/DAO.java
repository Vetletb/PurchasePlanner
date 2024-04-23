package no.ntnu.idatt1002.demo.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1002.demo.data.Storable;

/**
 * This class provides methods for accessing all tables in the database.
 */
public class DAO {
  private final DBConnectionProvider connectionProvider;

  /**
   * Constructor for the DAO class.
   * 
   * <p>
   * Sets the Database Connection Provider.
   * </p>
   *
   * @param connectionProvider the {@link DBConnectionProvider} provider
   */
  public DAO(DBConnectionProvider connectionProvider) {
    this.connectionProvider = connectionProvider;
  }

  /**
   * This method adds a storable object to the database.
   *
   * @param obj the {@link Storable} object to add
   * @throws RuntimeException if an error occurs while storing the object
   */
  public void addToDatabase(Storable obj) throws RuntimeException {
    String placeholders = "?,".repeat(obj.getAttributeNames().size());
    placeholders = placeholders.substring(0, placeholders.length() - 1);
    String sql = String.format("INSERT INTO %s(%s) VALUES(%s)",
        obj.getClass().getSimpleName(), String.join(", ", obj.getAttributeNames()), placeholders);
    List<String> attributes = obj.getAttributes();
    try (Connection connection = connectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      for (int i = 0; i < attributes.size(); i++) {
        preparedStatement.setString(i + 1, attributes.get(i));
      }
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Error storing " + obj.getClass().getSimpleName(), e);
    }
  }

  /**
   * This method deletes a stoable object from the database.
   *
   * @param obj the {@link Storable} object to be deleted
   */
  public void deleteFromDatabase(Storable obj) {
    String sql = String.format("DELETE FROM %s WHERE %s = ?",
        obj.getClass().getSimpleName(), obj.getIdName());
    try (Connection connection = connectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setInt(1, obj.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Error deleting " + obj.getClass().getSimpleName(), e);
    }
  }

  /**
   * This method updates a storable object in the database.
   *
   * @param obj the {@link Storable} object to be updated
   * @throws RuntimeException if an error occurs while updating the object
   */
  public void updateDatabase(Storable obj) throws RuntimeException {
    String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ?",
        obj.getClass().getSimpleName(),
        String.join(" = ?, ", obj.getAttributeNames()), obj.getIdName());
    List<String> attributes = obj.getAttributes();
    try (Connection connection = connectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      for (int i = 0; i < attributes.size(); i++) {
        preparedStatement.setString(i + 1, attributes.get(i));
        if (i == attributes.size() - 1) {
          preparedStatement.setInt(i + 2, obj.getId());
        }
      }
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Error updating " + obj.getClass().getSimpleName(), e);
    }
  }

  /**
   * Returns all attributes from a table.
   *
   * @param table      the table to get attributes from
   * @param joinTable  the table to join with if needed
   * @param joinColumn the column to join on if needed
   * @return a list of lists containing the attributes of the objects in the table
   * @throws RuntimeException if an error occurs while getting all items
   */
  public List<List<String>> getAllFromTable(String table, String joinTable, String joinColumn)
      throws RuntimeException {
    List<List<String>> items = new ArrayList<>();
    String sql;
    if (joinTable == null) {
      sql = "SELECT * FROM " + table;
    } else {
      sql = "SELECT * FROM " + table
          + " JOIN " + joinTable + " ON " + table + "." + joinColumn + " = "
          + joinTable + "." + joinColumn;
    }
    try (Connection connection = connectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      List<String> columns = getColumnsFromTable(table);
      if (joinTable != null) {
        columns.addAll(getColumnsFromTable(joinTable));
      }
      while (resultSet.next()) {
        List<String> item = new ArrayList<>();
        for (String column : columns) {
          item.add(resultSet.getString(column));
        }
        items.add(item);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error getting all items", e);
    }
    return items;
  }

  /**
   * This method searches for objects by name in the database.
   *
   * @param table      the table to search in
   * @param name       the name to search for (can also be only part of the name)
   * @param joinTable  the table to join with if needed
   * @param joinColumn the column to join on if needed
   * @return a list of lists containing the attributes of the objects with the
   *         given name
   * @throws RuntimeException if an error occurs while searching for items
   */
  public List<List<String>> searchFromTable(
      String table, String name, String joinTable, String joinColumn)
      throws RuntimeException {
    List<List<String>> items = new ArrayList<>();
    String sql;
    if (joinTable == null) {
      sql = "SELECT * FROM " + table + " WHERE name LIKE ?";
    } else {
      sql = "SELECT * FROM " + table
          + " JOIN " + joinTable + " ON " + table + "." + joinColumn + " = "
          + joinTable + "." + joinColumn
          + " WHERE name LIKE ?";
    }
    try (Connection connection = connectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, "%" + name + "%");
      ResultSet resultSet = preparedStatement.executeQuery();
      List<String> columns = getColumnsFromTable(table);
      if (joinTable != null) {
        columns.addAll(getColumnsFromTable(joinTable));
      }
      while (resultSet.next()) {
        List<String> item = new ArrayList<>();
        for (String column : columns) {
          item.add(resultSet.getString(column));
        }
        items.add(item);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error getting all items", e);
    }
    return items;
  }

  /**
   * This method filters objects by a given column in the database.
   *
   * @param table        the table to filter from
   * @param filterColumn the column to filter by
   * @param filter       the filter to use
   * @param joinTable    the table to join with if needed
   * @param joinColumn   the column to join on if needed
   * @return a list of items with the given category
   * @throws RuntimeException if an error occurs while filtering items
   */
  public List<List<String>> filterFromTable(
      String table, String filterColumn, String filter,
      String joinTable, String joinColumn)
      throws RuntimeException {
    List<List<String>> items = new ArrayList<>();
    String sql;
    if (joinTable == null) {
      sql = "SELECT * FROM " + table + " WHERE " + filterColumn + " LIKE ?";
    } else {
      sql = "SELECT * FROM " + table
          + " JOIN " + joinTable + " ON " + table + "." + joinColumn + " = "
          + joinTable + "." + joinColumn
          + " WHERE " + filterColumn + " LIKE ?";
    }
    try (Connection connection = connectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, filter);
      ResultSet resultSet = preparedStatement.executeQuery();
      List<String> columns = getColumnsFromTable(table);
      if (joinTable != null) {
        columns.addAll(getColumnsFromTable(joinTable));
      }
      while (resultSet.next()) {
        List<String> item = new ArrayList<>();
        for (String column : columns) {
          item.add(resultSet.getString(column));
        }
        items.add(item);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error getting all items", e);
    }
    return items;
  }

  /**
   * Returns all columns from a table.
   *
   * @param table the table to get columns from
   * @return a list of column names
   * @throws RuntimeException if an error occurs while retrieving column names
   */
  private List<String> getColumnsFromTable(String table) throws RuntimeException {
    List<String> columns = new ArrayList<>();
    try (Connection connection = connectionProvider.getConnection()) {
      DatabaseMetaData metaData = connection.getMetaData();
      ResultSet resultSet = metaData.getColumns(null, null, table, null);
      while (resultSet.next()) {
        columns.add(resultSet.getString("COLUMN_NAME"));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error retrieving column names from " + table, e);
    }
    return columns;
  }
}
