package com.photowey.spring.in.action.component;

import lombok.Getter;

/**
 * {@code ComponentBean}
 *
 * @author photowey
 * @date 2021/11/15
 * @since 1.0.0
 */
public class ComponentBeanRef {

    @Getter
    private ComponentBean componentBean;

    public ComponentBeanRef(ComponentBean componentBean) {
        this.componentBean = componentBean;
    }

    public String sayHello() {
        return "Say hello from:ComponentBeanRef";
    }

}
