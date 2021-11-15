package com.photowey.spring.in.action.config;

import com.photowey.spring.in.action.defer.HelloDeferredImportSelector;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * {@code DeferredImportConfigure}
 *
 * @author photowey
 * @date 2021/11/15
 * @since 1.0.0
 */
@Configuration
@Import(HelloDeferredImportSelector.class)
public class DeferredImportConfigure {
}
