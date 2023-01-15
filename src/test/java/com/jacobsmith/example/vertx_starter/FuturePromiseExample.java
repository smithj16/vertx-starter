package com.jacobsmith.example.vertx_starter;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(VertxExtension.class)
public class FuturePromiseExample {

  private static final Logger log = LoggerFactory.getLogger(FuturePromiseExample.class);

  @Test
  void promise_success(Vertx vert, VertxTestContext context) {
    final Promise<String> promise = Promise.promise();
    log.debug("start");
    vert.setTimer(500, id ->{
      promise.complete("success");
      log.debug("Promise completed");
      context.completeNow();
    });
    log.debug("End");
  }

  @Test
  void promise_failure (Vertx vert, VertxTestContext context) {
    final Promise<String> promise = Promise.promise();
    log.debug("Start");
    vert.setTimer(500, id -> {
      promise.fail("Failure");
      log.debug("Promise failed");
      context.completeNow();
    });
    log.debug("End");

  }

  @Test
  void future_success (Vertx vert, VertxTestContext context){

    final Promise<String> promise = Promise.promise();
    log.debug("Start");
    vert.setTimer(500, id -> {
      promise.complete("complete");
      log.debug("Timer done.");
      context.completeNow();
    });

    final Future<String> future = promise.future();
    future.onSuccess(result -> {
      log.debug("Result: {}", result);
      context.completeNow();
    }).onFailure(context::failNow);

  }


  @Test
  void future_failure(Vertx vertx, VertxTestContext context){

    final Promise<String> promise = Promise.promise();
    log.debug("Start");
    vertx.setTimer(500, id -> {
      promise.fail("failed");
      context.completeNow();
    });
    log.debug("End");

    final Future<String> future = promise.future();
    future.onFailure(result -> {
      log.debug("Promised Failed with error: {}", result);
      context.completeNow();
    });
  }

}
