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
package com.photowey.fastjson2.in.action.domain;

import com.alibaba.fastjson2.*;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 * {@code ProductTest}
 *
 * @author photowey
 * @date 2022/04/23
 * @since 1.0.0
 */
class ProductTest {

    @Test
    void testJson() {
        Product product = new Product();
        product.setId(1010L);
        product.setName("DataWorks");

        String jsonString = JSON.toJSONString(product);
        System.out.println(jsonString);

        String toJSONString = JSON.toJSONString(product, JSONWriter.Feature.BeanToArray);
        System.out.println(toJSONString);

        Product product2 = JSON.parseObject(jsonString, Product.class);
        System.out.println(product2);

        byte[] jsonbBytes = JSONB.toBytes(product);

        Product product3 = JSONB.parseObject(jsonbBytes, Product.class);
        System.out.println(product3);
    }


    @Test
    void testJsonBytes() {
        Product product = new Product();
        product.setId(1010L);
        product.setName("DataWorks");

        byte[] jsonbBytes = JSONB.toBytes(product);

        Product product2 = JSONB.parseObject(jsonbBytes, Product.class);
        System.out.println(product2);

        byte[] utf8Bytes = "{\"id\":1010,\"name\":\"DataWorks\"}".getBytes(StandardCharsets.UTF_8);
        Product product3 = JSON.parseObject(utf8Bytes, Product.class);
        System.out.println(product3);
    }

    @Test
    void testJsonParse() {
        Product product = new Product();
        product.setId(1010L);
        product.setName("DataWorks");

        String jsonString = JSON.toJSONString(product);
        System.out.println(jsonString);

        Product product2 = JSON.parseObject(jsonString, Product.class);
        System.out.println(product2);
    }

    @Test
    void testJsonPath() {
        String str = "{\"id\":1010,\"name\":\"DataWorks\"}";

        JSONPath path = JSONPath.of("$.id"); // 缓存起来重复使用能提升性能
        JSONReader parser = JSONReader.of(str);
        Object result = path.extract(parser);
        System.out.println(result);

        byte[] utf8Bytes = str.getBytes(StandardCharsets.UTF_8);

        JSONReader parser2 = JSONReader.of(utf8Bytes);
        Object result2 = path.extract(parser2);
        System.out.println(result2);
    }
}