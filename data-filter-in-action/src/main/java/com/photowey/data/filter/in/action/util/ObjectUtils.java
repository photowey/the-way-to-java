/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.data.filter.in.action.util;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * {@code ObjectUtils}
 *
 * @author photowey
 * @date 2021/11/09
 * @since 1.0.0
 */
public final class ObjectUtils {

    private ObjectUtils() {
        throw new AssertionError("No instance:" + this.getClass().getSimpleName() + " for you!");
    }

    public static String getStringValue(Object object) {
        if (Objects.isNull(object)) {
            return "";
        }

        return String.valueOf(object);

    }

    public static <T, V> V getFieldValue(T object, String field) {
        try {
            // 优先: 查找: getXxx() 方法
            Method method = findMethod(object, field);
            Object value = Objects.requireNonNull(method).invoke(object);
            return (V) value;
        } catch (Exception e) {
            // Ignore
        }

        return null;
    }

    public static <T, V> V getFieldValue(T object, Field field) {
        try {
            // 优先: 查找: getXxx() 方法
            Method method = findMethod(object, field.getName());
            Object value = Objects.requireNonNull(method).invoke(object);
            return (V) value;
        } catch (Exception e) {
            // Ignore
        }

        return null;
    }

    public static <T, V> V getNumberFieldValue(T object, String field) {
        try {
            Method method = findMethod(object, field);
            Object value = Objects.requireNonNull(method).invoke(object);
            if (!(value instanceof Number)) {
                throw new IllegalArgumentException("属性:" + field + "不是数值类型");
            }
            return (V) value;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            // Ignore
        }
        return null;
    }

    public static <T, V> V getNumberFieldValue(T object, String field, Number defaultValue) {
        try {
            Method method = findMethod(object, field);
            Object value = Objects.requireNonNull(method).invoke(object);
            if (null == value) {
                return (V) defaultValue;
            }
            if (!(value instanceof Number)) {
                throw new IllegalArgumentException("属性:" + field + "不是数值类型");
            }
            return (V) value;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            // Ignore
        }
        return null;
    }

    private static <T> Method findMethod(T object, String field) {
        // 优先: 查找: getXxx() 方法
        Method method = ReflectionUtils.findMethod(object.getClass(), concatGetMethod(field));
        if (null == method) {
            // 其次: 查找: xxx()
            // 可能会使用注解: @Accessors(fluent = true) - 导致没有 getXxx() 方法
            method = ReflectionUtils.findMethod(object.getClass(), field);
        }
        return method;
    }

    private static String concatGetMethod(String field) {
        String template = "get%s%s";

        return String.format(template, field.substring(0, 1).toUpperCase(), field.substring(1));
    }
}
