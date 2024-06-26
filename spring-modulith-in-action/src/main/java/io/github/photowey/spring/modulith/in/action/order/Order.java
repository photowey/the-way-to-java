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
package io.github.photowey.spring.modulith.in.action.order;

import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;

import java.util.UUID;

/**
 * {@code Order}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/06/27
 */
@Getter
public class Order implements AggregateRoot<Order, Order.OrderIdentifier> {

    private final OrderIdentifier id = new OrderIdentifier(UUID.randomUUID());

    public record OrderIdentifier(UUID id) implements Identifier {}
}
