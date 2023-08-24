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
package com.photowey.vertx.starter;

import com.photowey.vertx.starter.event.HelloEventVerticle;
import io.vertx.core.*;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.cluster.infinispan.InfinispanClusterManager;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.security.SecureRandom;
import java.util.Random;

public class MainVerticle extends AbstractVerticle {

    // @Override
    public void start0(Promise<Void> startPromise) throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
            req.response()
                .putHeader("content-type", "text/plain")
                .end("Hello from Vert.x!");
        }).listen(8888, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                System.out.println("HTTP server started on port 8888");
            } else {
                startPromise.fail(http.cause());
            }
        });
    }

    // @Override
    public void start1(Promise<Void> startPromise) throws Exception {
        Router router = Router.router(vertx);
        router.get("/api/v1/hello").handler(this::helloVertx);
        router.get("/api/v1/hello/:name").handler(this::helloName);

        vertx
            .createHttpServer()
            .requestHandler(router)
            .listen(8888, http -> {
                if (http.succeeded()) {
                    startPromise.complete();
                    System.out.println("HTTP server started on port 8888");
                } else {
                    startPromise.fail(http.cause());
                }
            });
    }

    // @Override
    public void start2(Promise<Void> startPromise) throws Exception {
        vertx.deployVerticle(new HelloEventVerticle());
        Router router = Router.router(vertx);
        router.get("/api/v1/hello").handler(this::helloVertxEvent);
        router.get("/api/v1/hello/:name").handler(this::helloNameEvent);

        vertx
            .createHttpServer()
            .requestHandler(router)
            .listen(8888, http -> {
                if (http.succeeded()) {
                    startPromise.complete();
                    System.out.println("HTTP server started on port 8888");
                } else {
                    startPromise.fail(http.cause());
                }
            });
    }

    // @Override
    public void start3(Promise<Void> startPromise) throws Exception {
        // Can't specify > 1 instances for already created verticle
        // 证明保证是单线程-执行
        DeploymentOptions opts = new DeploymentOptions().setWorker(true).setInstances(1);

        // vertx.deployVerticle(HelloEventVerticle.class.getName(), opts);
        vertx.deployVerticle(new HelloEventVerticle(), opts);

        Router router = Router.router(vertx);
        router.get("/api/v1/hello").handler(this::helloVertxEvent);
        router.get("/api/v1/hello/:name").handler(this::helloNameEvent);

        vertx
            .createHttpServer()
            .requestHandler(router)
            .listen(8888, http -> {
                if (http.succeeded()) {
                    startPromise.complete();
                    System.out.println("HTTP server started on port 8888");
                } else {
                    startPromise.fail(http.cause());
                }
            });
    }

    /**
     * $ java -jar .\target\vertx-starter-fat.jar -Cluster -Djava.net.preferIPv4Stack=true -Dhttp.port=7777
     *
     * @param startPromise
     * @throws Exception
     */
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        vertx.deployVerticle(new HelloEventVerticle());

        Router router = Router.router(vertx);
        router.get("/api/v1/hello").handler(this::helloVertxEvent);
        router.get("/api/v1/hello/:name").handler(this::helloNameEvent);

        int httpPort;
        try {
            httpPort = Integer.parseInt(System.getProperty("http.port", "8888"));
            System.out.println("\n------------------------the config port:" + httpPort + "------------------------\n");
        } catch (NumberFormatException e) {
            httpPort = 8888;
        }

        Random random = new SecureRandom();
        int nextInt = random.nextInt(100);
        final int port = httpPort + nextInt;

        // Cluster mode
        ClusterManager mgr = new InfinispanClusterManager();
        VertxOptions options = new VertxOptions().setClusterManager(mgr);

        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                vertx
                    .createHttpServer()
                    .requestHandler(router)
                    .listen(port, http -> {
                        if (http.succeeded()) {
                            startPromise.complete();
                            System.out.println("\n----------------------HTTP server started on port(s):" + port + "\n");
                        } else {
                            startPromise.fail(http.cause());
                        }
                    });
            } else {
                // failed!
            }
        });

    }

    void helloVertx(RoutingContext ctx) {
        ctx.request().response().end("Hello from Vert.x!");
    }

    void helloName(RoutingContext ctx) {
        String name = ctx.pathParam("name");
        ctx.request().response().end(String.format("Hello %s!", name));
    }

    void helloVertxEvent(RoutingContext ctx) {
        vertx.eventBus().request("hello.vertx.addr", "", reply -> {
            ctx.request().response().end((String) reply.result().body());
        });
    }

    void helloNameEvent(RoutingContext ctx) {
        String name = ctx.pathParam("name");
        vertx.eventBus().request("hello.name.addr", name, reply -> {
            ctx.request().response().end((String) reply.result().body());
        });
    }
}
