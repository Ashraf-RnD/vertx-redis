/*
 * @Author Md Ashraful Alam
 * @Time 6/10/21, 1:16 AM
 */

package ashraful.rnd.vertx_redis.verticles;

import ashraful.rnd.vertx_redis.handlers.RedisHandler;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;

public class RedisVerticle extends AbstractVerticle {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private RedisHandler redisHandler;

  @Override
  public void start(Promise<Void> startFuture) throws Exception {
    initHandlers();
    initHttpServer(startFuture,initRouter());
  }


  Router initRouter(){
    var router = Router.router(vertx);
    router.route()
      .handler(BodyHandler.create());

    router.post("/data/cache")
      .handler(redisHandler);

    return router;
  }

  private void initHttpServer(Promise<Void> serverPromise, Router router) {
    vertx.createHttpServer()
      .requestHandler(router)
      .rxListen(9980)
      .subscribe(
        httpServer -> {
          logger.info("HTTP server started on port 9980");
          serverPromise.complete();
        },
        throwable -> {
          logger.error("HTTP server failed to start on port 9980. Cause " + throwable.getCause());
          serverPromise.fail(throwable.getCause());
        });
  }


  private void initHandlers() {
    this.redisHandler = new RedisHandler();
  }
}
