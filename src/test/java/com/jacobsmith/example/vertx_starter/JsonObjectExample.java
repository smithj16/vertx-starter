package com.jacobsmith.example.vertx_starter;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonObjectExample {
  @Test
  void jsonObjectCanBeMapped(){
    final JsonObject myJsonObject = new JsonObject();
    myJsonObject.put("id", 1);
    myJsonObject.put("name", "Alice");
    myJsonObject.put("loves_vertx", true);

    assertEquals("", myJsonObject.encode());
  }

  @Test
  void canBeMappedFromMap(){
    final Map<String, Object> map = new HashMap<>();
    map.put("id", 1);
    map.put("name", "Jacob");
    map.put("loves_vertx", true);

    assertEquals(map, new JsonObject(map).getMap());
  }
}
