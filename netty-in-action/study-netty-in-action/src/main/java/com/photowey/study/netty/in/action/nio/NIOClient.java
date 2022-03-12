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
package com.photowey.study.netty.in.action.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * {@code NIOClient}
 *
 * @author photowey
 * @date 2022/03/08
 * @since 1.0.0
 */
public class NIOClient {

    public static void main(String[] args) {
        NIOClient client = new NIOClient();
        client.start();
    }

    public void start() {
        try {
            // 开启一个通道
            SocketChannel socketChannel = SocketChannel.open();
            // 非阻塞
            socketChannel.configureBlocking(false);
            // 设置提供服务的 ip+port
            InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

            // 连接-服务器
            if (!socketChannel.connect(inetSocketAddress)) {
                while (!socketChannel.finishConnect()) {
                    System.out.println("no blocking...");
                }
            }

            String hello = "Say hello from photowey~";
            ByteBuffer byteBuffer = ByteBuffer.wrap(hello.getBytes());
            socketChannel.write(byteBuffer);
            System.in.read();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
