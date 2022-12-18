package com.jacobsmith.example.vertx_starter.EventLoops;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventLoopExample extends AbstractVerticle {

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
