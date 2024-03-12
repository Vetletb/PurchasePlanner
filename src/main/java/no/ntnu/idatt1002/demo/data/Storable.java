package no.ntnu.idatt1002.demo.data;

import java.util.List;

/**
 * This interface is used to store objects in the database.
 */

public interface Storable {

  /**
   * This method returns the attribute names of the object.
   * @return the attribute names of the object
   */
  List<String> getAttributeNames();

  /**
   * This method returns the attributes of the object.
   * @return the attributes of the object
   */
  List<String> getAttributes();

  /**
   * This method returns the name of the id.
   * @return the name of the id
   */
  String getIdName();

  /**
   * This method returns the id of the object.
   * @return the id of the object
   */
  int getId();
}
