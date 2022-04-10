package com.photowey.xxl.job.in.action;

import com.photowey.print.in.action.printer.AppPrinter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * {@code App}
 *
 * @author photowey
 * @date 2022/04/10
 * @since 1.0.0
 */
@Slf4j
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(App.class, args);
        AppPrinter.print(applicationContext);
    }
}
