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
 * {@code ScalarReplace}
 *
 * <pre>
 * -Xmx128m -Xms128m -XX:+DoEscapeAnalysis -XX:+PrintGC -XX:+EliminateAllocations
 * </pre>
 *
 * @author photowey
 * @date 2022/10/07
 * @since 1.0.0
 */
public class ScalarReplace {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10_000_000; i++) {
            alloc();
        }
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + " ms");
    }

    public static void alloc() {
        // 未发生逃逸
        User u = new User();
        u.id = 5;
        u.name = "photowey";
    }

    public static class User {
        public int id;
        public String name;
    }
}
