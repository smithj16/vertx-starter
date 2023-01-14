package com.jacobsmith.example.vertx_starter.EventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestResponseExampleJSON {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new ResponseVerticle());
    vertx.deployVerticle(new RequestVerticle());
  }

  static class RequestVerticle extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(RequestResponseExample.RequestVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      EventBus eventBus = vertx.eventBus();
      final JsonObject message = new JsonObject().put("message", "Hello World")
        .put("version", 1);
      log.debug("Sending: {}", message);
      eventBus.<JsonArray>request("request.address", message, reply -> {
        try {
          log.debug("Response: {}", reply.result().body());
        }catch(Exception e){
          log.debug(e.getMessage());
        }
      });
    }
  }

  static class ResponseVerticle extends AbstractVerticle{

    private final static Logger log = LoggerFactory.getLogger(ResponseVerticle.class);
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<JsonObject>consumer(RequestVerticle.class.getName(), message -> {
       log.debug("Recieved message: " + message.body());
       message.reply(new JsonArray().add("one").add("two").add("three"));
      });
    }
  }
}
