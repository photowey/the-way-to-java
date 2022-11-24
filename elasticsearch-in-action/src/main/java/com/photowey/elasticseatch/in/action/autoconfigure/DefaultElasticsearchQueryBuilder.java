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
package com.photowey.elasticseatch.in.action.autoconfigure;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@code DefaultElasticsearchQueryBuilder}
 *
 * @author photowey
 * @date 2022/11/23
 * @since 1.0.0
 */
public class DefaultElasticsearchQueryBuilder implements ElasticsearchQueryBuilder {

    @Override
    public <V> QueryBuilder nestedQuery(String path, String field, V v) {
        return this.nestedQuery(path, field, v, ScoreMode.None);
    }

    @Override
    public <V> QueryBuilder nestedMatchQuery(String path, String field, V v) {
        return this.nestedMatchQuery(path, field, v, ScoreMode.None);
    }

    @Override
    public <V> QueryBuilder nestedQuery(String path, String field, V v, ScoreMode mode) {
        QueryBuilder termQuery = QueryBuilders.termQuery(String.format("%s.%s", path, field), v);

        return this.nestedQuery(path, termQuery, mode);
    }

    @Override
    public <V> QueryBuilder nestedRangeGtQuery(String path, String field, V v, ScoreMode mode) {
        String fieldx = String.format("%s.%s", path, field);
        QueryBuilder termQuery = QueryBuilders.rangeQuery(fieldx).gt(v);

        return this.nestedQuery(path, termQuery, mode);
    }

    @Override
    public <V> QueryBuilder nestedRangeLtQuery(String path, String field, V v, ScoreMode mode) {
        String fieldx = String.format("%s.%s", path, field);
        QueryBuilder termQuery = QueryBuilders.rangeQuery(fieldx).lt(v);

        return this.nestedQuery(path, termQuery, mode);
    }

    @Override
    public <V> QueryBuilder nestedRangeXtQuery(String path, String field, V v1, V v2, ScoreMode mode) {
        String fieldx = String.format("%s.%s", path, field);
        QueryBuilder termQuery = QueryBuilders.rangeQuery(fieldx).gt(v1).lt(v2);

        return this.nestedQuery(path, termQuery, mode);
    }

    @Override
    public <V> QueryBuilder nestedRangeGteQuery(String path, String field, V v, ScoreMode mode) {
        String fieldx = String.format("%s.%s", path, field);
        QueryBuilder termQuery = QueryBuilders.rangeQuery(fieldx).gte(v);

        return this.nestedQuery(path, termQuery, mode);
    }

    @Override
    public <V> QueryBuilder nestedRangeLteQuery(String path, String field, V v, ScoreMode mode) {
        String fieldx = String.format("%s.%s", path, field);
        QueryBuilder termQuery = QueryBuilders.rangeQuery(fieldx).lte(v);

        return this.nestedQuery(path, termQuery, mode);
    }

    @Override
    public <V> QueryBuilder nestedRangeXteQuery(String path, String field, V v1, V v2, ScoreMode mode) {
        String fieldx = String.format("%s.%s", path, field);
        QueryBuilder termQuery = QueryBuilders.rangeQuery(fieldx).gte(v1).lte(v2);

        return this.nestedQuery(path, termQuery, mode);
    }

    @Override
    public <V, CV> QueryBuilder nestedCascadeQuery(String path, String field, V v, String cascadeField, CV cv) {
        return this.nestedCascadeQuery(path, field, v, cascadeField, cv, ScoreMode.None);
    }

    @Override
    public <V, CV> QueryBuilder nestedCascadeQuery(String path, String field, V v, String cascadeField, CV cv, ScoreMode mode) {
        QueryBuilder termQuery = QueryBuilders.termQuery(String.format("%s.%s", path, field), v);
        QueryBuilder termQueryCascade = QueryBuilders.termQuery(String.format("%s.%s", path, cascadeField), cv);
        QueryBuilder termQueryx = QueryBuilders.boolQuery().filter(termQuery).filter(termQueryCascade);

        return this.nestedQuery(path, termQueryx, mode);
    }

    @Override
    public <V> QueryBuilder nestedMatchQuery(String path, String field, V v, ScoreMode mode) {
        QueryBuilder matchQuery = QueryBuilders.matchQuery(String.format("%s.%s", path, field), v);

        return this.nestedQuery(path, matchQuery, mode);
    }

    @Override
    public QueryBuilder nestedQuery(String path, QueryBuilder query, ScoreMode mode) {
        QueryBuilder boolQuery = QueryBuilders.boolQuery().filter(query);

        return QueryBuilders.nestedQuery(path, boolQuery, mode);
    }

    @Override
    public QueryBuilder nestedMustQuery(String path, QueryBuilder query, ScoreMode mode) {
        QueryBuilder boolQuery = QueryBuilders.boolQuery().must(query);

        return QueryBuilders.nestedQuery(path, boolQuery, mode);
    }

    @Override
    public QueryBuilder nestedMustNotQuery(String path, QueryBuilder query, ScoreMode mode) {
        QueryBuilder boolQuery = QueryBuilders.boolQuery().mustNot(query);

        return QueryBuilders.nestedQuery(path, boolQuery, mode);
    }

    @Override
    public QueryBuilder nestedShouldQuery(String path, QueryBuilder query, ScoreMode mode) {
        QueryBuilder boolQuery = QueryBuilders.boolQuery().should(query).minimumShouldMatch(1);

        return QueryBuilders.nestedQuery(path, boolQuery, mode);
    }

    @Override
    public FieldSortBuilder fieldSort(String field) {
        return this.fieldSort(field, SortOrder.DESC);
    }

    @Override
    public FieldSortBuilder fieldSort(String field, SortOrder order) {
        return new FieldSortBuilder(field).order(order);
    }

    @Override
    public Collection<FieldSortBuilder> fieldSort(String... fields) {
        return this.fieldSortDesc(fields);
    }

    @Override
    public Collection<FieldSortBuilder> fieldSortAsc(String... fields) {
        return Stream.of(fields).map((field) -> this.fieldSort(field, SortOrder.ASC)).collect(Collectors.toSet());
    }

    @Override
    public Collection<FieldSortBuilder> fieldSortDesc(String... fields) {
        return Stream.of(fields).map(this::fieldSort).collect(Collectors.toSet());
    }
}
