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
package com.photowey.drools.in.action.core.domain.petstore;

import lombok.Getter;

import java.io.Serializable;

/**
 * {@code Purchase}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/10
 */
@Getter
public class Purchase implements Serializable {

    private static final long serialVersionUID = -7260130106941902846L;

    private Order order;
    private Product product;

    public Purchase(Order order, Product product) {
        this.order = order;
        this.product = product;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((order == null) ? 0 : order.hashCode());
        result = PRIME * result + ((product == null) ? 0 : product.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Purchase other = (Purchase) obj;
        if (order == null) {
            if (other.order != null) return false;
        } else if (!order.equals(other.order)) return false;
        if (product == null) {
            if (other.product != null) return false;
        } else if (!product.equals(other.product)) return false;
        return true;
    }

}
