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
package com.photowey.study.netty.in.action.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * {@code ScatteringAndGatheringHandler}
 *
 * @author photowey
 * @date 2022/03/12
 * @since 1.0.0
 */
public class ScatteringAndGatheringHandler {

    public void scatteringAndGathering() {

        // FIXME
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {

            InetSocketAddress inetSocketAddress = new InetSocketAddress(9527);
            serverSocketChannel.socket().bind(inetSocketAddress);

            ByteBuffer[] byteBuffers = new ByteBuffer[2];
            byteBuffers[0] = ByteBuffer.allocate(5);
            byteBuffers[1] = ByteBuffer.allocate(3);

            SocketChannel socketChannel = serverSocketChannel.accept();
            int messageLength = 8;

            while (true) {
                int byteRead = 0;
                while (byteRead < messageLength) {
                    long length = socketChannel.read(byteBuffers);
                    if (length > 0) {
                        byteRead += length;
                        System.out.println("byteRead=" + byteRead);
                        Arrays.asList(byteBuffers).stream()
                                .map(buffer -> "position=" + buffer.position() + ", limit=" + buffer.limit()).forEach(System.out::println);
                    }
                }

                Arrays.asList(byteBuffers).forEach(ByteBuffer::flip);
                long byteWrite = 0;
                while (byteWrite < messageLength) {
                    long length = socketChannel.write(byteBuffers);
                    if (length > 0) {
                        byteWrite += length;
                    }
                }

                Arrays.asList(byteBuffers).forEach(ByteBuffer::clear);
                System.out.println("byteRead:=" + byteRead + " byteWrite=" + byteWrite + ", messageLength=" + messageLength);
            }

        } catch (Exception e) {
        }
    }

}
