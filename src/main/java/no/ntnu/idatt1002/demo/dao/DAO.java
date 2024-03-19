package no.ntnu.idatt1002.demo.dao;

import java.sql.*;
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
   * @param connectionProvider the connection provider
   */
  public DAO(DBConnectionProvider connectionProvider) {
    this.connectionProvider = connectionProvider;
  }

  /**
   * This method adds an object to the database.
   * @param obj the object to add
   */
  public void addToDatabase(Storable obj) {
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
   * This method deletes an item from the database.
   * @param obj the object to be deleted
   */
  public void deleteFromDatabase(Storable obj) {
    String sql = String.format("DELETE FROM %s WHERE %s = ?", obj.getClass().getSimpleName(), obj.getIdName());
    try (Connection connection = connectionProvider.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setInt(1, obj.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Error deleting " + obj.getClass().getSimpleName(), e);
    }
  }


  /**
   * This method updates an object in the database.
   * @param obj the object to be updated
   */
  public void updateDatabase(Storable obj) {
    String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ?",
        obj.getClass().getSimpleName(),
        String.join(" = ?, ", obj.getAttributeNames()),obj.getIdName());
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
   * This method returns all attributes from a table.
   * @param table the table to get attributes from
   * @return a list of lists containing all attributes from a table
   */
  public List<List<String>> getAllFromTable(String table, String joinTable, String joinColumn) {
    List<List<String>> items = new ArrayList<>();
    String sql;
    if (joinTable == null) {
      sql = "SELECT * FROM " + table;
    } else {
      sql = "SELECT * FROM " + table +
          " JOIN " + joinTable + " ON " + table + "." + joinColumn + " = " + joinTable + "." +  joinColumn;
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
   * @param table the table to search in
   * @param name the name to search for (can also be only part of the name)
   * @param joinTable the table to join with if needed
   * @param joinColumn the column to join on if needed
   * @return a list of lists containing the attributes of the objects with the given name
   */
  public List<List<String>> searchFromTable(String table, String name, String joinTable, String joinColumn) {
    List<List<String>> items = new ArrayList<>();
    String sql;
    if (joinTable == null) {
      sql = "SELECT * FROM "+ table + " WHERE name LIKE ?";
    } else {
      sql = "SELECT * FROM " + table +
          " JOIN " + joinTable + " ON " + table + "." + joinColumn + " = " + joinTable + "." +  joinColumn +
          " WHERE name LIKE ?";
    }
    try (Connection connection = connectionProvider.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, "%" + name + "%");
      ResultSet resultSet = preparedStatement.executeQuery();
      List<String> columns = getColumnsFromTable(table);
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
   * @param table the table to filter from
   * @param filterColumn the column to filter by
   * @param filter the filter to use
   * @param joinTable the table to join with if needed
   * @param joinColumn the column to join on if needed
   * @return a list of items with the given category
   */
  public List<List<String>> filterFromTable(String table, String filterColumn, String filter, String joinTable, String joinColumn) {
    List<List<String>> items = new ArrayList<>();
    String sql;
    if (joinTable == null) {
      sql = "SELECT * FROM " + table + " WHERE " + filterColumn + " LIKE ?";
    } else {
      sql = "SELECT * FROM " + table +
          " JOIN " + joinTable + " ON " + table + "." + joinColumn + " = " + joinTable + "." +  joinColumn +
          " WHERE " + filterColumn + " LIKE ?";
    }
    try (Connection connection = connectionProvider.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, filter);
      ResultSet resultSet = preparedStatement.executeQuery();
      List<String> columns = getColumnsFromTable(table);
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
   * This method returns all columns from a table.
   * @param table the table to get columns from
   * @return a list of all columns from the table
   */
  private List<String> getColumnsFromTable(String table) {
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
