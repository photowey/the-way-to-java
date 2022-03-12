package com.photowey.study.netty.in.action.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * {@code FileChannelTransferFrom}
 *
 * @author photowey
 * @date 2022/03/12
 * @since 1.0.0
 */
public class FileChannelTransferFrom {

    public void transferFrom() {
        try (FileInputStream input = new FileInputStream("doc/lp-one-more-light.jpg");
             FileOutputStream output = new FileOutputStream("doc/lp-one-more-light-read-write.jpg");) {

            FileChannel inputChannel = input.getChannel();
            FileChannel outputChannel = output.getChannel();

            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

            inputChannel.close();
            outputChannel.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
