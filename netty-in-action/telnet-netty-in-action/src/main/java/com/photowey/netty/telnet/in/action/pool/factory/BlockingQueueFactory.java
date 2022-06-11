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
package com.photowey.netty.telnet.in.action.pool.factory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * {@code BlockingQueueFactory}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public class BlockingQueueFactory {

    public static BlockingQueue<Runnable> buildQueue(int size) {
        return buildQueue(size, false);
    }

    public static BlockingQueue<Runnable> buildQueue(int size, boolean priority) {
        BlockingQueue<Runnable> queue;
        if (size == 0) {
            queue = new SynchronousQueue<>();
        } else {
            if (priority) {
                queue = size < 0 ? new PriorityBlockingQueue<>() : new PriorityBlockingQueue<>(size);
            } else {
                queue = size < 0 ? new LinkedBlockingDeque<>() : new LinkedBlockingDeque<>(size);
            }
        }
        return queue;
    }
}