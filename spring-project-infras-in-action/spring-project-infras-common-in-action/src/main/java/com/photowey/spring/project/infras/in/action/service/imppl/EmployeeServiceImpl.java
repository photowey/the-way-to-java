package com.photowey.spring.project.infras.in.action.service.imppl;

import com.photowey.spring.project.infras.in.action.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Service;

/**
 * {@code EmployeeServiceImpl}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/08/08
 */
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }
}