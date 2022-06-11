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

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;

import java.util.concurrent.CountDownLatch;

/**
 * {@code ZkMutexLock}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
public class ZkMutexLock extends AbstractLock {

    private CountDownLatch latch = null;
    private String root;

    public ZkMutexLock(ZkClient zkClient) {
        super(zkClient);
        this.root = LOCK_PATH_ROOT;
    }

    public ZkMutexLock(ZkClient zkClient, CountDownLatch latch, String root) {
        super(zkClient);
        this.latch = latch;
        this.root = root;
    }

    @Override
    public boolean tryLock() {
        try {
            this.zkClient.createEphemeral(this.root);
            return true;
        } catch (ZkException e) {
            return false;
        }
    }

    @Override
    public void waitLock() {
        IZkDataListener dataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                if (null != latch) {
                    latch.countDown();
                }
            }
        };

        zkClient.subscribeDataChanges(this.root, dataListener);

        if (this.zkClient.exists(this.root)) {
            this.latch = new CountDownLatch(1);
            try {
                this.latch.await();
            } catch (Exception e) {
                // Ignore
            }
        }

        zkClient.unsubscribeDataChanges(this.root, dataListener);
    }

    @Override
    public void unlock() {
        this.zkClient.delete(this.root);
    }
}
