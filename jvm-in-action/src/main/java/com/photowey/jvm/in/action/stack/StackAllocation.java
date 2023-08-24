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
package com.photowey.jvm.in.action.stack;

/**
 * {@code StackAllocation}
 *
 * <pre>
 * -Xmx256m -Xms256m -XX:+DoEscapeAnalysis -XX:+PrintGCDetails
 * </pre>
 *
 * @author photowey
 * @date 2022/10/07
 * @since 1.0.0
 */
public class StackAllocation {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10_000_000; i++) {
            alloc();
        }

        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + " ms");
        try {
            Thread.sleep(1_000_000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    private static void alloc() {
        // 未发生逃逸
        User user = new User();
    }

    static class User {

    }

}
