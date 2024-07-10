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
package com.photowey.com.photowey.object.pool.in.action.kryo;

import java.util.Queue;

/**
 * {@code KryoPoolQueueImpl}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/07/10
 */
class KryoPoolQueueImpl implements KryoPool {

    private final Queue<Kryo> queue;
    private final KryoFactory factory;

    KryoPoolQueueImpl(KryoFactory factory, Queue<Kryo> queue) {
        this.factory = factory;
        this.queue = queue;
    }

    public int size() {
        return queue.size();
    }

    public Kryo borrow() {
        Kryo res;
        if ((res = queue.poll()) != null) {
            return res;
        }
        return factory.create();
    }

    public void release(Kryo kryo) {
        queue.offer(kryo);
    }

    public <T> T run(KryoCallback<T> callback) {
        Kryo kryo = borrow();
        try {
            return callback.execute(kryo);
        } finally {
            release(kryo);
        }
    }

    public void clear() {
        queue.clear();
    }

}
