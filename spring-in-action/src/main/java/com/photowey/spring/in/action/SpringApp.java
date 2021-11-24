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
package com.photowey.spring.in.action;

import com.photowey.spring.in.action.dynamic.annotation.EnableDynamicInjected;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * {@code SpringApp}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
@Slf4j
@SpringBootApplication
@EnableDynamicInjected
@EnableAspectJAutoProxy
public class SpringApp {

    private static final String SERVER_SSL_KEY = "server.ssl.key-store";
    private static final String APPLICATION_NAME = "spring.application.name";
    private static final String PROFILES_ACTIVE = "spring.profiles.active";
    private static final String SERVER_PORT = "server.port";

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringApp.class, args);
        print(applicationContext);
    }

    public static void print(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        try {
            String protocol = "http";
            String applicationName = environment.getProperty(APPLICATION_NAME);
            if (null != environment.getProperty(SERVER_SSL_KEY)) {
                protocol = "https";
            }
            log.info("\n----------------------------------------------------------\n\t" +
                            "Bootstrap: the '{}' is Success!\n\t" +
                            "Application: '{}' is running! Access URLs:\n\t" +
                            "Local: \t\t{}://localhost:{}\n\t" +
                            "External: \t{}://{}:{}\n\t" +
                            "Swagger: \t{}://{}:{}/doc.html\n\t" +
                            "Profile(s): {}\n----------------------------------------------------------",
                    applicationName + " Context",
                    applicationName,
                    protocol, environment.getProperty(SERVER_PORT),
                    protocol, InetAddress.getLocalHost().getHostAddress(), environment.getProperty(SERVER_PORT),
                    protocol, InetAddress.getLocalHost().getHostAddress(), environment.getProperty(SERVER_PORT),
                    environment.getProperty(PROFILES_ACTIVE)
            );
        } catch (UnknownHostException e) {
            // Ignore
        }
    }
}
