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
package com.photowey.commom.in.action.util;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@code LambdaUtils}
 *
 * @author photowey
 * @date 2021/11/15
 * @since 1.0.0
 */
public final class LambdaUtils {

    private LambdaUtils() {
        // utility class; can't create
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static <T, D> List<D> toList(
            Collection<T> from,
            Function<T, D> mapper) {
        return from.stream()
                .map(mapper)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static <T, D> List<D> toList(
            Collection<T> from,
            Predicate<T> predicate,
            Function<T, D> mapper) {
        return from.stream()
                .filter(predicate)
                .map(mapper)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static <T, MIDDLE, D> List<D> toListm(
            Collection<T> from,
            Function<T, Stream<MIDDLE>> mapper,
            Function<MIDDLE, D> fx) {
        return from.stream()
                .flatMap(mapper)
                .map(fx)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static <T, D> Set<D> toSet(
            Collection<T> from,
            Function<T, D> mapper) {
        return from.stream()
                .map(mapper)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public static <T, D> Set<D> toSet(
            Collection<T> from,
            Predicate<T> predicate,
            Function<T, D> mapper) {
        return from.stream()
                .filter(predicate)
                .map(mapper)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public static <T, MIDDLE, D> Set<D> toSetm(
            Collection<T> from,
            Function<T, Stream<MIDDLE>> mapper,
            Function<MIDDLE, D> fx) {
        return from.stream()
                .flatMap(mapper)
                .map(fx)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public static <K, V> Map<K, V> toMap(
            Collection<V> from,
            Function<V, K> keyMapper) {
        return from.stream()
                .collect(Collectors.toMap(keyMapper, Function.identity()));
    }

    public static <K, T extends Collection<V>, V> List<V> flapMapList(Collection<K> from, Function<K, T> tMapper) {
        return flapMapList(from, tMapper, Collection::stream);
    }

    public static <K, T extends Collection<V>, V> Set<V> flapMapSet(Collection<K> from, Function<K, T> tMapper) {
        return flapMapSet(from, tMapper, Collection::stream);
    }

    public static <K, T, V> List<V> flapMapList(Collection<K> from, Function<K, T> tMapper, Function<T, Stream<V>> vMapper) {
        return from.stream()
                .map(tMapper)
                .flatMap(vMapper)
                .collect(Collectors.toList());
    }

    public static <K, T, V> Set<V> flapMapSet(Collection<K> from, Function<K, T> tMapper, Function<T, Stream<V>> vMapper) {
        return from.stream()
                .map(tMapper)
                .flatMap(vMapper)
                .collect(Collectors.toSet());
    }

    public static <T> List<T> filter(
            Collection<T> from,
            Predicate<T> predicate) {
        return from.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public static <T> T findAny(Collection<T> from, Predicate<T> predicate) {
        return from.stream()
                .filter(predicate)
                .findAny()
                .orElse(null);
    }

    public static <T, K> Map<K, List<T>> groupingBy(Collection<T> from, Function<T, K> function) {
        return from.stream().collect(Collectors.groupingBy(function));
    }

    public static <T> BigDecimal reduce(Collection<T> from, Function<T, BigDecimal> function) {
        return from.stream()
                .map(function)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static <T> long count(Collection<T> from, Predicate<T> predicate) {
        return from.stream().filter(predicate).count();
    }

    public static <T> boolean exists(Collection<T> from, Predicate<T> predicate) {
        return from.stream()
                .filter(predicate)
                .limit(1)
                .count() > 0;
    }

    public static <T> boolean test(T from, Predicate<T> predicate) {
        return predicate.test(from);
    }

    public static <T> List<T> copy(Collection<T> candidates) {
        return new ArrayList<>(candidates);
    }

}