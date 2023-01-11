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
package com.photowey.disruptor.in.action.model;

import java.util.function.Function;

/**
 * {@code Event}
 *
 * @author photowey
 * @date 2023/01/09
 * @since 1.0.0
 */
public interface Event {

    void setTopic(String topic);

    /**
     * Topic
     *
     * @return the topic of event
     */
    default String getTopic() {
        return "defaults";
    }

    void setMessage(Object message);

    /**
     * Body
     *
     * @return {@code Object} message
     */
    Object getMessage();

    /**
     * Convert {@code Object} message to {@code T}
     *
     * @param fx  the converter fx
     * @param <T> T type
     * @return T
     */
    default <T> T convert(Function<Object, T> fx) {
        return this.convert(this.getMessage(), fx);
    }

    /**
     * Convert {@code Object} message to {@code T}
     *
     * @param message the {@code Object} message
     * @param fx      the converter fx
     * @param <T>     T type
     * @return T
     */
    default <T> T convert(Object message, Function<Object, T> fx) {
        return fx.apply(message);
    }
}
