package com.photowey.netty.telnet.in.action.pool.factory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * {@code BlockingQueueFactory}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public class BlockingQueueFactory {

    public static BlockingQueue<Runnable> buildQueue(int size) {
        return buildQueue(size, false);
    }

    public static BlockingQueue<Runnable> buildQueue(int size, boolean priority) {
        BlockingQueue<Runnable> queue;
        if (size == 0) {
            queue = new SynchronousQueue<>();
        } else {
            if (priority) {
                queue = size < 0 ? new PriorityBlockingQueue<>() : new PriorityBlockingQueue<>(size);
            } else {
                queue = size < 0 ? new LinkedBlockingDeque<>() : new LinkedBlockingDeque<>(size);
            }
        }
        return queue;
    }
}