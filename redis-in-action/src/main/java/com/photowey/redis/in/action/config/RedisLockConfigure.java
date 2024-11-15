/*
 * Copyright © 2021 the original author or authors.
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
package com.photowey.redis.in.action.config;

import com.photowey.redis.in.action.property.redis.RedisProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;

/**
 * {@code RedisLockConfigure}
 *
 * @author photowey
 * @date 2022/01/01
 * @since 1.0.0
 */
@Configuration
public class RedisLockConfigure {

    private static final String GLOBAL_LOCK_REGISTRY_KEY = "io:github:photowey:global:lock";

    @Bean
    public LockRegistry redisLockRegistry(RedisConnectionFactory connectionFactory) {
        return new RedisLockRegistry(connectionFactory, GLOBAL_LOCK_REGISTRY_KEY);
    }

    @Bean
    public RedissonClient redissonClient(RedisProperties properties) {
        Config config = new Config();

        config.useSingleServer()
            .setAddress(properties.populateAddress())
            .setDatabase(properties.getDatabase())
            .setPassword(properties.getPassword())
            .setTimeout(properties.getTimeout());

        return Redisson.create(config);
    }
}
