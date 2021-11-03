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