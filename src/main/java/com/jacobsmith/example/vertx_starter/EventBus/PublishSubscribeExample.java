package com.jacobsmith.example.vertx_starter.EventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class PublishSubscribeExample {
  public static void main(String[] args) {
    Vertx vert = Vertx.vertx();
    vert.deployVerticle(new Publisher());
    vert.deployVerticle(new Subscriber1());
    vert.deployVerticle(new Subscriber2());
  }

  public static class Publisher extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.setPeriodic(Duration.ofSeconds(10).toMillis(), id -> {
        vertx.eventBus().publish(Publisher.class.getName(), "A message for everyone");
      });
    }
  }

  public static class Subscriber1 extends AbstractVerticle {
    private static final Logger log = LoggerFactory.getLogger(Subscriber1.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<String>consumer(Publisher.class.getName(), message -> {
        log.debug("Message recieved by sub1: " + message.body());
      });
    }
  }

  static class Subscriber2 extends AbstractVerticle {
    private static final Logger log = LoggerFactory.getLogger(Subscriber2.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<String>consumer(Publisher.class.getName(), message -> {
        log.debug("Message recieved by Sub2: " + message.body());
      });
    }
  }

}
