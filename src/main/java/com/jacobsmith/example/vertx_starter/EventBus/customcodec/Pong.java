package com.jacobsmith.example.vertx_starter.EventBus.customcodec;

public class Pong {
  private Integer id;

  public Pong(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  @Override
  public String toString() {
    return "Pong{" +
      "id=" + id +
      '}';
  }
}
