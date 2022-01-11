package com.photowey.sqlite.in.action;

import com.photowey.print.in.action.printer.AppPrinter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * {@code SQLiteApp}
 *
 * @author photowey
 * @date 2022/01/11
 * @since 1.0.0
 */
@Slf4j
@SpringBootApplication
public class SQLiteApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SQLiteApp.class, args);
        AppPrinter.print(applicationContext, false);
    }
}
