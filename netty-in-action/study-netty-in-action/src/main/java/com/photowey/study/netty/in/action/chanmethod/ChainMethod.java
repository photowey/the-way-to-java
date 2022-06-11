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
package com.photowey.study.netty.in.action.chanmethod;

import java.io.Serializable;

/**
 * {@code ChainMethod}
 *
 * @author photowey
 * @date 2022/03/11
 * @since 1.0.0
 */
public class ChainMethod implements Serializable {

    // chain-method plugin

    private Long id;
    private String name;
    private Integer age;
    private String address;

    // generate by shortcut

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Integer age() {
        return age;
    }

    public String address() {
        return address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ChainMethod id(Long id) {
        this.id = id;
        return this;
    }

    public ChainMethod name(String name) {
        this.name = name;
        return this;
    }

    public ChainMethod age(Integer age) {
        this.age = age;
        return this;
    }

    public ChainMethod address(String address) {
        this.address = address;
        return this;
    }
}
