package no.ntnu.idatt1002.demo.data;

import no.ntnu.idatt1002.demo.util.VerifyInput;

/**
 * This class is a simple bean for a quantity unit.
 */
public class QuantityUnit {
  private int quantity;
  private final String unit;

  /**
   * Constructor for the QuantityUnit class.
   *
   * @param quantity the quantity
   * @param unit     the unit
   */
  public QuantityUnit(int quantity, String unit) {
    VerifyInput.verifyPositiveNumberMinusOneNotAccepted(quantity, "quantity");
    VerifyInput.verifyNotEmpty(unit, "unit");
    this.quantity = quantity;
    this.unit = unit;
  }

  /**
   * Returns the quantity.
   *
   * @return the quantity
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Returns the unit.
   *
   * @return the unit
   */
  public String getUnit() {
    return unit;
  }

  /**
   * Adds a quantity to the current quantity.
   *
   * @param additionalQuantity the additional quantity
   */
  public void addQuantity(int additionalQuantity) {
    this.quantity += additionalQuantity;
  }

  public String toString() {
    return quantity + " : " + unit;
  }

  public static QuantityUnit add(QuantityUnit a, QuantityUnit b) {
    return new QuantityUnit(a.getQuantity() + b.getQuantity(), a.getUnit());
  }
}
