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
package com.photowey.drools.in.action.domain.petstore;

import lombok.Getter;

import java.io.Serializable;

/**
 * {@code Product}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/10
 */
@Getter
public class Product implements Serializable {

    private static final long serialVersionUID = -7260130106941902846L;

    private String name;
    private double price;

    public Product(String name, double cost) {
        this.name = name;
        this.price = cost;
    }

    @Override
    public String toString() {
        return name + " " + this.price;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((name == null) ? 0 : name.hashCode());
        long temp;
        temp = Double.doubleToLongBits(price);
        result = PRIME * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Product other = (Product) obj;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price)) return false;
        return true;
    }
}
