package com.photowey.print.in.action.printer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
                            "Local: \t\t{}://localhost:{}\n\t" +
                            "External: \t{}://{}:{}\n\t" +
                            "Swagger: \t{}://{}:{}/doc.html\n\t" +
                            "Profile(s): {}\n----------------------------------------------------------",
                    app + " Context",
                    app,
                    protocol, port,
                    protocol, host, port,
                    protocol, host, port,
                    profileActive
            );
        } catch (UnknownHostException e) {
            // Ignore
        }
    }

}
