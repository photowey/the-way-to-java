package com.photowey.rocketmq.in.action.config;

import com.photowey.rocketmq.in.action.property.RocketmqProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * {@code RocketmqConfigure}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(value = {RocketmqProperties.class})
public class RocketmqConfigure {
}
