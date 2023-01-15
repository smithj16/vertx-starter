package com.jacobsmith.example.vertx_starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Verticle extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(Verticle.class);

  public static void main(String[] args) {
    Vertx vert = Vertx.vertx();
    vert.deployVerticle(new Verticle());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x!");
    }).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });

    vertx.setPeriodic(500, id -> {
      log.debug(new Random().toString());
    });
  }
}
