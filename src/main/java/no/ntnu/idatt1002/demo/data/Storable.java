package no.ntnu.idatt1002.demo.data;

import java.util.List;

public interface Storable {
  List<String> getAttributeNames();
  List<String> getAttributes();
  String getIdName();
  int getId();
}
