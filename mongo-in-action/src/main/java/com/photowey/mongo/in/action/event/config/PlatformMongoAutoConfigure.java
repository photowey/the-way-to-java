package com.photowey.mongo.in.action.event.config;

import com.photowey.mongo.in.action.event.generator.KeyGenerator;
import com.photowey.mongo.in.action.event.generator.MongoKeyGenerator;
import com.photowey.mongo.in.action.event.listener.AnnotationMongoOperationEventListener;
import com.photowey.mongo.in.action.event.listener.MongoOperationEventListener;
import com.photowey.mongo.in.action.event.snowflake.SnowflakeKeyGenerator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

/**
 * {@code PlatformMongoAutoConfigure}
 * 平台 {@code MongoDB} -自动配置
 *
 * @author photowey
 * @date 2022/10/26
 * @since 1.0.0
 */
@Configuration
@AutoConfigureAfter(value = MongoDataAutoConfiguration.class)
public class PlatformMongoAutoConfigure {

    private static final String MONGO_TRANSACTION_MANAGER_BEAN_NAME = "org.springframework.data.mongodb.MongoTransactionManager";

    /**
     * {@code MongoDB} 事务管理器
     * {@link MongoTransactionManager}
     *
     * @param factory {@link MongoDatabaseFactory}
     * @return {@link MongoTransactionManager}
     */
    @Bean(MONGO_TRANSACTION_MANAGER_BEAN_NAME)
    @ConditionalOnMissingBean(MongoTransactionManager.class)
    public MongoTransactionManager mongoTransactionManager(MongoDatabaseFactory factory) {
        return new MongoTransactionManager(factory);
    }

    /**
     * {@code MongoDB} 事件监听
     * - 如果存在 {@link KeyGenerator} 的实例
     *
     * @return {@link MongoOperationEventListener}
     */
    @Bean
    public MongoOperationEventListener mongoOperationEventListener() {
        return new MongoOperationEventListener();
    }

    /**
     * {@code MongoDB} 事件监听
     * 注解实现
     *
     * @return {@link AnnotationMongoOperationEventListener}
     */
    @Bean
    public AnnotationMongoOperationEventListener annotationMongoOperationEventListener() {
        return new AnnotationMongoOperationEventListener();
    }

    @Bean
    @ConditionalOnMissingBean(MongoKeyGenerator.class)
    public MongoKeyGenerator mongoKeyGenerator() {
        return new SnowflakeKeyGenerator();
    }
}
