package com.photowey.spring.cloud.gateway.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * {@code OAuth2GatewayProperties}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "spring.cloud.oauth2")
public class OAuth2GatewayProperties {

    private List<String> ignoreUrls;
}
