package no.ntnu.idatt1002.demo.dao;

import no.ntnu.idatt1002.demo.data.Item;
import no.ntnu.idatt1002.demo.data.Storable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DAOTest {

  @Mock
  DBConnectionProvider dbConnectionProvider;

  @Mock
  Connection connection;

  @Mock
  PreparedStatement preparedStatement;

  @Mock
  ResultSet resultSet;

  @Mock
  Storable storable;

  @Mock
  DatabaseMetaData metaData;

  @InjectMocks
  DAO dao;

  @BeforeEach
  void setUp() throws SQLException {
    MockitoAnnotations.openMocks(this);
    when(dbConnectionProvider.getConnection()).thenReturn(connection);
    when(connection.getMetaData()).thenReturn(metaData);
    when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
  }

  @Test
  public void testAddToDatabase() throws SQLException {
    when(storable.getAttributeNames()).thenReturn(Arrays.asList("name", "category", "allergy"));
    when(storable.getAttributes()).thenReturn(Arrays.asList("item1", "category1", "allergy1"));

    dao.addToDatabase(storable);

    verify(dbConnectionProvider, times(1)).getConnection();
    verify(connection, times(1)).prepareStatement(anyString());
    verify(preparedStatement, times(1)).setString(1, "item1");
    verify(preparedStatement, times(1)).setString(2, "category1");
    verify(preparedStatement, times(1)).setString(3, "allergy1");
    verify(preparedStatement, times(1)).executeUpdate();
  }

  @Test
  public void testDeleteFromDatabase() throws SQLException {
    when(storable.getId()).thenReturn(1);

    dao.deleteFromDatabase(storable);

    verify(dbConnectionProvider, times(1)).getConnection();
    verify(connection, times(1)).prepareStatement(anyString());
    verify(preparedStatement, times(1)).setInt(1, 1);
    verify(preparedStatement, times(1)).executeUpdate();
  }

  @Test
  public void testUpdateDatabase() throws SQLException {
    when(storable.getId()).thenReturn(1);
    when(storable.getAttributeNames()).thenReturn(Arrays.asList("name", "category", "allergy"));
    when(storable.getAttributes()).thenReturn(Arrays.asList("item1", "category1", "allergy1"));

    dao.updateDatabase(storable);

    verify(dbConnectionProvider, times(1)).getConnection();
    verify(connection, times(1)).prepareStatement(anyString());
    verify(preparedStatement, times(1)).setString(1, "item1");
    verify(preparedStatement, times(1)).setString(2, "category1");
    verify(preparedStatement, times(1)).setString(3, "allergy1");
    verify(preparedStatement, times(1)).setInt(4, 1);
    verify(preparedStatement, times(1)).executeUpdate();
  }

  @Test
  public void testGetAllFromTable() throws SQLException {
    when(dbConnectionProvider.getConnection()).thenReturn(connection);
    when(connection.getMetaData()).thenReturn(metaData);
    when(metaData.getColumns(null, null, "table", null)).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true, true, true, false, true,  false);
    when(resultSet.getString("COLUMN_NAME")).thenReturn("column1", "column2", "column3");
    when(resultSet.getString("column1")).thenReturn("value1");
    when(resultSet.getString("column2")).thenReturn("value2");
    when(resultSet.getString("column3")).thenReturn("value3");

    List<List<String>> items = dao.getAllFromTable("table", null, null);

    verify(dbConnectionProvider, times(2)).getConnection();
    verify(connection, times(1)).prepareStatement(anyString());
    verify(preparedStatement, times(1)).executeQuery();
    verify(resultSet, times(6)).next();
    verify(resultSet, times(6)).getString(anyString());

    assertEquals(1, items.size());
    assertEquals(Arrays.asList("value1", "value2", "value3"), items.get(0));
  }

  @Test
  public void testSearchFromTable() throws SQLException {
    when(dbConnectionProvider.getConnection()).thenReturn(connection);
    when(connection.getMetaData()).thenReturn(metaData);
    when(metaData.getColumns(null, null, "table", null)).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true, true, true, false, true,  false);
    when(resultSet.getString("COLUMN_NAME")).thenReturn("column1", "column2", "column3");
    when(resultSet.getString("column1")).thenReturn("value1");
    when(resultSet.getString("column2")).thenReturn("value2");
    when(resultSet.getString("column3")).thenReturn("value3");

    List<List<String>> items = dao.searchFromTable("table", "name", null, null);

    verify(dbConnectionProvider, times(2)).getConnection();
    verify(connection, times(1)).prepareStatement(anyString());
    verify(preparedStatement, times(1)).executeQuery();
    verify(resultSet, times(6)).next();
    verify(resultSet, times(6)).getString(anyString());

    assertEquals(1, items.size());
    assertEquals(Arrays.asList("value1", "value2", "value3"), items.get(0));
  }

  @Test
  public void testFilterFromTable() throws SQLException {
    when(dbConnectionProvider.getConnection()).thenReturn(connection);
    when(connection.getMetaData()).thenReturn(metaData);
    when(metaData.getColumns(null, null, "table", null)).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true, true, true, false, true,  false);
    when(resultSet.getString("COLUMN_NAME")).thenReturn("column1", "column2", "column3");
    when(resultSet.getString("column1")).thenReturn("value1");
    when(resultSet.getString("column2")).thenReturn("value2");
    when(resultSet.getString("column3")).thenReturn("value3");

    List<List<String>> items = dao.filterFromTable("table", "name", "value", null, null);

    verify(dbConnectionProvider, times(2)).getConnection();
    verify(connection, times(1)).prepareStatement(anyString());
    verify(preparedStatement, times(1)).executeQuery();
    verify(resultSet, times(6)).next();
    verify(resultSet, times(6)).getString(anyString());

    assertEquals(1, items.size());
    assertEquals(Arrays.asList("value1", "value2", "value3"), items.get(0));
  }
}