package com.jacobsmith.example.vertx_starter.EventLoops;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventLoopExample extends AbstractVerticle {
   /*
      The event-bus is a tool for verticles to communicate with each other. Here are some key notes
      regarding the event-bus.
      - It's the nervous system of vert.x
      - It allows event driven communication in a thread-safe non-blocking manner
      - There once exist one eventbus instance per vertex instance
      - The event bus supports
         . publish/subscribe messaging
         . point to point messaging
         . request/response messaging
    */
  private static final Logger log = LoggerFactory.getLogger(EventLoopExample.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new EventLoopExample());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.debug("Start {}", getClass().getName());
  }
}
