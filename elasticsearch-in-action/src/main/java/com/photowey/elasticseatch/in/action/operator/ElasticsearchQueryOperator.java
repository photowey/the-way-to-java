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

import com.photowey.common.in.action.util.ObjectUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@code ElasticsearchQueryOperator}
 *
 * @author photowey
 * @date 2023/02/27
 * @since 1.0.0
 */
public class ElasticsearchQueryOperator {

    @Autowired
    private ElasticsearchOperator elasticsearchOperator;

    public <V> void shouldTermQuery(BoolQueryBuilder builder, String field, V v) {
        if (ObjectUtils.isNotNullOrEmpty(v)) {
            QueryBuilder query = QueryBuilders.termQuery(field, v);
            builder.should(query);
        }
    }

    public <V> void shouldMatchQuery(BoolQueryBuilder builder, String field, V v) {
        if (ObjectUtils.isNotNullOrEmpty(v)) {
            QueryBuilder query = QueryBuilders.matchQuery(field, v);
            builder.should(query);
        }
    }

    public <V> void shouldNestedQuery(BoolQueryBuilder builder, String path, String field, V v) {
        if (ObjectUtils.isNotNullOrEmpty(v)) {
            QueryBuilder query = this.elasticsearchOperator
                    .elasticsearchQueryBuilder()
                    .nestedQuery(path, field, v);

            builder.should(query);
        }
    }

    public <V> void shouldNestedGtQuery(BoolQueryBuilder builder, String path, String field, V v) {
        if (ObjectUtils.isNotNullOrEmpty(v)) {
            QueryBuilder query = this.elasticsearchOperator
                    .elasticsearchQueryBuilder()
                    .nestedRangeGtQuery(path, field, v, ScoreMode.None);

            builder.should(query);
        }
    }

    public <V> void shouldNestedGteQuery(BoolQueryBuilder builder, String path, String field, V v) {
        if (ObjectUtils.isNotNullOrEmpty(v)) {
            QueryBuilder query = this.elasticsearchOperator
                    .elasticsearchQueryBuilder()
                    .nestedRangeGteQuery(path, field, v, ScoreMode.None);

            builder.should(query);
        }
    }

    public <V> void shouldNestedLtQuery(BoolQueryBuilder builder, String path, String field, V v) {
        if (ObjectUtils.isNotNullOrEmpty(v)) {
            QueryBuilder query = this.elasticsearchOperator
                    .elasticsearchQueryBuilder()
                    .nestedRangeLtQuery(path, field, v, ScoreMode.None);

            builder.should(query);
        }
    }

    public <V> void shouldNestedLteQuery(BoolQueryBuilder builder, String path, String field, V v) {
        if (ObjectUtils.isNotNullOrEmpty(v)) {
            QueryBuilder query = this.elasticsearchOperator
                    .elasticsearchQueryBuilder()
                    .nestedRangeLteQuery(path, field, v, ScoreMode.None);

            builder.should(query);
        }
    }

    public <V> void shouldNestedXtQuery(BoolQueryBuilder builder, String path, String field, V v1, V v2) {
        if (ObjectUtils.isNullOrEmpty(v1) && ObjectUtils.isNullOrEmpty(v2)) {
            return;
        }
        if (ObjectUtils.isNotNullOrEmpty(v1) && ObjectUtils.isNullOrEmpty(v2)) {
            shouldNestedGtQuery(builder, path, field, v1);
            return;
        }
        if (ObjectUtils.isNullOrEmpty(v1) && ObjectUtils.isNotNullOrEmpty(v2)) {
            shouldNestedLtQuery(builder, path, field, v2);
            return;
        }

        QueryBuilder query = this.elasticsearchOperator
                .elasticsearchQueryBuilder()
                .nestedRangeXtQuery(path, field, v1, v2, ScoreMode.None);

        builder.should(query);
    }

    public <V> void shouldNestedXteQuery(BoolQueryBuilder builder, String path, String field, V v1, V v2) {
        if (ObjectUtils.isNullOrEmpty(v1) && ObjectUtils.isNullOrEmpty(v2)) {
            return;
        }
        if (ObjectUtils.isNotNullOrEmpty(v1) && ObjectUtils.isNullOrEmpty(v2)) {
            shouldNestedGteQuery(builder, path, field, v1);
            return;
        }
        if (ObjectUtils.isNullOrEmpty(v1) && ObjectUtils.isNotNullOrEmpty(v2)) {
            shouldNestedLteQuery(builder, path, field, v2);
            return;
        }

        QueryBuilder query = this.elasticsearchOperator
                .elasticsearchQueryBuilder()
                .nestedRangeXteQuery(path, field, v1, v2, ScoreMode.None);

        builder.should(query);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public <V> void shouldRangeGtQuery(BoolQueryBuilder builder, String path, String field, V v) {
        if (ObjectUtils.isNotNullOrEmpty(v)) {
            QueryBuilder query = this.elasticsearchOperator
                    .elasticsearchQueryBuilder()
                    .rangeGtQuery(path, field, v);

            builder.should(query);
        }
    }

    public <V> void shouldRangeGteQuery(BoolQueryBuilder builder, String path, String field, V v) {
        if (ObjectUtils.isNotNullOrEmpty(v)) {
            QueryBuilder query = this.elasticsearchOperator
                    .elasticsearchQueryBuilder()
                    .rangeGteQuery(path, field, v);

            builder.should(query);
        }
    }

    public <V> void shouldRangeLtQuery(BoolQueryBuilder builder, String path, String field, V v) {
        if (ObjectUtils.isNotNullOrEmpty(v)) {
            QueryBuilder query = this.elasticsearchOperator
                    .elasticsearchQueryBuilder()
                    .rangeLtQuery(path, field, v);

            builder.should(query);
        }
    }

    public <V> void shouldRangeLteQuery(BoolQueryBuilder builder, String path, String field, V v) {
        if (ObjectUtils.isNotNullOrEmpty(v)) {
            QueryBuilder query = this.elasticsearchOperator
                    .elasticsearchQueryBuilder()
                    .rangeLteQuery(path, field, v);

            builder.should(query);
        }
    }

    public <V> void shouldRangeXtQuery(BoolQueryBuilder builder, String path, String field, V v1, V v2) {
        if (ObjectUtils.isNullOrEmpty(v1) && ObjectUtils.isNullOrEmpty(v2)) {
            return;
        }

        // TODO 是否手动转移?
        if (ObjectUtils.isNotNullOrEmpty(v1) && ObjectUtils.isNullOrEmpty(v2)) {
            shouldRangeGtQuery(builder, path, field, v1);
            return;
        }
        if (ObjectUtils.isNullOrEmpty(v1) && ObjectUtils.isNotNullOrEmpty(v2)) {
            shouldRangeLtQuery(builder, path, field, v2);
            return;
        }

        QueryBuilder query = this.elasticsearchOperator
                .elasticsearchQueryBuilder()
                .rangeXtQuery(path, field, v1, v2);

        builder.should(query);
    }

    public <V> void shouldRangeXteQuery(BoolQueryBuilder builder, String path, String field, V v1, V v2) {
        if (ObjectUtils.isNullOrEmpty(v1) && ObjectUtils.isNullOrEmpty(v2)) {
            return;
        }

        // TODO 是否手动转移?
        if (ObjectUtils.isNotNullOrEmpty(v1) && ObjectUtils.isNullOrEmpty(v2)) {
            shouldRangeGteQuery(builder, path, field, v1);
            return;
        }
        if (ObjectUtils.isNullOrEmpty(v1) && ObjectUtils.isNotNullOrEmpty(v2)) {
            shouldRangeLteQuery(builder, path, field, v2);
            return;
        }

        QueryBuilder query = this.elasticsearchOperator
                .elasticsearchQueryBuilder()
                .rangeXteQuery(path, field, v1, v2);

        builder.should(query);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public <V> void shouldRangeGtQuery(BoolQueryBuilder builder, String field, V v) {
        this.shouldRangeGtQuery(builder, "", field, v);
    }

    public <V> void shouldRangeGteQuery(BoolQueryBuilder builder, String field, V v) {
        this.shouldRangeGteQuery(builder, "", field, v);
    }

    public <V> void shouldRangeLtQuery(BoolQueryBuilder builder, String field, V v) {
        this.shouldRangeLtQuery(builder, "", field, v);
    }

    public <V> void shouldRangeLteQuery(BoolQueryBuilder builder, String field, V v) {
        this.shouldRangeLteQuery(builder, "", field, v);
    }

    public <V> void shouldRangeXtQuery(BoolQueryBuilder builder, String field, V v1, V v2) {
        this.shouldRangeXtQuery(builder, "", field, v1, v2);
    }

    public <V> void shouldRangeXteQuery(BoolQueryBuilder builder, String field, V v1, V v2) {
        this.shouldRangeXteQuery(builder, "", field, v1, v2);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public <V> void shouldNestedMatchQuery(BoolQueryBuilder builder, String path, String field, V v) {
        if (ObjectUtils.isNotNullOrEmpty(v)) {
            QueryBuilder query = this.elasticsearchOperator
                    .elasticsearchQueryBuilder()
                    .nestedMatchQuery(path, field, v);

            builder.should(query);
        }
    }

    public <V, CV> void shouldNestedCascadeQuery(BoolQueryBuilder builder, String path, String field, V v, String cascadeField, CV cv) {
        if (ObjectUtils.isNotNullOrEmpty(v)) {
            QueryBuilder query = this.elasticsearchOperator
                    .elasticsearchQueryBuilder()
                    .nestedCascadeQuery(path, field, v, cascadeField, cv);

            builder.should(query);
        }
    }
}
