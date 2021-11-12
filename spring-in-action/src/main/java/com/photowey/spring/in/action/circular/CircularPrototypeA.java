package com.photowey.spring.in.action.circular;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * {@code CircularPrototypeA}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Data
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CircularPrototypeA implements Serializable {

    private static final long serialVersionUID = -8979696135838738219L;

    private String name = "I'm CircularPrototypeA~";

    @Autowired
    private CircularPrototypeB circularPrototypeB;
}
