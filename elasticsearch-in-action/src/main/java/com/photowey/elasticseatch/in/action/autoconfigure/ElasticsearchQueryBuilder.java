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
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.Collection;

/**
 * {@code ElasticsearchQueryBuilder}
 *
 * @author photowey
 * @date 2022/11/23
 * @since 1.0.0
 */
public interface ElasticsearchQueryBuilder {
    <V> QueryBuilder nestedQuery(String path, String field, V v);

    <V> QueryBuilder nestedMatchQuery(String path, String field, V v);

    <V> QueryBuilder nestedQuery(String path, String field, V v, ScoreMode mode);

    <V> QueryBuilder nestedRangeGtQuery(String path, String field, V v, ScoreMode mode);

    <V> QueryBuilder nestedRangeLtQuery(String path, String field, V v, ScoreMode mode);

    <V> QueryBuilder nestedRangeXtQuery(String path, String field, V v1, V v2, ScoreMode mode);

    <V> QueryBuilder nestedRangeGteQuery(String path, String field, V v, ScoreMode mode);

    <V> QueryBuilder nestedRangeLteQuery(String path, String field, V v, ScoreMode mode);

    <V> QueryBuilder nestedRangeXteQuery(String path, String field, V v1, V v2, ScoreMode mode);

    <V, CV> QueryBuilder nestedCascadeQuery(String path, String field, V v, String cascadeField, CV cv);

    <V, CV> QueryBuilder nestedCascadeQuery(String path, String field, V v, String cascadeField, CV cv, ScoreMode mode);

    <V> QueryBuilder nestedMatchQuery(String path, String field, V v, ScoreMode mode);

    QueryBuilder nestedQuery(String path, QueryBuilder query, ScoreMode mode);

    QueryBuilder nestedMustQuery(String path, QueryBuilder query, ScoreMode mode);

    QueryBuilder nestedMustNotQuery(String path, QueryBuilder query, ScoreMode mode);

    QueryBuilder nestedShouldQuery(String path, QueryBuilder query, ScoreMode mode);

    FieldSortBuilder fieldSort(String field);

    FieldSortBuilder fieldSort(String field, SortOrder order);

    Collection<FieldSortBuilder> fieldSort(String... field);

    Collection<FieldSortBuilder> fieldSortAsc(String... field);

    Collection<FieldSortBuilder> fieldSortDesc(String... field);

}
