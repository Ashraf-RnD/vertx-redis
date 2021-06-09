/*
 * @Author Md Ashraful Alam
 * @since 6/9/21, 4:49 PM
 */

package ashraful.rnd.vertx_redis.service;

import io.reactivex.Maybe;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.redis.client.Redis;
import io.vertx.reactivex.redis.client.RedisAPI;
import io.vertx.reactivex.redis.client.Response;
import io.vertx.redis.client.RedisOptions;

public class RedisService {
  private final static String PROJECT_CACHE_KEY = "ashraful-project";
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final String connectionString;

  public RedisService() {
    this.connectionString = "redis://"
      + Vertx.currentContext().config().getString("redis-host") + ":"
      + Vertx.currentContext().config().getInteger("redis-port");
  }

  public Redis getRedisClient(Vertx vertx) {
    return Redis.createClient(vertx, new RedisOptions().addConnectionString(connectionString));
  }

  public RedisAPI getRedisApi(Redis redis) {
    return RedisAPI.api(redis);
  }


  public Maybe<Response> saveCache(Redis client, RedisAPI redisApi, String dataKey, String data) {
    return client
      .rxConnect()
      .flatMapMaybe(redisConnection -> redisApi.rxHsetnx(PROJECT_CACHE_KEY, dataKey, data))
      .flatMap(responseMaybe -> redisApi.rxHgetall(PROJECT_CACHE_KEY));
  }

}
