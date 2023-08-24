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
package com.photowey.virtual.thread.in.action.server;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@code SimpleWork}
 *
 * @author photowey
 * @date 2022/12/25
 * @since 1.0.0
 */
@Slf4j
public class SimpleWork {

    AtomicLong id = new AtomicLong();
    ReentrantLock lock = new ReentrantLock();

    public String doJob() {
        String response = null;
        lock.lock();
        try {
            Thread.sleep(100);
            response = "Ping_" + id.incrementAndGet();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        } finally {
            lock.unlock();
        }

        Thread thread = Thread.currentThread();
        String tx = String.format("SimpleWork: handle job, thread:[%s:%d], isVirtual:%b", thread.getName(), thread.threadId(), thread.isVirtual());
        log.info("SimpleWork: handle job, thread:[{}:{}], isVirtual:{}", thread.getName(), thread.threadId(), thread.isVirtual());

        return response + "::" + tx;
    }
}
