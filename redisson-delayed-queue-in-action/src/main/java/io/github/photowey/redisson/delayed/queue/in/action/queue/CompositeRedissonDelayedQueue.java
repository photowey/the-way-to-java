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
package io.github.photowey.redisson.delayed.queue.in.action.queue;

import io.github.photowey.redisson.delayed.queue.in.action.core.pair.QueuePair;
import io.github.photowey.redisson.delayed.queue.in.action.core.task.RedissonDelayedTask;
import io.github.photowey.redisson.delayed.queue.in.action.manager.RedissonDelayedQueueManager;
import io.github.photowey.redisson.delayed.queue.in.action.property.RedissonProperties;
import org.redisson.api.RDelayedQueue;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * {@code CompositeRedissonDelayedQueue}
 *
 * @author photowey
 * @date 2024/03/12
 * @since 1.0.0
 */
public class CompositeRedissonDelayedQueue implements RedissonDelayedQueue {

    private final RedissonDelayedQueueManager manager;
    private ListableBeanFactory beanFactory;

    public CompositeRedissonDelayedQueue(RedissonDelayedQueueManager manager) {
        this.manager = manager;

        this.manager.register(this);
    }

    // ----------------------------------------------------------------

    @Override
    public RedissonDelayedQueueManager manager() {
        return this.manager;
    }

    // ----------------------------------------------------------------

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    public RedissonProperties redissonProperties() {
        return this.manager.redissonProperties();
    }

    // ----------------------------------------------------------------

    @Override
    public String topic() {
        return this.redissonProperties().getDelayed().getTopic();
    }

    @Override
    public void registerTask(String taskId) {
        String taskSet = this.redissonProperties().delayed().registry().taskSet();
        this.redisson().getSetCache(taskSet).add(taskId);
    }

    // ----------------------------------------------------------------

    @Override
    public <P extends Serializable> void offer(RedissonDelayedTask<P> task) {
        RDelayedQueue<RedissonDelayedTask<?>> delayedQueue = this.determineDelayedQueue(task);
        delayedQueue.offer(task, task.delayed(), task.determineTimeUnit());

        this.registerTask(task.taskId());
    }

    // ----------------------------------------------------------------

    private <P extends Serializable> RDelayedQueue<RedissonDelayedTask<?>> determineDelayedQueue(RedissonDelayedTask<P> task) {
        long delayMax = this.redissonProperties().delayed().max();
        task.checkDelayMillis(delayMax);

        QueuePair pair = this.determinePair(task);

        return pair.delayedQueue();
    }

    private <P extends Serializable> QueuePair determinePair(RedissonDelayedTask<P> task) {
        String topic = StringUtils.hasText(task.topic()) ? task.topic() : this.topic();
        task.topic(topic);
        if (ObjectUtils.isEmpty(topic)) {
            throw new RuntimeException("Unknown topic:" + topic);
        }

        QueuePair pair = this.manager().tryAcquirePair(topic);
        if (ObjectUtils.isEmpty(pair)) {
            throw new RuntimeException("The topic:[" + topic + "] is not registered.");
        }
        return pair;
    }
}