package com.jacobsmith.example.vertx_starter.Eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublishSubcribeExample {
  /*

   */
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Subscriber2());
    vertx.deployVerticle(new Subscriber());
    vertx.deployVerticle(new Publisher());
  }

  static class Publisher extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(Publisher.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().send(Publisher.class.getName(), "Hello world");
    }
  }

  static class Subscriber extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(Subscriber.class);
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<String>consumer(Publisher.class.getName(), message -> {
        log.debug("Message receieved by Sub1: " + message.body());
      });
    }
  }

  static class Subscriber2 extends AbstractVerticle {
    private static final Logger log = LoggerFactory.getLogger(Subscriber2.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().consumer(Publisher.class.getName(), message -> {
        log.debug("Message received by Sub2: " + message.body());
      });
    }
  }

}
