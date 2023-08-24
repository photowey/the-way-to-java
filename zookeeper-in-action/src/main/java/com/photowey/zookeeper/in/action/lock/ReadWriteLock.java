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
package com.photowey.zookeeper.in.action.lock;

import org.I0Itec.zkclient.ZkClient;

/**
 * {@code ReadWriteLock}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
public class ReadWriteLock implements IReadWriteLock {

    private final ZkClient zkClient;
    private final IWriteLock writeLock;
    private final IReadLock readLock;

    public ReadWriteLock(ZkClient zkClient) {
        this.zkClient = zkClient;
        this.writeLock = new WriteLock(this.zkClient, ROOT);
        this.readLock = new ReadLock(this.zkClient, ROOT);
    }

    public ReadWriteLock(ZkClient zkClient, String root) {
        this.zkClient = zkClient;
        this.writeLock = new WriteLock(this.zkClient, root);
        this.readLock = new ReadLock(this.zkClient, root);
    }

    @Override
    public IWriteLock writeLock() {
        return this.writeLock;
    }

    @Override
    public IReadLock readLock() {
        return this.readLock;
    }
}
