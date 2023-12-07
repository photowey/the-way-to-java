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
package com.photowey.common.in.action.shared.json.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.photowey.common.in.action.thrower.AssertionErrorThrower;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * {@code JSON}
 *
 * @author weichangjun
 * @date 2023/12/07
 * @since 1.0.0
 */
public final class JSON {

    private JSON() {
        AssertionErrorThrower.throwz(JSON.class);
    }

    private static ObjectMapper sharedObjectMapper;

    public static class PrivateView {}

    public static class PublicView {}

    private static final InheritableThreadLocal<ObjectMapper> objectMapperHolder = new InheritableThreadLocal<ObjectMapper>() {
        @Override
        protected ObjectMapper initialValue() {
            return initObjectMapper();
        }
    };

    private static ObjectMapper initDefaultObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
        objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        objectMapper.registerModule(new JavaTimeModule());

        // changed
        //objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return objectMapper;
    }

    private static ObjectMapper initObjectMapper() {
        return sharedObjectMapper != null ? sharedObjectMapper : initDefaultObjectMapper();
    }

    public static void injectSharedObjectMapper(ObjectMapper objectMapper) {
        sharedObjectMapper = objectMapper;
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException processingException) {
            return throwUnchecked(processingException);
        }
    }

    public static <T> T parseObject(String json, TypeReference<T> typeRef) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, typeRef);
        } catch (JsonProcessingException processingException) {
            return throwUnchecked(processingException);
        }
    }

    // ---------------------------------------------------------------- parse.array

    public static <T> List<T> parseArray(String json) {
        return parseObject(json, new TypeReference<List<T>>() {});
    }

    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        return parseObject(json, new TypeReference<List<T>>() {});
    }

    // ---------------------------------------------------------------- additional.methods

    public static <T> List<T> toList(String json) {
        return parseArray(json);
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        return parseArray(json, clazz);
    }

    public static <T> Set<T> toSet(String json) {
        return parseObject(json, new TypeReference<Set<T>>() {});
    }

    public static <T> Collection<T> toCollection(String json) {
        return parseObject(json, new TypeReference<Set<T>>() {});
    }

    public static <T> Map<String, T> toMap(String json) {
        return parseObject(json, new TypeReference<Map<String, T>>() {});
    }

    // ----------------------------------------------------------------

    public static <T> String toJSONString(T object) {
        return toJSONString(object, PublicView.class);
    }

    public static <T> String toPrivateJSONString(T object) {
        return toJSONString(object, PrivateView.class);
    }

    public static <T> String toJSONString(T object, Class<?> view) {
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
        // 如果外部有注入: 共享的 ObjectMapper, 那么共享的优先
        // 建议: 在 App 启动的时候直接注入 IOC 里面的 ObjectMapper 对象
        return sharedObjectMapper != null ? sharedObjectMapper : objectMapperHolder.get();
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
        return parseObject(json, JsonNode.class);
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
            // @formatter:off
            return mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(mapper.readValue(json, JsonNode.class));
            // @formatter:on
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
        throw new AssertionError("json: this.code.should.be.unreachable.here!");
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
