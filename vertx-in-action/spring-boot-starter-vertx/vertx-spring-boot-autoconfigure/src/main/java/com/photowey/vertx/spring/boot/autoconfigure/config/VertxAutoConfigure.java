/*
 * Copyright © 2022 photowey (photowey@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
