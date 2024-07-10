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
package com.photowey.object.pool.in.action.kryo;

import java.lang.ref.SoftReference;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * {@code KryoPool}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/07/10
 */
public interface KryoPool {

    /**
     * Takes a {@link Kryo} instance from the pool or creates a new one (using the factory) if the pool is empty.
     */
    Kryo borrow();

    /**
     * Returns the given {@link Kryo} instance to the pool.
     */
    void release(Kryo kryo);

    /**
     * Runs the provided {@link KryoCallback} with a {@link Kryo} instance from the pool (borrow/release around
     * {@link KryoCallback#execute(Kryo)}).
     */
    <T> T run(KryoCallback<T> callback);

    /**
     * Builder for a {@link KryoPool} instance, constructs a {@link KryoPoolQueueImpl} instance.
     */
    public static class Builder {

        private final KryoFactory factory;
        private Queue<Kryo> queue = new ConcurrentLinkedQueue<Kryo>();
        private boolean softReferences;

        public Builder(KryoFactory factory) {
            if (factory == null) {
                throw new IllegalArgumentException("factory must not be null");
            }
            this.factory = factory;
        }

        /**
         * Use the given queue for pooling kryo instances (by default a {@link ConcurrentLinkedQueue} is used).
         */
        public Builder queue(Queue<Kryo> queue) {
            if (queue == null) {
                throw new IllegalArgumentException("queue must not be null");
            }
            this.queue = queue;
            return this;
        }

        /**
         * Use {@link SoftReference}s for pooled {@link Kryo} instances, so that instances may be garbage collected when there's
         * memory demand (by default disabled).
         */
        public Builder softReferences() {
            softReferences = true;
            return this;
        }

        /**
         * Build the pool.
         */
        public KryoPool build() {
            Queue<Kryo> q = softReferences ? new SoftReferenceQueue(queue) : queue;
            return new KryoPoolQueueImpl(factory, q);
        }

        @Override
        public String toString() {
            return getClass().getName() + "[queue.class=" + queue.getClass() + ", softReferences=" + softReferences + "]";
        }
    }

}

