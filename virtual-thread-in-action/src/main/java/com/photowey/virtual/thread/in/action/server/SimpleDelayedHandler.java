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
package com.photowey.virtual.thread.in.action.server;

import com.photowey.virtual.thread.in.action.controller.HealthController;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * {@code SimpleDelayedHandler}
 *
 * @author photowey
 * @date 2022/12/25
 * @since 1.0.0
 */
@Slf4j
public class SimpleDelayedHandler implements HttpHandler {

    private final List<SimpleWork> workers = new ArrayList<>();
    private final int workersCount = 50;
    private final boolean withLock;
    AtomicLong id = new AtomicLong();

    public SimpleDelayedHandler(boolean withLock) {
        this.withLock = withLock;
        if (withLock) {
            for (int i = 0; i < workersCount; i++) {
                workers.add(new SimpleWork());
            }
        }
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        String response = null;
        if (withLock) {
            response = workers.get((int) (id.incrementAndGet() % workersCount)).doJob();
        } else {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            response = new HealthController.Status("Up").status() + id.incrementAndGet();
        }

        Thread thread = Thread.currentThread();
        log.info("handle request, thread:[{}:{}], isVirtual:{}", thread.getName(), thread.threadId(), thread.isVirtual());

        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
