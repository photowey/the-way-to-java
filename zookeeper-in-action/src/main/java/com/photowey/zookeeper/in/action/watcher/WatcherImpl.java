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
package com.photowey.zookeeper.in.action.watcher;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 * {@code WatcherImpl}
 *
 * @author photowey
 * @date 2021/11/03
 * @since 1.0.0
 */
@Slf4j
public class WatcherImpl implements Watcher {

    private CountDownLatch latch;

    public WatcherImpl() {
    }

    public WatcherImpl(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            if (Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
                if (null != latch) {
                    latch.countDown();
                }
                if (log.isInfoEnabled()) {
                    log.info("handle the zookeeper watch event,the zookeeper session established,state:[{}]", watchedEvent.getState());
                }
            } else if (Event.EventType.NodeCreated == watchedEvent.getType()) {
                if (log.isInfoEnabled()) {
                    log.info("handle the zookeeper watch event,the zookeeper node:[{}] created,state:[{}]", watchedEvent.getPath(), watchedEvent.getState());
                }
            } else if (Event.EventType.NodeDataChanged == watchedEvent.getType()) {
                if (log.isInfoEnabled()) {
                    log.info("handle the zookeeper watch event,the zookeeper node:[{}] data changed,state:[{}]", watchedEvent.getPath(), watchedEvent.getState());
                }
            } else if (Event.EventType.NodeDeleted == watchedEvent.getType()) {
                if (log.isInfoEnabled()) {
                    log.info("handle the zookeeper watch event,the zookeeper node:[{}] deleted,state:[{}]", watchedEvent.getPath(), watchedEvent.getState());
                }

            } else if (Event.EventType.NodeChildrenChanged == watchedEvent.getType()) {
                if (log.isInfoEnabled()) {
                    log.info("handle the zookeeper watch event,the zookeeper node:[{}] children changed,state:[{}]", watchedEvent.getPath(), watchedEvent.getState());
                }
            }
        }
    }
}