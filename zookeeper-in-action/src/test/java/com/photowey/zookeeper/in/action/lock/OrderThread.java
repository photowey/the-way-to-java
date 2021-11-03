package com.photowey.zookeeper.in.action.lock;

import java.util.concurrent.CountDownLatch;

/**
 * {@code OrderThread}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
public class OrderThread implements Runnable {

    private ILock lock;
    private CountDownLatch latch;
    private OrderNumFactory orderNumFactory;

    public OrderThread(ILock lock, CountDownLatch latch, OrderNumFactory orderNumFactory) {
        this.lock = lock;
        this.latch = latch;
        this.orderNumFactory = orderNumFactory;
    }

    @Override
    public void run() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            // Ignore
        }

        try {
            createOrderNum();
        } catch (Exception e) {
            // Ignore
        }
    }

    private void createOrderNum() throws Exception {
        lock.lock();
        String orderNum = orderNumFactory.createOrderNum();
        System.out.println(Thread.currentThread().getName() + "创建了订单号：[" + orderNum + "]");
        lock.unlock();
    }
}
