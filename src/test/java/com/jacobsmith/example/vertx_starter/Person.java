package com.jacobsmith.example.vertx_starter;

public class Person {
  private Integer id;
  private String name;
  private boolean lovesVertx;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isLovesVertx() {
    return lovesVertx;
  }

  public void setLovesVertx(boolean lovesVertx) {
    this.lovesVertx = lovesVertx;
  }

  public Person(Integer id, String name, boolean lovesVertx) {
    this.id = id;
    this.name = name;
    this.lovesVertx = lovesVertx;
  }

  @Override
  public String toString() {
    return "Person{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", lovesVertx=" + lovesVertx +
      '}';
  }
}
