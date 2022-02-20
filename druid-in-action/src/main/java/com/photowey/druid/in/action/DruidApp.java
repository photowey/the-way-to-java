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
package com.photowey.druid.in.action;

import com.photowey.print.in.action.printer.AppPrinter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * {@code DruidApp}
 *
 * @author photowey
 * @date 2022/02/20
 * @since 1.0.0
 */
@SpringBootApplication
@MapperScan("com.photowey.druid.in.action.repository")
public class DruidApp {

    /**
     * 参考 公众号(https://mp.weixin.qq.com/s/dROmektt0sZe9qEwvcij_w)
     *
     * @param args
     * @see * https://mp.weixin.qq.com/s/dROmektt0sZe9qEwvcij_w
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(DruidApp.class, args);
        AppPrinter.print(applicationContext, false, (log) -> {
            log.info("\n----------------------------------------------------------\n" +
                    "the druid stat url: http://localhost:9527/druid/index.html\n" +
                    "----------------------------------------------------------");
        });
    }

}
