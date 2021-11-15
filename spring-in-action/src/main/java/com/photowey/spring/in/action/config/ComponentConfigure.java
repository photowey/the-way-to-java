package com.photowey.spring.in.action.config;

import com.photowey.spring.in.action.component.ComponentBean;
import com.photowey.spring.in.action.component.ComponentBeanRef;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * {@code ComponentConfigure}
 *
 * @author photowey
 * @date 2021/11/15
 * @since 1.0.0
 */
@Component
public class ComponentConfigure {

    @Bean
    public ComponentBean componentBean() {
        return new ComponentBean();
    }

    @Bean
    public ComponentBeanRef componentBeanRef() {
        return new ComponentBeanRef(this.componentBean());
    }
}
