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
package io.github.photowey.redisson.delayed.queue.in.action.listener;

import io.github.photowey.redisson.delayed.queue.in.action.pattern.DelayedAntPathMatcher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * {@code AbstractAntDelayedQueueEventListener}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/11
 */
public abstract class AbstractAntDelayedQueueEventListener implements AntDelayedQueueEventListener {

    protected ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public ApplicationContext applicationContext() {
        return this.applicationContext;
    }

    @Override
    public DelayedAntPathMatcher matcher() {
        return this.applicationContext().getBean(DelayedAntPathMatcher.class);
    }
}
