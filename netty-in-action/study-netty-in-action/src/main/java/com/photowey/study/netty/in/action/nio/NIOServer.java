/*
 * Copyright © 2021 the original author or authors.
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
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * {@code NIOServer}
 *
 * @author photowey
 * @date 2022/03/12
 * @since 1.0.0
 */
public class NIOServer {

    public static void main(String[] args) {
        NIOServer server = new NIOServer();
        server.start();
    }

    public void start() {
        this.run();
    }

    public void run() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
             Selector selector = Selector.open()) {

            serverSocketChannel.socket().bind(new InetSocketAddress(6666));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("after server register SelectionKey.size = " + selector.keys().size());

            while (true) {
                // wait and timeout
                if (selector.select(1000) == 0) {
                    System.out.println("no connect...");
                    continue;
                }

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                System.out.println("after select() SelectionKey.size = " + selectionKeys.size());
                Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey selectionKey = keyIterator.next();

                    if (selectionKey.isAcceptable()) {
                        // create and register
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        System.out.println("the client connects successfully and creates a socketChannel == " + socketChannel.hashCode());
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                        System.out.println("after client register, SelectionKey = " + selector.keys().size());
                    }

                    if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                        socketChannel.read(byteBuffer);
                        byte[] array = byteBuffer.array();
                        System.out.println(" read client message.length == " + array.length);
                        System.out.println(" read client message == " + new String(array));
                    }
                }

                // remove
                keyIterator.remove();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
