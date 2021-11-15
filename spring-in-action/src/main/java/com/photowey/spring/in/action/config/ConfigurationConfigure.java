package com.photowey.spring.in.action.config;

import com.photowey.spring.in.action.component.ConfigurationBean;
import com.photowey.spring.in.action.component.ConfigurationBeanRef;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code ComponentConfigure}
 *
 * @author photowey
 * @date 2021/11/15
 * @since 1.0.0
 */
@Configuration
public class ConfigurationConfigure {

    @Bean
    public ConfigurationBean configurationBean() {
        return new ConfigurationBean();
    }

    @Bean
    public ConfigurationBeanRef configurationBeanRef() {
        return new ConfigurationBeanRef(this.configurationBean());
    }
}
