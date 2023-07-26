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
package com.photowey.nio.in.action.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Scanner;

/**
 * {@code SelectorTest}
 *
 * @author photowey
 * @date 2023/07/23
 * @since 1.0.0
 */
class SelectorTest {

    public static class Server {

        public static void main(String[] args) throws Exception {
            Server server = new Server();
            server.start();
        }

        public void start() throws Exception {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(9527));
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        SocketChannel client = serverSocketChannel.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer clientByteBuffer = ByteBuffer.allocate(1024);
                        int length = 0;
                        while ((length = channel.read(clientByteBuffer)) > 0) {
                            clientByteBuffer.flip();
                            System.out.println(new String(clientByteBuffer.array(), 0, length));
                            clientByteBuffer.clear();
                        }
                    } else if (selectionKey.isWritable()) {

                    } else if (selectionKey.isConnectable()) {

                    }
                    iterator.remove();
                }
            }
        }
    }

    public static class Client {

        public static void main(String[] args) throws Exception {
            Client client = new Client();
            client.start();
        }

        public void start() throws Exception {
            // 1.SocketChannel
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9527));
            // 2.configureBlocking
            socketChannel.configureBlocking(false);
            // 3.ByteBuffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String word = scanner.next();
                // 4.write
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                buffer.put((formatter.format(LocalDateTime.now()) + ": " + word).getBytes(StandardCharsets.UTF_8));
                // 5.flip
                buffer.flip();
                // 6.write
                socketChannel.write(buffer);
                // 7.clear
                buffer.clear();
            }
        }

    }

}
