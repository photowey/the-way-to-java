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
package com.photowey.juc.in.action.forkjoin;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * {@code ForkJoinHandler}
 *
 * @author photowey
 * @date 2021/12/04
 * @since 1.0.0
 */
@Slf4j
public class ForkJoinHandler {

    public static void main(String[] args) {
        v1();
        v2();
        v3();
    }

    public static long v1() {
        long start = System.currentTimeMillis();
        long sum = 0;
        for (long i = 1; i <= 1_000_000_000L; i++) {
            sum += i;
        }
        long end = System.currentTimeMillis();
        log.info("v1 use time:[{}],value = {}", (end - start), sum);
        return sum;
    }

    private static final int NCPU = Runtime.getRuntime().availableProcessors();

    public static long v2() {
        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool(NCPU);
        JoinTask joinTask = new JoinTask(0, 1_000_000_000L);
        long sum = forkJoinPool.invoke(joinTask);
        long end = System.currentTimeMillis();
        log.info("v2 use time:[{}],value = {}", (end - start), sum);
        return sum;
    }

    public static long v3() {
        long start = System.currentTimeMillis();
        long sum = LongStream.rangeClosed(0, 1_000_000_000L).parallel().reduce(0, Long::sum);
        long end = System.currentTimeMillis();
        log.info("v3 use time:[{}],value = {}", (end - start), sum);
        return sum;
    }

    public static class JoinTask extends RecursiveTask<Long> {

        private long low;
        private long high;
        private static final int threshold = 10000;

        public JoinTask(long low, long high) {
            this.low = low;
            this.high = high;
        }

        @Override
        protected Long compute() {
            if (high - low < threshold) {
                long sum = 0;
                for (long i = low; i <= high; i++) {
                    sum += i;
                }

                return sum;
            } else {
                long pivot = (low + high) / 2;

                JoinTask lowTask = new JoinTask(low, pivot);
                JoinTask highTask = new JoinTask(pivot + 1, high);

                lowTask.fork();
                highTask.fork();

                return lowTask.join() + highTask.join();
            }
        }
    }

}
