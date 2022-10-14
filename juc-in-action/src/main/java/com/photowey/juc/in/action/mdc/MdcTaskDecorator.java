package com.photowey.juc.in.action.mdc;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;
import java.util.Objects;

/**
 * {@code MdcTaskDecorator}
 *
 * @author photowey
 * @date 2022/10/14
 * @since 1.0.0
 */
public class MdcTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable task) {
        Map<String, String> ctx = MDC.getCopyOfContextMap();
        return () -> {
            try {
                if (Objects.nonNull(ctx)) {
                    MDC.setContextMap(ctx);
                }
                task.run();
            } finally {
                if (Objects.nonNull(ctx)) {
                    MDC.clear();
                }
            }
        };
    }
}