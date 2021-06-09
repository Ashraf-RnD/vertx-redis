/*
 * @Author Md Ashraful Alam
 * @Time 6/10/21, 1:15 AM
 */

package ashraful.rnd.vertx_redis.handlers;

import ashraful.rnd.vertx_redis.model.ProjectLogBook;
import ashraful.rnd.vertx_redis.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Handler;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.redis.client.Response;

public class RedisHandler extends AbstractHandler implements Handler<RoutingContext> {

  private final static String PROJECT_CACHE_KEY = "ashraful-project";
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final RedisService redisService;

  public RedisHandler() {
    this.redisService = new RedisService();

  }

  @Override
  public void handle(RoutingContext routingContext) {
    var request = getRequest(routingContext, ProjectLogBook.class);
    logger.info("handle:: ProjectLogBookRequest:: "+request.toString());

    var redisClient = redisService.getRedisClient(routingContext.vertx());
    var redisApi = redisService.getRedisApi(redisClient);

    redisService.saveCache(redisClient,redisApi,request.getProjectName(),routingContext.getBodyAsJson().encode())
      .subscribe(response -> {
        Response project = response.get(request.getProjectName());
        JsonObject jsonObject = new JsonObject(project.toString());
        var projectLogBook = jsonObject.mapTo(ProjectLogBook.class);
        logger.info("projectLogBook :: " + projectLogBook);

        prepareResponse(routingContext, projectLogBook);
      },routingContext::fail);


  }




}
