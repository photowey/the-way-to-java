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

import com.photowey.drools.in.action.core.domain.petstore.PetStore;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;

/**
 * {@code PetStoreTest}
 *
 * @author photowey
 * @since 2025/02/10
 */
class PetStoreTest {

    @Test
    void testPetStore() throws Exception {
        run();

        Thread.sleep(1000_000L);
    }

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        KieServices ks = KieServices.get();
        KieContainer kc = ks.getKieClasspathContainer();

        PetStore petStore = new PetStore();
        petStore.init(kc, true);
    }
}
