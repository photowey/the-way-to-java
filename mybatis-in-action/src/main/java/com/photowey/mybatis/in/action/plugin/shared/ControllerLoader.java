package com.photowey.mybatis.in.action.plugin.shared;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.List;

/**
 * {@code ControllerLoader}
 *
 * @author photowey
 * @date 2023/05/31
 * @since 1.0.0
 */

@Component
@Slf4j
@Lazy
public class ControllerLoader {

    private void unregisterController(String controllerName) {
        final RequestMappingHandlerMapping requestMappingHandlerMapping = SpringUtil.getBean("requestMappingHandlerMapping");
        if (requestMappingHandlerMapping != null) {
            final Object controller = SpringUtil.getBean(controllerName);
            if (controller == null) {
                return;
            }

            final Class<?> targetClass = controller.getClass();
            ReflectionUtils.doWithMethods(targetClass, new ReflectionUtils.MethodCallback() {
                @Override
                public void doWith(Method method) {
                    Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
                    try {
                        Method createMappingMethod = RequestMappingHandlerMapping.class.getDeclaredMethod("getMappingForMethod", Method.class, Class.class);
                        createMappingMethod.setAccessible(true);
                        RequestMappingInfo requestMappingInfo = (RequestMappingInfo) createMappingMethod.invoke(requestMappingHandlerMapping, specificMethod, targetClass);
                        if (requestMappingInfo != null) {
                            requestMappingHandlerMapping.unregisterMapping(requestMappingInfo);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, ReflectionUtils.USER_DECLARED_METHODS);
        }
    }

    public void registerController(String controllerName) throws Exception {
        final RequestMappingHandlerMapping requestMappingHandlerMapping = SpringUtil.getBean("requestMappingHandlerMapping");

        if (requestMappingHandlerMapping != null) {
            Object controller = SpringUtil.getBean(controllerName);
            if (controller == null) {
                return;
            }

            this.unregisterController(controllerName);

            Method method = requestMappingHandlerMapping.getClass()
                    .getSuperclass()
                    .getSuperclass()
                    .getDeclaredMethod("detectHandlerMethods", Object.class);

            method.setAccessible(true);
            method.invoke(requestMappingHandlerMapping, controllerName);
        }
    }

    public void registerController(List<String> beanNames) {
        if (!ObjectUtils.isEmpty(beanNames)) {
            beanNames.forEach(name -> {
                try {
                    this.registerController(name);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            });
        }
    }
}
