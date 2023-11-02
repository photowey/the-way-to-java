package com.photowey.commom.in.action.timewheel.v3;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@code TimeWheelDelayQueueTest}
 *
 * @author photowey
 * @date 2023/11/03
 * @since 1.0.0
 */
public class TimeWheelDelayQueueTest {

    @Test
    public void testThreeLevelTimeWheel() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        TimeWheelDelayQueue delayQueue = new TimeWheelDelayQueue();

        Runnable task = () -> {
            System.out.println("Task executed!");
            latch.countDown();
        };
        TimerTask timerTask = new TimerTask(5000, task);
        delayQueue.addTask(timerTask);

        TimeUnit.MILLISECONDS.sleep(6000);
        assertTrue(latch.getCount() == 0);
    }
}
