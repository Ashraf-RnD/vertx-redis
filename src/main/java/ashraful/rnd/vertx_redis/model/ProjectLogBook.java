/*
 * @Author Md Ashraful Alam
 * @Time 6/10/21, 1:15 AM
 */

package ashraful.rnd.vertx_redis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProjectLogBook {
  private String projectName;
  private String projectType;
  private String currentStatus;
  private String lapDuration;
  private String estimatedCost;
  private String riskType;

}
