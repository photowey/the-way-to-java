package com.photowey.zookeeper.in.action.lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * {@code SequenceZkLock}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
public class ZkSequenceLock extends AbstractLock {

    private String currentPath;
    private String beforePath;
    private String root;

    private CountDownLatch latch;

    public ZkSequenceLock(ZkClient zkClient) {
        super(zkClient);
        this.root = LOCK_PATH_ROOT;
        if (!this.zkClient.exists(this.root)) {
            this.zkClient.createPersistent(this.root, "root");
        }
    }

    @Override
    public boolean tryLock() {
        if (null == currentPath || currentPath.length() <= 0) {
            this.currentPath = this.zkClient.createEphemeralSequential(this.root + "/", "worker");
        }
        List<String> children = this.zkClient.getChildren(this.root);
        Collections.sort(children);
        if (currentPath.equals(this.root + "/" + children.get(0))) {
            return true;
        } else {
            // /lock/0000000001 -> 0000000001
            int index = Collections.binarySearch(children, currentPath.substring(this.root.length() + 1));
            beforePath = this.root + "/" + children.get(index - 1);
        }

        return false;
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

        // 监听-前置节点
        zkClient.subscribeDataChanges(beforePath, dataListener);

        if (this.zkClient.exists(beforePath)) {
            this.latch = new CountDownLatch(1);
            try {
                this.latch.await();
            } catch (Exception e) {
                // Ignore
            }
        }

        zkClient.unsubscribeDataChanges(beforePath, dataListener);
    }

    @Override
    public void unlock() {
        this.zkClient.delete(currentPath);
    }
}
