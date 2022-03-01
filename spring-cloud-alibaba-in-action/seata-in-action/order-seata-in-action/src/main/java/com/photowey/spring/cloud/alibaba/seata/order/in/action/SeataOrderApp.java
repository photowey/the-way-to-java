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
package com.photowey.spring.cloud.alibaba.seata.order.in.action;

import com.photowey.print.in.action.printer.AppPrinter;
import com.photowey.spring.cloud.alibaba.seata.in.action.mysql.support.annotation.EnableMybatisAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * {@code SeataOrderApp}
 *
 * @author photowey
 * @date 2022/03/01
 * @since 1.0.0
 */
@EnableFeignClients
@EnableDiscoveryClient
@EnableMybatisAutoConfigure
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SeataOrderApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SeataOrderApp.class, args);
        AppPrinter.print(applicationContext, false);
    }
}