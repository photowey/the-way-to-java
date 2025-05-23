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

import java.util.*;

/**
 * {@code Collections}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/01/28
 */
public final class Collections {

    private Collections() {
        // utility class; can't create
        AssertionErrorThrower.throwz(Collections.class);
    }

    // ----------------------------------------------------------------

    public static <T> boolean isEmpty(Collection<T> collection) {
        return null == collection || collection.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return Maps.isEmpty(map);
    }

    public static <K, V> boolean isNotEmpty(Map<K, V> collection) {
        return !isEmpty(collection);
    }

    // ----------------------------------------------------------------

    public static <T> List<T> emptyList() {
        return new ArrayList<>(0);
    }

    public static <T> Set<T> emptySet() {
        return new HashSet<>(0);
    }

    public static <K, V> Map<K, V> emptyMap() {
        return Maps.emptyMap();
    }
}
