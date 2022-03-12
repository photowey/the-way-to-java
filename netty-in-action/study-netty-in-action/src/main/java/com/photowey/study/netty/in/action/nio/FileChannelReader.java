package com.photowey.study.netty.in.action.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * {@code FileChannelWrite}
 *
 * @author photowey
 * @date 2022/03/12
 * @since 1.0.0
 */
public class FileChannelReader {

    public void read() {
        File file = new File("poetry.txt");
        try (FileInputStream input = new FileInputStream(file)) {
            FileChannel inputChannel = input.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
            inputChannel.read(byteBuffer);
            System.out.println("----------------------------- read succeed -----------------------------");
            System.out.println(new String(byteBuffer.array()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
