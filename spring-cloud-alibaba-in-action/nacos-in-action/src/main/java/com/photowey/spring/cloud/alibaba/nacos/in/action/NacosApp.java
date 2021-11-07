package com.photowey.spring.cloud.alibaba.nacos.in.action;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * {@code NacosApp}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
@EnableConfigurationProperties
@SpringBootApplication
public class NacosApp {

    public static void main(String[] args) {
        SpringApplication.run(NacosApp.class, args);
    }

}