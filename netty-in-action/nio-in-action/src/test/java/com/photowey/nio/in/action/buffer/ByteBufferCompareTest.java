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
 * {@code ByteBufferCompareTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/08/25
 */
class ByteBufferCompareTest {

    @Test
    void testCompare() {
        // 分配比较
        allocateCompare();
        // 读写比较
        operateCompare();
    }

    /**
     * 直接内存 和 堆内存的 分配空间比较
     * 结论
     * |- 在数据量提升时,直接内存相比非直接内的申请,有很严重的性能问题
     */
    public static void allocateCompare() {
        // 操作次数
        int times = 10000000;

        long st = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            // ByteBuffer.allocate(int capacity) 分配一个新的字节缓冲区
            // 非直接内存分配申请
            ByteBuffer _x = ByteBuffer.allocate(2);
        }
        long et = System.currentTimeMillis();

        System.out.println("在进行 [" + times + " ]次分配操作时,堆内存分配耗时: [" + (et - st) + "ms ]");

        long std = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            // ByteBuffer.allocateDirect(int capacity) 分配新的直接字节缓冲区
            // 直接内存分配申请
            ByteBuffer _x = ByteBuffer.allocateDirect(2);
        }
        long etd = System.currentTimeMillis();

        System.out.println("在进行 [" + times + " ]次分配操作时,直接内存: [" + (etd - std) + "ms ]");
    }

    /**
     * 直接内存 和 堆内存的 读写性能比较
     * 结论
     * |- 直接内存在直接的 IO 操作上,在频繁的读写时,会有显著的性能提升
     */
    public static void operateCompare() {
        int times = 100000000;

        ByteBuffer buffer = ByteBuffer.allocate(2 * times);
        long st = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            //   putChar(char value) 用来写入 char 值的相对 put 方法
            buffer.putChar('a');
        }
        buffer.flip();
        for (int i = 0; i < times; i++) {
            buffer.getChar();
        }
        long et = System.currentTimeMillis();

        System.out.println("在进行 [" + times + " ]次读写操作时，非直接内存读写耗时: [" + (et - st) + "ms ]");

        ByteBuffer bufferDirect = ByteBuffer.allocateDirect(2 * times);
        long std = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            //  putChar(char value) 用来写入 char 值的相对 put 方法
            bufferDirect.putChar('a');
        }
        bufferDirect.flip();
        for (int i = 0; i < times; i++) {
            bufferDirect.getChar();
        }
        long etd = System.currentTimeMillis();

        System.out.println("在进行 [" + times + " ]次读写操作时，直接内存读写耗时: [" + (etd - std) + "ms ]");
    }
}