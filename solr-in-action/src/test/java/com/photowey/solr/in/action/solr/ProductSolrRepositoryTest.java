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
package com.photowey.solr.in.action.solr;

import com.photowey.solr.in.action.domain.entity.ProductDocument;
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

    // FIXME

    @Test
    public void testSave() {
        ProductDocument product = new ProductDocument();
        product.setId("1494377100172378113");
        product.setProductName("华为手机");
        product.setCatalogName("手机");
        product.setPrice(new BigDecimal("8848").doubleValue());
        product.setDescription("华为手机Nova7");
        product.setPicture("picture/M00/00/00/wKgAC2I1ssaAAOhHAAhmyQQPGXM280.jpg");

        ProductDocument save = this.productSolrRepository.save(product);
        System.out.println(save);
    }

    @Test
    public void testFindById() {
        Optional<ProductDocument> documentOptional = this.productSolrRepository.findById("1494377100172378113");
        documentOptional.ifPresent(System.out::println);

    }
}