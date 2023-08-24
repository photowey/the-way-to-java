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
package com.photowey.bloom.filter.in.action.bloom.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code BloomFilterHolderTest}
 *
 * @author photowey
 * @date 2022/07/11
 * @since 1.0.0
 */
@SpringBootTest
class BloomFilterHolderTest {

    @Autowired
    private BloomFilterHolder bloomFilterHolder;

    @Autowired
    @Qualifier("bloomFilterHolderr")
    private BloomFilterHolder bloomFilterHolderr;

    @Test
    void testBloomFilter() {
        for (long i = 0; i < 100_000_000; i++) {
            this.bloomFilterHolder.put(i);
        }

        int count = 0;

        for (long i = 100_000_000; i < 100_000_000 * 2; i++) {
            if (this.bloomFilterHolder.mightContain(i)) {
                count++;
            }
        }

        System.out.println("count = " + count);
    }

    @Test
    void testBloomFilterV2() {
        for (long i = 0; i < 100_000_000; i++) {
            this.bloomFilterHolderr.put(i);
        }

        int count = 0;

        for (long i = 100_000_000; i < 100_000_000 * 2; i++) {
            if (this.bloomFilterHolderr.mightContain(i)) {
                count++;
            }
        }

        System.out.println("count = " + count);
    }
}