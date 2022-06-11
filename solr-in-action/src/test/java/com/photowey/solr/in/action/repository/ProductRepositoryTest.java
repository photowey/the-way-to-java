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
package com.photowey.solr.in.action.repository;

import com.photowey.solr.in.action.domain.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code ProductRepositoryTest}
 *
 * @author photowey
 * @date 2022/03/19
 * @since 1.0.0
 */
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testQuery() {
        Product product = this.productRepository.selectById("1");
        Assertions.assertNotNull(product);
        Assertions.assertEquals("花儿朵朵彩色金属门后挂&nbsp;8钩免钉门背挂钩2066", product.getProductName());
    }

}