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
package io.github.photowey.redisson.delayed.queue.in.action.executor;

import io.github.photowey.redisson.delayed.queue.in.action.core.task.RedissonDelayedTask;
import io.github.photowey.redisson.delayed.queue.in.action.event.RedissonDelayedTaskEvent;
import io.github.photowey.redisson.delayed.queue.in.action.getter.ApplicationContextGetter;
import io.github.photowey.redisson.delayed.queue.in.action.manager.RedissonDelayedQueueManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;

/**
 * {@code CompositeRedissonDelayedQueueExecutor}
 *
 * @author photowey
 * @date 2024/03/12
 * @since 1.0.0
 */
@Slf4j
public class CompositeRedissonDelayedQueueExecutor implements RedissonDelayedQueueExecutor, ApplicationContextGetter, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public ApplicationContext applicationContext() {
        return this.applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public <P extends Serializable> void execute(RedissonDelayedTask<P> task) {
        //this.applicationContext.publishEvent(new RedissonDelayedTaskEvent(task));
        this.manager().redissonEventListener().onEvent(new RedissonDelayedTaskEvent(task));
    }

    public RedissonDelayedQueueManager manager() {
        return this.applicationContext.getBean(RedissonDelayedQueueManager.class);
    }
}