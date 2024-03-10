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
package io.github.photowey.redisson.delayed.queue.in.action.manager;

import io.github.photowey.redisson.delayed.queue.in.action.core.pair.QueuePair;
import io.github.photowey.redisson.delayed.queue.in.action.core.task.RedissonDelayedTask;
import io.github.photowey.redisson.delayed.queue.in.action.property.RedissonClientProperties;
import io.github.photowey.redisson.delayed.queue.in.action.queue.DelayedQueue;
import io.github.photowey.redisson.delayed.queue.in.action.scheduler.RedissonDelayedQueueScheduler;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code DefaultRedissonDelayedQueueManager}
 *
 * @author photowey
 * @date 2024/03/03
 * @since 1.0.0
 */
public class DefaultRedissonDelayedQueueManager implements RedissonDelayedQueueManager {

    private final ConcurrentHashMap<String, DelayedQueue> ctx = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, QueuePair> pairs = new ConcurrentHashMap<>();

    private final RedissonClient redisson;

    private ConfigurableListableBeanFactory beanFactory;

    public DefaultRedissonDelayedQueueManager(RedissonClient redisson) {
        this.redisson = redisson;
    }

    // ----------------------------------------------------------------

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    // ----------------------------------------------------------------

    @Override
    public void afterSingletonsInstantiated() {
        this.init();
        // TODO
    }

    // ----------------------------------------------------------------

    @Override
    public RedissonClientProperties redissonProperties() {
        return this.beanFactory.getBean(RedissonClientProperties.class);
    }

    // ----------------------------------------------------------------

    @Override
    public RedissonClient redisson() {
        return this.redisson;
    }

    // ----------------------------------------------------------------

    public void init() {
        String topic = this.redissonProperties().getDelayed().getTopic();
        RBlockingDeque<RedissonDelayedTask<?>> blockingQueue = this.redisson().getBlockingDeque(topic);
        RDelayedQueue<RedissonDelayedTask<?>> delayedQueue = this.redisson().getDelayedQueue(blockingQueue);

        QueuePair pair = QueuePair.builder()
                .topic(topic)
                .blockingQueue(blockingQueue)
                .delayedQueue(delayedQueue)
                .build();

        this.registerPair(pair);
    }

    @Override
    public DelayedQueue register(DelayedQueue delayedQueue) {
        String topic = delayedQueue.topic();
        DelayedQueue image = ctx.get(topic);
        if (null == image) {
            return ctx.computeIfAbsent(topic, (x) -> delayedQueue);
        }

        return image;
    }

    @Override
    public DelayedQueue tryAcquire(String topic) {
        return this.ctx.get(topic);
    }

    @Override
    public boolean contains(String topic) {
        return this.ctx.containsKey(topic);
    }

    @Override
    public QueuePair registerPair(QueuePair pair) {
        String topic = pair.topic();
        QueuePair image = this.pairs.get(topic);
        if (null == image) {
            return this.pairs.computeIfAbsent(topic, (x) -> pair);
        }

        return image;
    }

    @Override
    public QueuePair tryAcquirePair(String topic) {
        return this.pairs.get(topic);
    }

    @Override
    public RedissonDelayedQueueScheduler redissonScheduler() {
        return this.beanFactory.getBean(RedissonDelayedQueueScheduler.class);
    }
}
