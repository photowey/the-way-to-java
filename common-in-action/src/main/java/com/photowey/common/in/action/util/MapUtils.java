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
package com.photowey.common.in.action.util;

import com.photowey.common.in.action.thrower.AssertionErrorThrower;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.objenesis.instantiator.util.ClassUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * {@code MapUtils}
 *
 * @author photowey
 * @date 2022/11/23
 * @since 1.0.0
 */
public final class MapUtils {

    private MapUtils() {
        // utility class; can't create
        AssertionErrorThrower.throwz(MapUtils.class);
    }

    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap<>();
    }

    public static <K, V> Map<K, V> newHashMap(int size) {
        return new HashMap<>(calculateInitialCapacityFromExpectedSize(size));
    }

    public static <K, V> Map<K, V> newHashMap(Map<K, V> map) {
        return new HashMap<>(map);
    }

    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap<>();
    }

    public static <K, V> Map<K, V> toImmutableMap(Map<K, V> map) {
        switch (map.size()) {
            case 0:
                return Collections.emptyMap();
            case 1:
                Map.Entry<K, V> entry = map.entrySet().iterator().next();
                return Collections.singletonMap(entry.getKey(), entry.getValue());
            default:
                return Collections.unmodifiableMap(map);
        }
    }

    public static <K, T, V> Map<K, V> toMap(T bean) {
        return null == bean ? null : (Map<K, V>) BeanMap.create(bean);
    }

    public static <K, T, V> T toBean(Map<K, V> map, Class<T> clazz) {
        T bean = ClassUtils.newInstance(clazz);
        BeanMap.create(bean).putAll(map);
        return bean;
    }

    public static <K, T, V> List<Map<K, V>> toMaps(List<T> beans) {
        return CollectionUtils.isEmpty(beans)
                ? Collections.emptyList()
                : beans.stream().map(bean -> (Map<K, V>) toMap(bean)).collect(Collectors.toList());
    }

    public static <K, T, V> List<T> toBeans(List<Map<K, V>> maps, Class<T> clazz) {
        return CollectionUtils.isEmpty(maps)
                ? Collections.emptyList()
                : maps.stream().map(map -> toBean(map, clazz)).collect(Collectors.toList());
    }

    public static <T, K> Map<K, T> toMap(Collection<T> from, Function<T, K> keyMapper) {
        if (CollectionUtils.isEmpty(from)) {
            return newHashMap();
        }
        return toMap(from, keyMapper, Function.identity());
    }

    public static <T, K> Map<K, T> toMap(
            Collection<T> from, Function<T, K> keyMapper, Supplier<? extends Map<K, T>> supplier) {
        if (CollectionUtils.isEmpty(from)) {
            return supplier.get();
        }
        return toMap(from, keyMapper, Function.identity(), supplier);
    }

    public static <T, K, V> Map<K, V> toMap(
            Collection<T> from, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        if (CollectionUtils.isEmpty(from)) {
            return newHashMap();
        }
        return toMap(from, keyMapper, valueMapper, (v1, v2) -> v1);
    }

    public static <T, K, V> Map<K, V> toMap(
            Collection<T> from,
            Function<T, K> keyMapper,
            Function<T, V> valueMapper,
            BinaryOperator<V> mergeFunction) {
        if (CollectionUtils.isEmpty(from)) {
            return newHashMap();
        }
        return toMap(from, keyMapper, valueMapper, mergeFunction, HashMap::new);
    }

    public static <T, K, V> Map<K, V> toMap(
            Collection<T> from,
            Function<T, K> keyMapper,
            Function<T, V> valueMapper,
            Supplier<? extends Map<K, V>> supplier) {
        if (CollectionUtils.isEmpty(from)) {
            return supplier.get();
        }
        return toMap(from, keyMapper, valueMapper, (v1, v2) -> v1, supplier);
    }

    public static <T, K, V> Map<K, V> toMap(
            Collection<T> from,
            Function<T, K> keyMapper,
            Function<T, V> valueMapper,
            BinaryOperator<V> mergeFunction,
            Supplier<? extends Map<K, V>> supplier) {
        if (CollectionUtils.isEmpty(from)) {
            return newHashMap();
        }
        return from.stream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction, supplier));
    }

    public static <T, K> Map<K, List<T>> toMultiMap(Collection<T> from, Function<T, K> keyMapper) {
        if (CollectionUtils.isEmpty(from)) {
            return newHashMap();
        }
        return from.stream().collect(Collectors.groupingBy(keyMapper, Collectors.mapping(mapper -> mapper, Collectors.toList())));
    }

    public static <T, K, V> Map<K, List<V>> toMultiMap(Collection<T> from, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        if (CollectionUtils.isEmpty(from)) {
            return newHashMap();
        }
        return from.stream().collect(Collectors.groupingBy(keyMapper, Collectors.mapping(valueMapper, Collectors.toList())));
    }

    private static int calculateInitialCapacityFromExpectedSize(int expectedSize) {
        return expectedSize < 3 ? expectedSize + 1 : (int) ((float) expectedSize / 0.75F + 1.0F);
    }


}