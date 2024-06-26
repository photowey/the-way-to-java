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
package io.github.photowey.spring.modulith.in.action.inventory;

import io.github.photowey.spring.modulith.in.action.order.OrderCompleted;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

/**
 * {@code InventoryManagement}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/06/27
 */
@Slf4j
@Service
@RequiredArgsConstructor
class InventoryManagement {

    private final InventoryInternal dependency;

    @ApplicationModuleListener
    void onOrderCompleted(OrderCompleted event) throws InterruptedException {

        var orderId = event.orderId();

        log.info("Received order completion for {}.", orderId);

        // Simulate busy work
        Thread.sleep(1000);

        log.info("Finished order completion for {}.", orderId);
    }
}
