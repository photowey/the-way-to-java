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
package com.photowey.webservice.core.in.action.proxy.objectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;

import java.util.List;
import java.util.Objects;

/**
 * {@code ObjectMapperProxy}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/05
 */
public class ObjectMapperProxyImpl implements BeanFactoryAware, InitializingBean, ObjectMapperProxy {

    private final JacksonProperties properties;

    private final ObjectMapper objectMapper;
    private final ObjectMapper xmlObjectMapper;

    private ConfigurableListableBeanFactory beanFactory;

    public ObjectMapperProxyImpl(JacksonProperties properties, ObjectMapper objectMapper, ObjectMapper xmlObjectMapper) {
        this.properties = properties;
        this.objectMapper = objectMapper;
        this.xmlObjectMapper = xmlObjectMapper;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public <T> String toJSONString(T t) {
        try {
            if (null == t) {
                return null;
            }
            return this.objectMapper.writeValueAsString(t);
        } catch (Exception e) {
            return throwUnchecked(e);
        }
    }

    @Override
    public <T> T parseObject(String json, Class<T> clazz) {
        if (null == json) {
            return null;
        }

        try {
            return this.objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            return throwUnchecked(e);
        }
    }

    @Override
    public <T> List<T> parseArray(String json, Class<T> clazz) {
        checkNPE(this.objectMapper);
        try {
            CollectionType collectionType = this.toListCollectionType(clazz);
            return this.objectMapper.readValue(json, collectionType);
        } catch (Exception e) {
            return throwUnchecked(e);
        }
    }

    @Override
    public <T> String toXMLString(T t) {
        try {
            if (null == t) {
                return null;
            }
            return this.xmlObjectMapper.writeValueAsString(t);
        } catch (Exception e) {
            return throwUnchecked(e);
        }
    }

    @Override
    public <T> T parseXMLObject(String xml, Class<T> clazz) {
        if (null == xml) {
            return null;
        }

        try {
            return this.xmlObjectMapper.readValue(xml, clazz);
        } catch (Exception e) {
            return throwUnchecked(e);
        }
    }

    @Override
    public <T> List<T> parseXMLArray(String xml, Class<T> clazz) {
        checkNPE(this.xmlObjectMapper);
        try {
            CollectionType collectionType = this.toListCollectionType(this.xmlObjectMapper, clazz);
            return this.xmlObjectMapper.readValue(xml, collectionType);
        } catch (Exception e) {
            return throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    private <T> CollectionType toListCollectionType(Class<T> clazz) {
        return toListCollectionType(this.objectMapper, clazz);
    }

    private <T> CollectionType toListCollectionType(ObjectMapper objectMapper, Class<T> clazz) {
        checkNPE(objectMapper);
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        return typeFactory.constructCollectionType(List.class, clazz);
    }

    public static void checkNPE(ObjectMapper objectMapper) {
        Objects.requireNonNull(objectMapper, "infras: the objectMapper can't be null.");
    }

    // ----------------------------------------------------------------

    public static <T> T throwUnchecked(final Throwable ex, final Class<T> returnType) {
        throwsUnchecked(ex);
        throw new AssertionError("json: this.code.should.be.unreachable.here!");
    }

    public static <T> T throwUnchecked(final Throwable ex) {
        return throwUnchecked(ex, null);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void throwsUnchecked(Throwable throwable) throws T {
        throw (T) throwable;
    }
}
