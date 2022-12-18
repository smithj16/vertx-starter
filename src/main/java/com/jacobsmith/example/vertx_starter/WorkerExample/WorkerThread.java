package com.jacobsmith.example.vertx_starter.WorkerExample;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerThread extends AbstractVerticle {

  /*
     Worker Threads allow you run code that performs any blocking operations (file I/O, thread calls, etc)
     using the executeBlocking method. A second approach to executing blocking code is to deploy a worker
     verticle and calling the setWorker method from the DeploymentOptions method to true.
   */

  private final static Logger log = LoggerFactory.getLogger(WorkerThread.class);

  public static void main(String[] args) {
    Vertx vert = Vertx.vertx();
    vert.deployVerticle(new WorkerThread());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    startPromise.complete();
    log.debug("Deploying Worker Verticle ....");
    vertx.deployVerticle(new WorkerVerticle(), new DeploymentOptions()
      .setWorker(true)
      .setWorkerPoolSize(1)
      .setWorkerPoolName("my-worker-verticle"));

    executeBlockingCode();

  }

  private void executeBlockingCode(){
    vertx.executeBlocking(event -> {
      // The first handler is executed on the worker thread
      log.debug("Executing blocking code.");
      try{
        Thread.sleep(5000);
        event.complete();
      }catch(InterruptedException e ){
        log.error("Failed: ", e);
        event.fail(e);
      }
    }, result -> {
      // the handler is executed on the eventloop thread
      if(result.succeeded()){
        log.debug("Blocking call done");
      }else{
        log.error("Blocking call failed: ", result.cause());
      }
    });
  }
}
