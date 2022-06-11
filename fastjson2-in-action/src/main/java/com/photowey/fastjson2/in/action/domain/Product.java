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
package com.photowey.fastjson2.in.action.domain;

import lombok.ToString;

import java.io.Serializable;

/**
 * {@code Product}
 *
 * @author photowey
 * @date 2022/04/23
 * @since 1.0.0
 */
@ToString
public class Product implements Serializable {

    private static final long serialVersionUID = -6899900509665415646L;

    public Long id;
    public String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product id(Long id) {
        this.id = id;
        return this;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }
}
