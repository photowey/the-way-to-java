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
 * {@code BufferMethodTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/08/25
 */
class BufferMethodTest {

    @Test
    void testMethods() {
        System.out.println("--------Test get----------");
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.put((byte) 'a') // 0
                .put((byte) 'b') // 1
                .put((byte) 'c') // 2
                .put((byte) 'd') // 3
                .put((byte) 'e') // 4
                .put((byte) 'f'); // 5

        // before flip()java.nio.HeapByteBuffer[pos=6 lim=32 cap=32]
        System.out.println("before flip()" + buffer);

        // 转换为读取模式
        buffer.flip();

        // before get():java.nio.HeapByteBuffer[pos=0 lim=6 cap=32]
        System.out.println("before get():" + buffer);

        // a
        System.out.println((char) buffer.get());

        // after get():java.nio.HeapByteBuffer[pos=1 lim=6 cap=32]
        System.out.println("after get():" + buffer);

        // get(index) 不影响 position 的值

        // c
        System.out.println((char) buffer.get(2));

        // after get(index):java.nio.HeapByteBuffer[pos=1 lim=6 cap=32]
        System.out.println("after get(index):" + buffer);
        byte[] dst = new byte[10];

        // position 移动两位
        buffer.get(dst, 0, 2);
        // after get(dst, 0, 2):java.nio.HeapByteBuffer[pos=3 lim=6 cap=32]
        System.out.println("after get(dst, 0, 2):" + buffer);
        // dst:bc        EOF
        System.out.println("dst:" + new String(dst));

        System.out.println("--------Test put----------");
        ByteBuffer bb = ByteBuffer.allocate(32);
        // before put(byte):java.nio.HeapByteBuffer[pos=0 lim=32 cap=32]
        System.out.println("before put(byte):" + bb);
        // after put(byte):java.nio.HeapByteBuffer[pos=1 lim=32 cap=32]
        System.out.println("after put(byte):" + bb.put((byte) 'z'));
        // put(2, (byte) 'c') 不改变 position 的位置
        bb.put(2, (byte) 'c');
        // after put(2,(byte) 'c'):java.nio.HeapByteBuffer[pos=1 lim=32 cap=32]
        System.out.println("after put(2,(byte) 'c'):" + bb);
        // z c                             EOF
        System.out.println(new String(bb.array()));

        bb.put(buffer);
        // after put(buffer):java.nio.HeapByteBuffer[pos=4 lim=32 cap=32]
        System.out.println("after put(buffer):" + bb);
        // zdef                            EOF
        System.out.println(new String(bb.array()));

        System.out.println("--------Test reset----------");
        buffer = ByteBuffer.allocate(20);

        // buffer = java.nio.HeapByteBuffer[pos=0 lim=20 cap=20]
        System.out.println("buffer = " + buffer);
        buffer.clear();
        buffer.position(5); // 移动 position 到 5
        buffer.mark(); // 记录当前 position 的位置
        buffer.position(10); // 移动 position 到 10
        // before reset:java.nio.HeapByteBuffer[pos=10 lim=20 cap=20]
        System.out.println("before reset:" + buffer);
        buffer.reset(); // 复位 position 到记录的地址
        // after reset:java.nio.HeapByteBuffer[pos=5 lim=20 cap=20]
        System.out.println("after reset:" + buffer);

        System.out.println("--------Test rewind--------");
        buffer.clear();
        // 移动 position 到 10
        buffer.position(10);
        // 限定最大可写入的位置为 15
        buffer.limit(15);

        // before rewind:java.nio.HeapByteBuffer[pos=10 lim=15 cap=20]
        System.out.println("before rewind:" + buffer);
        buffer.rewind(); // 将 position 设回 0
        // after rewind:java.nio.HeapByteBuffer[pos=0 lim=15 cap=20]
        System.out.println("after rewind:" + buffer);

        System.out.println("--------Test compact--------");
        buffer.clear();
        // 放入 4 个字节, position 移动到下个可写入的位置,也就是 4
        buffer.put("abcd".getBytes());
        // before compact:java.nio.HeapByteBuffer[pos=4 lim=20 cap=20]
        System.out.println("before compact:" + buffer);
        // abcd                EOF
        System.out.println(new String(buffer.array()));
        // 将 position 设回 0,并将 limit 设置成之前 position 的值
        buffer.flip();
        // after flip:java.nio.HeapByteBuffer[pos=0 lim=4 cap=20]
        System.out.println("after flip:" + buffer);
        // 从 Buffer 中读取数据的例子, 每读一次 position 移动一次

        // a
        System.out.println((char) buffer.get());
        // b
        System.out.println((char) buffer.get());
        // c
        System.out.println((char) buffer.get());
        // after three gets:java.nio.HeapByteBuffer[pos=3 lim=4 cap=20]
        System.out.println("after three gets:" + buffer);
        // abcd                EOF
        System.out.println(new String(buffer.array()));
        // compact() 方法将所有未读的数据('d')拷贝到 Buffer 起始处 [0]
        // 然后将 position 设到最后一个未读元素正后面 [1]
        buffer.compact();
        // after compact:java.nio.HeapByteBuffer[pos=1 lim=20 cap=20]
        System.out.println("after compact:" + buffer);
        // dbcd                EOF
        // ~^~~
        System.out.println(new String(buffer.array()));
    }

}