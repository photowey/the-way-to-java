package com.photowey.rocketmq.in.action.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code RocketmqProperties}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "spring.rocketmq", ignoreUnknownFields = true)
public class RocketmqProperties {

    private String host = "localhost";
    private Integer port = 9876;
    private String nameServer = String.format("%s:%s", this.getHost(), this.getPort());
}
