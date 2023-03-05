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
package com.photowey.elasticseatch.in.action.builder;

import com.photowey.elasticseatch.in.action.enums.MathOperatorEnum;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.Collection;
import java.util.function.Consumer;

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

    // -----------------------------------------------------------------------------------------------------------------

    <V> QueryBuilder nestedRangeGtQuery(String path, String field, V v, ScoreMode mode);

    <V> QueryBuilder nestedRangeLtQuery(String path, String field, V v, ScoreMode mode);

    <V> QueryBuilder nestedRangeXtQuery(String path, String field, V v1, V v2, ScoreMode mode);

    <V> QueryBuilder nestedRangeGteQuery(String path, String field, V v, ScoreMode mode);

    <V> QueryBuilder nestedRangeLteQuery(String path, String field, V v, ScoreMode mode);

    <V> QueryBuilder nestedRangeXteQuery(String path, String field, V v1, V v2, ScoreMode mode);

    // -----------------------------------------------------------------------------------------------------------------

    QueryBuilder filterQuery(QueryBuilder query);

    QueryBuilder shouldQuery(QueryBuilder query);

    QueryBuilder mustQuery(QueryBuilder query);

    QueryBuilder mustNotQuery(QueryBuilder query);

    // -----------------------------------------------------------------------------------------------------------------

    default <V> QueryBuilder rangeGtQuery(String field, V v) {
        return this.rangeGtQuery("", field, v);
    }

    default <V> QueryBuilder rangeLtQuery(String field, V v) {
        return this.rangeLtQuery("", field, v);
    }

    default <V> QueryBuilder rangeXtQuery(String field, V v1, V v2) {
        return this.rangeXtQuery("", field, v1, v2);
    }

    default <V> QueryBuilder rangeGteQuery(String field, V v) {
        return this.rangeGteQuery("", field, v);
    }

    default <V> QueryBuilder rangeLteQuery(String field, V v) {
        return this.rangeLteQuery("", field, v);
    }

    default <V> QueryBuilder rangeXteQuery(String field, V v1, V v2) {
        return this.rangeXteQuery("", field, v1, v2);
    }

    // -----------------------------------------------------------------------------------------------------------------

    <V> QueryBuilder rangeGtQuery(String path, String field, V v);

    <V> QueryBuilder rangeLtQuery(String path, String field, V v);

    <V> QueryBuilder rangeXtQuery(String path, String field, V v1, V v2);

    <V> QueryBuilder rangeGteQuery(String path, String field, V v);

    <V> QueryBuilder rangeLteQuery(String path, String field, V v);

    <V> QueryBuilder rangeXteQuery(String path, String field, V v1, V v2);

    <V> QueryBuilder rangeQuery(String path, String field, V v, MathOperatorEnum operator);

    <V> QueryBuilder rangeQuery(String path, String field, V v1, V v2, MathOperatorEnum operator);

    // -----------------------------------------------------------------------------------------------------------------

    <V, CV> QueryBuilder nestedCascadeQuery(String path, String field, V v, String cascadeField, CV cv);

    <V, CV> QueryBuilder nestedCascadeQuery(String path, String field, V v, String cascadeField, CV cv, ScoreMode mode);

    <V> QueryBuilder nestedMatchQuery(String path, String field, V v, ScoreMode mode);

    QueryBuilder nestedQuery(String path, QueryBuilder query, ScoreMode mode);

    QueryBuilder nestedMustQuery(String path, QueryBuilder query, ScoreMode mode);

    QueryBuilder nestedMustNotQuery(String path, QueryBuilder query, ScoreMode mode);

    QueryBuilder nestedShouldQuery(String path, QueryBuilder query, ScoreMode mode);

    FieldSortBuilder idSort();

    FieldSortBuilder pkSort();

    FieldSortBuilder fieldSort(String field);

    FieldSortBuilder fieldSort(String field, SortOrder order);

    Collection<FieldSortBuilder> fieldSort(String... field);

    Collection<FieldSortBuilder> fieldSortAsc(String... field);

    Collection<FieldSortBuilder> fieldSortDesc(String... field);

    // -----------------------------------------------------------------------------------------------------------------

    NativeSearchQuery pageableQuery(QueryBuilder builder, int pageNo, int pageSize);

    NativeSearchQuery pageableQuery(QueryBuilder builder, int pageNo, int pageSize, Consumer<NativeSearchQueryBuilder> fx);

    // -----------------------------------------------------------------------------------------------------------------

    HighlightBuilder.Field highlightField(String field);

    HighlightBuilder highlightBuilder();

    NativeSearchQuery highlightQuery(QueryBuilder builder, String field);

    NativeSearchQuery highlightPageableQuery(QueryBuilder builder, String field, int pageNo, int pageSize);

    NativeSearchQuery highlightPageableQuery(QueryBuilder builder, String field, int pageNo, int pageSize, boolean pageable);
}
