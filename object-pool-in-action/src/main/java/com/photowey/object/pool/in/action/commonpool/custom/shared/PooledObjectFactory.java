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

/**
 * An interface defining life-cycle methods for instances to be served by an {@link ObjectPool}.
 *
 * @param <T> Type of element managed in this factory.
 * @param <E> Type of exception thrown in this factory.
 * @since 2.0
 */
public interface PooledObjectFactory<T, E extends Exception> {

    PooledObject<T> makeObject() throws E;

    void activateObject(PooledObject<T> p) throws E;

    void passivateObject(PooledObject<T> p) throws E;

    boolean validateObject(PooledObject<T> p);

    void destroyObject(PooledObject<T> p) throws E;

    default void destroyObject(final PooledObject<T> p, final DestroyMode destroyMode) throws E {
        destroyObject(p);
    }
}

