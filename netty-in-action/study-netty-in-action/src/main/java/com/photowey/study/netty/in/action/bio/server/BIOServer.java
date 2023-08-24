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
package com.photowey.study.netty.in.action.bio.server;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@code BIOServer}
 *
 * @author photowey
 * @date 2022/03/08
 * @since 1.0.0
 */
public class BIOServer {

    private final ExecutorService executorService;
    private int port;
    private ServerSocket serverSocket;

    public BIOServer(int port) {
        this.port = port;
        executorService = Executors.newCachedThreadPool();
    }

    public void start() {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("--- >>> 服务器启动了 <<< ---");
            this.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        this.destroy();
    }

    public void run() {
        try {
            while (true) {
                System.out.println(String.format("线程信息 id =%d 名字=%s", Thread.currentThread().getId(), Thread.currentThread().getName()));
                System.out.println(" --- >>> 等待连接....");
                Socket socket = this.serverSocket.accept();
                System.out.println("<<< --- 连接到一个客户端");
                executorService.execute(() -> {
                    handleRequest(socket);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.stop();
        }
    }

    private void handleRequest(Socket socket) {
        byte[] buffer = new byte[1024];
        try (InputStream inputStream = socket.getInputStream()) {
            while (true) {
                System.out.println(String.format("处理连接-线程信息 id =%d 名字=%s", Thread.currentThread().getId(), Thread.currentThread().getName()));
                System.out.println(" --- >>> 等待Read....");
                int length = inputStream.read(buffer);
                if (length != -1) {
                    System.out.println("读取到的信息: " + new String(buffer, 0, length));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                System.out.println("--- 已关闭连接 ---");
            } catch (Exception e) {
            }
        }
    }

    public void destroy() {
        System.out.println("--- shutdown 线程池 ---");
        this.executorService.shutdown();
    }

}
