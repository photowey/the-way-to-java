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
package com.photowey.solr.in.action.service.impl;

import com.photowey.solr.in.action.domain.document.ProductDocument;
import com.photowey.solr.in.action.service.ProductSolrService;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * {@code ProductSolrServiceIMpl}
 *
 * @author photowey
 * @date 2022/03/19
 * @since 1.0.0
 */
@Service
public class ProductSolrServiceImpl implements ProductSolrService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public UpdateResponse save(ProductDocument document) {
        return this.solrTemplate.saveBean("core-products", document, Duration.of(3, ChronoUnit.SECONDS));
    }

}
