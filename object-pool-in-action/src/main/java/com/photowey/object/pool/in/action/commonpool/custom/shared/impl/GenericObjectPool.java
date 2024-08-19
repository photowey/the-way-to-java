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

import com.photowey.object.pool.in.action.commonpool.custom.shared.ObjectPool;
import com.photowey.object.pool.in.action.commonpool.custom.shared.PooledObjectFactory;
import com.photowey.object.pool.in.action.commonpool.custom.shared.enums.DestroyMode;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A configurable {@link ObjectPool} implementation.
 *
 * @param <T> Type of element pooled in this pool.
 * @param <E> Type of exception thrown in this pool.
 * @since 2.0
 */
public class GenericObjectPool<T, E extends Exception> implements ObjectPool<T, E> {

    private final AtomicInteger activeCount = new AtomicInteger(0);
    private final AtomicInteger idleCount = new AtomicInteger(0);

    private final PooledObjectFactory<T, E> factory;

    public GenericObjectPool(final PooledObjectFactory<T, E> factory) {
        this(factory, new GenericObjectPoolConfig());
    }

    public GenericObjectPool(final PooledObjectFactory<T, E> factory, GenericObjectPoolConfig config) {
        this.factory = factory;
    }

    @Override
    public void addObject() throws E {
        throw new UnsupportedOperationException("Unsupported now");
    }

    @Override
    public void addObjects(int count) throws E {
        throw new UnsupportedOperationException("Unsupported now");
    }

    @Override
    public T borrowObject() throws E {
        throw new UnsupportedOperationException("Unsupported now");
    }

    @Override
    public void returnObject(T obj) throws E {
        throw new UnsupportedOperationException("Unsupported now");
    }

    @Override
    public void invalidateObject(T obj) throws E {
        throw new UnsupportedOperationException("Unsupported now");
    }

    @Override
    public void invalidateObject(T obj, DestroyMode destroyMode) throws E {
        throw new UnsupportedOperationException("Unsupported now");
    }

    @Override
    public void clear() throws E {
        throw new UnsupportedOperationException("Unsupported now");
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Unsupported now");
    }

    @Override
    public int getNumActive() {
        return this.activeCount.get();
    }

    @Override
    public int getNumIdle() {
        return this.idleCount.get();
    }
}
