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
package com.photowey.solr.in.action.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * {@code Product}
 *
 * @author photowey
 * @date 2022/03/19
 * @since 1.0.0
 */
@Data
@TableName("products")
@SolrDocument(collection = "core-products")
public class Product implements Serializable {

    private static final long serialVersionUID = -2527219966149219594L;

    @Id
    @Field("id")
    @TableId("pid")
    private String id;

    @Field("prod_pname")
    @TableField(value = "pname")
    private String productName;

    @Field("prod_catalog_name")
    @TableField(value = "catalog_name")
    private String catalogName;

    @Field("prod_price")
    private BigDecimal price;

    @Field("prod_description")
    private String description;

    @Field("prod_picture")
    private String picture;
}
