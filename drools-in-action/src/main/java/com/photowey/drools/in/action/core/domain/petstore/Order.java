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
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * {@code Order}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/10
 */
@Setter
@Getter
public class Order implements Serializable {

    private static final long serialVersionUID = -7260130106941902846L;

    private static String newline = System.getProperty("line.separator");

    private List<Purchase> items;

    private double grossTotal = -1;
    private double discountedTotal = -1;

    public Order() {
        this.items = new ArrayList<>();
    }

    public void addItem(Purchase item) {
        this.items.add(item);
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();

        buf.append("ShoppingCart:" + newline);

        Iterator<Purchase> itemIter = getItems().iterator();

        while (itemIter.hasNext()) {
            buf.append("\t" + itemIter.next() + newline);
        }

        return buf.toString();
    }
}
