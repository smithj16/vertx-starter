package com.jacobsmith.example.vertx_starter.Eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestResponseExample {
  private static final Logger log = LoggerFactory.getLogger(RequestResponseExample.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    log.debug("Deploying Request and Response verticles.....");
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());
  }

  static class RequestVerticle extends AbstractVerticle{
    private static final Logger log = LoggerFactory.getLogger(RequestVerticle.class);
    private static final String address = "my-request-address";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      EventBus eventBus = vertx.eventBus();
      final String message = "Hello World!";
      log.debug("Sending: {}", message);
      eventBus.<String>request(address, message, reply -> {
        log.debug("Response: {}", reply.result().body());
      });
    }
  }

  static class ResponseVerticle extends AbstractVerticle{
    private final static Logger log = LoggerFactory.getLogger(ResponseVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    vertx.eventBus().<String>consumer(RequestVerticle.address, message -> {
      log.debug("Received: {}", message.body());
      message.reply("Message received.");
    });
    }
  }
}
