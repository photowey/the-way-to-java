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
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * {@code DefaultElasticsearchOperator}
 *
 * @author photowey
 * @date 2022/11/23
 * @since 1.0.0
 */
public class DefaultElasticsearchOperator implements ElasticsearchOperator {

    private final RestHighLevelClient elasticsearchClient;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final ElasticsearchQueryBuilder elasticsearchQueryBuilder;

    public DefaultElasticsearchOperator(
            RestHighLevelClient elasticsearchClient,
            ElasticsearchRestTemplate elasticsearchRestTemplate,
            ElasticsearchQueryBuilder elasticsearchQueryBuilder
    ) {
        this.elasticsearchClient = elasticsearchClient;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
        this.elasticsearchQueryBuilder = elasticsearchQueryBuilder;
    }

    @Override
    public RestHighLevelClient elasticsearchClient() {
        return this.elasticsearchClient;
    }

    @Override
    public ElasticsearchRestTemplate elasticsearchRestTemplate() {
        return this.elasticsearchRestTemplate;
    }

    @Override
    public ElasticsearchQueryBuilder elasticsearchQueryBuilder() {
        return this.elasticsearchQueryBuilder;
    }

    @Override
    public <T> boolean createIndex(Class<T> clazz) {
        if (this.existIndex(clazz)) {
            return true;
        }

        IndexOperations indexOps = this.elasticsearchRestTemplate.indexOps(clazz);
        return indexOps.create();
    }

    @Override
    public <T> boolean deleteIndex(Class<T> clazz) {
        IndexOperations indexOps = this.elasticsearchRestTemplate.indexOps(clazz);
        return indexOps.delete();
    }

    @Override
    public <T> boolean existIndex(Class<T> clazz) {
        IndexOperations indexOps = this.elasticsearchRestTemplate.indexOps(clazz);
        return indexOps.exists();
    }

    @Override
    public <T> boolean putMapping(Class<T> clazz) {
        IndexOperations indexOps = this.elasticsearchRestTemplate.indexOps(clazz);
        return indexOps.putMapping();
    }

    @Override
    public <T> T save(T document) {
        return this.elasticsearchRestTemplate.save(document);
    }

    @Override
    public <T> List<T> batchSave(Collection<T> documents) {
        Iterable<T> iter = this.elasticsearchRestTemplate.save(documents);
        return StreamSupport
                .stream(iter.spliterator(), true)
                .collect(Collectors.toList());
    }

    @Override
    public <ID, T> ID deleteById(ID id, Class<T> clazz) {
        this.elasticsearchRestTemplate.delete(String.valueOf(id), clazz);

        return id;
    }

    @Override
    public <ID, T> T findById(ID id, Class<T> clazz) {
        return this.elasticsearchRestTemplate.get(String.valueOf(id), clazz);
    }

    @Override
    public <T> SearchHits<T> searchQuery(NativeSearchQuery query, Class<T> clazz) {
        IndexCoordinates coordinates = this.elasticsearchRestTemplate.getIndexCoordinatesFor(clazz);
        return this.elasticsearchRestTemplate.search(query, clazz, coordinates);
    }
}
