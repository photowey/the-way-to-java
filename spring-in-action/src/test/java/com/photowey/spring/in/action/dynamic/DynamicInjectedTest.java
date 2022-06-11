/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.spring.in.action.dynamic;

import com.photowey.spring.in.action.SpringApp;
import com.photowey.spring.in.action.dynamic.annotation.DynamicInjected;
import com.photowey.spring.in.action.dynamic.context.DynamicContext;
import com.photowey.spring.in.action.dynamic.context.KvPair;
import com.photowey.spring.in.action.service.payment.IPayment;
import com.photowey.spring.in.action.service.payment.SyrupConsumer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@code DynamicInjectedTest}
 *
 * @author photowey
 * @date 2021/11/10
 * @since 1.0.0
 */
@Slf4j
@SpringBootTest(classes = SpringApp.class)
public class DynamicInjectedTest {

    @DynamicInjected(candidates = "alipayPayment")
    private IPayment payment;

    @DynamicInjected(candidates = "alipayPayment")
    private IPayment payment2;

    @DynamicInjected(value = "batch")
    private IPayment batchPayments;

    @Autowired
    private List<IPayment> payments;

    private IPayment methodPayments;

    @DynamicInjected(candidates = "wechatPayment")
    public void setMethodPayments(IPayment methodPayments) {
        this.methodPayments = methodPayments;
    }

    @Test
    void testAutowiredBatch() {
        for (IPayment payment : payments) {
            payment.doPay("20211110884895271101", "20211110999999999", 100_000);
        }
    }

    @Test
    void testDynamicInjectedBatch() {
        this.batchPayments.doPay("20211110884895271101", "20211110999999999", 100_000);
        List<KvPair> kvPairs = DynamicContext.get();
        Assertions.assertEquals(2, kvPairs.size());
        List<String> sorted = kvPairs.stream().map(KvPair::getYou).map(String::valueOf).sorted().collect(Collectors.toList());
        String join = String.join(",", sorted);
        Assertions.assertEquals("alipayPayment,wechatPayment", join);
    }

    @Test
    void testDynamicInjectedAssign() {
        this.payment.doPay("20211110884895271101", "20211110999999999", 100_000);
        List<KvPair> kvPairs = DynamicContext.get();
        Assertions.assertEquals(1, kvPairs.size());
        KvPair kvPair = kvPairs.get(0);
        Assertions.assertEquals("alipayPayment", kvPair.getYou());
        Assertions.assertEquals("OK", kvPair.getMe());
    }

    @Test
    void testDynamicInjectedMethod() {
        this.methodPayments.doPay("20211110884895271101", "20211110999999999", 100_000);
        List<KvPair> kvPairs = DynamicContext.get();
        Assertions.assertEquals(1, kvPairs.size());
        KvPair kvPair = kvPairs.get(0);
        Assertions.assertEquals("wechatPayment", kvPair.getYou());
        Assertions.assertEquals("OK", kvPair.getMe());
    }

    // @Autowired
    private SyrupConsumer syrupConsumer;

    // Not support now~
    // @Test
    void testDynamicInjectedConstructor() {
        this.syrupConsumer.doPay("20211110884895271101", "20211110999999999", 100_000);
        List<KvPair> kvPairs = DynamicContext.get();
        Assertions.assertEquals(1, kvPairs.size());
        KvPair kvPair = kvPairs.get(0);
        Assertions.assertEquals("wechatPayment", kvPair.getYou());
        Assertions.assertEquals("OK", kvPair.getMe());
    }
}
