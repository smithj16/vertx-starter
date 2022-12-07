package com.jacobsmith.example.vertx_starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleA extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(VerticleA.class);
  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    log.debug("Start {} " , getClass().getName());

    vertx.deployVerticle(new VerticleAA(), whenDeployed -> {
      log.debug("Deployed {} ", VerticleAA.class.getName());
      vertx.undeploy(whenDeployed.result());
    });

    startPromise.complete();
  }
}
