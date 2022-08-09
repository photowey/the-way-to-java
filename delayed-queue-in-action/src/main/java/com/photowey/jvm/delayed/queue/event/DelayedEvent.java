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
package com.photowey.jvm.delayed.queue.event;

import java.util.concurrent.Delayed;
import java.util.function.Function;

/**
 * {@code DelayedEvent}
 *
 * @author photowey
 * @date 2022/08/07
 * @since 1.0.0
 */
public interface DelayedEvent<D> extends Delayed {

    String getEventId();

    String getEventType();

    Long getRunAt();

    Object getData();

    D transfer(Function<Object, D> function);

    default D transferd() {
        return (D) this.getData();
    }

}
