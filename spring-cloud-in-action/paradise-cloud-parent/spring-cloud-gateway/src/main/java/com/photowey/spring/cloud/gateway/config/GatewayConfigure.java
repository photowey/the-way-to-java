package com.photowey.spring.cloud.gateway.config;

import com.photowey.spring.cloud.gateway.property.OAuth2GatewayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * {@code GatewayConfigure}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(OAuth2GatewayProperties.class)
public class GatewayConfigure {
}
