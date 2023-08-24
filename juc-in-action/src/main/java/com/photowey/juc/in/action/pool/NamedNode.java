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
package com.photowey.juc.in.action.pool;

import com.photowey.juc.in.action.pool.queue.NamedBlockingQueue;

/**
 * {@code NamedNode}
 *
 * @author photowey
 * @date 2021/11/29
 * @since 1.0.0
 */
public class NamedNode extends Thread {

    private NamedTask target;
    private final NamedThreadPool threadPool;
    private final NamedBlockingQueue taskQueue;

    public NamedNode(NamedTask target, NamedThreadPool threadPool) {
        this.target = target;
        this.threadPool = threadPool;
        this.taskQueue = this.threadPool.getTaskQueue();
    }

    public NamedNode(NamedTask target, NamedThreadPool threadPool, String name) {
        super(name);
        this.target = target;
        this.threadPool = threadPool;
        this.taskQueue = this.threadPool.getTaskQueue();
    }

    /**
     * 这里执行完之后-不能直接结束
     * 1.线程池-直接指派
     * 2.任务是从阻塞队列取出来的
     */
    @Override
    public void run() {
        while (target != null || (target = this.taskQueue.poll()) != null) {
            this.target.run();
            this.target = null;
        }
    }
}
