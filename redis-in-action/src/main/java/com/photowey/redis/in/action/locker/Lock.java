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
package com.photowey.redis.in.action.locker;

/**
 * {@code Lock}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/15
 */
public interface Lock {

    boolean lock(String lockKey);

    boolean unlock(String lockKey);

    default <T extends Throwable, D> D throwUnchecked(T throwable) {
        if (null == throwable) {
            return null;
        }
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException) throwable;
        }

        throw new RuntimeException(throwable);
    }
}
