package com.jacobsmith.example.vertx_starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class MainVerticle extends AbstractVerticle {
  /*
    Every verticle runs on it's own thread.
   */
  private static final Logger log = LoggerFactory.getLogger(MainVerticle.class);
  public static void main(String[] args) {
    final Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new MainVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.debug("Start {}", getClass().getName());
    vertx.deployVerticle(new VerticleA());
    vertx.deployVerticle(new VerticleB());
    vertx.deployVerticle(VerticleN.class.getName(), new DeploymentOptions().setInstances(4)
      .setConfig(new JsonObject().put(
        "id ", UUID.randomUUID().toString()
      ).put(
        "name ", VerticleN.class.getSimpleName()
      )));

    startPromise.complete();
  }
}
