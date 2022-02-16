package com.photowey.vertx.spring.boot.autoconfigure.annotation;

import java.lang.annotation.*;

/**
 * {@code BlockingHandler}
 * 标记一个 {@link io.vertx.core.Handler<io.vertx.ext.web.RoutingContext>} 是一个 {@code BlockingHandler}
 *
 * @author photowey
 * @date 2022/02/17
 * @since 1.0.0
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BlockingHandler {
}
