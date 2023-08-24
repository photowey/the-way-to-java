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
package com.photowey.netty.telnet.in.action.netty.session;

import com.photowey.netty.telnet.in.action.constant.NamedConstants;
import com.photowey.netty.telnet.in.action.exception.NamedException;
import com.photowey.netty.telnet.in.action.netty.port.PortSelector;
import com.photowey.netty.telnet.in.action.netty.server.NettyTelnetServer;
import com.photowey.netty.telnet.in.action.netty.server.TelnetServer;
import com.photowey.netty.telnet.in.action.pool.NamedThreadPool;
import com.photowey.netty.telnet.in.action.pool.registry.NamedThreadPoolRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * {@code StandardTelnetServerServiceImpl}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
@Slf4j
@Component
public class StandardTelnetServerServiceImpl implements TelnetServerService, SmartInitializingSingleton, DisposableBean {

    private static final int WORKER_THREAD_POOL_SIZE = 2;

    private int port = -1;

    private AtomicBoolean shutdown = new AtomicBoolean(false);

    private boolean enableTelnetServer = false;

    private TelnetServer telnetServer;

    public StandardTelnetServerServiceImpl() {
        this.walk();
    }

    public void walk() {
        this.enableTelnetServer = System.getProperty(NamedConstants.TELNET_SERVER_ENABLE, "true").equalsIgnoreCase("true");
        if (this.enableTelnetServer) {
            try {
                this.port = PortSelector.selectAvailablePort(NamedConstants.DEFAULT_TELNET_PORT, NamedConstants.DEFAULT_SELECT_PORT_SIZE);
            } catch (NumberFormatException e) {
                throw new NamedException(e);
            }
        }
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.init();
    }

    @Override
    public void destroy() throws Exception {
        this.dispose();
    }

    @Override
    public void run() {
        try {
            log.info("the telnet server listening on port: {}", this.port);
            NamedThreadPool workerPool = this.populateWorkerThreadPool();
            this.telnetServer = new NettyTelnetServer(port, workerPool.getExecutor());
            this.telnetServer.open();
        } catch (InterruptedException e) {
            throw new NamedException(e);
        }
    }

    @Override
    public void shutdown() {
        if (this.shutdown.compareAndSet(false, true)) {
            try {
                if (this.telnetServer != null) {
                    this.telnetServer.shutdown();
                    this.telnetServer = null;
                }
            } catch (Throwable t) {
                throw new NamedException(t);
            }
        }
    }

    @Override
    public void init() throws NamedException {
        if (this.enableTelnetServer) {
            this.run();
        } else {
            log.error("init action:the current telnet server not enabled");
        }
    }

    @Override
    public void dispose() throws NamedException {
        if (this.enableTelnetServer) {
            this.shutdown();
        } else {
            log.error("shutdown action:the current telnet server not enabled");
        }
    }

    @Override
    public int getPriority() {
        return HIGHEST_PRECEDENCE;
    }

    public NamedThreadPool populateWorkerThreadPool() {
        NamedThreadPool workerPool = new NamedThreadPool()
                .setCorePoolSize(WORKER_THREAD_POOL_SIZE)
                .setDaemon(true)
                .setThreadPoolName(NamedConstants.TELNET_SERVER_WORKER_THREAD_POOL_NAME);

        NamedThreadPoolRegistry.registerThreadPool(NamedConstants.TELNET_SERVER_WORKER_THREAD_POOL_NAME, workerPool);

        return workerPool;
    }

}