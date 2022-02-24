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

import com.photowey.validator.in.action.iterator.EmptyIterator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * {@code AnnotationProcessorAdaptor}
 * 注解处理器适配器
 *
 * @author photowey
 * @date 2022/02/23
 * @since 1.0.0
 */
public abstract class AnnotationProcessorAdaptor<A extends Annotation, T> implements ConstraintValidator<A, T> {

    public static final Iterator RESETTABLE_INSTANCE = new EmptyIterator();

    @Override
    public void initialize(A constraintAnnotation) {
    }

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {
        return true;
    }

    protected boolean isNullOrEmpty(Object target) {
        if (null == target) {
            return true;
        }

        if (target instanceof CharSequence) {
            return this.isBlank((CharSequence) target);
        }

        if (isCollectionsSupportType(target)) {
            return this.sizeIsEmpty(target);
        }

        return false;
    }

    protected boolean isNotNullOrEmpty(Object target) {
        return !this.isNullOrEmpty(target);
    }

    protected boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    protected boolean isNotEmpty(CharSequence cs) {
        return !this.isEmpty(cs);
    }

    public boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    protected boolean isNotBlank(CharSequence cs) {
        return !this.isBlank(cs);
    }

    protected boolean isNumeric(CharSequence cs) {
        if (this.isEmpty(cs)) {
            return false;
        } else {
            int sz = cs.length();

            for (int i = 0; i < sz; ++i) {
                if (!Character.isDigit(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    private boolean sizeIsEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof Collection) {
            return ((Collection) object).isEmpty();
        } else if (object instanceof Iterable) {
            return isEmptyIterable((Iterable) object);
        } else if (object instanceof Map) {
            return ((Map) object).isEmpty();
        } else if (object instanceof Object[]) {
            return ((Object[]) (object)).length == 0;
        } else if (object instanceof Iterator) {
            return !((Iterator) object).hasNext();
        } else if (object instanceof Enumeration) {
            return !((Enumeration) object).hasMoreElements();
        } else {
            try {
                return Array.getLength(object) == 0;
            } catch (IllegalArgumentException var2) {
                throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
            }
        }
    }

    public static boolean isEmpty(Iterator<?> iterator) {
        return iterator == null || !iterator.hasNext();
    }

    public static boolean isEmptyIterable(Iterable<?> iterable) {
        return iterable instanceof Collection ? ((Collection) iterable).isEmpty() : isEmpty(emptyIteratorIfNull(iterable));
    }

    private static <E> Iterator<E> emptyIteratorIfNull(Iterable<E> iterable) {
        return (iterable != null ? iterable.iterator() : EmptyIterator.emptyIterator());
    }

    private static boolean isCollectionsSupportType(Object target) {
        return target instanceof Collection || target instanceof Map
                || target instanceof Enumeration || target instanceof Iterator
                || target.getClass().isArray();
    }
}
