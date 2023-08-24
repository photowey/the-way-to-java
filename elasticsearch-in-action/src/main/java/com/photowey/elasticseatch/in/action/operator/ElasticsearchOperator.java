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
package com.photowey.elasticseatch.in.action.operator;

import com.photowey.elasticseatch.in.action.builder.ElasticsearchQueryBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

import java.util.Collection;

/**
 * {@code ElasticsearchOperator}
 *
 * @author photowey
 * @date 2022/11/23
 * @since 1.0.0
 */
public interface ElasticsearchOperator {

    /**
     * 获取
     * {@link RestHighLevelClient}
     *
     * @return {@link RestHighLevelClient}
     */
    RestHighLevelClient elasticsearchClient();

    /**
     * 获取
     * {@link ElasticsearchRestTemplate}
     *
     * @return {@link ElasticsearchRestTemplate}
     */
    ElasticsearchRestTemplate elasticsearchRestTemplate();

    /**
     * 获取
     * {@link ElasticsearchQueryBuilder}
     *
     * @return {@link ElasticsearchQueryBuilder}
     */
    ElasticsearchQueryBuilder elasticsearchQueryBuilder();

    /**
     * 根据
     * 文档数据模型-创建索引
     *
     * @param clazz 文档类型
     * @param <T>   T 类型
     * @return {@code boolean}
     */
    <T> boolean createIndex(Class<T> clazz);

    /**
     * 根据
     * 文档数据模型-删除文档
     *
     * @param clazz 文档类型
     * @param <T>   T 类型
     * @return {@code boolean}
     */
    <T> boolean deleteIndex(Class<T> clazz);

    /**
     * 根据
     * 文档数据模型-判断索引是否搜存在
     *
     * @param clazz 文档类型
     * @param <T>   T 类型
     * @return {@code boolean}
     */
    <T> boolean existIndex(Class<T> clazz);

    /**
     * 根据
     * 实体模型->生成映射关系
     * -> 通常情况下又 DSL 完成
     * -> 不推荐采用代码的方式
     *
     * @param clazz 文档类型
     * @param <T>   T 类型
     * @return {@code boolean}
     */
    <T> boolean putMapping(Class<T> clazz);

    /**
     * 根据
     * 文档模型-索引文档
     *
     * @param document 文档
     * @param <T>      T 类型
     * @return {@code boolean}
     */
    <T> T save(T document);

    /**
     * 根据
     * 文档模型列表-批量索引文档
     *
     * @param documents 文档列表
     * @param <T>       T 类型
     * @return {@code boolean}
     */
    <T> Iterable<T> batchSave(Collection<T> documents);


    /**
     * 根据
     * 主键标识-删除文档
     *
     * @param id    主键
     * @param clazz 文档类型
     * @param <ID>  主键类型
     * @param <T>   T 类型
     * @return 主键标识
     */
    <ID, T> ID deleteById(ID id, Class<T> clazz);

    /**
     * 根据
     * 主键标识-查询文档
     *
     * @param id    主键
     * @param clazz 文档类型
     * @param <ID>  主键类型
     * @param <T>   T 类型
     * @return T
     */
    <ID, T> T findById(ID id, Class<T> clazz);

    /**
     * 复合查询
     *
     * @param query {@link NativeSearchQuery}
     * @param clazz 文档类型
     * @param <T>   T 类型
     * @return {@link SearchHits<?> }
     */
    <T> SearchHits<T> searchQuery(NativeSearchQuery query, Class<T> clazz);
}
