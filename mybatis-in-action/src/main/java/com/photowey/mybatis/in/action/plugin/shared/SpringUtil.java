package com.photowey.mybatis.in.action.plugin.shared;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * {@code SpringUtil}
 *
 * @author photowey
 * @date 2023/05/31
 * @since 1.0.0
 */
public class SpringUtil {

    private static DefaultListableBeanFactory beanFactory;

    public static void setBeanFactory(DefaultListableBeanFactory beanFactory) {
        SpringUtil.beanFactory = beanFactory;
    }

    public static DefaultListableBeanFactory getBeanFactory() {
        return SpringUtil.beanFactory;
    }

    public static <T> T getBean(String name) {
        return (T) SpringUtil.beanFactory.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return (T) SpringUtil.beanFactory.getBean(clazz);
    }
}
