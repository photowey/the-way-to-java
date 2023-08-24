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

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * {@code AspectAop}
 *
 * @author photowey
 * @date 2021/11/17
 * @since 1.0.0
 */
@Slf4j
@Aspect
@Component
public class AspectAop {

    @Pointcut(value = "execution(public * com.photowey.spring.in.action.aop.service.*.*(..))")
    public void sayHello() {

    }

    @Around("sayHello()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("--- >>> com.photowey.spring.in.action.aop.AspectAop#around::before <<< ---");
        Object retVal = joinPoint.proceed();
        log.info("--- >>> com.photowey.spring.in.action.aop.AspectAop#around::after <<< ---");

        return retVal;
    }
}
