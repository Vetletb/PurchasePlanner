package no.ntnu.idatt1002.demo.view.components;

import java.util.List;
import java.util.Map;

import javax.swing.event.ChangeListener;

import org.controlsfx.control.CheckComboBox;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.demo.Logger;
import no.ntnu.idatt1002.demo.data.Storable;

/**
 * A field for a form.
 * 
 * <p>
 * The field class is responsible for rendering the correct input field based on
 * the type of the field.
 * </p>
 * 
 * <p>
 * There are three types of fields:
 * <ul>
 * <li>NUMBER: A field for numbers. Returns the number typed</li>
 * <li>STRING: A field for strings. Returns the string typed</li>
 * <li>LIST: A field for selecting from a list of options. Returns the index of
 * the selected item</li>
 * </ul>
 * 
 * All fields have a placeholder that is displayed when the field is empty.
 */
public class Field<T> {

  /**
   * Enum for the different types of fields.
   */
  public enum FieldType {
    NUMBER,
    STRING,
    LIST,
    MAP
  }

  /**
   * Create a new field of type number.
   *
   * @return a new field of type number.
   */
  public static Field<Number> ofNumber(String placeholder) {
    return new Field<Number>(FieldType.NUMBER, placeholder);
  }

  public static Field<String> ofString(String placeholder) {
    return new Field(FieldType.STRING, placeholder);
  }

  /**
   * Create a new field of type list.
   *
   * @return a new field of type list.
   */
  public static Field<Integer> ofList(String placeholder, String[] options) {
    return new Field<Integer>(FieldType.LIST, placeholder, options);
  }

  public static Field<Integer[]> ofMap(String placeholder, Map<Integer, ? extends Storable> options) {
    return new Field<Integer[]>(FieldType.MAP, placeholder, options);
  }

  private FieldType type;
  private String placeholder;
  private T value;
  private Map<Integer, ? extends Storable> mapOptions;
  private String[] options;
  private String name;
  private VBox renderedField;

  public Node getRenderedField() {
    return renderedField;
  }

  public FieldType getType() {
    return type;
  }

  public T getValue() {
    return value;
  }

  public Field<T> setValue(T value) {
    this.value = value;
    return this;
  }

  private Field(FieldType type, String placeholder) {
    this.type = type;
    this.placeholder = placeholder;
    render();
  }

  private Field(FieldType type, String name, Map<Integer, ? extends Storable> options) {
    this.type = type;
    this.name = name;
    this.mapOptions = options;
    render();
  }

  private Field(FieldType type, String name, String[] options) {
    this.type = type;
    this.name = name;
    this.options = options;
    render();
  }

  @SuppressWarnings("unchecked")
  private void render() {

    if (renderedField == null) {
      renderedField = new VBox();
    }

    renderedField.getChildren().clear();
    switch (type) {
      case NUMBER:
        renderedField.getChildren().add(new NumberField(v -> {
          this.value = (T) (Number) v;
        }));

        break;
      case STRING:
        renderedField.getChildren().add(new InputField(placeholder).setOnSubmit(value -> {
          this.value = (T) value;
        }).setOnChange(value -> {
          this.value = (T) value;
        }));
        break;
      case MAP:
        DropdownMultiselect dropdown = new DropdownMultiselect(mapOptions);
        dropdown.setPrefHeight(35);
        dropdown.setPrefWidth(200);
        dropdown.getCheckModel().getCheckedItems().addListener(new ListChangeListener() {
          public void onChanged(ListChangeListener.Change c) {
            value = (T) dropdown.getCheckModel().getCheckedItems().stream()
                .map(s -> s.getId())
                .toArray();
          }
        });

        renderedField.getChildren().addAll(new Text(name), dropdown);
        break;
      default:
        break;
    }
  }
}