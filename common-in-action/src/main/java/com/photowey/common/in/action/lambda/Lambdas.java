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
package com.photowey.common.in.action.lambda;

import com.photowey.common.in.action.thrower.AssertionErrorThrower;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@code Lambdas}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/12/16
 */
public final class Lambdas {

    private Lambdas() {
        // utility class; can't create
        AssertionErrorThrower.throwz(Lambdas.class);
    }

    public static <T, D> List<D> toList(Collection<T> from, Function<T, D> mapper) {
        return from.stream()
            .map(mapper)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public static <T, D> List<D> toList(Collection<T> from, Predicate<T> predicate, Function<T, D> mapper) {
        return from.stream()
            .filter(predicate)
            .map(mapper)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public static <T, MIDDLE, D> List<D> toFlapList(Collection<T> from, Function<T, Stream<MIDDLE>> mapper, Function<MIDDLE, D> fx) {
        return from.stream()
            .flatMap(mapper)
            .map(fx)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public static <T, D> Set<D> toSet(Collection<T> from, Function<T, D> mapper) {
        return from.stream()
            .map(mapper)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
    }

    public static <T, D> Set<D> toSet(Collection<T> from, Predicate<T> predicate, Function<T, D> mapper) {
        return from.stream()
            .filter(predicate)
            .map(mapper)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
    }

    public static <T, MIDDLE, D> Set<D> toFlapSet(Collection<T> from, Function<T, Stream<MIDDLE>> mapper, Function<MIDDLE, D> fx) {
        return from.stream()
            .flatMap(mapper)
            .map(fx)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
    }

    public static <K, V> Map<K, V> toMap(Collection<V> from, Function<V, K> keyMapper) {
        return from.stream().collect(Collectors.toMap(keyMapper, Function.identity()));
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

    public static <T> List<T> filter(Collection<T> from, Predicate<T> predicate) {
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

    // ----------------------------------------------------------------

    public static <T> void nothing(T t) {}

    public static <T, U> void nothing(T t, U u) {}

    public static <T, U, V> void nothing(T t, U u, V v) {}

    public static <T, U, V, R> void nothing(T t, U u, V v, R r) {}

    public static <T, U, V, R, W> void nothing(T t, U u, V v, R r, W w) {}
}

