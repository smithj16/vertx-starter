package WorkerVerticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerVerticleExample extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(WorkerVerticleExample.class);

  public static void main(String[] args) {

    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new WorkerVerticleExample());

  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    vertx.executeBlocking(event -> {
      log.debug("Executing blocking code");
      try {
        Thread.sleep(5);
        event.complete();
      } catch (InterruptedException e) {
        log.error("Failed: ", e);
        event.fail(e);
      }
    }, result -> {
      if (result.succeeded()){
        log.debug("Blocking call done.");
      } else {
        log.debug("Blocking call failed due to: ", result.cause());
      }
    });

  }
}
