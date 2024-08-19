/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.object.pool.in.action.commonpool.custom.shared.impl;

import com.photowey.object.pool.in.action.commonpool.custom.shared.PooledObjectFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Implementation specific utilities.
 *
 * @since 2.0
 */
public final class PoolImplUtils {

    private PoolImplUtils() {}

    @SuppressWarnings("rawtypes")
    static Class<?> getFactoryType(final Class<? extends PooledObjectFactory> factoryClass) {
        final Class<PooledObjectFactory> type = PooledObjectFactory.class;
        final Object genericType = getGenericType(type, factoryClass);
        if (genericType instanceof Integer) {
            // POOL-324 org.apache.commons.pool3.impl.GenericObjectPool.getFactoryType() throws
            // java.lang.ClassCastException
            //
            // A bit hackish, but we must handle cases when getGenericType() does not return a concrete types.
            final ParameterizedType pi = getParameterizedType(type, factoryClass);
            if (pi != null) {
                final Type[] bounds = ((TypeVariable) pi.getActualTypeArguments()[((Integer) genericType).intValue()])
                        .getBounds();
                if (bounds != null && bounds.length > 0) {
                    final Type bound0 = bounds[0];
                    if (bound0 instanceof Class) {
                        return (Class<?>) bound0;
                    }
                }
            }
            // last resort: Always return a Class
            return Object.class;
        }
        return (Class<?>) genericType;
    }

    private static <T> Object getGenericType(final Class<T> type, final Class<? extends T> clazz) {
        if (type == null || clazz == null) {
            // Error will be logged further up the call stack
            return null;
        }

        // Look to see if this class implements the generic interface
        final ParameterizedType pi = getParameterizedType(type, clazz);
        if (pi != null) {
            return getTypeParameter(clazz, pi.getActualTypeArguments()[0]);
        }

        // Interface not found on this class. Look at the superclass.
        @SuppressWarnings("unchecked") final Class<? extends T> superClass = (Class<? extends T>) clazz.getSuperclass();

        final Object result = getGenericType(type, superClass);
        if (result instanceof Class<?>) {
            // Superclass implements interface and defines explicit type for generic
            return result;
        }
        if (result instanceof Integer) {
            // Superclass implements interface and defines unknown type for generic
            // Map that unknown type to the generic types defined in this class
            final ParameterizedType superClassType = (ParameterizedType) clazz.getGenericSuperclass();
            return getTypeParameter(clazz, superClassType.getActualTypeArguments()[((Integer) result).intValue()]);
        }
        // Error will be logged further up the call stack
        return null;
    }

    private static <T> ParameterizedType getParameterizedType(final Class<T> type, final Class<? extends T> clazz) {
        for (final Type iface : clazz.getGenericInterfaces()) {
            // Only need to check interfaces that use generics
            if (iface instanceof ParameterizedType) {
                final ParameterizedType pi = (ParameterizedType) iface;
                // Look for the generic interface
                if (pi.getRawType() instanceof Class && type.isAssignableFrom((Class<?>) pi.getRawType())) {
                    return pi;
                }
            }
        }
        return null;
    }

    private static Object getTypeParameter(final Class<?> clazz, final Type argType) {
        if (argType instanceof Class<?>) {
            return argType;
        }
        final TypeVariable<?>[] tvs = clazz.getTypeParameters();
        for (int i = 0; i < tvs.length; i++) {
            if (tvs[i].equals(argType)) {
                return Integer.valueOf(i);
            }
        }
        return null;
    }

    static boolean isPositive(final Duration delay) {
        return delay != null && !delay.isNegative() && !delay.isZero();
    }

    static Instant max(final Instant a, final Instant b) {
        return a.compareTo(b) > 0 ? a : b;
    }

    static Instant min(final Instant a, final Instant b) {
        return a.compareTo(b) < 0 ? a : b;
    }

    static Duration nonNull(final Duration value, final Duration defaultValue) {
        return value != null ? value : Objects.requireNonNull(defaultValue, "defaultValue");
    }

    static Duration toDuration(final long amount, final TimeUnit timeUnit) {
        return Duration.of(amount, toChronoUnit(timeUnit));
    }

    static ChronoUnit toChronoUnit(final TimeUnit timeUnit) {
        switch (Objects.requireNonNull(timeUnit)) {
            case NANOSECONDS:
                return ChronoUnit.NANOS;
            case MICROSECONDS:
                return ChronoUnit.MICROS;
            case MILLISECONDS:
                return ChronoUnit.MILLIS;
            case SECONDS:
                return ChronoUnit.SECONDS;
            case MINUTES:
                return ChronoUnit.MINUTES;
            case HOURS:
                return ChronoUnit.HOURS;
            case DAYS:
                return ChronoUnit.DAYS;
            default:
                throw new IllegalArgumentException(timeUnit.toString());
        }
    }
}