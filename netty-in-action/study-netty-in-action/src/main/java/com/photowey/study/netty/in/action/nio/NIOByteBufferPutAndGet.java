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
