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
package com.photowey.spring.in.action.config;

import com.photowey.spring.in.action.serializer.jackson.timestamp.LocalDateTimeTimestampDeserializer;
import com.photowey.spring.in.action.serializer.jackson.timestamp.LocalDateTimeTimestampSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

/**
 * {@code LocalDateTimeTimeStampFormatterConfigurer}
 *
 * @author photowey
 * @date 2023/05/02
 * @since 1.0.0
 */
//@Configuration
public class LocalDateTimeTimeStampFormatterConfigurer {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeTimestampSerializer());
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeTimestampDeserializer());
        };
    }

}
