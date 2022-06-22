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
package com.photowey.mycat2.in.action;

import com.photowey.print.in.action.printer.AppPrinter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * {@code Mycat2App}
 *
 * @author photowey
 * @date 2022/06/15
 * @since 1.0.0
 */
@SpringBootApplication
public class Mycat2App {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Mycat2App.class, args);
        AppPrinter.print(applicationContext, false);
    }

}
