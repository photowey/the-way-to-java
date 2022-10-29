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
package com.photowey.mongo.in.action.event.listener;

import com.photowey.mongo.in.action.annotation.BusinessId;
import com.photowey.mongo.in.action.annotation.DocumentId;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * {@code AnnotationMongoOperationEventListener}
 *
 * @author photowey
 * @date 2022/10/26
 * @since 1.0.0
 */
public class AnnotationMongoOperationEventListener extends AbstractMongoEventListener<Object> implements BeanFactoryAware {

    private ListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object document = event.getSource();
        ReflectionUtils.doWithFields(document.getClass(), (field) -> {
            if (field.isAnnotationPresent(DocumentId.class)) {
                ReflectionUtils.makeAccessible(field);
                Object documentId = field.get(document);
                if (ObjectUtils.isEmpty(documentId)) {
                    Field businessIdField = findFieldByAnnotation(document, BusinessId.class);
                    if (null != businessIdField) {
                        ReflectionUtils.makeAccessible(businessIdField);
                        Object businessId = businessIdField.get(document);
                        // isNotEmpty
                        if (!ObjectUtils.isEmpty(businessId)) {
                            field.set(document, businessId);
                        }
                    }
                }
            }
        });
    }

    private <T extends Annotation> Field findFieldByAnnotation(Object document, Class<T> businessId) {
        Field[] fields = document.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(businessId)) {
                return field;
            }
        }

        return null;
    }
}
