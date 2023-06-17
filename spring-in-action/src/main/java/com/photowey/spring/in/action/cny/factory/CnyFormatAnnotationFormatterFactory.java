/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.spring.in.action.cny.factory;

import com.google.common.collect.Sets;
import com.photowey.spring.in.action.cny.annotation.CnyFormat;
import com.photowey.spring.in.action.cny.formatter.CnyFormatter;
import org.springframework.context.support.EmbeddedValueResolutionSupport;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.Set;

/**
 * {@code CnyFormatAnnotationFormatterFactory}
 *
 * @author photowey
 * @date 2023/06/17
 * @since 1.0.0
 */
public class CnyFormatAnnotationFormatterFactory
        extends EmbeddedValueResolutionSupport implements AnnotationFormatterFactory<CnyFormat> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        return Sets.newHashSet(String.class, Number.class);
    }

    @Override
    public Printer<?> getPrinter(CnyFormat annotation, Class<?> fieldType) {
        CnyFormatter formatter = new CnyFormatter(annotation.toYuan(), annotation.toFen());

        return formatter;
    }

    @Override
    public Parser<?> getParser(CnyFormat annotation, Class<?> fieldType) {
        CnyFormatter formatter = new CnyFormatter(annotation.toYuan(), annotation.toFen());

        return formatter;
    }
}
