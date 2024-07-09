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
package com.photowey.spring.project.infras.in.action;

import io.github.photowey.spring.infras.core.printer.AppPrinter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * {@code App}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/28
 */
@SpringBootApplication
public class App {

    /*
----------------------------------------------------------
	Bootstrap: the 'spring-project-infras-core-in-action Context' is Success!
	Application: 'spring-project-infras-core-in-action' is running! Access URLs:
	Local: 		http://localhost:7923
	External: 	http://192.168.1.101:7923
	Swagger: 	http://192.168.1.101:7923/swagger-ui/index.html
	Actuator: 	http://192.168.1.101:7923/actuator/health
	Profile(s): dev
----------------------------------------------------------
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(App.class, args);
        AppPrinter.print(applicationContext);
        // http://192.168.1.101:7923/doc.html
        //AppPrinter.print(applicationContext, AppContext.swaggerAppContext());
        // http://192.168.1.101:7923/swagger-ui/index.html
        //AppPrinter.print(applicationContext, AppContext.swaggerAppContext("swagger-ui/index.html"));
    }
}
