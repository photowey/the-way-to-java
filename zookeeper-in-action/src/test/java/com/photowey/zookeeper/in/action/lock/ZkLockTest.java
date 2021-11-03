package com.photowey.zookeeper.in.action.lock;

import org.I0Itec.zkclient.ZkClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;

/**
 * {@code ZkLockTest}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
@SpringBootTest
class ZkLockTest {

    @Autowired
    private ZkClient zkClient;
    private OrderNumFactory orderNumFactory;

    public static final int THREAD_COUNT = 50;

    @BeforeEach
    public void init() {
        this.orderNumFactory = new OrderNumFactory();
    }

    @Test
    void testZkMutexLock() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            ZkMutexLock lock = new ZkMutexLock(zkClient);
            new Thread(new OrderThread(lock, latch, orderNumFactory)).start();
            latch.countDown();
        }

        Thread.sleep(5_000);
    }

    @Test
    void testZkSequenceLock() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            ZkSequenceLock lock = new ZkSequenceLock(zkClient);
            new Thread(new OrderThread(lock, latch, orderNumFactory)).start();
            latch.countDown();
        }

        Thread.sleep(5_000);
    }

}