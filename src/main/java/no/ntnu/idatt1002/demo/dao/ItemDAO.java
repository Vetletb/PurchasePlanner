package no.ntnu.idatt1002.demo.dao;

import no.ntnu.idatt1002.demo.data.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides methods for accessing the item table in the database.
 */
public class ItemDAO {
private final DBConnectionProvider connectionProvider;

  /**
   * Constructor for the ItemDAO class.
   * @param connectionProvider the connection provider
   */
  public ItemDAO(DBConnectionProvider connectionProvider) {
    this.connectionProvider = connectionProvider;
  }

  /**
   * This method adds an item to the database.
   * @param item the item to add
   */
  public void addItem(Item item) {
      String sql = "INSERT INTO item(name, category, allergy) VALUES(?,?,?)";
      try (Connection connection = connectionProvider.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, item.getName());
        preparedStatement.setString(2, item.getCategory());
        preparedStatement.setString(3, item.getAllergy());
        preparedStatement.executeUpdate();
      } catch (SQLException e) {
        throw new RuntimeException("Error creating item", e);
      }
    }

  /**
   * This method deletes an item from the database.
    * @param item_id the id of the item to delete
   */
  public void deleteItem(int item_id) {
      String sql = "DELETE FROM item WHERE item_id = ?";
      try (Connection connection = connectionProvider.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setInt(1, item_id);
        preparedStatement.executeUpdate();
      } catch (SQLException e) {
        throw new RuntimeException("Error deleting item", e);
      }
    }


  /**
   * This method updates an item in the database.
    * @param item the item to update
   */
  public void updateItem(Item item) {
    String sql = "UPDATE item SET name = ?, category = ?, allergy = ? WHERE item_id = ?";
    try (Connection connection = connectionProvider.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, item.getName());
      preparedStatement.setString(2, item.getCategory());
      preparedStatement.setString(3, item.getAllergy());
      preparedStatement.setInt(4, item.getItem_id());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Error updating item", e);
    }
  }

  /**
   * This method returns all items from the database.
   * @return a list of all items
   */
  public List<Item> getAllItems() {
    List<Item> items = new ArrayList<>();
    String sql = "SELECT * FROM item";
    try (Connection connection = connectionProvider.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
         ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        int item_id = resultSet.getInt("item_id");
        String name = resultSet.getString("name");
        String category = resultSet.getString("category");
        String allergy = resultSet.getString("allergy");
        items.add(new Item(item_id, name, category, allergy));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error getting all items", e);
    }
    return items;
  }


  /**
   * This method searches for items by name in the database.
   * @param name the name to search for (can also be only part of the name)
   * @return a list of items with the given name
   */
  public List<Item> searchItemsByName(String name) {
    List<Item> items = new ArrayList<>();
    String sql = "SELECT * FROM item WHERE name LIKE ?";
    try (Connection connection = connectionProvider.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, "%" + name + "%");
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        int item_id = resultSet.getInt("item_id");
        String itemName = resultSet.getString("name");
        String category = resultSet.getString("category");
        String allergy = resultSet.getString("allergy");
        items.add(new Item(item_id, itemName, category, allergy));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error searching items by name", e);
    }
    return items;
  }

  /**
   * This method filters items by category in the database.
   * @param category the category to filter by
   * @return a list of items with the given category
   */
  public List<Item> filterItemsByCategory(String category) {
    List<Item> items = new ArrayList<>();
    String sql = "SELECT * FROM item WHERE category = ?";
    try (Connection connection = connectionProvider.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, category);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        int item_id = resultSet.getInt("item_id");
        String name = resultSet.getString("name");
        String itemCategory = resultSet.getString("category");
        String allergy = resultSet.getString("allergy");
        items.add(new Item(item_id, name, itemCategory, allergy));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error filtering items by category", e);
    }
    return items;
  }
}
