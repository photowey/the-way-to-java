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
package com.photowey.redisson.in.action.sync.config;

import com.photowey.redisson.in.action.sync.Lock;
import com.photowey.redisson.in.action.sync.redis.RedisLock;
import com.photowey.redisson.in.action.sync.redis.property.RedisLockProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * {@code RedissonLockAutoConfigure}
 *
 * @author photowey
 * @date 2023/02/01
 * @since 1.0.0
 */
@ConditionalOnClass(RedissonClient.class)
@AutoConfiguration(after = RedisAutoConfiguration.class)
@EnableConfigurationProperties(value = {RedisLockProperties.class})
public class RedissonLockAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(Lock.class)
    @ConditionalOnProperty(name = "spring.redis.lock.redisson.enabled", havingValue = "true", matchIfMissing = false)
    public Lock redisLock(RedisLockProperties properties) {
        RedissonClient redisson = this.populateRedissonClient(properties);
        return new RedisLock(redisson, properties);
    }

    private RedissonClient populateRedissonClient(RedisLockProperties properties) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(properties.getAddress())
                .setDatabase(properties.getDatabase())
                .setPassword(properties.getPassword())
                .setTimeout(properties.getTimeout());

        return Redisson.create(config);
    }
}
