package com.photowey.mybatis.in.action.engine;

import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * {@code IMybatisEngine}
 *
 * @author photowey
 * @date 2021/11/02
 * @since 1.0.0
 */
public interface IMybatisEngine extends IEngine, BeanFactoryAware, ApplicationContextAware, EnvironmentAware {

    ListableBeanFactory beanFactory();

    ApplicationContext applicationContext();

    Environment environment();

    // =========================================

    IRepositoryEngine repositoryEngine();

    IServiceEngine serviceEngine();
}
