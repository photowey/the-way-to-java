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
package com.photowey.jvm.delayed.queue.in.action.registry;

import com.photowey.jvm.delayed.queue.in.action.event.DelayedEvent;
import com.photowey.jvm.delayed.queue.in.action.listener.DelayedQueueListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code DefaultDelayedQueueListenerRegistry}
 *
 * @author weichangjun
 * @date 2023/01/14
 * @since 1.0.0
 */
public class DefaultDelayedQueueListenerRegistry implements DelayedQueueListenerRegistry {

    private static ConcurrentHashMap<Class<DelayedEvent>, List<DelayedQueueListener<DelayedEvent>>> LISTENER_MAP = new ConcurrentHashMap<>();

    public void registerListener(DelayedQueueListener<DelayedEvent> delayedListener) {
        List<DelayedQueueListener<DelayedEvent>> delayedQueueListeners = LISTENER_MAP.get(delayedListener.getEvent());
        if (null == delayedQueueListeners) {
            synchronized (LISTENER_MAP) {
                delayedQueueListeners = LISTENER_MAP.get(delayedListener.getEvent());
                if (null == delayedQueueListeners) {
                    delayedQueueListeners = new ArrayList<>();
                    delayedQueueListeners.add(delayedListener);
                    LISTENER_MAP.put(delayedListener.getEvent(), delayedQueueListeners);
                }
            }
        } else {
            delayedQueueListeners.add(delayedListener);
        }
    }

    public List<DelayedQueueListener<DelayedEvent>> getDelayedQueueListeners(DelayedEvent delayedEvent) {
        return LISTENER_MAP.get(delayedEvent.getClass());
    }

    @Override
    public void destroy() throws Exception {
        LISTENER_MAP.clear();
        LISTENER_MAP = null;
    }
}
