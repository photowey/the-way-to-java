package com.photowey.spring.cloud.alibaba.nacos.in.action.config;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.context.properties.EnableConfigurationPropertiesExt;
import org.springframework.context.annotation.Configuration;

/**
 * {@code NacosConfigure}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationPropertiesExt
@NacosPropertySource(dataId = "nacos-in-action", autoRefreshed = true)
public class NacosConfigure {
}
