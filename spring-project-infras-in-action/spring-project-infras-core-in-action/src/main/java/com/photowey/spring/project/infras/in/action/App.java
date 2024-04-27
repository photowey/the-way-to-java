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
	Bootstrap: the 'spring-project-infras-core-example Context' is Success!
	Application: 'spring-project-infras-core-example' is running! Access URLs:
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
