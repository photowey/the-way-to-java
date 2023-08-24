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
package com.photowey.virtual.thread.in.action;

import com.photowey.virtual.thread.in.action.server.SimpleDelayedHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

@Slf4j
//@SpringBootApplication
public class App {

    public static void main(String[] args) throws IOException {
        //SpringApplication.run(App.class, args);
        runServer(true, true);
    }

    private static void runServer(boolean virtual, boolean withLock) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(7923), 0);
        httpServer.createContext("/tnt", new SimpleDelayedHandler(withLock));

        if (virtual) {
            httpServer.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        } else {
            httpServer.setExecutor(Executors.newFixedThreadPool(200));
        }
        httpServer.start();

        log.info("Tomcat started on port(s): {} (http) with context path '{}'", 7923, "tnt");
    }
}
