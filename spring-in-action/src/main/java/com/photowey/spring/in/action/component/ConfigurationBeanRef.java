package com.photowey.spring.in.action.component;

import lombok.Getter;

/**
 * {@code ConfigurationBeanRef}
 *
 * @author photowey
 * @date 2021/11/15
 * @since 1.0.0
 */
public class ConfigurationBeanRef {

    @Getter
    private ConfigurationBean configurationBean;

    public ConfigurationBeanRef(ConfigurationBean configurationBean) {
        this.configurationBean = configurationBean;
    }

    public String sayHello() {
        return "Say hello from:ConfigurationBeanRef";
    }

}
