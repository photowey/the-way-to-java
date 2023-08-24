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
package com.photowey.rocketmq.in.action.message.order;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code TMallOrderFactory}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
public final class TMallOrderFactory {

    private TMallOrderFactory() {
    }

    /**
     * 生成模拟订单数据
     * 3个订单
     * 每个订单4个状态
     */
    public static List<TMallOrder> buildOrders() {
        List<TMallOrder> orderList = new ArrayList<>();

        orderList.add(populateOrder(2021103188489527001L, "创建"));
        orderList.add(populateOrder(2021103188489527002L, "创建"));
        orderList.add(populateOrder(2021103188489527001L, "付款"));
        orderList.add(populateOrder(2021103188489527003L, "创建"));
        orderList.add(populateOrder(2021103188489527002L, "付款"));

        orderList.add(populateOrder(2021103188489527003L, "付款"));
        orderList.add(populateOrder(2021103188489527002L, "推送"));
        orderList.add(populateOrder(2021103188489527003L, "推送"));

        orderList.add(populateOrder(2021103188489527002L, "完成"));
        orderList.add(populateOrder(2021103188489527001L, "推送"));
        orderList.add(populateOrder(2021103188489527003L, "完成"));
        orderList.add(populateOrder(2021103188489527001L, "完成"));

        return orderList;
    }

    public static TMallOrder populateOrder(Long orderId, String action) {
        TMallOrder order = new TMallOrder();
        order.setOrderId(orderId);
        order.setActon(action);

        return order;
    }
}
