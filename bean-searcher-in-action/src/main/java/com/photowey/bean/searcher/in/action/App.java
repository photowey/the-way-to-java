package com.photowey.bean.searcher.in.action;

import com.photowey.print.in.action.printer.AppPrinter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * {@code App}
 *
 * @author photowey
 * @date 2022/05/05
 * @since 1.0.0
 */
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(App.class, args);
        AppPrinter.print(applicationContext, false);
    }

}
