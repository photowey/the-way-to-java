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
package io.github.photowey.jwt.authcenter.core.domain.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.github.photowey.jwt.authcenter.core.exception.Exceptions;

import java.util.List;
import java.util.Objects;

/**
 * {@code JSON}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/14
 */
public final class JSON {

    private static ObjectMapper objectMapper;
    private static ObjectMapper xmlObjectMapper;

    // ----------------------------------------------------------------

    public static void json(ObjectMapper json) {
        objectMapper = json;
    }

    public static ObjectMapper json() {
        return objectMapper;
    }

    public static void xml(ObjectMapper xml) {
        xmlObjectMapper = xml;
    }

    public static ObjectMapper xml() {
        return xmlObjectMapper;
    }

    // ----------------------------------------------------------------

    public static String toPrettyString(ObjectMapper objectMapper, String json) {
        checkNPE(objectMapper);
        try {
            // @formatter:off
            return objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(objectMapper.readValue(json, JsonNode.class));
            // @formatter:on
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e, String.class);
        }
    }

    public static String toPrettyString(String json) {
        return toPrettyString(json(), json);
    }

    public static String toPrettyXMLString(String xml) {
        return toPrettyString(xml(), xml);
    }

    public static <T> String toBodyPrettyString(T body) {
        return toPrettyString(toJSONString(body));
    }

    public static <T> String toBodyPrettyXMLString(T body) {
        return toPrettyString(toXMLString(body));
    }

    // ----------------------------------------------------------------

    @SuppressWarnings("all")
    public static <T> String toJSONString(T body) {
        try {
            return json().writeValueAsString(body);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return json().readValue(json, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    public static <T> T parseObject(String json, TypeReference<T> clazz) {
        try {
            return json().readValue(json, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        return parseArray(json(), json, clazz);
    }

    public static <T> List<T> parseArray(ObjectMapper objectMapper, String json, Class<T> clazz) {
        return parseList(objectMapper, json, clazz);
    }

    // ----------------------------------------------------------------

    @SuppressWarnings("all")
    public static <T> String toXMLString(T body) {
        try {
            return xml().writeValueAsString(body);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    @SuppressWarnings("all")
    public static <T> T parseXMLObject(String xml, Class<T> clazz) {
        try {
            return xml().readValue(xml, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    @SuppressWarnings("all")
    public static <T> T parseXMLObject(String xml, TypeReference<T> clazz) {
        try {
            return xml().readValue(xml, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    @SuppressWarnings("all")
    public static <T> List<T> parseXMLArray(String xml, Class<T> clazz) {
        return parseXMLArray(xml(), xml, clazz);
    }

    @SuppressWarnings("all")
    public static <T> List<T> parseXMLArray(ObjectMapper xmlObjectMapper, String xml,
                                            Class<T> clazz) {
        return parseList(xmlObjectMapper, xml, clazz);
    }

    // ----------------------------------------------------------------

    public static <T> List<T> parseList(String json, Class<T> clazz) {
        try {
            return parseList(json(), json, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    public static <T> List<T> parseList(ObjectMapper objectMapper, String json, Class<T> clazz) {
        checkNPE(objectMapper);
        try {
            CollectionType collectionType = toListCollectionType(objectMapper, clazz);
            return objectMapper.readValue(json, collectionType);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    private static <T> CollectionType toListCollectionType(
        ObjectMapper objectMapper,
        Class<T> clazz) {
        checkNPE(objectMapper);
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        return typeFactory.constructCollectionType(List.class, clazz);
    }

    // ----------------------------------------------------------------

    @SuppressWarnings("all")
    private static void checkNPE(ObjectMapper objectMapper) {
        Objects.requireNonNull(objectMapper, "ObjectMapper: the objectMapper can't be null.");
    }
}
