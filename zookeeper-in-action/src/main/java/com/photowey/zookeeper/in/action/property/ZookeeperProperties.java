package com.photowey.zookeeper.in.action.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code ZookeeperProperties}
 *
 * @author photowey
 * @date 2021/11/03
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "spring.zookeeper", ignoreUnknownFields = true)
public class ZookeeperProperties {

    private String host = "localhost";
    private Integer port = 2181;
    private String connectString = "localhost:2181";
    private Integer sessionTimeout = 6000;
    private Integer connectionTimeout = 6000;
    private Integer baseSleepTimeMs = 1000;
    private Integer maxRetries = 3;
}
