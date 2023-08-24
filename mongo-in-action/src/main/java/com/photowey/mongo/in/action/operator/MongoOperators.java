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
package com.photowey.mongo.in.action.operator;

import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.regex.Pattern;

/**
 * {@code MongoOperators}
 *
 * @author photowey
 * @date 2022/11/25
 * @since 1.0.0
 */
public class MongoOperators {

    public static final String PK_ID = "_id";

    /**
     * 根据
     * 主键标识-查询
     * 默认: KEY: _id
     *
     * @param pkV  主键标识
     * @param <PK> 主键类型
     * @return {@link Query}
     */
    public static <PK> Query findById(PK pkV) {
        return findById(PK_ID, pkV);
    }

    /**
     * 根据
     * 主键标识-查询
     *
     * @param pkK  KEY
     * @param pkV  主键标识
     * @param <PK> 主键类型
     * @return {@link Query}
     */
    public static <PK> Query findById(String pkK, PK pkV) {
        return new Query(Criteria.where(pkK).is(pkV));
    }

    /**
     * 根据
     * 主键标识-查询
     *
     * @param query {@link Query}
     * @param pkK   KEY
     * @param pkV   主键标识
     * @param <PK>  主键类型
     */
    public static <PK> void findById(Query query, String pkK, PK pkV) {
        eq(query, pkK, pkV);
    }

    /**
     * 精确匹配
     *
     * @param k   KEY
     * @param v   VALUE
     * @param <V> VALUE 类型
     */
    public static <V> Query eq(String k, V v) {
        return new Query(Criteria.where(k).is(v));
    }

    /**
     * 精确匹配
     *
     * @param query {@link Query}
     * @param k     KEY
     * @param v     VALUE
     * @param <V>   VALUE 类型
     */
    public static <V> void eq(Query query, String k, V v) {
        query.addCriteria(Criteria.where(k).is(v));
    }

    /**
     * 根据
     * 主键标识-批量操作
     *
     * @param bulkOps {@link BulkOperations}
     * @param pkV     主键标识
     * @param k       KEY
     * @param v       VALUE
     * @param <PK>    主键类型
     * @param <V>     VALUE 类型
     */
    public static <PK, V> void updateById(BulkOperations bulkOps, PK pkV, String k, V v) {
        bulkOps.updateOne(findById(pkV), Update.update(k, v));
    }

    /**
     * 模糊查询
     *
     * @param k   KEY
     * @param v   VALUE
     * @param <V> VALUE 类型
     * @return {@link Query}
     */
    public static <V> Query like(String k, V v) {
        Pattern pattern = Pattern.compile("^.*" + v + ".*$", Pattern.CASE_INSENSITIVE);
        return new Query(Criteria.where(k).regex(pattern));
    }

    /**
     * 模糊查询
     *
     * @param query {@link Query}
     * @param k     KEY
     * @param v     VALUE
     * @param <V>   VALUE 类型
     */
    public static <V> void like(Query query, String k, V v) {
        Pattern pattern = Pattern.compile("^.*" + v + ".*$", Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where(k).regex(pattern));
    }

}
