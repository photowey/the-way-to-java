package com.photowey.spring.in.action.config;

import com.photowey.spring.in.action.controller.HelloHttpRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.Properties;

/**
 * {@code SimpleUrlMappingConfigure}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Configuration
public class SimpleUrlMappingConfigure {

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
        SimpleUrlHandlerMapping suhm = new SimpleUrlHandlerMapping();

        Properties properties = new Properties();
        properties.put("/hello/http/request/handler", this.helloHttpRequestHandler());
        suhm.setMappings(properties);

        return suhm;
    }

    @Bean
    public HttpRequestHandler helloHttpRequestHandler() {
        return new HelloHttpRequestHandler();
    }

}
