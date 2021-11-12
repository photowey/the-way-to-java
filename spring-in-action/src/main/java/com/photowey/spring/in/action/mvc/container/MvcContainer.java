package com.photowey.spring.in.action.mvc.container;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * {@code MvcContainer}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@ComponentScan(
        value = "com.photowey.spring.in.action",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
        },
        useDefaultFilters = false
)
public class MvcContainer {
}
