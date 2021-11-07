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
