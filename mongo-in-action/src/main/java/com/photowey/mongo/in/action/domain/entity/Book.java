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
package com.photowey.mongo.in.action.domain.entity;

import com.photowey.mongo.in.action.document.MongoDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * {@code Book}
 *
 * @author photowey
 * @date 2021/11/24
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "book-hub")
public class Book implements Serializable, MongoDocument {

    private static final long serialVersionUID = 2749540190414077192L;

    /**
     * 主键标识
     */
    @Id
    private String id;
    /**
     * 业务标识
     * 可以将业务标识自动转换为主键标识
     */
    private String businessId;
    /**
     * 价格
     */
    private Integer price;
    /**
     * 书名
     */
    private String name;
    /**
     * 简介
     */
    private String info;
    /**
     * 出版社
     */
    private String publish;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
