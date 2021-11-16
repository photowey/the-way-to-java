/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.spring.in.action.prototype;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.HashSet;
import java.util.Set;

/**
 * {@code PrototypeBeanTest}
 *
 * @author photowey
 * @date 2021/11/17
 * @since 1.0.0
 */
@SpringBootTest
class PrototypeBeanTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testPrototypeBean() {
        PrototypeBean prototypeBean = this.applicationContext.getBean(PrototypeBean.class);
        Assertions.assertNotNull(prototypeBean);

        Set<Integer> hashCodes = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            // 多例 - 变-单例
            int hashCode = prototypeBean.printHashCode();
            hashCodes.add(hashCode);
        }

        Assertions.assertTrue(1 == hashCodes.size());
    }

    @Test
    void testPrototypeBeanProxy() {
        PrototypeBean prototypeBean = this.applicationContext.getBean(PrototypeBean.class);
        Assertions.assertNotNull(prototypeBean);

        Set<Integer> hashCodes = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            // 继续是多例
            int hashCode = prototypeBean.printProxyHashCode();
            hashCodes.add(hashCode);
        }

        Assertions.assertTrue(5 == hashCodes.size());
    }
}