package com.jacobsmith.example.vertx_starter.Eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Point2PointExample {
  /*
  point to point messaging allows one verticle to send a message to another
  verticle without requiring a response.
   */

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Sender());
    vertx.deployVerticle(new Receiver());
  }

  static class Sender extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.setPeriodic(1000, id -> {
        // send every second
        vertx.eventBus().send(Sender.class.getName(), "Sending a message");
      });
    }
  }

  static class Receiver extends AbstractVerticle{

    private static final Logger log = LoggerFactory.getLogger(Receiver.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().consumer(Sender.class.getName(), message -> {
        log.debug("Message received : " + message.body().toString());
      });
    }
  }

}
