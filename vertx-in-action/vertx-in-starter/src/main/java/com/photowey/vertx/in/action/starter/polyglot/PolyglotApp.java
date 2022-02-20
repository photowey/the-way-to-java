/*
 * Copyright © 2022 photowey (photowey@gmail.com)
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
package com.photowey.vertx.in.action.starter.polyglot;

import com.photowey.vertx.in.action.starter.domain.config.HttpConfig;
import com.photowey.vertx.in.action.starter.hello.GreetingVerticle;
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
 * {@code PolyglotApp}
 *
 * @author photowey
 * @date 2022/02/20
 * @since 1.0.0
 */
public class PolyglotApp {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.exceptionHandler(Throwable::printStackTrace).deployVerticle(new PolyglotVerticle());
    }

    public static class PolyglotVerticle extends AbstractVerticle {

        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            vertx.deployVerticle(new GreetingVerticle());
            vertx.deployVerticle("Name.groovy");

            Router router = Router.router(vertx);

            router.get("/api/v1/hello").handler(this::helloVertxHandler);
            router.get("/api/v1/hello/:name").handler(this::helloNameHandler);

            this.doConfig(startPromise, router);
        }

        private void doConfig(Promise<Void> startPromise, Router router) {

            ConfigStoreOptions defaultConfig = new ConfigStoreOptions()
                .setType("file").setFormat("json").setConfig(new JsonObject().put("path", "conf/config.json"));
            ConfigRetrieverOptions opts = new ConfigRetrieverOptions().addStore(defaultConfig);

            ConfigRetriever configRetriever = ConfigRetriever.create(vertx, opts);
            configRetriever.getConfig(asyncResult -> this.handleConfigResults(startPromise, router, asyncResult));
        }

        private void handleConfigResults(Promise<Void> startPromise, Router router, io.vertx.core.AsyncResult<JsonObject> asyncResult) {
            if (asyncResult.succeeded()) {
                JsonObject config = asyncResult.result();
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
