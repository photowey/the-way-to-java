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
package com.photowey.print.in.action.printer;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.function.Consumer;

/**
 * {@code AppPrinter}
 *
 * @author photowey
 * @date 2021/11/26
 * @since 1.0.0
 */
@Slf4j
public class AppPrinter {

    private static final String SERVER_SSL_KEY = "server.ssl.key-store";
    private static final String APPLICATION_NAME = "spring.application.name";
    private static final String PROFILES_ACTIVE = "spring.profiles.active";
    private static final String SERVER_PORT = "server.port";

    public static void print(ConfigurableApplicationContext applicationContext) {
        print(applicationContext, true);
    }

    public static void print(ConfigurableApplicationContext applicationContext, boolean swagger) {
        print(applicationContext, swagger, (log) -> {
        });
    }

    public static void print(ConfigurableApplicationContext applicationContext, boolean swagger, Consumer<Logger> callback) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String protocol = "http";
        String app = environment.getProperty(APPLICATION_NAME);
        String port = environment.getProperty(SERVER_PORT);
        String profileActive = environment.getProperty(PROFILES_ACTIVE);
        if (null != environment.getProperty(SERVER_SSL_KEY)) {
            protocol = "https";
        }
        try {
            String host = InetAddress.getLocalHost().getHostAddress();
            if (swagger) {
                log.info("\n----------------------------------------------------------\n\t" +
                                "Bootstrap: the '{}' is Success!\n\t" +
                                "Application: '{}' is running! Access URLs:\n\t" +
                                "Local: \t\t{}://localhost:{}\n\t" +
                                "External: \t{}://{}:{}\n\t" +
                                "Swagger: \t{}://{}:{}/doc.html\n\t" +
                                "Actuator: \t{}://{}:{}/actuator/health\n\t" +
                                "Profile(s): {}\n----------------------------------------------------------",
                        app + " Context",
                        app,
                        protocol, port,
                        protocol, host, port,
                        protocol, host, port,
                        protocol, host, port,
                        profileActive
                );
            } else {
                log.info("\n----------------------------------------------------------\n\t" +
                                "Bootstrap: the '{}' is Success!\n\t" +
                                "Application: '{}' is running! Access URLs:\n\t" +
                                "Local: \t\t{}://localhost:{}\n\t" +
                                "External: \t{}://{}:{}\n\t" +
                                "Actuator: \t{}://{}:{}/actuator/health\n\t" +
                                "Profile(s): {}\n----------------------------------------------------------",
                        app + " Context",
                        app,
                        protocol, port,
                        protocol, host, port,
                        protocol, host, port,
                        profileActive
                );
            }
        } catch (UnknownHostException e) {
            // Ignore
        }

        if (null != callback) {
            callback.accept(log);
        }
    }

}
