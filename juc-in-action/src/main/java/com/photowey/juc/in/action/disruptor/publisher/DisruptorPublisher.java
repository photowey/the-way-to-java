/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.juc.in.action.disruptor.publisher;

import com.photowey.juc.in.action.disruptor.subscriber.TaskSubscriber;

/**
 * {@code DisruptorPublisher}
 *
 * @author photowey
 * @date 2021/12/04
 * @since 1.0.0
 */
public interface DisruptorPublisher extends Publisher {

    /**
     * Register {@link TaskSubscriber}
     *
     * @param subscriber The task subscriber.
     * @param <DT>       The task-event data type.(DT == DATA TYPE)
     */
    default <DT> void registerSubscriber(final TaskSubscriber<DT> subscriber) {

    }

    default void close() {
    }
}
