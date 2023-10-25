/*
 * Copyright (C) 2021-2023 Thomas Akehurst
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.commom.in.action.shared.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.photowey.commom.in.action.thrower.AssertionErrorThrower;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * {@code Json}
 *
 * @author photowey
 * @date 2023/10/26
 * @since 1.0.0
 */
public final class Json {

    private Json() {
        AssertionErrorThrower.throwz(Json.class);
    }

    public static class PrivateView {}

    public static class PublicView {}

    private static final InheritableThreadLocal<ObjectMapper> objectMapperHolder =
            new InheritableThreadLocal<>() {
                @Override
                protected ObjectMapper initialValue() {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
                    objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
                    objectMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
                    objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
                    objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
                    objectMapper.registerModule(new JavaTimeModule());
                    objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                    return objectMapper;
                }
            };

    public static <T> T read(String json, Class<T> clazz) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException processingException) {
            return throwUnchecked(processingException);
        }
    }

    public static <T> T read(String json, TypeReference<T> typeRef) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, typeRef);
        } catch (JsonProcessingException processingException) {
            return throwUnchecked(processingException);
        }
    }

    public static <T> List<T> toList(String json) {
        return read(json, new TypeReference<>() {});
    }

    public static <T> Set<T> toSet(String json) {
        return read(json, new TypeReference<>() {});
    }

    public static <T> Collection<T> toCollection(String json) {
        return read(json, new TypeReference<>() {});
    }

    public static <T> Map<String, T> toMap(String json) {
        return read(json, new TypeReference<>() {});
    }

    public static <T> String write(T object) {
        return write(object, PublicView.class);
    }

    public static <T> String writePrivate(T object) {
        return write(object, PrivateView.class);
    }

    public static <T> String write(T object, Class<?> view) {
        try {
            ObjectMapper mapper = getObjectMapper();
            ObjectWriter objectWriter = mapper.writerWithDefaultPrettyPrinter();
            if (view != null) {
                objectWriter = objectWriter.withView(view);
            }
            return objectWriter.writeValueAsString(object);
        } catch (IOException ioe) {
            return throwUnchecked(ioe, String.class);
        }
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapperHolder.get();
    }

    public static <T> byte[] toByteArray(T object) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.writeValueAsBytes(object);
        } catch (IOException ioe) {
            return throwUnchecked(ioe, byte[].class);
        }
    }

    public static JsonNode node(String json) {
        return read(json, JsonNode.class);
    }

    public static int maxDeepSize(JsonNode one, JsonNode two) {
        return Math.max(deepSize(one), deepSize(two));
    }

    public static int deepSize(JsonNode node) {
        if (node == null) {
            return 0;
        }

        int acc = 1;
        if (node.isContainerNode()) {
            for (JsonNode child : node) {
                acc++;
                if (child.isContainerNode()) {
                    acc += deepSize(child);
                }
            }
        }

        return acc;
    }

    public static String prettyPrint(String json) {
        ObjectMapper mapper = getObjectMapper();
        try {
            return mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(mapper.readValue(json, JsonNode.class));
        } catch (IOException e) {
            return throwUnchecked(e, String.class);
        }
    }

    public static <T> T mapToObject(Map<String, Object> map, Class<T> targetClass) {
        ObjectMapper mapper = getObjectMapper();
        return mapper.convertValue(map, targetClass);
    }

    public static <T> Map<String, Object> objectToMap(T theObject) {
        ObjectMapper mapper = getObjectMapper();
        return mapper.convertValue(theObject, new TypeReference<Map<String, Object>>() {});
    }

    public static int schemaPropertyCount(JsonNode schema) {
        int count = 0;
        final JsonNode propertiesNode = schema.get("properties");
        if (propertiesNode != null && !propertiesNode.isEmpty()) {
            for (JsonNode property : propertiesNode) {
                count++;
                if (property.has("properties")) {
                    count += schemaPropertyCount(property);
                }
            }
        }

        return count;
    }

    public static <T> T throwUnchecked(final Throwable ex, final Class<T> returnType) {
        throwsUnchecked(ex);
        throw new AssertionError(
                "This code should be unreachable. Something went terribly wrong here!");
    }

    public static <T> T throwUnchecked(final Throwable ex) {
        return throwUnchecked(ex, null);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void throwsUnchecked(Throwable toThrow) throws T {
        throw (T) toThrow;
    }

    public static <T> T uncheck(Callable<T> work, Class<T> returnType) {
        try {
            return work.call();
        } catch (Exception e) {
            return throwUnchecked(e, returnType);
        }
    }
}
