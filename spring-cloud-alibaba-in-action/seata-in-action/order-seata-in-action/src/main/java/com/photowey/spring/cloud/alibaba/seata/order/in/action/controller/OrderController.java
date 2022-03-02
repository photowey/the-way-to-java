/*
 *  Copyright 1999-2021 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.photowey.spring.cloud.alibaba.seata.order.in.action.controller;

import com.photowey.spring.cloud.alibaba.seata.order.in.action.feign.StockFeignClient;
import com.photowey.spring.cloud.alibaba.seata.order.in.action.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Program Name: springcloud-nacos-seata
 * <p>
 * Description:
 * <p>
 *
 * @author zhangjianwei
 * @version 1.0
 * @date 2019/8/28 4:05 PM
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderService orderService;
    @Resource
    private StockFeignClient stockFeignClient;

    /**
     * 为了方便测试:
     * 执行流程:
     * 1.先远程减库存
     * 2.本地提交订单
     * - 本地 - 选择性异常触发回滚
     */

    /**
     * 下单：插入订单表、扣减库存，模拟回滚
     *
     * @return
     * @author photowey
     * @see * http://localhost:7928/order/placeOrder/commit
     */
    @GetMapping("/placeOrder/commit")
    public Boolean placeOrderCommit() {
        // 扣库存成功-订单成功--结果: 库存减-订单增
        orderService.placeOrder("1", "product-1", 1);
        return true;
    }

    /**
     * 下单：插入订单表、扣减库存，模拟回滚
     *
     * @return
     * @author photowey
     * @see * http://localhost:7928/order/placeOrder/rollback
     */
    @GetMapping("/placeOrder/rollback")
    public Boolean placeOrderRollback() {
        // 扣库存成功-订单失败--结果: 库存减-订单回滚
        orderService.placeOrder("1", "product-2", 1);
        return true;
    }

    // --------------------------------------------------------------------------------------------------

    /**
     * 下单：插入订单表、扣减库存，模拟回滚
     *
     * @return
     * @author photowey
     * @see * http://localhost:7928/order/global/placeOrder/commit
     */
    @GetMapping("/global/placeOrder/commit")
    public Boolean globalPlaceOrderCommit() {
        // 扣库存成功-订单成功--结果: 库存减-订单增
        orderService.globalPlaceOrder("1", "product-1", 1);
        return true;
    }

    /**
     * 下单：插入订单表、扣减库存，模拟回滚
     *
     * @return
     * @author photowey
     * @see * http://localhost:7928/order/global/placeOrder/rollback
     */
    @GetMapping("/global/placeOrder/rollback")
    public Boolean globalPlaceOrderRollback() {
        // 扣库存成功-订单失败--结果: 库存回滚-订单回滚
        orderService.globalPlaceOrder("1", "product-2", 1);
        return true;
    }

    @GetMapping("/placeOrder")
    public Boolean placeOrder(String userId, String commodityCode, Integer count) {
        orderService.placeOrder(userId, commodityCode, count);
        return true;
    }
}
