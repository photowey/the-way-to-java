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
package com.photowey.solr.in.action.solr.repository;

import com.photowey.solr.in.action.domain.document.ProductDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * {@code ProductSolrRepositoryTest}
 *
 * @author photowey
 * @date 2022/03/19
 * @since 1.0.0
 */
@SpringBootTest
class ProductSolrRepositoryTest {

    @Autowired
    private ProductSolrRepository productSolrRepository;

    @Test
    public void testSave() {
        ProductDocument product = new ProductDocument();
        product.setId("1336605944028684290");
        product.setProductName("小米手机");
        product.setCatalogName("手机");
        product.setPrice(new BigDecimal("8848").doubleValue());
        product.setDescription("小米手机12");
        product.setPicture("picture/M00/00/00/wKgAC2I1ssaAAOhHAAhmyQQPGXM280.jpg");

        ProductDocument document = this.productSolrRepository.save(product);
        Assertions.assertNotNull(document);
    }

    @Test
    public void testFindById() {
        Optional<ProductDocument> documentOptional = this.productSolrRepository.findById("1494377100172378113");
        documentOptional.ifPresent(System.out::println);
    }
}