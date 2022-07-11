package com.photowey.graphql.in.action;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * {@code App}
 *
 * @author photowey
 * @date 2022/07/11
 * @since 1.0.0
 */
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        // ConfigurableApplicationContext applicationContext = SpringApplication.run(App.class, args);
         // AppPrinter.print(applicationContext, false);
    }

}
