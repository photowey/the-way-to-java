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
package com.photowey.elasticseatch.in.action.config;

import com.photowey.elasticseatch.in.action.autoconfigure.ElasticsearchQueryBuilder;
import com.photowey.elasticseatch.in.action.operator.DefaultElasticsearchOperator;
import com.photowey.elasticseatch.in.action.operator.ElasticsearchOperator;
import com.photowey.elasticseatch.in.action.operator.ElasticsearchQueryOperator;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * {@code AppElasticsearchAutoConfigure}
 *
 * @author photowey
 * @date 2022/11/23
 * @since 1.0.0
 */
@AutoConfiguration(after = ElasticsearchDataAutoConfiguration.class)
@Import(value = {RestClientElasticsearchConfigure.class})
public class AppElasticsearchAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(ElasticsearchOperator.class)
    public ElasticsearchOperator elasticsearchOperator(
            RestHighLevelClient elasticsearchClient,
            ElasticsearchRestTemplate elasticsearchRestTemplate,
            ElasticsearchQueryBuilder elasticsearchQueryBuilder) {

        return new DefaultElasticsearchOperator(elasticsearchClient, elasticsearchRestTemplate, elasticsearchQueryBuilder);
    }

    @Bean
    public ElasticsearchQueryOperator elasticsearchQueryOperator() {
        return new ElasticsearchQueryOperator();
    }
}
