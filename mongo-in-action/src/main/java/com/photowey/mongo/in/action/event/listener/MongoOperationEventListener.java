package com.photowey.mongo.in.action.event.listener;

import com.photowey.mongo.in.action.document.MongoDocument;
import com.photowey.mongo.in.action.event.generator.MongoKeyGenerator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * {@code MongoOperationEventListener}
 *
 * @author photowey
 * @date 2022/10/26
 * @since 1.0.0
 */
public class MongoOperationEventListener extends AbstractMongoEventListener<MongoDocument> implements BeanFactoryAware {

    private ListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<MongoDocument> event) {
        MongoDocument document = event.getSource();
        if (ObjectUtils.isEmpty(document.getId())) {
            if (StringUtils.hasText(document.getBizId())) {
                document.setId(document.getBizId());
            } else {
                MongoKeyGenerator keyGenerator = this.beanFactory.getBean(MongoKeyGenerator.class);
                String objectId = keyGenerator.objectId();
                document.setId(objectId);
            }
        }
    }
}
