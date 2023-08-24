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
package com.photowey.vertx.spring.boot.autoconfigure.server;

import com.photowey.vertx.spring.boot.autoconfigure.annotation.BlockingHandler;
import com.photowey.vertx.spring.boot.autoconfigure.property.VertxProperties;
import com.photowey.vertx.spring.boot.core.model.HandlerMapping;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * {@code HttpServerVerticle}
 *
 * @author photowey
 * @date 2022/02/17
 * @since 1.0.0
 */
@Slf4j
public class HttpServerVerticle extends AbstractVerticle {

    protected static ApplicationContext applicationContext;

    /**
     * <pre>
     *     {@literal @}Override
     *     public void start(Promise<Void> startPromise) throws Exception {
     *         vertx.createHttpServer().requestHandler(req -> {
     *             req.response()
     *                 .putHeader("content-type", "text/plain")
     *                 .end("Hello from Vert.x!");
     *         }).listen(8888, http -> {
     *             if (http.succeeded()) {
     *                 startPromise.complete();
     *                 System.out.println("HTTP server started on port 8888");
     *             } else {
     *                 startPromise.fail(http.cause());
     *             }
     *         });
     *     }
     * </pre>
     *
     * @param startPromise
     * @throws Exception
     */
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        VertxProperties props = this.applicationContext().getBean(VertxProperties.class);
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        int port = props.getPort();

        this.addHandlers(router, props.getHandlerMappings());

        server.requestHandler(router).listen(port, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                log.info("Vertx server started on port(s):{} (http) ", port);
            } else {
                startPromise.fail(http.cause());
            }
        });
    }

    private void addHandlers(Router router, List<HandlerMapping> handlerMappings) {
        for (HandlerMapping hm : handlerMappings) {
            for (String handlerName : hm.getHandlers()) {
                Handler<RoutingContext> handler = (Handler<RoutingContext>) this.applicationContext().getBean(handlerName, Handler.class);
                switch (hm.getMethod()) {
                    case "OPTIONS":
                        this.addHandler(router.options(hm.getPath()), handler);
                        break;
                    case "GET":
                        this.addHandler(router.get(hm.getPath()), handler);
                        break;
                    case "HEAD":
                        this.addHandler(router.head(hm.getPath()), handler);
                        break;
                    case "POST":
                        this.addHandler(router.post(hm.getPath()), handler);
                        break;
                    case "PUT":
                        this.addHandler(router.put(hm.getPath()), handler);
                        break;
                    case "PATCH":
                        this.addHandler(router.patch(hm.getPath()), handler);
                        break;
                    case "DELETE":
                        this.addHandler(router.delete(hm.getPath()), handler);
                        break;
                    case "TRACE":
                        this.addHandler(router.trace(hm.getPath()), handler);
                        break;
                    case "CONNECT":
                        this.addHandler(router.connect(hm.getPath()), handler);
                        break;
                    default:
                        // ignore
                        break;
                }
            }
        }
    }

    private void addHandler(Route route, Handler<RoutingContext> handler) {
        if (this.isBlockingHandler(handler)) {
            route.blockingHandler(handler);
        } else {
            route.handler(handler);
        }
    }

    private boolean isBlockingHandler(Handler<?> handler) {
        return handler.getClass().isAnnotationPresent(BlockingHandler.class);
    }

    public static void setApplicationContext(ApplicationContext ctx) {
        applicationContext = ctx;
    }

    private ApplicationContext applicationContext() {
        return applicationContext;
    }
}
