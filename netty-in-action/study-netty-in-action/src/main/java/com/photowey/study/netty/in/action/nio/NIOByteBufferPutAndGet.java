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
package com.photowey.study.netty.in.action.nio;

import java.nio.ByteBuffer;

/**
 * {@code NIOByteBufferPutAndGet}
 *
 * @author photowey
 * @date 2022/03/12
 * @since 1.0.0
 */
public class NIOByteBufferPutAndGet {

    public void putAndGet() {

        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        // Put
        byteBuffer.putInt(128);
        byteBuffer.putLong(128);
        byteBuffer.putChar('&');
        byteBuffer.putShort((short) 32);

        byteBuffer.flip();

        // Get
        System.out.println("--------------------------- read --------------------------- ");
        System.out.println("byteBuffer.putInt()==" + byteBuffer.getInt());
        System.out.println("byteBuffer.getLong()==" + byteBuffer.getLong());
        System.out.println("byteBuffer.getChar()==" + byteBuffer.getChar());
        System.out.println("byteBuffer.getShort()==" + byteBuffer.getShort());

    }

}
