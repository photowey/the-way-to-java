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
package com.photowey.juc.in.action.guarded;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code GuardedObject}
 *
 * @author photowey
 * @date 2021/11/18
 * @since 1.0.0
 */
@Slf4j
public class GuardedObjectTimeout {

    private static Object lock = new Object();

    private String response;

    public String getResponse() {
        synchronized (lock) {
            while (null == response) {
                try {
                    lock.wait();
                } catch (Exception e) {
                }
            }
        }

        return response;
    }

    public String getResponse(long timeout) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout 不能为负数");
        }
        synchronized (lock) {
            long base = System.currentTimeMillis();
            long timePassed = 0L;
            while (null == response) {
                long delay = timeout - timePassed;
                if (delay <= 0) {
                    log.info("--- timeout break ---");
                    break;
                }
                try {
                    lock.wait(delay);
                } catch (Exception e) {
                }

                timePassed = System.currentTimeMillis() - base;
                log.info("--- timePassed:{} ---", timePassed);
            }
        }

        return response;
    }

    public void setResponse(String response) {
        synchronized (lock) {
            this.response = response;
            lock.notifyAll();
        }
    }

}
