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

import org.kie.api.runtime.KieContainer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code PetStore}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/10
 */
public class PetStore implements Serializable {

    private static final long serialVersionUID = -3475791832743362978L;

    public void init(KieContainer kc, boolean exitOnClose) {
        List<Product> stock = new ArrayList<>();
        stock.add(new Product("Gold Fish", 5));
        stock.add(new Product("Fish Tank", 25));
        stock.add(new Product("Fish Food", 2));

        PetStoreUI ui = new PetStoreUI(stock, new PetStoreUI.CheckoutCallback(kc));
        ui.createAndShowGUI(exitOnClose);
    }
}
