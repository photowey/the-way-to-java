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
package com.photowey.vertx.starter.config;

import com.photowey.vertx.starter.domain.config.HttpConfig;
import com.photowey.vertx.starter.event.HelloEventVerticle;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * {@code ConfigApp}
 *
 * @author photowey
 * @date 2022/02/20
 * @since 1.0.0
 */
public class ConfigApp {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.exceptionHandler(Throwable::printStackTrace).deployVerticle(new ConfigVerticle());
    }

    public static class ConfigVerticle extends AbstractVerticle {

        // @Override
        public void start0(Promise<Void> startPromise) throws Exception {
            // Use conf/config.json from resources/classpath
            ConfigRetriever configRetriever = ConfigRetriever.create(vertx);
            configRetriever.getConfig(config -> {
                if (config.succeeded()) {
                    JsonObject configJson = config.result();
                    System.out.println(configJson.encodePrettily());
                }
            });
        }

        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            ConfigStoreOptions defaultConfig = new ConfigStoreOptions().setType("file").setFormat("json").setConfig(new JsonObject().put("path", "conf/config.json"));
            ConfigRetrieverOptions opts = new ConfigRetrieverOptions().addStore(defaultConfig);

            ConfigRetriever configRetriever = ConfigRetriever.create(vertx, opts);
            configRetriever.getConfig(asyncResult -> this.handleConfigResults(startPromise, asyncResult));
        }

        private void handleConfigResults(Promise<Void> startPromise, io.vertx.core.AsyncResult<JsonObject> asyncResult) {
            if (asyncResult.succeeded()) {
                vertx.deployVerticle(new HelloEventVerticle());

                Router router = Router.router(vertx);
                router.get("/api/v1/hello").handler(this::helloVertxHandler);
                router.get("/api/v1/hello/:name").handler(this::helloNameHandler);

                JsonObject config = asyncResult.result();
                // JsonObject httpNode = config.getJsonObject("http");
                // Integer httpPort = httpNode.getInteger("port", 8888);

                HttpConfig httpConfig = config.mapTo(HttpConfig.class);
                int httpPort = httpConfig.getHttp().getPort();

                vertx
                    .createHttpServer()
                    .requestHandler(router)
                    .listen(httpPort, http -> {
                        if (http.succeeded()) {
                            startPromise.complete();
                            System.out.println("\n------------------------------------HTTP server started on port(s):" + httpPort);
                        } else {
                            startPromise.fail(http.cause());
                        }
                    });

            } else {
                startPromise.fail("\n------------------------------------ Unable to load configuration.");
            }
        }

        private void helloVertxHandler(RoutingContext ctx) {
            vertx.eventBus().request("hello.vertx.addr", "", reply -> {
                ctx.request().response().end((String) reply.result().body());
            });
        }

        private void helloNameHandler(RoutingContext ctx) {
            String name = ctx.pathParam("name");
            vertx.eventBus().request("hello.name.addr", name, reply -> {
                ctx.request().response().end((String) reply.result().body());
            });
        }
    }
}
