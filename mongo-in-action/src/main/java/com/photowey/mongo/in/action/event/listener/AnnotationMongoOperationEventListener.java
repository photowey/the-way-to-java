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
