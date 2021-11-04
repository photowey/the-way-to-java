package com.photowey.dubbo.producer.in.action.setup;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.PropertySource;

/**
 * {@code AnnotationSetup}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
@EnableDubbo(scanBasePackages = "com.photowey")
@PropertySource(value = "dubbo.properties")
public class AnnotationSetup {
}
