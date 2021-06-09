/*
 * @Author Md Ashraful Alam
 * @Time 6/10/21, 1:22 AM
 */

package ashraful.rnd.vertx_redis.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.redis.client.Response;

public abstract class AbstractHandler {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final ObjectMapper objectMapper;

  public AbstractHandler() {
    this.objectMapper = new ObjectMapper();
  }

  protected <T> T getRequest(RoutingContext routingContext, Class<T> tClass) {
    return routingContext.getBodyAsJson().mapTo(tClass);
  }

  protected <T> void prepareResponse(RoutingContext routingContext, T t) throws JsonProcessingException {
    routingContext.response().putHeader("content-type", "application/json");
    routingContext.response().end(objectMapper.writeValueAsString(t));
  }

}
