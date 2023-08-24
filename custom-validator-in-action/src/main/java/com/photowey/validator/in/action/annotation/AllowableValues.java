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
package com.photowey.validator.in.action.annotation;

import com.photowey.validator.in.action.processor.AllowableValuesValidatorAnnotationProcessor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * {@code AllowableValues}
 * 需求:
 * 在一些场景,用户的输入是可枚举的数据, 后端需要对数据的范围做校验.
 * 这个时候 注解 {@link AllowableValues} 氤氲而生.
 *
 * @author photowey
 * @date 2022/02/24
 * @since 1.0.0
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = AllowableValuesValidatorAnnotationProcessor.class)
public @interface AllowableValues {

    /**
     * 允许的值表达式
     * 支持写法:
     * ①: range:
     * -- range 目前只支持 数值类型
     * - 1.负无穷类型
     * range(-infinity-xxx] || range(-infinity-xxx)
     * - 2.正无穷类型
     * range[xxx-infinity) || range(xxx-infinity)
     * - 3.普通区间
     * range[xxx-yyy] || range(xxx-yyy] || range[xxx-yyy) || range(xxx-yyy)
     * - 4.单值区间
     * range[xxx]
     * ②: 包含数值和非数值
     * - 1.多值
     * xxx,yyy,zzz  (可以是数值类型)
     * - 2.单值
     * xxx
     *
     * @return 允许的值
     */
    String value() default "";

    /**
     * 是否必须
     *
     * @return 布尔值
     */
    boolean required() default true;

    /**
     * 列表的长度
     *
     * @return 列表的长度
     */
    int size() default Integer.MAX_VALUE;

    /**
     * 匹配失败返回的错误信息
     *
     * @return 匹配错误信息
     */
    String message() default "enter the correct parameters,please!";

    /**
     * groups
     *
     * @return Class<?>[]
     */
    Class<?>[] groups() default {};

    /**
     * Payload
     *
     * @return Class<? extends Payload>[]
     */
    Class<? extends Payload>[] payload() default {};
}
