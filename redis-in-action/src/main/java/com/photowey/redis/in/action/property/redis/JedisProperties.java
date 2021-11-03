package com.photowey.redis.in.action.property.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * {@code RedisProperties}
 *
 * @author photowey
 * @date 2021/10/26
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "spring.redis.jedis.pool", ignoreUnknownFields = true)
public class JedisProperties implements Serializable {

    private static final long serialVersionUID = 4515226416928528689L;
    
    private Integer maxTotal = 8;
    private Integer maxIdle = 8;
    private Long maxWait = -1L;
    private Integer minIdle = 0;
}
