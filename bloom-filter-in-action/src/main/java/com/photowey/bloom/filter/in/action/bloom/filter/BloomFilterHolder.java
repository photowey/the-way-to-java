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
package com.photowey.bloom.filter.in.action.bloom.filter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * {@code BloomFilterHolder}
 *
 * @author photowey
 * @date 2022/07/11
 * @since 1.0.0
 */
public class BloomFilterHolder {

    private final BloomFilter<Long> bloomFilter;

    public BloomFilterHolder(long expectedInsertions, double fpp) {
        bloomFilter = BloomFilter.create(Funnels.longFunnel(), expectedInsertions, fpp);
    }


    public void put(Long value) {
        this.bloomFilter.put(value);
    }

    public boolean mightContain(Long value) {
        return this.bloomFilter.mightContain(value);
    }


}
