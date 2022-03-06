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
package com.photowey.vertx.starter.mysql;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

/**
 * {@code MysqlApp}
 *
 * @author photowey
 * @date 2022/02/20
 * @since 1.0.0
 */
public class MysqlApp {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.exceptionHandler(Throwable::printStackTrace).deployVerticle(new MysqlVerticle());
    }

    public static class MysqlVerticle extends AbstractVerticle {

        /**
         * <pre>
         * {
         *   "id" : 1494377100172378113,
         *   "employee_no" : "2021109527Meta",
         *   "org_id" : 2021109527,
         *   "org_name" : "宇宙漫游者Meta",
         *   "order_no" : 1024,
         *   "status" : 1,
         *   "remark" : "我是备注Meta",
         *   "gmt_create" : "2022-02-18T02:24",
         *   "create_by" : "UserInfoMeta",
         *   "gmt_modified" : "2022-02-18T02:24",
         *   "update_by" : "UserInfoMeta",
         *   "deleted" : 1
         * }
         * </pre>
         *
         * @param startPromise {@link Promise<Void> }
         * @throws Exception
         */
        @Override
        public void start(Promise<Void> startPromise) throws Exception {

            MySQLConnectOptions connectOptions = new MySQLConnectOptions();
            connectOptions.setHost("192.168.0.11").setPort(3307).setUser("root").setPassword("root").setDatabase("vertx-in-action")
                .setCharset("utf8mb4").setUseAffectedRows(true);

            PoolOptions poolOptions = new PoolOptions();
            poolOptions.setMaxSize(4).setMaxWaitQueueSize(-1);

            MySQLPool mySQLPool = MySQLPool.pool(vertx, connectOptions, poolOptions);

            mySQLPool.getConnection()
                .compose(connection -> connection.preparedQuery("SELECT * FROM sys_employee WHERE id = ?").execute(Tuple.of(1494377100172378113L)).onComplete(handler -> connection.close()))
                .onSuccess(rs -> {
                    for (Row row : rs) {
                        System.out.println("the index of column is: \n" + row.toJson().encodePrettily());
                    }
                }).onFailure(Throwable::printStackTrace);
        }
    }

}
