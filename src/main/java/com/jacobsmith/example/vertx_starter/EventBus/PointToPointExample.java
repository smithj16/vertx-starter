package com.jacobsmith.example.vertx_starter.EventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PointToPointExample {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Receiver());
    vertx.deployVerticle(new Sender());
  }

  static class Sender extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.setPeriodic(1000, id -> {
        vertx.eventBus().send(Sender.class.getName(), "Sending a message...");
      });
    }
  }

  static class Receiver extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(Receiver.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<String>consumer(Sender.class.getName(), message -> {
        log.debug("Message received: " + message.body());
      });
    }
  }
}
