package com.photowey.study.netty.in.action.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * {@code FileChannelReaderAndWriter}
 *
 * @author photowey
 * @date 2022/03/12
 * @since 1.0.0
 */
public class FileChannelReaderAndWriter {

    public void readAndWrite() {

        try (FileInputStream input = new FileInputStream("poetry.txt");
             FileOutputStream output = new FileOutputStream("poetry-read-write.txt");) {

            FileChannel inputChannel = input.getChannel();
            FileChannel outputChannel = output.getChannel();

            // FIXME
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);

            while (true) {
                /**
                 * <pre>
                 *     public Buffer clear() {
                 *         position = 0;
                 *         limit = capacity;
                 *         mark = -1;
                 *         return this;
                 *     }
                 * </pre>
                 */
                byteBuffer.clear();
                int read = inputChannel.read(byteBuffer);
                System.out.println("inputChannel.read == " + read);
                if (read == -1) {
                    break;
                }

                byteBuffer.flip();

                outputChannel.write(byteBuffer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
