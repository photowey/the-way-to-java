/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.juc.in.action.disruptor.task.factory;

import com.photowey.juc.in.action.disruptor.subscriber.TaskSubscriber;

import java.util.HashSet;
import java.util.Set;

/**
 * {@code AbstractTaskFactory}
 *
 * @author photowey
 * @date 2021/12/04
 * @since 1.0.0
 */
public abstract class AbstractTaskFactory<T> implements TaskFactory<T> {

    private final Set<TaskSubscriber<T>> subscribers = new HashSet<>();

    @Override
    public AbstractTaskFactory<T> registerSubscriber(final TaskSubscriber<T> subscriber) {
        this.subscribers.add(subscriber);
        return this;
    }

    public Set<TaskSubscriber<T>> getSubscribers() {
        return this.subscribers;
    }
}
