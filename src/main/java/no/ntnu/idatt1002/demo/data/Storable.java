package no.ntnu.idatt1002.demo.data;

import java.util.List;

/**
 * This interface is used to store objects in the database.
 */
public interface Storable {

  /**
   * This method returns the attribute names of the object.
   * These should be the same as the column names in the database.
   *
   * @return the attribute names of the object
   */
  List<String> getAttributeNames();

  /**
   * This method returns the attributes of the object.
   * These should be the values to be stored in the columns of the database.
   *
   * @return the attributes of the object
   */
  List<String> getAttributes();

  /**
   * This method returns the name of the id.
   * This should be the same as the column name in the database.
   *
   * @return the name of the id
   */
  String getIdName();

  /**
   * This method returns the id of the object.
   * This should be the value to be stored in the id column of the database.
   *
   * @return the id of the object
   */
  int getId();
}
