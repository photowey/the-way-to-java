/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.commom.in.action.util;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * {@code GenericUtils}
 *
 * @author photowey
 * @date 2021/12/06
 * @since 1.0.0
 */
public final class GenericUtils {

    private static final String PARAMETERIZED_TYPE_IMPL_FULL_NAME = "sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl";

    private GenericUtils() {
        // utils class; can't create
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static Class<?> determineGenericSuperclassType(Class<?> clazz) {
        return determineGenericSuperclassType(clazz, 0);
    }

    public static Class<?> determineGenericSuperclassType(Class<?> clazz, int index) {
        Type superclass = clazz.getGenericSuperclass();
        checkArgument(superclass instanceof ParameterizedType, "%s isn't parameterized", superclass);
        Type runtimeType = ((ParameterizedType) superclass).getActualTypeArguments()[index];

        return TypeToken.of(runtimeType).getRawType();
    }

    public static Class<?> determineGenericInterfaceType(Class<?> clazz) {
        return determineGenericInterfaceType(clazz, 0);
    }

    public static Class<?> determineGenericInterfaceType(Class<?> clazz, int index) {
        Type[] types = clazz.getGenericInterfaces();
        for (Type type : types) {
            // TODO 由于模块化问题-可能导致 {@code ParameterizedTypeImpl} 的可见性问题
            if (PARAMETERIZED_TYPE_IMPL_FULL_NAME.equals(type.getClass().getName())) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                return (Class<?>) actualTypeArguments[index];
            }
        }

        return null;
    }

    public static void checkArgument(boolean condition, String errorMessageTemplate, Object... parameters) {
        if (!condition) {
            throw new RuntimeException(String.format(errorMessageTemplate, parameters));
        }
    }
}
