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
package com.photowey.juc.in.action.pool;

import com.photowey.juc.in.action.pool.queue.NamedBlockingQueue;
import com.photowey.juc.in.action.pool.strategy.RejectedStrategyHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;

/**
 * {@code NamedThreadPool}
 *
 * @author photowey
 * @date 2021/11/29
 * @since 1.0.0
 */
@Slf4j
public class NamedThreadPool {

    private final HashSet<NamedNode> coreSet;
    private int corePoolSize;
    private RejectedStrategyHandler strategyHandler;

    @Getter
    private final NamedBlockingQueue taskQueue;

    public NamedThreadPool() {
        this(1, 4);
    }

    public NamedThreadPool(int corePoolSize, int capacity) {
        this(corePoolSize, capacity, null);
    }

    public NamedThreadPool(int corePoolSize, int capacity, RejectedStrategyHandler strategyHandler) {
        this(corePoolSize, new NamedBlockingQueue(capacity, strategyHandler));
    }

    public NamedThreadPool(int corePoolSize, NamedBlockingQueue taskQueue) {
        this(corePoolSize, taskQueue, taskQueue.getStrategyHandler());
    }

    public NamedThreadPool(int corePoolSize, NamedBlockingQueue taskQueue, RejectedStrategyHandler strategyHandler) {
        this.coreSet = new HashSet<>();
        this.corePoolSize = corePoolSize;
        this.taskQueue = taskQueue;
        this.strategyHandler = strategyHandler;
        boolean abort = this.taskQueue.getStrategyHandler() != null && strategyHandler == null;
        if (!abort) {
            this.taskQueue.setStrategyHandler(this.strategyHandler);
        }
    }

    public void submit(NamedTask target) {
        // 考虑当前线程池中的核心线程有没有达到上限?
        if (coreSet.size() < corePoolSize) {
            // 核心线程数 - 还有空闲 - 直接创建线程
            log.info("the core thread is idle:[{}-{}], create a new task", coreSet.size(), corePoolSize);
            NamedNode namedNode = new NamedNode(target, this, "t" + (coreSet.size() + 1));
            log.info("the new task:[{}] add into the core-set", target.getName());
            this.coreSet.add(namedNode);
            namedNode.start();
        } else {
            // 核心线程数 - 达到上限
            if (target.getAwaitMillis() < 0) {
                this.taskQueue.put(target);
            } else {
                // this.taskQueue.tryPut(target, target.getAwaitMillis());
                this.taskQueue.tryPut(target);
            }
        }
    }

}
