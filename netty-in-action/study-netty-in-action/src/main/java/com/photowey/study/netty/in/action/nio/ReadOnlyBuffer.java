package com.photowey.study.netty.in.action.nio;

import java.nio.ByteBuffer;

/**
 * {@code ReadOnlyBuffer}
 *
 * @author photowey
 * @date 2022/03/12
 * @since 1.0.0
 */
public class ReadOnlyBuffer {

    public static final int BUFFER_SIZE = 64;

    public void readOnly() {

        ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        for (int i = 0; i < BUFFER_SIZE; i++) {
            byteBuffer.put((byte) i);
        }

        byteBuffer.flip();

        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        while (readOnlyBuffer.hasRemaining()) {
            System.out.println("readOnlyBuffer==" + readOnlyBuffer.get());
        }

        readOnlyBuffer.put((byte) 127);
    }

}
