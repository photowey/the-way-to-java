/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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

import com.photowey.solr.in.action.domain.document.AreaDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FacetOptions;
import org.springframework.data.solr.core.query.FacetQuery;
import org.springframework.data.solr.core.query.SimpleFacetQuery;
import org.springframework.data.solr.core.query.result.FacetFieldEntry;
import org.springframework.data.solr.core.query.result.FacetPage;

import java.util.List;

/**
 * {@code SolrTemplateFacetQueryTest}
 *
 * @author photowey
 * @date 2022/03/24
 * @since 1.0.0
 */
@SpringBootTest
class SolrTemplateFacetQueryTest {

    // Facet query

    @Autowired
    private SolrTemplate solrTemplate;

    @Test
    void testProductJsonFacetQuery() {

        // http://192.168.0.11:8983/solr/core-areas/select?q=merger_name:中国*&facet=on&facet.field=first&facet.mincount=1
        // http://192.168.0.11:8983/solr/core-areas/select?q=merger_name%3A%E4%B8%AD%E5%9B%BD*&facet=true&facet.mincount=1&facet.limit=10&facet.field=first&wt=javabin&version=2

        // 没有好的测试数据模型 - 勉强构造
        FacetQuery facetQuery = new SimpleFacetQuery(new Criteria("merger_name").startsWith("中国"));

        FacetOptions facetOptions = new FacetOptions();
        facetOptions.addFacetOnField("first");
        facetOptions.setFacetMinCount(1);
        facetQuery.setFacetOptions(facetOptions);

        FacetPage<AreaDocument> facetPage = this.solrTemplate.queryForFacetPage(AreaDocument.SOLR_CORE_AREA_NAME, facetQuery, AreaDocument.class);
        long totalElements = facetPage.getTotalElements();
        int totalPages = facetPage.getTotalPages();
        println("totalElements=" + totalElements);
        println("totalPages=" + totalPages);

        Page<FacetFieldEntry> fieldEntries = facetPage.getFacetResultPage("first");
        List<FacetFieldEntry> entryList = fieldEntries.getContent();
        for (FacetFieldEntry facetEntry : entryList) {
            println("key=" + facetEntry.getKey() + "--value=" + facetEntry.getValue());
        }

    }

    private static void println(String content) {
        System.out.println(content);
    }

}
