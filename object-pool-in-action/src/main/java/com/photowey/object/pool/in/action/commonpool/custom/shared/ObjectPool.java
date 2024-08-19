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

import com.photowey.object.pool.in.action.commonpool.custom.shared.enums.DestroyMode;

import java.io.Closeable;

/**
 * A pooling simple interface.
 * <p>
 *
 * @param <T> Type of element pooled in this pool.
 * @param <E> Type of exception thrown by this pool.
 * @see PooledObjectFactory
 * @since 2.0
 */
public interface ObjectPool<T, E extends Exception> extends Closeable {

    void addObject() throws E;

    default void addObjects(final int count) throws E {
        for (int i = 0; i < count; i++) {
            addObject();
        }
    }

    T borrowObject() throws E;

    void returnObject(T obj) throws E;

    void invalidateObject(T obj) throws E;

    default void invalidateObject(final T obj, final DestroyMode destroyMode) throws E {
        invalidateObject(obj);
    }

    // ----------------------------------------------------------------

    void clear() throws E;

    @Override
    void close();

    int getNumActive();

    int getNumIdle();
}
