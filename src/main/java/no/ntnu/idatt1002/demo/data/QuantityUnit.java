package no.ntnu.idatt1002.demo.data;

public class QuantityUnit {
  private int quantity;
  private final String unit;

  public QuantityUnit(int quantity, String unit) {
    this.quantity = quantity;
    this.unit = unit;
  }

  public int getQuantity() {
    return quantity;
  }

  public String getUnit() {
    return unit;
  }

  public void addQuantity(int additionalQuantity) {
    this.quantity += additionalQuantity;
  }

  public String toString() {
    return quantity + " : " + unit;
  }
}
