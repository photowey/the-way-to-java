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
package com.photowey.zookeeper.in.action.lock;

import org.I0Itec.zkclient.ZkClient;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * {@code WriteLock}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
public class WriteLock implements IWriteLock {

    private final ZkClient zkClient;

    private String root;
    private String lockPath;

    public WriteLock(ZkClient zkClient, String root) {
        this.zkClient = zkClient;
        this.root = root;
        if (!this.zkClient.exists(root)) {
            this.zkClient.createPersistent(root);
        }
    }

    @Override
    public void lock() {
        CountDownLatch latch = new CountDownLatch(1);
        // /RWLock/w-000000000101
        String lockName = root + "/" + WRITE_SYMBOL + "-";
        this.lockPath = zkClient.createEphemeralSequential(lockName, "write");

        while (true) {
            List<String> children = zkClient.getChildren(root);
            // r-0000000001 -> 0000000001
            List<String> cleanChildren = children.stream().map(child -> child.substring(2)).collect(Collectors.toList());
            this.sort(children);
            this.sort(cleanChildren);
            int index = Collections.binarySearch(cleanChildren, lockPath.substring(lockName.length()));
            if (index == 0) {
                break;
            } else {
                // Subscribe the prev
                this.zkClient.subscribeChildChanges(root + "/" + children.get(index - 1), (parentPath, currentChildren) -> latch.countDown());
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    // Ignore
                }
            }
        }
    }

    @Override
    public void unlock() {
        if (this.lockPath != null) {
            this.zkClient.delete(lockPath);
            this.lockPath = "";
        }
    }
}
