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
package com.photowey.wechat.sdk.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.photowey.wechat.sdk.core.annotation.IgnoredField;

/**
 * {@code ApplyObjectMapper}
 *
 * @author photowey
 * @date 2024/03/06
 * @since 1.0.0
 */
public interface ApplyObjectMapper {

    default void applyObjectMapper(ObjectMapper objectMapper) {
        objectMapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setAnnotationIntrospector(new JacksonIgnoreAnnotationIntrospector())
                .registerModule(new JavaTimeModule());
    }

    class JacksonIgnoreAnnotationIntrospector extends JacksonAnnotationIntrospector {

        @Override
        public boolean hasIgnoreMarker(final AnnotatedMember m) {
            if (super.hasIgnoreMarker(m)) {
                return true;
            }
            if (m instanceof AnnotatedField) {
                return m.hasAnnotation(IgnoredField.class);
            }

            return false;
        }
    }
}
