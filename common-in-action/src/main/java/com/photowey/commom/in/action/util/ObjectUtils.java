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

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * {@code ObjectUtils}
 *
 * @author photowey
 * @date 2021/11/15
 * @since 1.0.0
 */
public final class ObjectUtils {

    private ObjectUtils() {
        // utility class; can't create
        AssertionErrorThrower.throwz(ObjectUtils.class);
    }

    public static <T> T defaultIfNull(T target, T defaultValue) {
        return defaultIfNullOrEmpty(target, defaultValue);
    }

    public static <T> T defaultIfNull(T target, Supplier<T> fx) {
        return defaultIfNullOrEmpty(target, fx);
    }

    public static <T> T defaultIfNullOrEmpty(final T object, final T defaultValue) {
        return isNotNullOrEmpty(object) ? object : defaultValue;
    }

    public static <T> T defaultIfNullOrEmpty(final T target, Supplier<T> fx) {
        return isNotNullOrEmpty(target) ? target : fx.get();
    }

    public static boolean isNullOrEmpty(Object value) {
        if (null == value) {
            return true;
        }

        if (value instanceof CharSequence) {
            return isBlank((CharSequence) value);
        }

        if (isCollectionsSupportType(value)) {
            return sizeIsEmpty(value);
        }

        return false;
    }

    public static boolean isNotNullOrEmpty(Object value) {
        return !isNullOrEmpty(value);
    }

    // =================================================================

    /**
     * 当
     * 为空时执行回调函数
     *
     * @param target 目标对象
     * @param fx     回调函数
     * @param <T>    T 类型
     */
    public static <T> void executeEmpty(T target, Consumer<T> fx) {
        if (isNullOrEmpty(target)) {
            fx.accept(target);
        }
    }

    /**
     * 当
     * 不为空时执行回调函数
     *
     * @param target 目标对象
     * @param fx     回调函数
     * @param <T>    T 类型
     */
    public static <T> void executeNotEmpty(T target, Consumer<T> fx) {
        if (isNotNullOrEmpty(target)) {
            fx.accept(target);
        }
    }

    // =================================================================

    public static <T> boolean equals(final T t1, final T t2) {
        return Objects.equals(t1, t2);
    }

    public static <T> boolean notEqual(final T t1, final T t2) {
        return !equals(t1, t2);
    }

    public static <T extends Number> int compareTo(final T t1, final T t2) {
        checkNull(t1, t2);
        BigDecimal t11 = t1 instanceof BigDecimal ? (BigDecimal) t1 : new BigDecimal(t1.toString());
        BigDecimal t21 = t1 instanceof BigDecimal ? (BigDecimal) t2 : new BigDecimal(t2.toString());

        return t11.compareTo(t21);
    }

    public static <T extends Number> boolean compareEquals(final T t1, final T t2) {
        checkNull(t1, t2);
        BigDecimal t11 = t1 instanceof BigDecimal ? (BigDecimal) t1 : new BigDecimal(t1.toString());
        BigDecimal t21 = t1 instanceof BigDecimal ? (BigDecimal) t2 : new BigDecimal(t2.toString());

        return t11.compareTo(t21) == 0;
    }

    public static <T extends Number> boolean compareGt(final T t1, final T t2) {
        checkNull(t1, t2);
        BigDecimal t11 = t1 instanceof BigDecimal ? (BigDecimal) t1 : new BigDecimal(t1.toString());
        BigDecimal t21 = t1 instanceof BigDecimal ? (BigDecimal) t2 : new BigDecimal(t2.toString());

        return t11.compareTo(t21) > 0;
    }

    public static <T extends Number> boolean compareLt(final T t1, final T t2) {
        checkNull(t1, t2);
        BigDecimal t11 = t1 instanceof BigDecimal ? (BigDecimal) t1 : new BigDecimal(t1.toString());
        BigDecimal t21 = t1 instanceof BigDecimal ? (BigDecimal) t2 : new BigDecimal(t2.toString());

        return t11.compareTo(t21) < 0;
    }

    // =================================================================

    private static boolean isCollectionsSupportType(Object value) {
        boolean isCollectionOrMap = value instanceof Collection || value instanceof Map;
        boolean isEnumerationOrIterator = value instanceof Enumeration || value instanceof Iterator;

        return isCollectionOrMap
                || isEnumerationOrIterator
                || value.getClass().isArray();
    }

    // =================================================================

    /**
     * copy from {@code org.apache.commons.lang3.StringUtils#isBlank(java.lang.CharSequence)}
     */
    private static boolean isBlank(CharSequence cs) {
        int strLen = length(cs);
        if (strLen == 0) {
            return true;
        } else {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * copy from {@code org.apache.commons.lang3.StringUtils#length(java.lang.CharSequence)}
     */
    private static int length(CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    /**
     * copy from {@code org.apache.commons.collections.CollectionUtils#sizeIsEmpty(java.lang.Object)}
     */
    private static boolean sizeIsEmpty(Object object) {
        if (object instanceof Collection) {
            return ((Collection) object).isEmpty();
        } else if (object instanceof Map) {
            return ((Map) object).isEmpty();
        } else if (object instanceof Object[]) {
            return ((Object[]) (object)).length == 0;
        } else if (object instanceof Iterator) {
            return !((Iterator) object).hasNext();
        } else if (object instanceof Enumeration) {
            return !((Enumeration) object).hasMoreElements();
        } else if (object == null) {
            throw new IllegalArgumentException("Unsupported object type: null");
        } else {
            try {
                return Array.getLength(object) == 0;
            } catch (IllegalArgumentException var2) {
                throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
            }
        }
    }

    // =================================================================

    private static <T extends Number> void checkNull(T t1, T t2) {
        AssertUtils.notNull(t1, "Argument:t1 can't be null");
        AssertUtils.notNull(t2, "Argument:t2 can't be null");
    }
}
