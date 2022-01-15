package com.photowey.oauth2.authentication.server;

import com.photowey.oauth2.authentication.service.annotation.EnableAuthenticationService;
import com.photowey.print.in.action.printer.AppPrinter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * {@code AuthenticationServer}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableAuthenticationService
public class AuthenticationServer {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(AuthenticationServer.class, args);
        AppPrinter.print(applicationContext, false);
    }

}