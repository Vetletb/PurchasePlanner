package no.ntnu.idatt1002.demo.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import no.ntnu.idatt1002.demo.data.MyEntity;
import org.junit.jupiter.api.Test;

class MyEntityRepoTest {

  @Test
  void testThatWeCanReadMyEntityFromDatabase() {
    MyEntity e = new MyEntityRepo().getMyEntity("id");
    assertEquals("name", e.getName());
  }

}