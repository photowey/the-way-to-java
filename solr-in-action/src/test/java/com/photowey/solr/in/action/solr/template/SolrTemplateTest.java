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
package com.photowey.solr.in.action.solr.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.photowey.solr.in.action.domain.PageDocument;
import com.photowey.solr.in.action.domain.document.ProductDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

/**
 * {@code SolrTemplateTest}
 *
 * @author photowey
 * @date 2022/03/21
 * @since 1.0.0
 */
@SpringBootTest
class SolrTemplateTest {

    // Combined query

    @Autowired
    private SolrTemplate solrTemplate;

    @Test
    void testQueryOr() {
        // Query query = new SimpleQuery("prod_catalog_name:幽默杂货 OR prod_catalog_name:时尚卫浴");
        Query query = new SimpleQuery("prod_catalog_name:幽默杂货 || prod_catalog_name:时尚卫浴");
        query.setOffset(1024L);
        query.setRows(10);
        ScoredPage<ProductDocument> documents = this.solrTemplate.queryForPage(ProductDocument.SOLR_CORE_PRODUCTS_NAME, query, ProductDocument.class);

        long totalElements = documents.getTotalElements();
        int totalPages = documents.getTotalPages();
        Float maxScore = documents.getMaxScore();

        PageDocument<ProductDocument> document = new PageDocument<>(totalElements, totalPages, maxScore, documents.getContent());
        System.out.println(JSON.toJSONString(document, SerializerFeature.PrettyFormat));
    }

    @Test
    void testQueryAnd() {
        // Query query = new SimpleQuery("prod_catalog_name:幽默杂货 AND prod_catalog_name:时尚卫浴");
        Query query = new SimpleQuery("+prod_catalog_name:幽默杂货 +prod_catalog_name:时尚卫浴");
        query.setOffset(0L);
        query.setRows(10);
        ScoredPage<ProductDocument> documents = this.solrTemplate.queryForPage(ProductDocument.SOLR_CORE_PRODUCTS_NAME, query, ProductDocument.class);

        long totalElements = documents.getTotalElements();
        int totalPages = documents.getTotalPages();
        Assertions.assertEquals(0, totalElements);
        Assertions.assertEquals(0, totalPages);
    }

    @Test
    void testQueryNot() {
        // Query query = new SimpleQuery("prod_catalog_name:幽默杂货 NOT prod_catalog_name:时尚卫浴");
        Query query = new SimpleQuery("+prod_catalog_name:幽默杂货 -prod_catalog_name:时尚卫浴");
        query.setOffset(0L);
        query.setRows(10);
        ScoredPage<ProductDocument> documents = this.solrTemplate.queryForPage(ProductDocument.SOLR_CORE_PRODUCTS_NAME, query, ProductDocument.class);

        long totalElements = documents.getTotalElements();
        int totalPages = documents.getTotalPages();
        Float maxScore = documents.getMaxScore();

        PageDocument<ProductDocument> document = new PageDocument<>(totalElements, totalPages, maxScore, documents.getContent());
        System.out.println(JSON.toJSONString(document, SerializerFeature.PrettyFormat));
    }

    @Test
    void testQueryLikeRight() {
        Query query = new SimpleQuery("prod_catalog_name:时尚*");
        query.setOffset(0L);
        query.setRows(10);
        ScoredPage<ProductDocument> documents = this.solrTemplate.queryForPage(ProductDocument.SOLR_CORE_PRODUCTS_NAME, query, ProductDocument.class);

        long totalElements = documents.getTotalElements();
        int totalPages = documents.getTotalPages();
        Float maxScore = documents.getMaxScore();

        PageDocument<ProductDocument> document = new PageDocument<>(totalElements, totalPages, maxScore, documents.getContent());
        System.out.println(JSON.toJSONString(document, SerializerFeature.PrettyFormat));
    }
}
