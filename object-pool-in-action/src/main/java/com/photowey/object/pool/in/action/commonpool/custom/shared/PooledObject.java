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
package com.photowey.object.pool.in.action.commonpool.custom.shared;

import com.photowey.object.pool.in.action.commonpool.custom.shared.enums.PooledObjectState;

import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.Deque;

/**
 * Defines the wrapper that is used to track the additional information, such as
 * state, for the pooled objects.
 * <p>
 * Implementations of this class are required to be thread-safe.
 * </p>
 *
 * @param <T> the type of object in the pool.
 * @since 2.0
 */
public interface PooledObject<T> extends Comparable<PooledObject<T>> {

    static boolean isNotNull(final PooledObject<?> pooledObject) {
        return !isNull(pooledObject);
    }

    static boolean isNull(final PooledObject<?> pooledObject) {
        return pooledObject == null || pooledObject.getObject() == null;
    }

    boolean allocate();

    boolean deallocate();

    boolean endEvictionTest(Deque<PooledObject<T>> idleQueue);

    T getObject();

    void invalidate();

    void markAbandoned();

    void markReturning();

    void printStackTrace(PrintWriter writer);

    boolean startEvictionTest();

    void use();

    // ----------------------------------------------------------------

    void setLogAbandoned(boolean logAbandoned);

    // ----------------------------------------------------------------

    Instant getCreateInstant();

    default Duration getFullDuration() {
        return Duration.between(getCreateInstant(), Instant.now());
    }

    Duration getIdleDuration();

    Instant getLastBorrowInstant();

    Instant getLastReturnInstant();

    Instant getLastUsedInstant();

    PooledObjectState getState();

    // ----------------------------------------------------------------

    default Duration getActiveDuration() {
        // Take copies to avoid threading issues
        final Instant lastReturnInstant = getLastReturnInstant();
        final Instant lastBorrowInstant = getLastBorrowInstant();
        // @formatter:off
        return lastReturnInstant.isAfter(lastBorrowInstant) ?
                Duration.between(lastBorrowInstant, lastReturnInstant) :
                Duration.between(lastBorrowInstant, Instant.now());
        // @formatter:on
    }

    default long getBorrowedCount() {
        return -1;
    }

    // ----------------------------------------------------------------

    default void setRequireFullStackTrace(final boolean requireFullStackTrace) {
        // noop
    }

    // ----------------------------------------------------------------

    @Override
    int compareTo(PooledObject<T> other);

    @Override
    boolean equals(Object obj);

    @Override
    int hashCode();

    @Override
    String toString();
}

