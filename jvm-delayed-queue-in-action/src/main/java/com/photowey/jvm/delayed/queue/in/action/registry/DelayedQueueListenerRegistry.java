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
package com.photowey.jvm.delayed.queue.in.action.registry;

import com.photowey.jvm.delayed.queue.in.action.event.DelayedEvent;
import com.photowey.jvm.delayed.queue.in.action.listener.DelayedQueueListener;
import org.springframework.beans.factory.DisposableBean;

import java.util.List;

/**
 * {@code DelayedQueueListenerRegistry}
 *
 * @author photowey
 * @date 2023/01/14
 * @since 1.0.0
 */
public interface DelayedQueueListenerRegistry extends DisposableBean {

    void registerListener(DelayedQueueListener<DelayedEvent> delayedListener);

    List<DelayedQueueListener<DelayedEvent>> getDelayedQueueListeners(DelayedEvent delayedEvent);
}
