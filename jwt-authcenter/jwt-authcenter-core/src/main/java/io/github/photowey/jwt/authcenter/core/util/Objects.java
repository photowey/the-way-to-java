/*
 * Copyright © 2025 the original author or authors.
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
package io.github.photowey.jwt.authcenter.core.util;

import io.github.photowey.jwt.authcenter.core.thrower.AssertionErrorThrower;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * {@code Objects}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/14
 */
public final class Objects {

    private Objects() {
        // utility class; can't create
        AssertionErrorThrower.throwz(Objects.class);
    }

    // ----------------------------------------------------------------

    public static <T> boolean isNull(T target) {
        return java.util.Objects.isNull(target);
    }

    public static <T> boolean isNotNull(T target) {
        return !isNull(target);
    }

    // ----------------------------------------------------------------

    public static <T> T defaultIfNull(T target, T defaultValue) {
        return isNotNull(target) ? target : defaultValue;
    }

    public static <T> T defaultIfNull(T target, Supplier<T> fx) {
        if (isNotNull(target)) {
            return target;
        }

        return fx.get();
    }

    public static <T extends Collection<E>, E> T defaultIfEmpty(T targets, T defaultValues) {
        return Collections.isNotEmpty(targets) ? targets : defaultValues;
    }

    public static <T extends Collection<E>, E> T defaultIfEmpty(T targets, Supplier<T> fx) {
        if (Collections.isNotEmpty(targets)) {
            return targets;
        }

        return fx.get();
    }

    public static <T extends Map<K, V>, K, V> T defaultIfEmpty(T kvs, T defaultValues) {
        return Maps.isNotEmpty(kvs) ? kvs : defaultValues;
    }

    public static <T extends Map<K, V>, K, V> T defaultIfEmpty(T kvs, Supplier<T> fx) {
        if (Maps.isNotEmpty(kvs)) {
            return kvs;
        }

        return fx.get();
    }
}
