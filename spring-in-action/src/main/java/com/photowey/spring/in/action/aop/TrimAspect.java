/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.spring.in.action.aop;

import com.photowey.spring.in.action.annotation.TrimField;
import com.photowey.spring.in.action.domain.model.TrimModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;

/**
 * {@code TrimAspect}
 *
 * @author photowey
 * @date 2023/07/06
 * @since 1.0.0
 */
@Slf4j
@Aspect
@Component
public class TrimAspect {

    @Pointcut(value = "@annotation(com.photowey.spring.in.action.annotation.TrimMethod)")
    public void trimMethodPointcut() {

    }

    @Around("trimMethodPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            this.handleTrimModel(joinPoint, arg);
            boolean isTargetField = this.handleTrimStringModel(joinPoint, i, arg);
            if (isTargetField) {
                args[i] = String.valueOf(arg).replaceAll(" ", "");
            }
        }

        return joinPoint.proceed(args);
    }


    private void handleTrimModel(ProceedingJoinPoint joinPoint, Object arg) {
        this.handleInnerTrimModel(joinPoint, arg);
        this.handleTrimListModel(joinPoint, arg);
    }

    private void handleTrimListModel(ProceedingJoinPoint joinPoint, Object arg) {
        if (arg instanceof Collection) {
            Collection argz = (Collection) arg;
            for (Object x : argz) {
                this.handleTrimModel(joinPoint, x);
            }
        }
    }

    private void handleInnerTrimModel(ProceedingJoinPoint joinPoint, Object arg) {
        if (arg instanceof TrimModel) {
            ReflectionUtils.doWithFields(arg.getClass(), (field) -> {
                ReflectionUtils.makeAccessible(field);
                Object pv = field.get(arg);
                if (pv instanceof String) {
                    field.set(arg, String.valueOf(pv).replaceAll(" ", ""));
                } else {
                    // TODO 处理嵌套模型
                    this.handleTrimModel(joinPoint, pv);
                }
            }, (f) -> f.isAnnotationPresent(TrimField.class));
        }
    }

    private boolean handleTrimStringModel(ProceedingJoinPoint joinPoint, int index, Object arg) {
        if (arg instanceof String) {
            Method method = this.determineRequestMethod(joinPoint);
            Parameter[] parameters = method.getParameters();
            Parameter parameter = parameters[index];

            return parameter.isAnnotationPresent(TrimField.class);
        }

        return false;
    }

    private Method determineRequestMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}
