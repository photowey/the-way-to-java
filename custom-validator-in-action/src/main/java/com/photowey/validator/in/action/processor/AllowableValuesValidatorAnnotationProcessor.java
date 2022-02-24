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
package com.photowey.validator.in.action.processor;

import com.photowey.validator.in.action.annotation.AllowableValues;

import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@code AllowableValuesValidatorAnnotationProcessor}
 * {@link AllowableValues} 注解 的处理器
 *
 * @author photowey
 * @date 2022/02/23
 * @since 1.0.0
 */
public class AllowableValuesValidatorAnnotationProcessor extends AnnotationProcessorAdaptor<AllowableValues, Object> {

    private String allowableValues;
    private boolean required;
    private int size;

    private static final String RANGE_PREFIX = "range";
    private static final String INFINITY_PREFIX = "infinity";
    private static final String NEGATIVE_INFINITY_PREFIX = "range(-infinity";

    @Override
    public void initialize(AllowableValues constraintAnnotation) {
        this.allowableValues = constraintAnnotation.value();
        this.required = constraintAnnotation.required();
        this.size = constraintAnnotation.size();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (this.isNullOrEmpty(value)) {
            return !this.required;
        }

        // 处理 Collection 类型
        if (value instanceof Collection) {
            Collection<?> collection = (Collection<?>) value;
            if (collection.size() > this.size) {
                return false;
            }

            return this.handleCollection(value, collection);
        }

        return this.handleOther(value);
    }

    private boolean handleOther(Object value) {
        // 处理其余类型, 全部当作 String 类型
        String expectValue = String.valueOf(value);
        String trimmed = this.allowableValues.trim();

        if (trimmed.startsWith(RANGE_PREFIX)) {
            return this.handleRange(expectValue, trimmed);
        } else {
            return this.handleNormal(value, expectValue, trimmed);
        }
    }

    private boolean handleCollection(Object value, Collection<?> collection) {
        for (Object target : collection) {
            if (!(target instanceof Number)) {
                throw new IllegalArgumentException("@AllowableValues.value() range 配置 暂时只支持数值类型.");
            }
            boolean handleOther = this.handleOther(target);
            if (!handleOther) {
                return false;
            }
        }

        return true;
    }

    private boolean handleNormal(Object value, String expectValue, String trimmed) {
        // 不支持: xxx-yyy 写法
        if (trimmed.contains("-")) {
            throw new IllegalArgumentException("@AllowableValues.value() 配置不支持类似于: {@code xxx-yyy} 写法.");
        }
        if (trimmed.contains(",")) {
            // xxx,yyy,zzz
            return Stream.of(trimmed.split(",")).collect(Collectors.toSet()).contains(expectValue);
        } else {
            // xxx
            return value instanceof BigDecimal
                    ? new BigDecimal(trimmed).compareTo(new BigDecimal(expectValue)) == 0
                    : trimmed.equalsIgnoreCase(expectValue);
        }
    }

    private boolean handleRange(String expectValue, String trimmed) {
        // 判断-必须是数值
        if (!super.isNumeric(expectValue)) {
            throw new IllegalArgumentException("@AllowableValues.value() range 配置 暂时只支持数值类型.");
        }
        if (trimmed.contains(NEGATIVE_INFINITY_PREFIX)) {
            // range(-infinity-xxx] || range(-infinity-xxx)
            String trimmedNumber = trimmed
                    .replaceAll("range\\(-infinity-", "")
                    .replaceAll("]", "")
                    .replaceAll("\\)", "");
            return trimmed.contains("]")
                    ? new BigDecimal(expectValue).compareTo(new BigDecimal(trimmedNumber)) <= 0
                    : new BigDecimal(expectValue).compareTo(new BigDecimal(trimmedNumber)) < 0;
        } else if (trimmed.contains(INFINITY_PREFIX)) {
            // range[xxx-infinity) || range(xxx-infinity)
            String trimmedNumber = trimmed
                    .replaceAll("range", "")
                    .replaceAll("\\[", "")
                    .replaceAll("\\(", "")
                    .replaceAll("-infinity\\)", "");
            return trimmed.contains("[")
                    ? new BigDecimal(expectValue).compareTo(new BigDecimal(trimmedNumber)) >= 0
                    : new BigDecimal(expectValue).compareTo(new BigDecimal(trimmedNumber)) > 0;
        } else if (trimmed.contains("-")) {
            // range[xxx-yyy] || range(xxx-yyy] || range[xxx-yyy) || range(xxx-yyy)
            String trimmedNumber = trimmed
                    .replaceAll("range", "")
                    .replaceAll("\\[", "")
                    .replaceAll("\\(", "")
                    .replaceAll("]", "")
                    .replaceAll("\\)", "");
            // xxx-zzz
            return trimmed.contains("[")
                    ? new BigDecimal(expectValue).compareTo(new BigDecimal(trimmedNumber.split("-")[0])) >= 0
                    : new BigDecimal(expectValue).compareTo(new BigDecimal(trimmedNumber.split("-")[0])) > 0
                    && trimmed.contains("]")
                    ? new BigDecimal(expectValue).compareTo(new BigDecimal(trimmedNumber.split("-")[1])) <= 0
                    : new BigDecimal(expectValue).compareTo(new BigDecimal(trimmedNumber.split("-")[1])) < 0;
        } else {
            // range[xxx]
            // 不支持:  range[xxx,yyy] 等写法
            if (trimmed.contains("(") || trimmed.contains(")")) {
                throw new IllegalArgumentException("@AllowableValues.value() 配置错误.");
            }
            String trimmedNumber = trimmed
                    .replaceAll("range\\[", "")
                    .replaceAll("]", "");

            return new BigDecimal(trimmedNumber).compareTo(new BigDecimal(expectValue)) == 0;
        }
    }

}

