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

import com.photowey.commom.in.action.thrower.AssertionErrorThrower;

import java.util.*;

/**
 * {@code CollectionUtils}
 *
 * @author photowey
 * @date 2022/11/23
 * @since 1.0.0
 */
public final class CollectionUtils {

    private CollectionUtils() {
        // utility class; can't create
        AssertionErrorThrower.throwz(CollectionUtils.class);
    }

    public static <T> Set<T> asSet(T... ts) {
        return new HashSet<>(Arrays.asList(ts));
    }

    public static <T> Set<T> newHashSet() {
        return new HashSet<>();
    }

    public static <T> Set<T> newHashSet(int size) {
        return new HashSet<>(calculateInitialCapacityFromExpectedSize(size));
    }

    public static <T> Set<T> newHashSet(Collection<? extends T> collection) {
        return new HashSet<>(collection);
    }

    public static <T> List<T> asList(T... ts) {
        return new ArrayList<>(Arrays.asList(ts));
    }

    public static <T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    public static <T> List<T> toImmutableList(List<? extends T> list) {
        switch (list.size()) {
            case 0:
                return Collections.emptyList();
            case 1:
                return Collections.singletonList(list.get(0));
            default:
                return Collections.unmodifiableList(list);
        }
    }

    public static <T> Set<T> toImmutableSet(Set<? extends T> set) {
        switch (set.size()) {
            case 0:
                return Collections.emptySet();
            case 1:
                return Collections.singleton(set.iterator().next());
            default:
                return Collections.unmodifiableSet(set);
        }
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return org.apache.commons.collections4.CollectionUtils.isEmpty(collection);
    }

    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return org.apache.commons.collections4.CollectionUtils.isNotEmpty(collection);
    }

    private static int calculateInitialCapacityFromExpectedSize(int expectedSize) {
        return expectedSize < 3 ? expectedSize + 1 : (int) ((float) expectedSize / 0.75F + 1.0F);
    }
}