package com.photowey.spring.in.action.circular;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * {@code FactoryBeanA}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Slf4j
@Data
public class FactoryBeanA implements Serializable {

    private static final long serialVersionUID = -5713276703290721437L;

    private String name = "I'm FactoryBeanA~";

    @Autowired
    private FactoryBeanB factoryBeanB;
}
