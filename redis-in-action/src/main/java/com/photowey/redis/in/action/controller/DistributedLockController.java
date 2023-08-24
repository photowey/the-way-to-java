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
package com.photowey.redis.in.action.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * {@code DistributedLockController}
 *
 * @author photowey
 * @date 2022/01/01
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/lock")
public class DistributedLockController {
    @Autowired
    private RedisLockRegistry redisLockRegistry;

    // 注意,如果使用新版 SpringBoot 进行集成时需要使用 Redis4 版本,否则会出现下面的异常告警,
    // 主要是 unlock() 释放锁时使用了 UNLINK 命令,这个需要 Redis4 版本才能支持

    @GetMapping("/redis")
    public ResponseEntity<String> redisLock() {
        Lock lock = this.redisLockRegistry.obtain("redis");
        try {
            // 尝试在指定时间内加锁
            // 如果已经有其他锁锁住,则当前线程不能加锁,则返回false,加锁失败;加锁成功则返回true
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                log.info("acquire the lock is succeed");
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (InterruptedException e) {
            log.error("acquire the lock error", e);
        } finally {
            lock.unlock();
        }

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}