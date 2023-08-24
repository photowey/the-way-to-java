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
package com.photowey.commom.in.action.constant;

/**
 * {@code SharedConstants}
 *
 * @author photowey
 * @date 2023/05/23
 * @since 1.0.0
 */
public interface SharedConstants {

    String SHARED_OBJECT_MAPPER_BEAN_NAME = "com.fasterxml.jackson.databind.ObjectMapper#Jdk8Module&JavaTimeModule";
    String SHARED_FEIGN_CODEC_DECODER_BEAN_NAME = "feign.codec.Decoder";

    String APPLICATION_NAME = "spring.application.name";

    // --------------------------------------------------------- Long

    long MILLIS_UNIT = 1000L;
    long TIME_STAMP_LENGTH = 13;
}
