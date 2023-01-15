package com.jacobsmith.example.vertx_starter;

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
  }
}
