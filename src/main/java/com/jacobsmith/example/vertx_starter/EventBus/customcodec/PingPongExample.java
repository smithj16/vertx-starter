package com.jacobsmith.example.vertx_starter.EventBus.customcodec;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingPongExample {

  private final static Logger LOG = LoggerFactory.getLogger(PingPongExample.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(new PingVerticle(), ar -> {
      if(ar.failed())
        LOG.error(ar.cause().getMessage());
    });

    vertx.deployVerticle(new PongVerticle(), ar -> {
      if(ar.failed())
        LOG.error(ar.cause().getMessage());
    });

  }

  static class PingVerticle extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(PingVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      EventBus eventBus = vertx.eventBus();
      final Ping message = new Ping("hello", true);
      log.debug("Sending: {}", message);
      vertx.setPeriodic(1000, id -> {
        eventBus.<Pong>request(PingVerticle.class.getName(), message, reply -> {
          if(reply.failed())
            log.error("Failed: {}", reply.cause());
          log.debug("Response: {}", reply.result().body());
        });
      });
    }
  }

  static class PongVerticle extends AbstractVerticle{

    private final static Logger log = LoggerFactory.getLogger(PongVerticle.class);
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<Ping>consumer(PingVerticle.class.getName(), message  -> {
        log.debug("Received Message: {}", message.body());
        message.reply(new Pong(0));
      }).exceptionHandler(error -> log.error("ExeceptionHandler: {}", error.getCause()));
    }
  }
}
