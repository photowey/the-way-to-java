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
package com.photowey.spring.cloud.alibaba.seata.order.in.action.service;

import com.photowey.spring.cloud.alibaba.seata.order.in.action.domain.entity.Order;
import com.photowey.spring.cloud.alibaba.seata.order.in.action.feign.AccountFeignClient;
import com.photowey.spring.cloud.alibaba.seata.order.in.action.feign.StockFeignClient;
import com.photowey.spring.cloud.alibaba.seata.order.in.action.repository.OrderDAO;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

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
@Service
public class OrderService {

    @Resource
    private AccountFeignClient accountFeignClient;
    @Resource
    private StockFeignClient stockFeignClient;
    @Resource
    private OrderDAO orderDAO;

    /**
     * 下单：创建订单、减库存，涉及到两个服务
     *
     * @param userId
     * @param commodityCode
     * @param count
     */
    @Transactional(rollbackFor = Exception.class)
    public void placeOrder(String userId, String commodityCode, Integer count) {
        BigDecimal orderMoney = new BigDecimal(count).multiply(new BigDecimal(5));
        Order order = new Order().setUserId(userId).setCommodityCode(commodityCode).setCount(count).setMoney(orderMoney);
        // 为了方便测试-扣减库存在先(远程调用)
        // deduct 成功 - order 异常
        stockFeignClient.deduct(commodityCode, count);
        orderDAO.insert(order);
        if (commodityCode.equals("product-2")) {
            throw new RuntimeException("异常:模拟业务异常:order branch exception");
        }
    }

    /**
     * 采用-seata 全局事务
     *
     * @param userId
     * @param commodityCode
     * @param count
     * @author photowey
     */
    @GlobalTransactional
    public void globalPlaceOrder(String userId, String commodityCode, Integer count) {
        BigDecimal orderMoney = new BigDecimal(count).multiply(new BigDecimal(5));
        Order order = new Order().setUserId(userId).setCommodityCode(commodityCode).setCount(count).setMoney(orderMoney);
        stockFeignClient.deduct(commodityCode, count);
        orderDAO.insert(order);
        if (commodityCode.equals("product-2")) {
            throw new RuntimeException("异常:模拟业务异常:order branch exception");
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void create(String userId, String commodityCode, Integer count) {

        BigDecimal orderMoney = new BigDecimal(count).multiply(new BigDecimal(5));

        Order order = new Order().setUserId(userId).setCommodityCode(commodityCode).setCount(count).setMoney(
                orderMoney);
        orderDAO.insert(order);

        accountFeignClient.reduce(userId, orderMoney);

    }

}
