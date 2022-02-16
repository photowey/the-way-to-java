package com.photowey.vertx.spring.boot.autoconfigure.config;

import com.photowey.vertx.spring.boot.autoconfigure.deploy.VerticleDeployer;
import com.photowey.vertx.spring.boot.autoconfigure.property.VertxProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code VertxAutoConfigure}
 *
 * @author photowey
 * @date 2022/02/17
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(VertxProperties.class)
public class VertxAutoConfigure {

    @Bean
    @ConditionalOnMissingBean
    public VerticleDeployer verticleDeployer() {
        return new VerticleDeployer();
    }
}
