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
package com.photowey.mongo.in.action.event.config;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
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
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

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
    private static final String MONGO_TYPE_MAPPER_BEAN_NAME = "org.springframework.data.mongodb.core.convert.MongoTypeMapper";

    @Bean(MONGO_TYPE_MAPPER_BEAN_NAME)
    public MongoTypeMapper mongoTypeMapper() {
        return new DefaultMongoTypeMapper(null);
    }

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
        TransactionOptions txnOptions = TransactionOptions.builder()
                .readPreference(ReadPreference.primary())
                .readConcern(ReadConcern.MAJORITY)
                .writeConcern(WriteConcern.MAJORITY)
                .build();

        return new MongoTransactionManager(factory, txnOptions);
    }

    /**
     * 自定义 {@link MappingMongoConverter}
     * 为了 去掉 {@code Spring-Data} 为我们添加的 {@code _class} 属性
     * <p>
     * {@code _class} 是为了帮助文档能够映射到 {@code POJO} 子类
     * ->
     * 为了节省空间, 我们现在将其删除掉
     * -> 为了不至于转换出错,所以在数据模型设计的时候,尽量不要出现有子类集成的场景
     *
     * @param mongoDatabaseFactory {@link MongoDatabaseFactory}
     * @param context              {@link MongoMappingContext}
     * @param conversions          {@link MongoCustomConversions}
     * @param mongoTypeMapper      {@link MongoTypeMapper}
     * @return {@link MongoConverter}
     * @see {@code MongoDatabaseFactoryDependentConfiguration#mappingMongoConverter()}
     */
    // @Bean
    // @Primary
    public MongoConverter simpleMappingMongoConverter(
            MongoDatabaseFactory mongoDatabaseFactory,
            MongoMappingContext context,
            MongoCustomConversions conversions,
            MongoTypeMapper mongoTypeMapper) {

        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        
        MappingMongoConverter mmc = new MappingMongoConverter(dbRefResolver, context);
        mmc.setCustomConversions(conversions);

        // 去掉 _class
        mmc.setTypeMapper(mongoTypeMapper);

        return mmc;
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
