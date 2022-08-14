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
package com.photowey.juc.in.action.queue;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * {@code ArrayBlockingQueueTest}
 *
 * @author photowey
 * @date 2022/08/14
 * @since 1.0.0
 */
@Slf4j
class ArrayBlockingQueueTest {

    /**
     * ArrayBlockingQueue
     * 1.限定队列长度的,且队列中不能包含 null,队列长度创建后就不能更改
     * 2.插入元素在队尾,删除元素从队头开始
     * 3.实现BlockingQueue接口的类必须都是线程安全的
     * 4.队列满的情况下,会阻塞插入线程,队列空的情况下,会阻塞删除元素线程
     * 5.支持公平、非公平锁,默认非公平锁,由于操作共用一把锁,在高并发场景下,可能会出现性能瓶颈
     */

    @Test
    void testQueueFull() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(2);
            boolean add1 = blockingQueue.add(1);
            log.info("blockingQueue[2] add 1:{}", add1);
            boolean add2 = blockingQueue.add(2);
            log.info("blockingQueue[2] add 2:{}", add2);
            boolean add3 = blockingQueue.add(3);
            log.info("blockingQueue[2] add 3:{}", add3);
        }, "Queue full");
    }

    @Test
    void testRemoveNoSuchElement() throws InterruptedException {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(3);
            boolean add1 = blockingQueue.add(1);
            log.info("blockingQueue[3] add 1:{}", add1);
            blockingQueue.put(2);
            log.info("blockingQueue[3] put 2");
            boolean add3 = blockingQueue.offer(3);
            log.info("blockingQueue[3] offer 3:{}", add3);

            Integer remove1 = blockingQueue.remove(); // size = 2
            log.info("blockingQueue[3] remove 1:{}", remove1);
            Integer remove2 = blockingQueue.remove();
            log.info("blockingQueue[3] remove 2:{}", remove2);
            Integer remove3 = blockingQueue.remove();
            log.info("blockingQueue[3] remove 3:{}", remove3);

            Integer remove4 = blockingQueue.remove();
            log.info("blockingQueue[3] remove 4:{}", remove4);
        });
    }

    @Test
    void testPutBlocking() throws InterruptedException {
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(3);
        boolean add = blockingQueue.add(1);
        log.info("add 1:{}", add);
        blockingQueue.put(2);
        boolean offer = blockingQueue.offer(3);
        log.info("offer 3:{}", offer);

        new Thread(() -> {
            try {
                Thread.sleep(3_000);
                Integer take1 = blockingQueue.take();
                log.info("take 1:{}", take1);
            } catch (Exception ignored) {
            }
        }, "consumer").start();

        blockingQueue.put(4);
        log.info("put 4");
    }

    @Test
    void testTakeBlocking() throws InterruptedException {
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(3);
        boolean add = blockingQueue.add(1);
        log.info("add 1:{}", add);
        blockingQueue.put(2);
        boolean offer = blockingQueue.offer(3);
        log.info("offer 3:{}", offer);

        Integer take1 = blockingQueue.take();
        log.info("take 1:{}", take1);
        Integer take2 = blockingQueue.take();
        log.info("take 2:{}", take2);
        Integer take3 = blockingQueue.take();
        log.info("take 3:{}", take3);

        new Thread(() -> {
            try {
                Thread.sleep(3_000);
                blockingQueue.offer(4);
                log.info("offer 4");
            } catch (Exception ignored) {
            }
        }, "producer").start();

        log.info("pre take 4");
        Integer take4 = blockingQueue.take();
        log.info("take 4:{}", take4);
    }


    @Test
    void testOffer() {
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(3);
        boolean offer1 = blockingQueue.offer(1);
        log.info("offer 1:{}", offer1);
        boolean offer2 = blockingQueue.offer(2);
        log.info("offer 2:{}", offer2);
        boolean offer3 = blockingQueue.offer(3);
        log.info("offer 3:{}", offer3);

        log.info("offer3 size:{}", blockingQueue.size());

        boolean offer4 = blockingQueue.offer(4);
        log.info("offer 4:{}", offer4);
    }

    @Test
    void testPoll() {
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(3);
        boolean offer1 = blockingQueue.offer(1);
        log.info("offer 1:{}", offer1);
        boolean offer2 = blockingQueue.offer(2);
        log.info("offer 2:{}", offer2);
        boolean offer3 = blockingQueue.offer(3);
        log.info("offer 3:{}", offer3);

        log.info("offer 3 size:{}", blockingQueue.size());

        boolean offer4 = blockingQueue.offer(4);
        log.info("offer 4:{}", offer4);
        // 利用 offer 来向队列中存储元素时,存储成功时会返回 true,失败时会返回 false;

        Integer poll1 = blockingQueue.poll();
        log.info("poll 1:{}", poll1);
        Integer poll2 = blockingQueue.poll();
        log.info("poll 2:{}", poll2);
        Integer poll3 = blockingQueue.poll();
        log.info("poll 3:{}", poll3);
        log.info("poll 3 size:{}", blockingQueue.size());

        Integer poll4 = blockingQueue.poll();
        log.info("poll 4:{}", poll4);
        // 利用 poll 来从队列中取出元素,若队列中元素不足时,会返回 null。
    }
}