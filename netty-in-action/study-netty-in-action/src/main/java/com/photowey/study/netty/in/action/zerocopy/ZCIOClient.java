/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.study.netty.in.action.zerocopy;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * {@code ZCIOClient}
 *
 * @author photowey
 * @date 2022/03/12
 * @since 1.0.0
 */
public class ZCIOClient {

    public static void main(String[] args) {

        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 7923));

            FileInputStream input = new FileInputStream(new File("D:\\common-files\\txt\\JavaNIO.pdf"));
            FileChannel inputChannel = input.getChannel();
            long startTime = System.currentTimeMillis();
            // FIXME BigFile
            long transferCount = inputChannel.transferTo(0, inputChannel.size(), socketChannel);
            System.out.println("total bytes =" + transferCount + " use time:" + (System.currentTimeMillis() - startTime));
            inputChannel.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
