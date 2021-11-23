/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.mongo.in.action.generator;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@code KeyGenerator}
 *
 * @author photowey
 * @date 2021/11/24
 * @since 1.0.0
 */
@Component
public class KeyGenerator implements IKeyGenerator {

    /**
     * 自增基数-时间戳
     * 981144306000 == 2001-02-03 04:05:06
     */
    private final AtomicLong pilot = new AtomicLong(981144306000L);

    private ReentrantLock lock;

    private static final int SALT_LENGTH = 8;
    private static final int SALT_MOVE_STEP = 8;

    @PostConstruct
    public void init() {
        this.lock = new ReentrantLock();
    }

    @Override
    public long nextId() {
        this.lock.lock();
        try {
            long now = this.pilot.incrementAndGet();
            SecureRandom random = new SecureRandom();
            int salt = random.nextInt(255);
            int factor = random.nextInt(255);
            long nextId = now << (SALT_LENGTH + SALT_MOVE_STEP) | salt << SALT_MOVE_STEP | factor;
            return nextId;
        } catch (Exception e) {
            throw new RuntimeException("handle the nextId exception", e);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public String nextStrId() {
        return String.valueOf(this.nextId());
    }

}
