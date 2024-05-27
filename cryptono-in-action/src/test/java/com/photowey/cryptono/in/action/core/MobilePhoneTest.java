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
package com.photowey.cryptono.in.action.core;

import com.photowey.cryptono.in.action.core.model.MobilePhone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * {@code MobilePhoneTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/26
 */
class MobilePhoneTest {

    @Test
    void testSplit() {
        String phoneNumber = "13112345678";
        int groupLength = 3;
        List<String> group = MobilePhone.split(groupLength, phoneNumber);

        Assertions.assertEquals(9, group.size());
        Assertions.assertEquals("131", group.get(0));
        Assertions.assertEquals("311", group.get(1));
        Assertions.assertEquals("112", group.get(2));
        Assertions.assertEquals("123", group.get(3));
        Assertions.assertEquals("234", group.get(4));
        Assertions.assertEquals("345", group.get(5));
        Assertions.assertEquals("456", group.get(6));
        Assertions.assertEquals("567", group.get(7));
        Assertions.assertEquals("678", group.get(8));
    }
}