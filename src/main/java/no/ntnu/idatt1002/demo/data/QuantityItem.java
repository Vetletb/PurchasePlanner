package no.ntnu.idatt1002.demo.data;

import java.util.ArrayList;
import java.util.List;

public class QuantityItem extends Item {
  private int quantityItem_id;
  private final int quantity;
  private final String unit;

  public QuantityItem(int item_id, String name, String category, String allergy, int quantity, String unit) {
    super(item_id, name, category, allergy);
    this.quantity = quantity;
    this.unit = unit;
  }

  @Override
  public List<String> getAttributes() {
    List<String> attributes = new ArrayList<>();
    attributes.add(Integer.toString(super.getItem_id()));
    attributes.add(Integer.toString(quantity));
    attributes.add(unit);
    return attributes;
  }

  @Override
  public List<String> getAttributeNames() {
    List<String> attributes = new ArrayList<>();
    attributes.add("item_id");
    attributes.add("quantity");
    attributes.add("unit");
    return attributes;
  }

  @Override
  public int getId() {
    return quantityItem_id;
  }

  @Override
  public String getIdName() {
    return "quantityItem_id";
  }

  public int getQuantity() {
    return quantity;
  }

  public String getUnit() {
    return unit;
  }
}
