/*
 * Copyright © 2025 the original author or authors.
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
package io.github.photowey.jwt.authcenter.core.security;

import io.github.photowey.jwt.authcenter.core.thrower.AssertionErrorThrower;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * {@code AnnotationUtils}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/14
 */
public final class AnnotationUtils {

    private AnnotationUtils() {
        AssertionErrorThrower.throwz(AnnotationUtils.class);
    }

    public static <T extends Annotation> T findAnnotation(
        ProceedingJoinPoint joinPoint,
        Class<T> annotationClass) {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();

        T annotation = method.getAnnotation(annotationClass);
        if (Objects.isNull(annotation)) {
            // 如果 方法上面没有注解 - 再次尝试从类上面去过去相应的注解
            Class<?> declaringClass = method.getDeclaringClass();
            annotation = declaringClass.getAnnotation(annotationClass);
        }

        return annotation;
    }

}

