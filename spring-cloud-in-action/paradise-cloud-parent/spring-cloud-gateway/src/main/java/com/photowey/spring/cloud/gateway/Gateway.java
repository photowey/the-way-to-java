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
package com.photowey.spring.cloud.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableDiscoveryClient
@SpringBootApplication
public class Gateway {

    public static final Logger log = LoggerFactory.getLogger(Gateway.class);

    private static final String SERVER_SSL_KEY = "server.ssl.key-store";
    private static final String APPLICATION_NAME = "spring.application.name";
    private static final String PROFILES_ACTIVE = "spring.profiles.active";
    private static final String SERVER_PORT = "server.port";

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Gateway.class, args);
        print(applicationContext);
    }

    public static void print(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        try {
            String protocol = "http";
            String app = environment.getProperty(APPLICATION_NAME);
            String port = environment.getProperty(SERVER_PORT);
            String profileActive = environment.getProperty(PROFILES_ACTIVE);
            String host = InetAddress.getLocalHost().getHostAddress();
            if (null != environment.getProperty(SERVER_SSL_KEY)) {
                protocol = "https";
            }
            log.info("\n----------------------------------------------------------\n\t" +
                            "Bootstrap: the '{}' is Success!\n\t" +
                            "Application: '{}' is running! Access URLs:\n\t" +
                            "Gateway: \t{}://{}:{}\n\t" +
                            "Profile(s): {}\n----------------------------------------------------------",
                    app + " Context",
                    app,
                    protocol, host, port,
                    profileActive
            );
        } catch (UnknownHostException e) {
            // Ignore
        }
    }
}
