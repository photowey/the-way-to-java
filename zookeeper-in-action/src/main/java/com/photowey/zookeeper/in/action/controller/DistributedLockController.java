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
package com.photowey.zookeeper.in.action.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;
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
    private ZookeeperLockRegistry zookeeperLockRegistry;

    @GetMapping("/zookeeper")
    public ResponseEntity<String> zookeeperLock() {
        Lock lock = zookeeperLockRegistry.obtain("zookeeper");
        try {
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
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