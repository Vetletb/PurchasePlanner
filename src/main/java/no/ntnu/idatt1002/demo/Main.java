package no.ntnu.idatt1002.demo;

import javafx.application.Application;
import no.ntnu.idatt1002.demo.dao.DBConnectionProvider;
import no.ntnu.idatt1002.demo.dao.ItemDAO;
import no.ntnu.idatt1002.demo.data.Item;
import no.ntnu.idatt1002.demo.view.App;

/**
 * Use this class to start the application.
 */
public class Main {

  /**
   * Main method for the application.
   */
  public static void main(String[] args) {
    Application.launch(App.class, args);
//    DBConnectionProvider db = DBConnectionProvider.getInstance();
//    ItemDAO itemDAO = new ItemDAO(db);
//    Item item = new Item(13,"Milk", "Dairy", "Lactose");
//
//    Item item2 = new Item(12,"Carrot", "Vegetable", "carrot allergy");
//    itemDAO.addToDatabase(item);
//    itemDAO.deleteFromDatabase(item);
//    itemDAO.updateDatabase(item2);
  }
}
