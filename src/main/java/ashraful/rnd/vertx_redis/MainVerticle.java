/*
 * @Author Md Ashraful Alam
 * @Time 6/10/21, 1:16 AM
 */

package ashraful.rnd.vertx_redis;

import ashraful.rnd.vertx_redis.verticles.RedisVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public void start(Promise<Void> vertxPromise) throws Exception {
    var options = new DeploymentOptions().setConfig(vertx.getOrCreateContext().config());

    vertx.rxDeployVerticle(RedisVerticle.class.getName(), options)
      .subscribe(s -> {
        logger.info("All the verticles are up and running");
        vertxPromise.complete();
      }, throwable -> {
        logger.error("Problem running one or more verticles. Cause -> ", throwable.getCause());
        vertxPromise.fail(throwable);
      });

  }
}
