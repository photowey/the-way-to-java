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
package com.photowey.mongo.in.action.ext.service;

import com.photowey.mongo.in.action.constant.MongoConstants;
import com.photowey.mongo.in.action.document.DocumentUpdateCounter;
import com.photowey.mongo.in.action.fx.ThreeConsumer;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ObjectUtils;

import java.util.function.Consumer;

/**
 * {@code MongoServiceExt}
 *
 * @author photowey
 * @date 2022/12/23
 * @since 1.0.0
 */
public interface MongoServiceExt<T, ID> {

    String DEFAULT_PK_ID = MongoConstants.DEFAULT_PK_ID;
    String DEFAULT_DELETED_KEY = MongoConstants.DEFAULT_DELETED_KEY;
    int DEFAULT_DELETED_VALUE = MongoConstants.DEFAULT_DELETED_VALUE;

    /**
     * 获取 {@link MongoOperations} 实例
     *
     * @return {@link MongoOperations}
     */
    default MongoOperations mongoOperations() {
        return null;
    }

    /**
     * 添加到列表
     *
     * @param target   目标对象
     * @param pk       主键标识
     * @param node     文档节点 key
     * @param clazz    文档类型
     * @param <PK>     业务主键类型
     * @param <TARGET> 目标对象类型
     */
    default <PK, TARGET> void addToSet(TARGET target, PK pk, String node, Class<T> clazz) {
        this.addToSetOps(target, pk, node, (mongoTemplate, query, update) -> {
            mongoTemplate.updateFirst(query, update, clazz);
        });
    }

    default <V, TARGET> void addToSet(TARGET target, String key, V value, String node, Class<T> clazz) {
        this.addToSetOps(target, key, value, node, (mongoTemplate, query, update) -> {
            mongoTemplate.updateFirst(query, update, clazz);
        });
    }

    /**
     * 批量
     * 添加到列表
     *
     * @param target   目标对象
     * @param pk       主键标识
     * @param node     文档节点 key
     * @param clazz    文档类型
     * @param <PK>     业务主键类型
     * @param <TARGET> 目标对象类型
     */
    default <PK, TARGET> void batchAddToSet(TARGET target, PK pk, String node, Class<T> clazz) {
        this.addToSetOps(target, pk, node, (mongoTemplate, query, update) -> {
            mongoTemplate.updateMulti(query, update, clazz);
        });
    }

    default <V, TARGET> void batchAddToSet(TARGET target, String key, V value, String node, Class<T> clazz) {
        this.addToSetOps(target, key, value, node, (mongoTemplate, query, update) -> {
            mongoTemplate.updateMulti(query, update, clazz);
        });
    }

    default <PK, TARGET> void addToSetOps(TARGET target, PK pk, String node, ThreeConsumer<MongoOperations, Query, Update> fx) {
        this.addToSetOps(target, DEFAULT_PK_ID, this.wrapId(pk), node, fx);
    }

    default <V, TARGET> void addToSetOps(TARGET target, String key, V value, String node, ThreeConsumer<MongoOperations, Query, Update> fx) {
        MongoOperations mongoTemplate = this.mongoOperations();
        if (null == mongoTemplate) {
            return;
        }
        Query query = new Query(Criteria.where(key).is(value));
        Update update = new Update();
        update.addToSet(node, target);

        fx.accept(mongoTemplate, query, update);
    }

    /**
     * 从列表移除
     *
     * @param target   目标对象
     * @param pk       主键标识
     * @param node     文档节点 key
     * @param clazz    文档类型
     * @param <PK>     业务主键类型
     * @param <TARGET> 目标对象类型
     */
    default <PK, TARGET> void pull(TARGET target, PK pk, String node, Class<T> clazz) {
        this.pullOps(target, pk, node, (mongoTemplate, query, update) -> {
            mongoTemplate.updateFirst(query, update, clazz);
        });
    }

    default <V, TARGET> void pull(TARGET target, String key, V v, String node, Class<T> clazz) {
        this.pullOps(target, key, v, node, (mongoTemplate, query, update) -> {
            mongoTemplate.updateFirst(query, update, clazz);
        });
    }

    /**
     * 批量
     * 从列表移除
     *
     * @param target   目标对象
     * @param pk       主键标识
     * @param node     文档节点 key
     * @param clazz    文档类型
     * @param <PK>     业务主键类型
     * @param <TARGET> 目标对象类型
     */
    default <PK, TARGET> void batchPull(TARGET target, PK pk, String node, Class<T> clazz) {
        this.pullOps(target, pk, node, (mongoTemplate, query, update) -> {
            mongoTemplate.updateMulti(query, update, clazz);
        });
    }

    default <V, TARGET> void batchPull(TARGET target, String key, V v, String node, Class<T> clazz) {
        this.pullOps(target, key, v, node, (mongoTemplate, query, update) -> {
            mongoTemplate.updateMulti(query, update, clazz);
        });
    }

    default <PK, TARGET> void pullOps(TARGET target, PK pk, String node, ThreeConsumer<MongoOperations, Query, Update> fx) {
        this.pullOps(target, DEFAULT_PK_ID, this.wrapId(pk), node, fx);
    }

    default <V, TARGET> void pullOps(TARGET target, String key, V v, String node, ThreeConsumer<MongoOperations, Query, Update> fx) {
        MongoOperations mongoTemplate = this.mongoOperations();
        if (null == mongoTemplate) {
            return;
        }
        Query query = new Query(Criteria.where(key).is(v));
        Update update = new Update();
        update.pull(node, target);

        fx.accept(mongoTemplate, query, update);
    }

    /**
     * 执行
     * 文档更新
     *
     * @param pk       业务主键标识
     * @param document {@code BSON} 文档
     * @param clazz    文档类型
     * @param <PK>     业务主键类型
     */
    default <PK> void doUpdate(PK pk, Document document, Class<T> clazz) {
        ID id = this.wrapId(pk);
        Query query = this.createQueryById(id);
        Update update = Update.fromDocument(document);

        this.mongoOperations().updateFirst(query, update, clazz);
    }

    default void updateDocument(Consumer<Criteria> criteriaFx, Document document, Class<T> clazz) {
        this.doUpdate(criteriaFx, document, (mongoTemplate, query, update) -> {
            this.mongoOperations().updateFirst(query, update, clazz);
        });
    }

    default void batchUpdateDocument(Consumer<Criteria> criteriaFx, Document document, Class<T> clazz) {
        this.doUpdate(criteriaFx, document, (mongoTemplate, query, update) -> {
            this.mongoOperations().updateMulti(query, update, clazz);
        });
    }

    default void doUpdate(Consumer<Criteria> condition, Document document, ThreeConsumer<MongoOperations, Query, Update> fx) {
        MongoOperations mongoTemplate = this.mongoOperations();
        if (null == mongoTemplate) {
            return;
        }

        Criteria criteria = new Criteria();
        condition.accept(criteria);

        Query query = new Query(criteria);
        Update update = Update.fromDocument(document);

        fx.accept(mongoTemplate, query, update);
    }

    /**
     * 执行
     * 文档逻辑删除
     *
     * @param pk    业务主键标识
     * @param clazz 文档类型
     * @param <PK>  业务主键类型
     */
    default <PK> void doLogicDelete(PK pk, Class<T> clazz) {
        ID id = this.wrapId(pk);
        Query query = this.createQueryById(id);
        Update update = new Update();
        update.set(DEFAULT_DELETED_KEY, this.deleted());

        this.mongoOperations().updateFirst(query, update, clazz);
    }

    // ------------------------------------------------------------------------- fx

    default <V> boolean setIfPresent(V v, Consumer<V> then) {
        if (ObjectUtils.isEmpty(v)) {
            return false;
        }

        then.accept(v);

        return true;
    }

    default <V> boolean setIfPresent(V v, DocumentUpdateCounter counter, Consumer<V> then) {
        if (ObjectUtils.isEmpty(v)) {
            return false;
        }

        then.accept(v);
        counter.increment();

        return true;
    }

    default Document toBsonDocument(T t) {
        Document document = (Document) this.mongoOperations().getConverter().convertToMongoType(t);

        return document;
    }

    default Query createQueryById(ID pkV) {
        return new Query(Criteria.where(DEFAULT_PK_ID).is(pkV));
    }

    default <PK> ID wrapId(PK pk) {
        return (ID) String.valueOf(pk);
    }

    default int deleted() {
        return DEFAULT_DELETED_VALUE;
    }
}
