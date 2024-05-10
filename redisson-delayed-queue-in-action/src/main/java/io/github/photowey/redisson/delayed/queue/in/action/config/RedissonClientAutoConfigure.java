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
package io.github.photowey.redisson.delayed.queue.in.action.config;

import io.github.photowey.redisson.delayed.queue.in.action.executor.CompositeRedissonDelayedQueueExecutor;
import io.github.photowey.redisson.delayed.queue.in.action.executor.RedissonDelayedQueueExecutor;
import io.github.photowey.redisson.delayed.queue.in.action.listener.CompositeRedissonDelayedQueueEventListener;
import io.github.photowey.redisson.delayed.queue.in.action.listener.RedissonDelayedQueueBeanPostProcessor;
import io.github.photowey.redisson.delayed.queue.in.action.manager.DefaultRedissonDelayedQueueManager;
import io.github.photowey.redisson.delayed.queue.in.action.manager.RedissonDelayedQueueManager;
import io.github.photowey.redisson.delayed.queue.in.action.property.RedissonClientProperties;
import io.github.photowey.redisson.delayed.queue.in.action.queue.CompositeRedissonDelayedQueue;
import io.github.photowey.redisson.delayed.queue.in.action.queue.RedissonDelayedQueue;
import io.github.photowey.redisson.delayed.queue.in.action.scheduler.CompositeRedissonDelayedQueueScheduler;
import io.github.photowey.redisson.delayed.queue.in.action.scheduler.RedissonDelayedQueueScheduler;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 * {@code RedissonClientAutoConfigure}
 *
 * @author photowey
 * @date 2024/03/01
 * @since 1.0.0
 */
@ConditionalOnClass(RedissonClient.class)
@AutoConfiguration(before = RedisAutoConfiguration.class)
@Import(value = {
        RedissonDelayedQueueBeanPostProcessor.class,
})
public class RedissonClientAutoConfigure {

    @Bean
    public RedissonClientProperties redissonProperties(Environment environment) {
        return bind(environment, RedissonClientProperties.getPrefix(), RedissonClientProperties.class);
    }

    @Bean
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redisson(RedissonClientProperties redissonProperties) {
        return this.populateRedissonClient(redissonProperties);
    }

    @Bean
    public RedissonDelayedQueueManager redissonManager(RedissonClient redisson) {
        return new DefaultRedissonDelayedQueueManager(redisson);
    }

    @Bean
    @ConditionalOnMissingBean(RedissonDelayedQueueExecutor.class)
    public RedissonDelayedQueueExecutor redissonExecutor() {
        return new CompositeRedissonDelayedQueueExecutor();
    }

    @Bean
    @ConditionalOnMissingBean(RedissonDelayedQueue.class)
    public RedissonDelayedQueue redissonDelayedQueue(RedissonDelayedQueueManager manager) {
        return new CompositeRedissonDelayedQueue(manager);
    }

    @Bean
    @ConditionalOnMissingBean(RedissonDelayedQueueScheduler.class)
    public RedissonDelayedQueueScheduler redissonScheduler(RedissonDelayedQueueManager manager, RedissonDelayedQueueExecutor executor) {
        return new CompositeRedissonDelayedQueueScheduler(manager, executor);
    }

    @Bean
    public CompositeRedissonDelayedQueueEventListener compositeRedissonDelayedQueueEventListener() {
        return new CompositeRedissonDelayedQueueEventListener();
    }

    public static <T> T bind(Environment environment, String prefix, Class<T> clazz) {
        Binder binder = Binder.get(environment);

        return binder.bind(prefix, clazz).get();
    }

    private RedissonClient populateRedissonClient(RedissonClientProperties properties) {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer()
                .setAddress(properties.getAddress())
                .setDatabase(properties.getDatabase())
                .setTimeout(properties.getTimeout());

        if (StringUtils.hasLength(properties.getPassword())) {
            singleServerConfig.setPassword(properties.getPassword());
        }

        return Redisson.create(config);
    }
}