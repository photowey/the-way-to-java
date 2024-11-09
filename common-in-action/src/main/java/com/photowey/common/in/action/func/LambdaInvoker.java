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
package com.photowey.common.in.action.func;

import com.photowey.common.in.action.formatter.StringFormatter;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * {@code LambdaInvoker}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
public interface LambdaInvoker {

    default Logger log() {
        return null;
    }

    // ----------------------------------------------------------------

    default void run(Runnable task) {
        this.run(task, null);
    }

    default void run(Runnable task, String message, Object... args) {
        try {
            task.run();
        } catch (Throwable e) {
            if (Objects.nonNull(this.log()) && this.isNotBlank(message)) {
                this.log().error(StringFormatter.format(message, args), e);
            }
            throw new RuntimeException(e);
        }
    }

    // ----------------------------------------------------------------

    default <T> T call(Callable<T> task) {
        return this.call(task, null);
    }

    default <T> T call(Callable<T> task, String message, Object... args) {
        try {
            return task.call();
        } catch (Throwable e) {
            if (Objects.nonNull(this.log()) && this.isNotBlank(message)) {
                this.log().error(StringFormatter.format(message, args), e);
            }
            throw new RuntimeException(e);
        }
    }

    default boolean isNotBlank(String message) {
        return Objects.nonNull(message) && !message.trim().isEmpty();
    }
}
