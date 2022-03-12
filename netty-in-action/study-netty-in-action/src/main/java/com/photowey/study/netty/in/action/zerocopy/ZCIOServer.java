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

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * {@code ZCIOServer}
 *
 * @author photowey
 * @date 2022/03/12
 * @since 1.0.0
 */
public class ZCIOServer {

    public static void main(String[] args) {

        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            InetSocketAddress address = new InetSocketAddress(7923);
            serverSocketChannel.socket().bind(address);
            ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                int read = 0;
                while (read != -1) {
                    try {
                        read = socketChannel.read(byteBuffer);
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }

                byteBuffer.rewind();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
