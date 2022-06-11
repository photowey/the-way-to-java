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

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * {@code ReadLock}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
public class ReadLock implements IReadLock {

    private final ZkClient zkClient;

    private String root;
    private String lockPath;

    public ReadLock(ZkClient zkClient, String root) {
        this.zkClient = zkClient;
        this.root = root;
        if (!this.zkClient.exists(root)) {
            this.zkClient.createPersistent(root);
        }
    }

    @Override
    public void lock() {
        CountDownLatch latch = new CountDownLatch(1);
        // /RWLock/r-000000000101
        String lockName = root + "/" + READ_SYMBOL + "-";
        this.lockPath = zkClient.createEphemeralSequential(lockName, "read");

        List<String> children = this.zkClient.getChildren(root);
        this.sort(children);
        int index = 0;
        for (int i = children.size() - 1; i >= 0; i--) {
            if (this.lockPath.equals(root + "/" + children.get(i))) {
                index = i;
            } else if (i < index && children.get(i).contains(WRITE_SYMBOL)) {
                // /RWLock/w-000000000100
                this.zkClient.subscribeChildChanges(root + "/" + children.get(i), (parentPath, currentChildren) -> latch.countDown());
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
        if (null != this.lockPath) {
            this.zkClient.delete(this.lockPath);
            this.lockPath = "";
        }
    }
}
