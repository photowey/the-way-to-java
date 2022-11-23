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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public static <T, D> List<D> toList(Collection<T> resources, Function<T, D> function) {
        return resources.stream().map(function::apply).collect(Collectors.toList());
    }

    public static <T, D> Set<D> toSet(Collection<T> resources, Function<T, D> function) {
        return resources.stream().map(function::apply).collect(Collectors.toSet());
    }

    public static <K, V> Map<K, V> toMap(Collection<V> from, Function<V, K> keyMapper) {
        return from.stream().collect(Collectors.toMap(keyMapper, Function.identity()));
    }

    public static <T> List<T> filter(Collection<T> resources, Predicate<? super T> predicate) {
        return resources.stream().filter(predicate::test).collect(Collectors.toList());
    }

    public static <T> T filterAny(Collection<T> resources, Predicate<T> predicate) {
        return resources.stream().filter(predicate::test).findAny().orElse(null);
    }

    public static <T, K> Map<K, List<T>> groupingBy(Collection<T> resources, Function<T, K> function) {
        return resources.stream().collect(Collectors.groupingBy(function));
    }

    public static <T> BigDecimal reduce(Collection<T> resources, Function<T, BigDecimal> function) {
        return resources.stream().map(function).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static <T> long count(Collection<T> from, Predicate<T> predicate) {
        return from.stream().filter(predicate).count();
    }

    public static <T> boolean exists(Collection<T> from, Predicate<T> predicate) {
        return from.stream().filter(predicate).limit(1).count() > 0;
    }


    public static <T> boolean test(T resource, Predicate<T> predicate) {
        return predicate.test(resource);
    }

    public static <T> List<T> copy(Collection<T> candidates) {
        List<T> copy = candidates.stream().map(candidate -> candidate).collect(Collectors.toList());
        return copy;
    }
}
