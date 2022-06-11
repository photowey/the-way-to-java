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
package com.photowey.juc.in.action.jdk.order;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code OrderRearrangement}
 * 指令重排
 * 1.编译器优化重排序
 * 2.指令级并行重排序
 * 3.内存系统重排序(内存屏障: memory barriers | memory fence) OrderAccess::fence()
 *
 * @author photowey
 * @date 2021/12/01
 * @since 1.0.0
 */
@Slf4j
public class OrderRearrangement {

    /**
     * v1:
     * private static int a = 0, b = 0;
     * private static int x = 0, y = 0;
     */

    /**
     * x = 0, y = 0, 不会发生了
     */
    private volatile static int a = 0, b = 0;
    private volatile static int x = 0, y = 0;

    public static void main(String[] args) {
        int counter = 0;
        while (true) {
            counter++;
            a = 0;
            b = 0;
            x = 0;
            y = 0;

            Thread t1 = new Thread(() -> {
                a = 1;
                x = b;
            }, "t1");
            Thread t2 = new Thread(() -> {
                b = 1;
                y = a;
            }, "t2");
            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
            } catch (Exception e) {
            }

            log.info("the counter:[{}],the x={}, y={}", counter, x, y);
            if (x == 0 && y == 0) {
                // the counter:[10341],the x=0, y=0 -- 指令重排
                break;
            }
        }

        // 如果不发生指令重排的三种情况 -- CPU 调度和上下文切换导致
        // x=0, y=1
        // x=1, y=0
        // x=1, y=1
        // -------------
        // x=0, y=0 -- 发生了指令重排
    }
}
