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
package com.photowey.elasticseatch.in.action.builder;

import com.photowey.commom.in.action.util.ObjectUtils;
import com.photowey.elasticseatch.in.action.constant.ElasticsearchQueryConstants;
import com.photowey.elasticseatch.in.action.enums.MathOperatorEnum;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.Collection;
import java.util.function.Consumer;
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
        QueryBuilder termQuery = QueryBuilders.termQuery(this.formatPath(path, field), v);

        return this.nestedQuery(path, termQuery, mode);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public <V> QueryBuilder nestedRangeGtQuery(String path, String field, V v, ScoreMode mode) {
        QueryBuilder termQuery = QueryBuilders.rangeQuery(this.formatPath(path, field)).gt(v);

        return this.nestedQuery(path, termQuery, mode);
    }

    @Override
    public <V> QueryBuilder nestedRangeLtQuery(String path, String field, V v, ScoreMode mode) {
        QueryBuilder termQuery = QueryBuilders.rangeQuery(this.formatPath(path, field)).lt(v);

        return this.nestedQuery(path, termQuery, mode);
    }

    @Override
    public <V> QueryBuilder nestedRangeXtQuery(String path, String field, V v1, V v2, ScoreMode mode) {
        QueryBuilder termQuery = QueryBuilders.rangeQuery(this.formatPath(path, field)).gt(v1).lt(v2);

        return this.nestedQuery(path, termQuery, mode);
    }

    @Override
    public <V> QueryBuilder nestedRangeGteQuery(String path, String field, V v, ScoreMode mode) {
        QueryBuilder termQuery = QueryBuilders.rangeQuery(this.formatPath(path, field)).gte(v);

        return this.nestedQuery(path, termQuery, mode);
    }

    @Override
    public <V> QueryBuilder nestedRangeLteQuery(String path, String field, V v, ScoreMode mode) {
        QueryBuilder termQuery = QueryBuilders.rangeQuery(this.formatPath(path, field)).lte(v);

        return this.nestedQuery(path, termQuery, mode);
    }

    @Override
    public <V> QueryBuilder nestedRangeXteQuery(String path, String field, V v1, V v2, ScoreMode mode) {
        QueryBuilder termQuery = QueryBuilders.rangeQuery(this.formatPath(path, field)).gte(v1).lte(v2);

        return this.nestedQuery(path, termQuery, mode);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public QueryBuilder filterQuery(QueryBuilder query) {
        return QueryBuilders.boolQuery().filter(query);
    }

    @Override
    public QueryBuilder shouldQuery(QueryBuilder query) {
        return QueryBuilders.boolQuery().should(query);
    }

    @Override
    public QueryBuilder mustQuery(QueryBuilder query) {
        return QueryBuilders.boolQuery().must(query);
    }

    @Override
    public QueryBuilder mustNotQuery(QueryBuilder query) {
        return QueryBuilders.boolQuery().mustNot(query);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public <V> QueryBuilder rangeGtQuery(String path, String field, V v) {
        QueryBuilder termQuery = QueryBuilders.rangeQuery(this.formatPath(path, field)).gt(v);

        return this.mustQuery(termQuery);
    }

    @Override
    public <V> QueryBuilder rangeLtQuery(String path, String field, V v) {
        QueryBuilder termQuery = QueryBuilders.rangeQuery(this.formatPath(path, field)).lt(v);

        return this.mustQuery(termQuery);
    }

    @Override
    public <V> QueryBuilder rangeXtQuery(String path, String field, V v1, V v2) {
        QueryBuilder termQuery = QueryBuilders.rangeQuery(this.formatPath(path, field)).gt(v1).lt(v2);

        return this.mustQuery(termQuery);
    }

    @Override
    public <V> QueryBuilder rangeGteQuery(String path, String field, V v) {
        QueryBuilder termQuery = QueryBuilders.rangeQuery(this.formatPath(path, field)).gte(v);

        return this.mustQuery(termQuery);
    }

    @Override
    public <V> QueryBuilder rangeLteQuery(String path, String field, V v) {
        QueryBuilder termQuery = QueryBuilders.rangeQuery(this.formatPath(path, field)).lte(v);

        return this.mustQuery(termQuery);
    }

    @Override
    public <V> QueryBuilder rangeXteQuery(String path, String field, V v1, V v2) {
        QueryBuilder termQuery = QueryBuilders.rangeQuery(this.formatPath(path, field)).gte(v1).lte(v2);

        return this.mustQuery(termQuery);
    }

    @Override
    public <V> QueryBuilder rangeQuery(String path, String field, V v, MathOperatorEnum operator) {
        switch (operator) {
            case GT:
                return this.rangeGtQuery(field, v);
            case LT:
                return this.rangeLtQuery(field, v);
            case GTE:
                return this.rangeGteQuery(field, v);
            case LTE:
                return this.rangeLteQuery(field, v);
        }

        throw new UnsupportedOperationException("UnSupported operator");
    }

    @Override
    public <V> QueryBuilder rangeQuery(String path, String field, V v1, V v2, MathOperatorEnum operator) {
        switch (operator) {
            case GT_LT:
                return this.rangeXtQuery(path, field, v1, v2);
            case GTE_LTE:
                return this.rangeXteQuery(path, field, v1, v2);
        }

        throw new UnsupportedOperationException("UnSupported operator");
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public <V, CV> QueryBuilder nestedCascadeQuery(String path, String field, V v, String cascadeField, CV cv) {
        return this.nestedCascadeQuery(path, field, v, cascadeField, cv, ScoreMode.None);
    }

    @Override
    public <V, CV> QueryBuilder nestedCascadeQuery(String path, String field, V v, String cascadeField, CV cv, ScoreMode mode) {
        QueryBuilder termQuery = QueryBuilders.termQuery(this.formatPath(path, field), v);
        QueryBuilder termQueryCascade = QueryBuilders.termQuery(this.formatPath(path, cascadeField), cv);
        QueryBuilder termQueryx = QueryBuilders.boolQuery().filter(termQuery).filter(termQueryCascade);

        return this.nestedQuery(path, termQueryx, mode);
    }

    @Override
    public <V> QueryBuilder nestedMatchQuery(String path, String field, V v, ScoreMode mode) {
        QueryBuilder matchQuery = QueryBuilders.matchQuery(String.format(ElasticsearchQueryConstants.TESTED_PATH_TEMPLATE, path, field), v);

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

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public FieldSortBuilder idSort() {
        return this.pkSort();
    }

    @Override
    public FieldSortBuilder pkSort() {
        return this.fieldSort(ElasticsearchQueryConstants.FIELD_ID_STRING);
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

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public NativeSearchQuery pageableQuery(QueryBuilder builder, int pageNo, int pageSize) {
        return this.pageableQuery(builder, pageNo, pageSize, (b) -> {
        });
    }

    @Override
    public NativeSearchQuery pageableQuery(QueryBuilder builder, int pageNo, int pageSize, Consumer<NativeSearchQueryBuilder> fx) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .withSorts(this.pkSort())
                .withPageable(PageRequest.of(pageNo, pageSize));

        fx.accept(queryBuilder);

        return queryBuilder.build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public HighlightBuilder.Field highlightField(String field) {
        return new HighlightBuilder.Field(field);
    }

    @Override
    public HighlightBuilder highlightBuilder() {
        return SearchSourceBuilder
                .highlight()
                .preTags(ElasticsearchQueryConstants.HIGHLIGHT_PRE_TAG)
                .postTags(ElasticsearchQueryConstants.HIGHLIGHT_POST_TAG);
    }

    @Override
    public NativeSearchQuery highlightQuery(QueryBuilder builder, String field) {
        return this.highlightPageableQuery(builder, field, 0, 0, false);
    }

    @Override
    public NativeSearchQuery highlightPageableQuery(QueryBuilder builder, String field, int pageNo, int pageSize) {
        return this.highlightPageableQuery(builder, field, pageNo, pageSize, true);
    }

    @Override
    public NativeSearchQuery highlightPageableQuery(QueryBuilder builder, String field, int pageNo, int pageSize, boolean pageable) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .withHighlightFields(this.highlightField(field))
                .withHighlightBuilder(this.highlightBuilder())
                .withSorts(this.pkSort());

        if (pageable) {
            nativeSearchQueryBuilder.withPageable(PageRequest.of(pageNo, pageNo));
        }

        return nativeSearchQueryBuilder.build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    private String formatPath(String path, String field) {
        if (ObjectUtils.isNullOrEmpty(path)) {
            return field;
        }

        return String.format(ElasticsearchQueryConstants.TESTED_PATH_TEMPLATE, path, field);
    }
}
