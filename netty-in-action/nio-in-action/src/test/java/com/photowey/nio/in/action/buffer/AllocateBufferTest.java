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
package com.photowey.nio.in.action.buffer;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * {@code AllocateBufferTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/08/25
 */
class AllocateBufferTest {

    @Test
    void testAllocate() {
        System.out.println("---------- Test allocate --------");
        System.out.println("before allocate:" + Runtime.getRuntime().freeMemory());

        // 堆上分配
        ByteBuffer buffer = ByteBuffer.allocate(1024000);
        System.out.println("buffer = " + buffer);
        System.out.println("after allocate:" + Runtime.getRuntime().freeMemory());

        // 这部分用的直接内存
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(102400);
        System.out.println("directBuffer = " + directBuffer);
        System.out.println("after direct allocate:" + Runtime.getRuntime().freeMemory());

        System.out.println("---------- Test wrap --------");
        byte[] bytes = new byte[32];
        buffer = ByteBuffer.wrap(bytes);
        System.out.println(buffer);

        buffer = ByteBuffer.wrap(bytes, 10, 10);
        System.out.println(buffer);
    }
}