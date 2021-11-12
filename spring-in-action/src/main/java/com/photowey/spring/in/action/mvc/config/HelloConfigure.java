package com.photowey.spring.in.action.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * {@code HelloConfigure}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Configuration
@EnableWebMvc
public class HelloConfigure implements WebMvcConfigurer {
}
